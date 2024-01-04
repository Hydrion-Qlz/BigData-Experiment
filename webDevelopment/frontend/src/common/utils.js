import {stringify} from "qs";
import CryptoJS from 'crypto-js'

const baseUrl = "http://localhost:8080/api"

function getQueryParam(url, type) {
    return stringify({
        url: url,
        type: type
    })
}

function getImgUrl(url, type = "segment") {
    return baseUrl + "/common/img?" + getQueryParam(url, type)
}

function encrypt(word) {
    const key = CryptoJS.enc.Utf8.parse("1234567890000000"); //16位
    const iv = CryptoJS.enc.Utf8.parse("1234567890000000");
    let encrypted = "";
    if (typeof word == "string") {
        const srcs = CryptoJS.enc.Utf8.parse(word);
        encrypted = CryptoJS.AES.encrypt(srcs, key, {
            iv: iv,
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7
        });
    } else if (typeof word == "object") {
        //对象格式的转成json字符串
        const data = JSON.stringify(word);
        const srcs = CryptoJS.enc.Utf8.parse(data);
        encrypted = CryptoJS.AES.encrypt(srcs, key, {
            iv: iv,
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7
        });
    }
    return encrypted.ciphertext.toString();
}


export {
    getImgUrl,
    getQueryParam,
    baseUrl,
    encrypt
}