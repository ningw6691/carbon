var UtilVar = {
    ENC: false, //返回结果是否加密
    baseUrl: process.env.VUE_APP_BASE_URL,
    code: 401,
}
const runtimeType = {

    production: () => {
        
    },
    yh: () => {

    },
    //开发环境
    development: () => {

    },

}
runtimeType[process.env.VUE_APP_URL_ENV]()
export default UtilVar

