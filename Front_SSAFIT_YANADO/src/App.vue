<template>
  <div id="app">
    <header-nav></header-nav>
    <router-view />
    <footer-nav></footer-nav>
  </div>
</template>
<!-- class="text-white" -->
<script>
import HeaderNav from "@/components/common/HeaderNav.vue";
import FooterNav from "@/components/common/FooterNav.vue";
export default {
  components: {
    HeaderNav,
    FooterNav,
  },
};
</script>

<style>
@font-face {
  font-family: "hanna_air";
  src: url("../public/fonts/BMHANNAAir_ttf.ttf");
}
@font-face {
  font-family: "hanna_pro";
  src: url("../public/fonts/BMHANNAPro.ttf");
}
@font-face {
  font-family: "hanna_11";
  src: url("../public/fonts/BMHANNA_11yrs_ttf.ttf");
}
html,
body {
  height: 100%;
  margin: 0;
  padding: 0;
}

#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}
h1,
h2,
h3,
h4,
h5 {
  font-family: "hanna_11";
}
p {
  font-family: "hanna_air";
}
footer-nav {
  margin-top: auto;
}
</style>
