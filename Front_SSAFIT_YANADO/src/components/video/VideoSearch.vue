<template>
  <div class="container">
    <div>
      <div class="searchBar" style="margin-top: auto">
        <b-input-group style="width: 800px" class="mt-3">
          <b-form-input
            @keyup.enter="search"
            class="rounded-pill"
            type="text"
            v-model="keyword"
          ></b-form-input>
          <b-input-group-append>
            <b-button class="rounded-pill" variant="info" @click="search"
              >Button</b-button
            >
          </b-input-group-append>
        </b-input-group>
      </div>
    </div>
    <div>
      <h3>검색 결과</h3>
      <ul class="youtube-list">
        <video-item
          v-for="video in videos"
          :key="video.id.videoId"
          :video="video"
        ></video-item>
      </ul>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import VideoItem from "./VideoItem.vue";

export default {
  components: { VideoItem },
  computed: {
    ...mapState(["videos"]),
  },
  name: "VideoSearch",
  data() {
    return {
      keyword: "",
    };
  },

  methods: {
    search() {
      if (this.keyword.length === 0) {
        alert("검색어를 입력해주세요");
      } else {
        this.$store.dispatch("searchYoutube", this.keyword.concat(" ", "운동"));
      }
    },
  },
  created() {
    if (!this.$route.params.keyword) {
      this.$store.dispatch("searchYoutube", "운동");
    } else {
      this.$store.dispatch(
        "searchYoutube",
        this.$route.params.keyword.concat(" ", "운동")
      );
    }
  },
};
</script>

<style></style>
