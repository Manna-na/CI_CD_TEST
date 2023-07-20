<template>
  <b-navbar sticky toggleable="lg" variant="Dark">
    <b-navbar-brand href="/"><b>SSAFIT</b></b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <!-- <b-nav-item>
          <b-icon
            @click="navigateToWorkoutLocation"
            icon="geo-alt-fill"
          ></b-icon>
        </b-nav-item> -->
        <b-nav-item>
          <b-icon @click="navigateToDiary" icon="calendar-check-fill"></b-icon>
        </b-nav-item>
        <b-nav-item-dropdown right v-if="isLoggined">
          <!-- Using 'button-content' slot -->
          <template #button-content>
            <em><b-icon icon="person-circle"></b-icon></em>
          </template>
          <b-dropdown-item @click="navigateToUser">
            <b-icon icon="person"></b-icon> Profile</b-dropdown-item
          >
          <b-dropdown-item @click="signOut">
            <b-icon icon="power" aria-hidden="true"></b-icon> Log Out
          </b-dropdown-item>
        </b-nav-item-dropdown>

        <b-nav-item-dropdown right v-else>
          <!-- Using 'button-content' slot -->
          <template #button-content>
            <em><b-icon icon="person-circle"></b-icon></em>
          </template>
          <b-dropdown-item @click="navigateToUserRegist"
            ><b-icon icon="people"></b-icon> Sign Up</b-dropdown-item
          >
          <b-dropdown-item @click="navigateToUserLogin"
            ><b-icon icon="key"></b-icon> Log In</b-dropdown-item
          >
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<script>
export default {
  name: "HeaderNav",
  data() {
    return {
      isLoggined: false,
    };
  },
  methods: {
    navigateToWorkoutLocation() {
      const currentPath = this.$route.path;
      const targetPath = "/workout-location";

      if (currentPath !== targetPath) {
        this.$router.replace(targetPath);
      }
    },
    navigateToDiary() {
      if (this.isLoggined) {
        const currentPath = this.$route.path;
        const targetPath = "/diary";

        if (currentPath !== targetPath) {
          this.$router.replace(targetPath);
        }
      } else {
        alert("로그인이 필요합니다.");
      }
    },
    navigateToUser() {
      const currentPath = this.$route.path;
      const targetPath = "/myPage";

      if (currentPath !== targetPath) {
        this.$router.replace(targetPath);
      }
    },
    navigateToUserLogin() {
      const currentPath = this.$route.path;
      const targetPath = "/login";

      if (currentPath !== targetPath) {
        this.$router.replace(targetPath);
      }
    },
    navigateToUserRegist() {
      const currentPath = this.$route.path;
      const targetPath = "/regist";

      if (currentPath !== targetPath) {
        this.$router.replace(targetPath);
      }
    },
    signOut() {
      // 로그아웃 처리 및 필요한 작업 수행
      // 세션 로그아웃 등
      // 로그아웃 후 리다이렉트 등 필요한 동작 수행
      sessionStorage.removeItem("loginUser");
      sessionStorage.removeItem("userId");
      const currentPath = this.$route.path;
      const targetPath = "/";

      if (currentPath !== targetPath) {
        this.$router.replace(targetPath);
      }
      window.location.reload();
    },
  },
  created() {
    const loginUser = sessionStorage.getItem("loginUser");
    if (loginUser) {
      this.isLoggined = true;
    } else {
      this.isLoggined = false;
    }
  },
};
</script>

<style>
nav {
  padding: 30px;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2);
  background-color: white;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: white;
}
</style>
