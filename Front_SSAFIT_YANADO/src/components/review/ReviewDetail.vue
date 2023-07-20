<template>
  <div class="container" style="width: 500px">
    <h3 class="mt-5">댓글 수정하기</h3>
    <fieldset class="mt-5">
      <div class="mb-3">
        <label for="id" class="form-label">아이디</label>
        <input
          readonly
          type="text"
          id="id"
          v-model="review.userId"
          class="form-control"
        />
      </div>
      <div class="mb-3">
        <label for="content" class="form-label">내용</label>
        <textarea
          id="content"
          cols="10"
          rows="10"
          v-model="review.content"
          class="form-control"
        ></textarea>
      </div>
      <div class="d-flex justify-content-center mb-5 mt-3">
        <button
          class="btn btn-primary me-3 mr-2"
          style="width: 20%"
          @click="updateReview"
        >
          수정
        </button>
        <button class="btn btn-secondary" style="width: 20%" @click="goToList">
          취소
        </button>
      </div>
    </fieldset>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "ReviewDetail",
  computed: {
    ...mapState(["review"]),
  },
  created() {
    const pathName = new URL(document.location).pathname.split("/");
    const reviewId = pathName[pathName.length - 1];
    this.$store.dispatch("getReview", reviewId);
  },
  methods: {
    updateReview() {
      this.$store.dispatch("updateReview", this.review);
    },
    goToList() {
      this.$router.go(-1);
    },
  },
};
</script>

<style></style>
