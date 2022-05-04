import axios from 'axios'

const service = axios.create({
    baseURL: 'http://127.0.0.1:8383',
});

type Tsing = {
    app_id: string
    timestamp: string
    nonce_str: string
    signature: string
}

service.interceptors.response.use((resp) => {
    return resp.data;
});

export const sign = (): Promise<Tsing> => {
    return service.post('/sign')
}