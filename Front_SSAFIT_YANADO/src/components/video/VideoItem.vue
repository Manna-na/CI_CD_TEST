<template>
  <div class="container">
    <b-card no-body class="overflow-hidden" style="max-width: 100%">
      <b-row no-gutters>
        <b-col md="4" class="text-center">
          <b-card-img
            :src="`https://img.youtube.com/vi/${video.id.videoId}/mqdefault.jpg`"
            alt="Image"
            class="rounded-0"
            style="max-height: 100%; max-width: 100%"
          ></b-card-img>
        </b-col>
        <b-col md="8">
          <b-card-body>
            <router-link
              :to="{
                path: `/search/${video.id.videoId}`,
                params: { videoId: video.id.videoId },
              }"
              >{{ video.snippet.title }}
            </router-link>
            <b-card-text>
              {{ video.snippet.channelTitle }}<br />
              {{ video.snippet.description }}
            </b-card-text>
          </b-card-body>
        </b-col>
      </b-row>
    </b-card>
  </div>
</template>

<script>
export default {
  name: "VideoItem",
  props: {
    video: {
      type: Object,
      required: true,
    },
  },
  mounted() {
    let tempVideo = {
      videoId: this.video.id.videoId,
      videoTitle: this.video.snippet.title,
      channelName: this.video.snippet.channelTitle,
    };
    this.$store.commit("GET_VIDEO", tempVideo);
  },
};
</script>
<style></style>
