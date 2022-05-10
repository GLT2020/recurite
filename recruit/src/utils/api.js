import axios from 'axios';
import { Message } from 'element-ui';
import router from '../router';

//请求拦截器
axios.interceptors.request.use(config => {
    //如果存在token,请求携带这个token
    if (window.sessionStorage.getItem('tokenStr')) {
        config.headers['Authorization'] = window.sessionStorage.getItem('tokenStr');

    }
    return config
}, error => {
    console.log(error);
})



//响应拦截器
axios.interceptors.response.use(success => {
    if (success.status && success.status == 200) {
        if (success.data.code === 500 || success.data.code === 401 || success.data.code === 403) {
            Message.error({ message: success.data.message });
            return;
        }
        if (success.data.message) {
            Message.success({ message: success.data.message });
        }
    }
    return success.data;
}, error => {
    //服务器问题
    if (error.response.code == 504 || error.response.code == 404) {
        Message.error({ message: "服务器出现故障，请稍后再试" });
    }
    //权限问题
    else if (error.response.code == 403) {
        Message.error({ message: "您的权限不足！" })
    }
    //没有登录
    else if (error.response.code == 401) {
        Message.error({ message: '尚未登陆，请先登录' });
        //跳转到登录页面
        router.replace('/');
    }
    else {
        if (error.response.data.message) {
            Message.error({ message: error.response.data.message });
        } else {
            Message.error({ message: "发现未知错误，请稍后再试" })
        }
    }
    return;
})

let base = '';

//传输json格式的post请求
export const postRequest = (url, params) => {
    console.log(params);
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: params
    })
}

//传输json格式的get请求
export const getRequest = (url, params) => {
    console.log(params);
    return axios({
        method: 'get',
        url: `${base}${url}`,
        data: params
    })
}

//传输json格式的put请求
export const putRequest = (url, params) => {
    console.log(params);
    return axios({
        // headers: {
        //     'Content-Type': 'application/json'
        // },
        method: 'put',
        url: `${base}${url}`,
        data: params
    })
}

//传输json格式的delete请求
export const deleteRequest = (url, params) => {
    console.log(params);
    return axios({
        method: 'delete',
        url: `${base}${url}`,
        data: params
    })
}

//传输json格式的delete请求
export const postFileRequest = (url, params) => {
    console.log(params);
    return axios({
        headers: { "Content-Type": "multipart/form-data" },
        method: 'delete',
        url: `${base}${url}`,
        data: params
    })
}





