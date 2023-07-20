<template>
  <div class="container text-center mt-5">
    <h3>영상 보기!</h3>
    <div v-if="video">
      <iframe
        width="560"
        height="315"
        :src="videoURL"
        frameborder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
        allowfullscreen
      ></iframe>
      <h4 v-if="video">
        {{ video.videoTitle }}
      </h4>
      <div class="nav justify-content-center">
        <button
          type="button"
          class="btn btn-outline-danger"
          @click="insertLike"
        >
          <b-icon
            width="16"
            height="16"
            fill="currentColor"
            v-bind:icon="like ? 'heart-fill' : 'heart'"
          />
          좋아요 {{ likeCnt }}
        </button>
      </div>
    </div>
    <hr />
    <review-list :videoId="videoId" />
  </div>
</template>

<script>
import { mapState } from "vuex";
import ReviewList from "../review/ReviewList.vue";
import axios from "axios";

export default {
  name: "VideoDetail",
  components: {
    ReviewList,
  },
  data() {
    return {
      videoId: this.$route.params.videoId,
    };
  },
  methods: {
    insertLike() {
      const set = {
        userId: sessionStorage.getItem("userId"),
        videoId: this.videoId,
      };
      this.$store.dispatch("insertLike", set);
    },
    insertPause() {},
  },
  computed: {
    ...mapState(["video"]),
    ...mapState(["likeCnt"]),
    ...mapState(["like"]),
    videoURL() {
      return `https://www.youtube.com/embed/${this.videoId}`;
    },
  },
  mounted() {
    this.$store.dispatch("getVideo", this.videoId);
    this.$store.dispatch("getLikeCnt", this.videoId);
    const set = {
      userId: sessionStorage.getItem("userId"),
      videoId: this.videoId,
    };
    this.$store.dispatch("getLike", set);
    this.$store.dispatch("getPause", set);
  },
  beforeCreate() {
    axios({
      url: `http://localhost:9999/api-video/${this.$route.params.videoId}`,
      method: "POST",
    }).then((res) => {
      // 이미 videoId가 있는 경우 저장하지 않음
      if (res.data === false) {
        let newVideo = {
          videoId: this.videoId,
          videoTitle: this.video.videoTitle,
          channelName: this.video.channelName,
        };
        console.log(newVideo);
        this.$store.dispatch("insertVideo", newVideo);
      }
    });
  },
};
</script>

<style></style>
