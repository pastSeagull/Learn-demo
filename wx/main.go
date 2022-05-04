package main

import (
	"crypto/sha1"
	"encoding/json"
	"fmt"
	"log"
	"math/rand"
	"net/http"
	"net/url"
	"strconv"
	"time"

	"github.com/asdine/storm"
	"github.com/parnurzeal/gorequest"
)

// Ticket 类型
type Ticket struct {
	Errcode   int    `json:"errcode,omitempty"`
	Errmsg    string `json:"errmsg,omitempty"`
	Ticket    string `json:"ticket,omitempty"`
	ExpiresIn int    `json:"expires_in,omitempty"`
}

// Token 类型
type Token struct {
	AccessToken string `json:"access_token,omitempty"`
	ExpiresIn   int    `json:"expires_in,omitempty"`
}

// Sign 签名类型
type Sign struct {
	AppID     string `json:"app_id,omitempty"`
	Timestamp string `json:"timestamp,omitempty"`
	NonceStr  string `json:"nonce_str,omitempty"`
	Signature string `json:"signature,omitempty"`
}

var (
	//微信公众号
	wxAppID     = "wx07fc804ac07d0cac"
	wxSecret    = "c3e69ffb6b7efa4f1280e67e012a7ede"
	port        = ":8383"
	letterRunes = []rune("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
)

func init() {
	rand.Seed(time.Now().UnixNano())
}

func main() {
	go func() {
		for {
			GetWeixin(wxAppID, wxSecret)
			time.Sleep(time.Duration(7200) * time.Second)
		}
	}()

	http.HandleFunc("/sign", signHandler)
	http.ListenAndServe(port, nil)
	log.Println("Server start at", port)

}

//signHandler 异步处理微信签名
func signHandler(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()
	w.Header().Set("Access-Control-Allow-Origin", "*") //允许访问所有域

	wxNoncestr := RandStringRunes(32)
	wxURL, _ := url.QueryUnescape(r.FormValue("url"))
	timestamp, signature := GetCanshu(wxNoncestr, wxURL)
	var u = Sign{
		AppID:     wxAppID,
		Timestamp: timestamp,
		NonceStr:  wxNoncestr,
		Signature: signature,
	}
	fmt.Println("get sign", u)
	w.Header().Add("Access-Control-Allow-Headers", "Content-Type") //header的类型
	w.Header().Set("Content-type", "application/json")             //返回数据格式是json
	b, err := json.Marshal(u)
	if err != nil {
		log.Println(err.Error())
	}
	w.Write(b)
}

//GetCanshu 微信签名算法
func GetCanshu(noncestr, url string) (timestamp, signature string) {
	db, err := storm.Open("db/weixin.db")
	if err != nil {
		log.Println("Database open err:", err.Error())
	}
	defer db.Close()

	defer func() { //异常处理
		if err := recover(); err != nil {
			time.Sleep(time.Duration(3) * time.Second)
		}
	}()
	var tc Ticket
	if e := db.Get("sessions", "ticket", &tc); e != nil {
		panic(e.Error())
	}

	timestamp = strconv.FormatInt(time.Now().Unix(), 10)
	longstr := "jsapi_ticket=" + tc.Ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url

	h := sha1.New()
	if _, e := h.Write([]byte(longstr)); e != nil {
		log.Println(e.Error())
	}

	signature = fmt.Sprintf("%x", h.Sum(nil))
	return
}

//GetWeixin 得到微信AccessToken和JSTicket
func GetWeixin(appid, secret string) {
	var tk Token
	var tc Ticket
	db, err := storm.Open("db/weixin.db")
	if err != nil {
		log.Println("Database open err:", err.Error())
	}
	defer db.Close()

	gorequest.New().Get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret).EndStruct(&tk)
	gorequest.New().Get("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + tk.AccessToken + "&type=jsapi").EndStruct(&tc)

	if e := db.Set("sessions", "token", &tk); e != nil {
		log.Println(e.Error())
	}
	if e := db.Set("sessions", "ticket", &tc); e != nil {
		log.Println(e.Error())
	}
}

//RandStringRunes 生成随机字符串
func RandStringRunes(n int) string {
	b := make([]rune, n)
	for i := range b {
		b[i] = letterRunes[rand.Intn(len(letterRunes))]
	}
	return string(b)
}
