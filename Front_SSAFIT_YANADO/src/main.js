import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import VueCarousel from "vue-carousel";
import { VBHover } from "bootstrap-vue";
import VueSweetalert2 from "vue-sweetalert2";

// Note: Vue automatically prefixes the directive name with 'v-'
Vue.directive("b-hover", VBHover);
// Import Bootstrap and BootstrapVue CSS files (order is important)
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

// carousel 해줘...
// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue);
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin);

// Vue.use(SplitCarousel);
Vue.use(VueCarousel);

Vue.use(VueSweetalert2);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
