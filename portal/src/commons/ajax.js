import { getToken, Ajax, createAjaxHoc, createAjaxHooks } from '@ra-lib/admin';
import { Modal } from 'antd';
import { AJAX_PREFIX, AJAX_TIMEOUT } from 'src/config';
import handleError from './handle-error';
import handleSuccess from './handle-success';

// 创建Ajax实例，设置默认值
const ajax = new Ajax({
    baseURL: AJAX_PREFIX,
    timeout: AJAX_TIMEOUT,
    onError: handleError,
    onSuccess: handleSuccess,
    // withCredentials: true, // 跨域携带cookie，对应后端 Access-Control-Allow-Origin不可以为 '*'，需要指定为具体域名
});

// 请求拦截
ajax.instance.interceptors.request.use(
    (cfg) => {
        if (!cfg.headers) cfg.headers = {};
        // 这里每次请求都会动态获取，放到创建实例中，只加载一次，有时候会出问题。
        cfg.headers['auth-token'] = getToken();
        return cfg;
    },
    (error) => {
        // Do something with request error
        return Promise.reject(error);
    },
);

// 响应拦截
ajax.instance.interceptors.response.use(
    (res) => {
        // Do something before response
        if (!res.data.success) {
            Modal.error({
                title: "请求失败！",
                content : res.message == null ? "您的请求无法完成，请稍后再试" : res.message
            })
        }
        return res.data;
    },
    (error) => {
        // Do something with response error
        return Promise.reject(error);
    },
);

const hooks = createAjaxHooks(ajax);
const hoc = createAjaxHoc(ajax);

export default ajax;

export const ajaxHoc = hoc;

export const get = ajax.get;
export const post = ajax.post;
export const put = ajax.put;
export const del = ajax.del;
export const patch = ajax.patch;
export const download = ajax.download;

export const useGet = hooks.useGet;
export const usePost = hooks.usePost;
export const usePut = hooks.usePut;
export const useDel = hooks.useDel;
export const usePatch = hooks.usePatch;
export const useDownload = hooks.useDownload;
