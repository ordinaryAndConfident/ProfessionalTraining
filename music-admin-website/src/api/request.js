import axios from 'axios';
import Qs from 'qs'

axios.defaults.timeout = 5000; // 超时时间5s
axios.defaults.withCredentials = true; // 允许跨域
// 响应头
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
// 基础 url
axios.defaults.baseURL = "http://localhost:9527/";
//axios.defaults.baseURL = "http://106.55.62.2:9527/";


//响应拦截器
axios.interceptors.response.use(
    response => {
        // 如果 response里面的status是200说明请求成功
        if (response.status == 200) {
            return Promise.resolve(response);
        } else {
            return Promise.reject(response);
        }
    },
    error => {
        if (error.response.status) {
            switch (error.response.code) {
                case 401: // 未登录
                    router.replace({
                        path: '/', // 返回根目录
                        query: {
                            redirect: router.currentthis.fullPath
                        }
                    });
                    break;
                case 404: // 没找到
                    break;
            }
            return Promise.reject(error.response);
        }
    }
);

// get请求
export function get(url, params = {}) {
    //异步访问
    return new Promise((resolve, reject) => {
        axios.get(url, { params: params }).then((res) => {
            resolve(res.data);
        }).catch((err) => {
            reject(err);
        })
    });
}

//post请求
export function post(url, data = {}, headers = {}) {
    //异步访问
    return new Promise((resolve, reject) => {
        data = data instanceof FormData ? data : Qs.stringify(data);
        axios.post(url, data, headers).then((res) => {
            resolve(res.data);
        }).catch((err) => {
            reject(err);
        })
    });
}

//put请求
export function put(url, data = {}) {
    //异步访问
    return new Promise((resolve, reject) => {
        axios.put(url, Qs.stringify(data)).then((res) => {
            resolve(res.data);
        }).catch((err) => {
            reject(err);
        })
    });
}