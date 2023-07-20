<template>
  <div class="container">
    <b-form @submit.prevent="addComment">
      <b-form-textarea
        id="textarea"
        placeholder="Enter something..."
        v-model="commentText"
        rows="3"
        max-rows="6"
      ></b-form-textarea>
      <b-button
        variant="primary"
        pill
        type="submit"
        style="
          margin-top: 10px;
          margin-right: 10px;
          box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
        "
        >댓글 작성</b-button
      >
      <b-button
        pill
        type="reset"
        style="margin-top: 10px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2)"
        >취소</b-button
      >
    </b-form>
    <hr />
    <div>
      <div class="text-left">
        <p style="font-size: 16px; font-weight: bold">댓글 {{ reviewCount }}</p>
      </div>
      <hr />
      <ul class="list-unstyled" hover :items="reviews">
        <b-media
          tag="li"
          v-for="(review, index) in pageReviewList"
          :key="index"
        >
          <div style="display: flex; align-items: center">
            <b-icon
              icon="person-fill"
              style="width: 3em; height: 3em; margin-right: 10px"
            ></b-icon>
            <div style="flex-grow: 1; text-align: left">
              <h3 class="mt-0 mb-1" style="display: inline">
                {{ review.userId }}
              </h3>
              <p style="display: inline">{{ review.date }}</p>
              <p class="mb-0">
                {{ review.content }}
              </p>
            </div>
            <div
              style="text-align: right"
              v-if="isLoggined && review.userId === userId"
            >
              <b-button
                class="mr-1"
                @click="updateComment(review)"
                variant="outline-primary"
                >수정</b-button
              >
              <b-button
                @click="deleteComment(review.reviewId)"
                variant="outline-danger"
                >삭제</b-button
              >
            </div>
          </div>
          <hr />
        </b-media>
      </ul>
    </div>
    <b-pagination
      v-model="currentPage"
      :total-rows="reviewCount"
      :per-page="perPage"
      align="center"
      class="mb-4"
    ></b-pagination>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "ReviewList",
  props: {
    videoId: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      commentText: "",
      currentPage: 1,
      perPage: 5,
      isLoggined: false,
      userId: "",
    };
  },
  methods: {
    addComment() {
      if (this.commentText === "") {
        alert("댓글을 입력해주세요");
        return;
      }
      let newReview = {
        // userId를 현재 로그인한 user로 바꿔야 한다.
        userId: this.userId,
        videoId: this.$route.params.videoId,
        content: this.commentText,
      };
      this.$router.go(0);
      this.$store.dispatch("insertReview", newReview);
    },
    updateComment(review) {
      // 수정 버튼 클릭 시 ReviewDetail.vue로 이동
      this.$router.push({
        path: `/review/${review.videoId}/${review.reviewId}`,
      });
    },
    deleteComment(reviewId) {
      if (confirm("정말 삭제하시겠습니까?")) {
        this.$store.dispatch("deleteReview", reviewId);
      }
    },
    getUser() {
      if (this.loginUser) {
        return true;
      } else {
        return false;
      }
    },
  },
  computed: {
    ...mapState(["reviews"]),

    reviewCount() {
      return this.reviews.length;
    },
    pageReviewList() {
      return this.reviews.slice(
        (this.currentPage - 1) * this.perPage,
        this.currentPage * this.perPage
      );
    },
  },
  mounted() {
    this.$store.dispatch("getReviews", this.$route.params.videoId);
    const loginUser = sessionStorage.getItem("loginUser");
    const userId = sessionStorage.getItem("userId");
    this.userId = userId;

    if (loginUser) {
      this.isLoggined = true;
    } else {
      this.isLoggined = false;
    }
  },
};
</script>

<style></style>
