<template>
  <Transition name="modal">
    <div v-if="show" class="modal-mask">
      <div class="modal-container">
        <div class="modal-header nav justify-content-center">
          <slot name="header">정말 회원탈퇴 하시겠습니까?</slot>
        </div>

        <div class="modal-body nav justify-content-center">
          <slot name="body"
            >회원탈퇴를 하기 위해 비밀번호를 다시 입력해주세요</slot
          ><br /><br />
          <input
            type="password"
            placeholder="비밀번호를 다시 입력해주세요"
            maxlength="20"
            class="text-center"
            v-model="chkPassword"
            style="width: 250px"
          />
        </div>

        <div class="modal-footer nav justify-content-center">
          <slot name="footer">
            <button
              class="btn btn-primary btn-default btn-sm mt-2"
              @click="$emit('close')"
            >
              돌아가기
            </button>
            <button
              class="btn btn-danger btn-default btn-sm mt-2"
              @click="onEmit"
            >
              회원탈퇴
            </button>
          </slot>
        </div>
      </div>
    </div>
  </Transition>
</template>

<script>
export default {
  props: {
    show: Boolean,
    userId: String,
  },
  data() {
    return {
      chkPassword: "",
    };
  },
  methods: {
    onEmit() {
      this.$emit("deleteUser", this.userId, this.chkPassword);
    },
  },
};
</script>

<style>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  transition: opacity 0.3s ease;
}

.modal-container {
  width: 500px;
  margin: auto;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
}

.modal-header h3 {
  margin-top: 0;
  color: #42b983;
}

.modal-body {
  margin: 20px 0;
}

.modal-default-button {
  float: right;
}

.modal-enter-from {
  opacity: 0;
}

.modal-leave-to {
  opacity: 0;
}

.modal-enter-from .modal-container,
.modal-leave-to .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
</style>
