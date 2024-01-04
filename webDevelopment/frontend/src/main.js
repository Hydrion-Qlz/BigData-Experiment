import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import 'ant-design-vue/dist/antd.css';
import Antd from 'ant-design-vue';
import axios from 'axios'
import {baseUrl} from "@/common/utils";
import "@/common/scrollBar.css"
import "@/common/init.css"

const app = createApp(App).use(store).use(router).use(Antd)
app.config.globalProperties.baseUrl = baseUrl

const instance = axios.create({
    baseURL: app.config.globalProperties.baseUrl,
    timeout: 60000
})


app.config.globalProperties.$get = function (url, params = {}, config = {}) {
    return instance.get(url, {params, ...config})
}

app.config.globalProperties.$post = function (url, data = {}) {
    return instance.post(url, data)
}


app.mount("#app")