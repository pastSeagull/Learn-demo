import {sign} from "./axios";
import { useEffect } from 'react'
import wx from 'weixin-js-sdk'


export const App = () => {
  const scanQRCode = () => {
    sign().then(res => {

      wx.config({
        debug: true,
        appId: res.app_id,
        timestamp: res.timestamp,
        nonceStr: res.nonce_str,
        signature: res.signature,
        jsApiList: [
          'scanQRCode'
        ]
      })
      // 成功or失败回调
      wx.ready(() => {
        console.log("ok")

        wx.scanQRCode({
          needResult: 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
          scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
          success: function (res: any) {
            let result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
          }
        });

      })
      wx.error((err: Error) => {
        console.log("err", err)
      })
    })
  }

  return <div>
    <button onClick={scanQRCode}>扫一扫</button>
  </div>
}