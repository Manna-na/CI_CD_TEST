<template>
  <section class="text-center">
    <div class="container" style="width: 530px">
      <div
        class="d-flex justify-content-center align-items-center"
        style="height: 80vh"
      >
        <b-container
          fluid
          class="shadow"
          style="
            padding-top: 30px;
            padding-bottom: 30px;
            padding-right: 30px;
            padding-left: 30px;
          "
        >
          <form>
            <b-input-group class="mb-2">
              <b-form-input
                type="text"
                id="id"
                v-model="userId"
                placeholder="아이디"
              ></b-form-input>
            </b-input-group>
            <b-input-group class="mb-2">
              <b-form-input
                type="password"
                id="password"
                v-model="password"
                placeholder="비번"
              ></b-form-input>
            </b-input-group>
            <div class="d-flex justify-content-center" style="margin-top: 20px">
              <b-button class="btn btn-primary btn-block mb-4" @click="login"
                >로그인</b-button
              >
            </div>
          </form>
        </b-container>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: "UserLogin",
  data() {
    return {
      userId: "",
      password: "",
    };
  },
  methods: {
    login() {
      let user = {
        userId: this.userId,
        password: this.password,
      };
      this.$store.dispatch("userLogin", user);
    },
  },
};
</script>

<style></style>
