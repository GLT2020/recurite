import axios from "axios";
const service = axios.create({
    responseType: 'arraybuffer'
})

//请求拦截器
service.interceptors.request.use(config => {
    //如果存在token,请求携带这个token
    config.headers['Authorization'] = window.sessionStorage.getItem('tokenStr');
    return config;
}, error => {
    console.log(error);
})

service.interceptors.response.use(resp => {
    console.log(resp.headers);
    const headers = resp.headers;
    // 是否为json格式的正则表达式
    let reg = RegExp(/application\/json/);
    // 如果返回的格式为json，则将其转化为流
    if (headers['content-type'].match(reg)) {
        console.log("这里是流的JSON回复=》》》》》");
        resp.data = unitToString(resp.data);
    } else {

        let fileDownload = require('js-file-download');
        let fileName = headers['content-disposition'].split(';')[1].split('filename=')[1];
        let contentType = headers['content-type'];
        // 解码防止中文乱码
        fileName = decodeURIComponent(fileName);
        fileDownload(resp.data, fileName, contentType);
    }
}, error => {
    console.log(error);
})


function unitToString(unitArray) {
    let encodeString = String.fromCharCode.apply(null, new Uint8Array(unitArray));
    let decodedString = decodeURIComponent(escape(encodeString));
    return JSON.parse(decodedString);
}


let base = '';
export const downloadRequest = (url, params) => {
    return service({
        method: 'get',
        url: `${base}${url}`,
        data: params
    })
}

export default service;