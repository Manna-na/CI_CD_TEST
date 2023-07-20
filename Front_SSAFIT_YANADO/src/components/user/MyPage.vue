<template>
  <div>
    <form
      class="container shadow-lg"
      style="
        width: 800px;
        padding-left: 50px;
        padding-right: 50px;
        padding-top: 10px;
        margin-top: 50px;
        margin-bottom: 50px;
      "
    >
      <div class="text-center mt-5" style="font-weight: 500; margin-top: 50px">
        <div>
          <b-icon icon="person-circle" font-scale="2"></b-icon>
        </div>
        <h4 class="text-center mb-0 mr-1">Mypage</h4>
      </div>
      <hr />
      <!-- 2 column grid layout with text inputs for the first and last names -->
      <div class="row mb-4">
        <div class="col">
          <div class="text-center form-outline">
            <p style="font-size: 20px">id는 :</p>
          </div>
        </div>
        <div class="col">
          <div class="text-center form-outline">
            <p style="font-size: 20px">{{ user.userId }}</p>
            <!-- <span class="form-label" for="form6Example2">Last name</span> -->
            <!-- <input type="text" id="form6Example2" class="form-control" style="width:300px"> -->
          </div>
        </div>
      </div>

      <!-- Text input -->
      <div class="row mb-4">
        <div class="col">
          <div class="text-center form-outline">
            <p style="font-size: 20px">이름은</p>
          </div>
        </div>
        <div class="col">
          <div class="text-center form-outline">
            <p style="font-size: 20px">{{ user.name }}</p>
          </div>
        </div>
      </div>
      <!-- Text input -->
      <b-input-group>
        <b-input-group-prepend is-text>
          <b-icon icon="envelope"></b-icon>
        </b-input-group-prepend>
        <b-form-input
          type="email"
          id="form6Example4"
          readonly
          :value="user.email"
          class="form-control"
        ></b-form-input>
      </b-input-group>
      <div class="form-outline mb-4">
        <!-- <input
          type="text"
          id="form6Example4"
          readonly
          :value="user.email"
          class="form-control"
        /> -->
      </div>

      <!-- Email input -->
      <div class="form-outline mb-4">
        <p style="font-weight: bolder; font-size: larger; margin-bottom: 5px">
          비밀번호 변경
        </p>
        <label class="form-label" for="form6Example5"
          >현재 비밀번호를 입력해주세요</label
        >
        <input type="text" id="form6Example5" class="form-control" />
      </div>

      <!-- Number input -->
      <div class="form-outline mb-4">
        <label class="form-label" for="form6Example6"
          >바꾸실 비밀번호를 입력해주세요</label
        >
        <input type="text" id="form6Example6" class="form-control" />
      </div>

      <!-- Number input -->
      <div class="form-outline mb-4">
        <label class="form-label" for="form6Example6"
          >다시 한번 바꾸실 비밀번호를 입력해주세요</label
        >
        <input type="text" id="form6Example6" class="form-control" />
      </div>

      <!-- Submit button -->
      <b-row class="mb-2 mt-2">
        <b-col>
          <button class="btn btn-primary btn-default btn-sm mt-2 w-100 mb-5">
            비밀번호 수정</button
          ><br />
        </b-col>
      </b-row>
    </form>
    <b-col>
      <div class="text-center">
        <button
          class="btn btn-danger btn-default btn-sm mt-1 w-10 mb-5"
          @click="showModal = true"
          style="margin-bottom;: 100px"
        >
          회원 탈퇴
        </button>
      </div>
    </b-col>
    <Modal
      :show="showModal"
      :userId="user.userId"
      @close="showModal = false"
      @deleteUser="deleteUser"
    >
    </Modal>
  </div>
</template>

<script>
import axios from "axios";
import Modal from "../deleteUserModal.vue";
export default {
  name: "MyPage",
  components: {
    Modal,
  },
  data() {
    return {
      user: {
        userId: "",
        password: "",
        email: "",
        name: "",
      },
      inputPassword: "",
      chkPassword: "",
      showModal: false,
    };
  },
  created() {
    let loginUser = sessionStorage.getItem("loginUser");
    let userId = sessionStorage.getItem("userId");
    const API_URL = `http://localhost:9999/api-user/${userId}`;
    axios({
      url: API_URL,
      method: "POST",
      data: loginUser,
    }).then((res) => {
      this.user = res.data;
    });
  },
  methods: {
    userModify() {},
    deleteUser(userId, chkPassword) {
      const set = {
        userId: userId,
        chkPassword: chkPassword,
      };
      this.$store.dispatch("deleteUser", set);
    },
  },
};
</script>
<style></style>
