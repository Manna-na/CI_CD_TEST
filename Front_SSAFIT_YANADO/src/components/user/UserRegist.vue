<template>
  <div class="container mb-5 mt-4 shadow">
    <h2 class="mt-3">회원 가입</h2>
    <fieldset class="mt-5">
      <div class="mb-3">
        <label for="userId" class="form-label">아이디</label>
        <input
          type="text"
          id="userId"
          v-model="user.userId"
          class="form-control"
          maxlength="20"
        />
        <div v-if="!idValid && user.userId" class="text-danger">
          아이디를 정확히 입력해주세요.
        </div>
      </div>
      <div class="mb-3">
        <label for="password" class="form-label">비밀번호</label>
        <input
          type="password"
          id="password"
          v-model="user.password"
          class="form-control"
          maxlength="16"
          @blur="passwordValid"
        />
        <div v-if="!passwordValidFlag" class="text-danger">
          비밀번호를 정확히 입력해주세요.
        </div>
      </div>
      <div class="mb-3">
        <label for="passwordChk" class="form-label">비밀번호 확인</label>
        <input
          type="password"
          id="passwordChk"
          v-model="passwordChk"
          class="form-control"
          maxlength="16"
          @blur="passwordChkValid"
        />
        <div v-if="!passwordChkFlag" class="text-danger">
          비밀번호와 일치하지 않습니다.
        </div>
      </div>
      <div class="mb-3">
        <label for="name" class="form-label">이름</label>
        <input
          type="text"
          id="name"
          v-model="user.name"
          class="form-control"
          maxlength="10"
        />
      </div>
      <div class="mb-3">
        <label for="email" class="form-label">이메일</label>
        <div class="input-group">
          <input
            type="email"
            id="email"
            v-model="user.email"
            class="form-control"
          />
          <button
            v-show="emailValid"
            class="btn btn-outline-secondary"
            @click="emailChk"
          >
            {{ !this.chkCode ? "이메일 인증" : "인증번호 재전송" }}
          </button>
        </div>
        <div v-if="!emailValid" class="text-danger">
          이메일을 정확히 입력해주세요.
        </div>
        <input
          v-show="emailValid && this.chkCode"
          type="text"
          id="inputChkCode"
          v-model="inputChkCode"
          class="form-control mt-2"
        />
        <button
          v-show="emailValid && this.chkCode"
          class="btn btn-primary mt-2"
          @click="checkCode"
        >
          인증하기
        </button>
      </div>
      {{ this.chkCode }}
      {{ this.inputChkCode }}
      <div
        v-if="
          idValid &&
          passwordValidFlag &&
          passwordChkFlag &&
          emailValid &&
          user.name !== ''
        "
      >
        <button class="btn btn-primary" @click="regist">등록</button>
      </div>
    </fieldset>
  </div>
</template>

<script>
export default {
  name: "UserRegist",
  data() {
    return {
      user: {
        userId: "",
        password: "",
        name: "",
        email: "",
      },
      passwordChk: "",
      passwordValidFlag: true,
      passwordChkFlag: true,
      chkCode: "",
      chkEmail: "",
      inputChkCode: "",
      inputChkCodeFlag: false,
    };
  },
  methods: {
    regist() {
      if (
        this.user.userId === "" ||
        this.user.password === "" ||
        this.user.name === "" ||
        this.user.email === ""
      ) {
        alert("모든 내용을 입력해주세요");
        return;
      }
      if (this.passwordChk != this.user.password) {
        alert("비밀번호 확인을 진행해주세요");
        return;
      }
      this.$store.dispatch("createUser", this.user);
    },
    passwordChkValid() {
      if (this.user.password === this.passwordChk) {
        this.passwordChkFlag = true;
      } else {
        this.passwordChkFlag = false;
      }
    },
    passwordValid() {
      if (
        /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,16}$/.test(
          this.user.password
        )
      ) {
        this.passwordValidFlag = true;
      } else {
        this.passwordValidFlag = false;
      }
    },
    emailChk() {
      this.$store.dispatch("emailChk", this.user.email).then((res) => {
        console.log(res);
      });
      this.chkEmail = this.user.email;
    },
    checkCode() {
      if (this.inputChkCode === this.chkCode) {
        alert("인증이 완료되었습니다.");
      } else {
        alert("올바르지 않은 코드입니다!");
      }
    },
  },
  computed: {
    idValid() {
      return /^[a-z]+[a-z0-9]{4,19}$/.test(this.user.userId);
    },
    emailValid() {
      return /^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\w+\.)+\w+$/.test(this.user.email);
    },
  },
};
</script>
