import axios from 'axios'
import router from "@/router";

const request = axios.create({
    baseURL: '',  // 注意！！ 这里是全局统一加上了 '/api' 前缀，也就是说所有接口都会加上'/api'前缀在，页面里面写接口的时候就不要加 '/api'了，否则会出现2个'/api'，类似 '/api/api/user'这样的报错，切记！！！
    timeout: 5000
})

request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    //config.headers['token'] = user.token;  // 设置请求头
    //取出sessionStorage里面缓存的用户信息
    const userJson =sessionStorage.getItem("user")
    // console.log('userJson1')
    // // console.log(userJson.)
    // console.log(userJson)
    if(!userJson){
        console.log('userJsonnull')
        // console.log(userJson)
        // router.push("/login")
    }

    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        return res;
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)
export default request