import Vue from 'vue'
import App from './App'

import classification from './pages/classification/classification.vue'
Vue.component('classification',classification)

import homepage from './pages/homepage/homepage.vue'
Vue.component('homepage',homepage)

import message from './pages/message/message.vue'
Vue.component('message',message)

import personalcenter from './pages/personalcenter/personalcenter.vue'
Vue.component('personalcenter',personalcenter)

import cuCustom from './colorui/components/cu-custom.vue'
Vue.component('cu-custom',cuCustom)

Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
