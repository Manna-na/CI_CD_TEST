<template>
  <div class="container">
    <section class="new-index">
      <div class="index">
        <h1>Get some exercise</h1>
        <div class="searchBar">
          <b-input-group style="width: 800px" class="mt-3">
            <b-form-input
              @keyup.enter="search"
              v-model="keyword"
              class="rounded-pill"
            ></b-form-input>
            <b-input-group-append>
              <b-button @click="search" class="rounded-pill" variant="info"
                >Search</b-button
              >
            </b-input-group-append>
          </b-input-group>
        </div>
      </div>
    </section>
    <div class="text-center mt-5">
      <div class="d-flex align-items-center justify-content-center">
        <h4 class="text-center mb-0 mr-1">좋아요 순</h4>
        <b-img
          :src="require('@/assets/heart.png')"
          width="25px"
          height="30px"
          style="padding-bottom: 5px"
        ></b-img>
      </div>
    </div>
    <b-row>
      <b-col cols="12">
        <carousel
          :perPage="3"
          :autoplay="true"
          :autoplay-timeout="3000"
          :navigationEnabled="true"
          class="p-2"
          :loop="true"
        >
          <slide
            class="p-2"
            v-for="(video, index) in dbLikeVideos"
            :key="index"
          >
            <b-card
              class="shadow-sm"
              :img-src="`https://img.youtube.com/vi/${video.videoId}/mqdefault.jpg`"
              img-alt="Image"
              tag="article"
              style="width: 20rem; height: 27rem; cursor: pointer"
              @mouseover="zoomInCard"
              @mouseout="zoomOutCard"
              @click="moveToDetail(video.videoId)"
              ref="cards"
            >
              <b-card-text
                class="custom-card-title text-center align-content-center d-flex align-items-center justify-content-center"
                style="height: 150px"
              >
                {{ video.videoTitle }}
              </b-card-text>
              <hr />
              <div class="d-flex align-items-center">
                <b-card-text style="width: 210px; margin-bottom: 0px">
                  <b-badge variant="info">{{ video.channelName }}</b-badge>
                </b-card-text>
                <div style="width: 50%">
                  <span style="color: red">
                    {{ likeVideos.includes(video.videoId) ? "♥" : "♡" }}
                  </span>
                  {{ video.likeCnt }}
                  <b-icon width="16" height="16" icon="eye" />
                  {{ video.viewCnt }}
                  <b-icon width="16" height="16" icon="chat-right-dots" />
                  {{ video.commentCnt }}
                </div>
              </div>
            </b-card>
          </slide>
        </carousel>
      </b-col>
    </b-row>
    <div class="mb-5">
      <div class="text-center mt-5">
        <div class="d-flex align-items-center justify-content-center">
          <h4 class="text-center mb-0 mr-1">조회수 많은 순</h4>
          <b-img
            :src="require('@/assets/view.png')"
            width="25px"
            height="35px"
          ></b-img>
        </div>
      </div>
      <b-row class>
        <b-col cols="12">
          <carousel
            :perPage="3"
            :autoplay="true"
            :autoplay-timeout="3000"
            :navigationEnabled="true"
            class="p-2"
            :loop="true"
          >
            <slide
              class="p-2"
              v-for="(video, index) in dbViewVideos"
              :key="index"
            >
              <b-card
                class="shadow-sm"
                :img-src="`https://img.youtube.com/vi/${video.videoId}/mqdefault.jpg`"
                img-alt="Image"
                tag="article"
                style="width: 20rem; height: 27rem; cursor: pointer"
                @mouseover="zoomInCard"
                @mouseout="zoomOutCard"
                @click="moveToDetail(video.videoId)"
                ref="cards"
              >
                <b-card-text
                  class="custom-card-title text-center align-content-center d-flex align-items-center justify-content-center"
                  style="height: 150px"
                >
                  {{ video.videoTitle }}
                </b-card-text>
                <hr />
                <div class="d-flex align-items-center">
                  <b-card-text style="width: 210px; margin-bottom: 0px">
                    <b-badge variant="info">{{ video.channelName }}</b-badge>
                  </b-card-text>
                  <div style="width: 50%">
                    <span style="color: red">
                      {{ likeVideos.includes(video.videoId) ? "♥" : "♡" }}
                    </span>
                    {{ video.likeCnt }}
                    <b-icon width="16" height="16" icon="eye" />
                    {{ video.viewCnt }}
                    <b-icon width="16" height="16" icon="chat-right-dots" />
                    {{ video.commentCnt }}
                  </div>
                </div>
              </b-card>
            </slide>
          </carousel>
        </b-col>
      </b-row>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "VideoList",
  data() {
    return {
      // mode: 1,
      keyword: "",
    };
  },
  computed: {
    ...mapState(["dbLikeVideos"]),
    ...mapState(["dbViewVideos"]),
    ...mapState(["likeVideos"]),
    videoCount() {
      return this.getDbLikeVideos.length;
    },
  },

  created() {
    this.$store.dispatch("getDbLikeVideos");
    this.$store.dispatch("getDbViewVideos");
    if (sessionStorage.getItem("userId") != null) {
      this.$store.dispatch("getLikeVideos", sessionStorage.getItem("userId"));
    }
  },
  methods: {
    search() {
      this.$router.push({
        name: "videoSearch",
        params: { keyword: this.keyword.concat(" ", "운동") },
      });
    },
    moveToDetail(vidoeId) {
      this.$router.push(`/search/${vidoeId}`);
    },
    zoomInCard(event) {
      const card = event.currentTarget;
      card.style.transform = "scale(1.03)";
    },
    zoomOutCard(event) {
      const card = event.currentTarget;
      card.style.transform = "scale(1)";
    },
  },
};
</script>

<style scoped>
.custom-card-title {
  font-size: 1.3rem;
  font-family: "hanna_11";
}
.new-index {
  display: flex;
}

.index {
  background-image: linear-gradient(
      rgba(255, 255, 255, 0.5),
      rgba(255, 255, 255, 0.5)
    ),
    url("@/assets/요가사진.jpg");
  background-size: cover;
  width: 100%;
  height: 360px;
}

.searchBar {
  margin-top: 200px;
  display: flex;
  justify-content: center;
}

h1 {
  position: absolute;
  color: #000000;
  font-size: 3rem;
  line-height: 0.9;
  text-align: center;
  padding-top: 150px;
  padding-left: 150px;
}

.text-front {
  padding-top: 30px;
}
</style>
