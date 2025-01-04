
import axios from 'axios';
import UtilVar from "@/config/UtilVar";
import { Notification, MessageBox, Message } from 'element-ui'
import router from '@/router'

let baseUrl = UtilVar.baseUrl
const CancelToken = axios.CancelToken;
export { baseUrl };
// axios.defaults.withCredentials = true;
// 添加请求拦截器
axios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么 传token
    let token = localStorage.getItem("token");
    config.headers.common['Content-Type'] = "application/json;charset=utf-8";
    config.headers.common['token'] = token;  //Authorization
    return config;
}, function (error) {
    // 对请求错误做些什么
    console.log(error)
    return Promise.reject(error);
});
/**
 * @响应拦截
 */
axios.interceptors.response.use(res => {
    // 未设置状态码则默认成功状态
    const code = res.data.code || 200;
    // 获取错误信息
    const msg = res.data.msg;
    if (code === 500) {
        Message({
            dangerouslyUseHTMLString: true,
            message: msg,
            type: 'error'
        })
        return Promise.reject(new Error(msg))
    } else if (code !== 200) {
        Notification.error({
            title: msg
        })
        return Promise.reject('error')
    } else {
        res.data.success = true;
        return res.data
    }
}, error => {
    console.error('err' + error)
    let { message } = error;
    if (message == "Network Error") {
        message = "后端接口连接异常";
    }
    else if (message.includes("timeout")) {
        message = "系统接口请求超时";
    }
    else if (message.includes("Request failed with status code")) {
        message = "系统接口" + message.substr(message.length - 3) + "异常";
    }
    Message({
        message: message,
        type: 'error',
        duration: 5 * 1000
    })
    return Promise.reject(error)
})

let configs_ENC = {
    headers: { 'enc': UtilVar.ENC }
}
//处理是否加密数据
let isEncryptionParam = (params) => {
    return params

}
export const GET = async (url, params) => {
    try {
        params = isEncryptionParam(params)
        const data = await axios.get(`${baseUrl}${url}`, {
            params: params,
            headers: configs_ENC.headers
        }, configs_ENC);
        return data;
    } catch (error) {
        return error;
    }
}
//没有基地址 访问根目录下文件

export const GETNOBASE = async (url, params) => {
    try {
        const data = await axios.get(url, {
            params: params,
        });
        return data;
    } catch (error) {
        return error;
    }
}
export const POST = async (url, params) => {
    try {
        params = isEncryptionParam(params)
        const data = await axios.post(`${baseUrl}${url}`, params, configs_ENC);
        return data;
    } catch (error) {
        return error;
    }
}
export const PUT = async (url, params) => {
    try {
        params = isEncryptionParam(params)
        const data = await axios.put(`${baseUrl}${url}`, params, configs_ENC);
        return data;
    } catch (error) {
        return error;
    }
}
export const DELETE = async (url, params) => {
    try {
        params = isEncryptionParam(params)
        const data = await axios.delete(`${baseUrl}${url}`, { data: params, headers: configs_ENC.headers }, configs_ENC);
        return data;
    } catch (error) {
        return error;
    }
}


/**
 * @文件类型提交方法
 */
let configs = {
    headers: { 'Content-Type': 'multipart/form-data' },

}
export const FILESubmit = async (url, params, config) => {
    try {
        const data = await axios.post(`${baseUrl}${url}`, params, {
            ...configs,
            cancelToken: new CancelToken(function executor(c) {
                config.setCancel && config.setCancel(c)
            }),
            onUploadProgress: (e) => {
                if (e.total > 0) {
                    e.percent = e.loaded / e.total * 100;
                }
                // console.log(config)
                config.onProgress && config.onProgress(e)
            },

        });
        return data;
    } catch (err) {
        return err;
    }
}

/**
 * 下载文档流
 * @param {config.responseType} 下载文件流根据后端 配置   arraybuffer || blod
 */
export const FILE = async (config = {}, body, params) => {
    try {
        const data = await axios({
            method: config.method || 'get',
            url: `${baseUrl}${config.url}`,
            data: body,
            params: params,
            responseType: config.responseType || 'blob',
            onDownloadProgress: (e) => {
                // console.log(e,e.currentTarget)
                // if (e.currentTarget.response.size > 0) {
                //     e.percent = e.loaded / e.currentTarget.response.size * 100;
                // }
                // event.srcElement.getResponseHeader('content-length')
                config.onProgress && config.onProgress(e)
            },
        });
        return data;
    } catch (err) {
        return err;
    }
}


