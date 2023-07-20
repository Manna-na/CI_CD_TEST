<template>
  <div class="container mb-5">
    <b-row class="d-flex justify-content-start mt-5">
      <b-col>
        <b-img
          :src="require('@/assets/journaling.png')"
          class="mb-2 mr-1"
          width="30px"
          height="40px"
        ></b-img>
        <h3>운동 일기장!</h3>
        <p>오늘의 운동을 기록해보아요</p>
      </b-col>
      <b-col class="d-flex justify-content-end align-content-center mb-4 mt-4">
        <b-button
          style="margin-right: 70px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2)"
          @click="moveToDiaryList"
          ><b-img
            class="mb-1 mt-1"
            :src="require('@/assets/calendar.png')"
            width="50px"
            height="40px"
          ></b-img
        ></b-button>
      </b-col>
    </b-row>
    <b-row style="margin-top: 2rem">
      <b-col
        class="col-sm-4 md-4 lg-3"
        v-for="(diary, index) in diarys"
        :key="index"
      >
        <div>
          <b-card
            class="shadow-lg"
            style="
              width: 18rem;
              height: 20rem;
              margin-bottom: 3rem;
              margin-bottom: 3rem;
              border-radius: 10px;
            "
            @mouseover="zoomInCard"
            @mouseout="zoomOutCard"
            ref="cards"
          >
            <div class="d-flex justify-content-between">
              <div class="align-self-center mt-2">
                <h3>{{ diary.date }}</h3>
                <p>운동 시간: {{ diary.start + "~" + diary.finish }}</p>
              </div>
              <div
                id="img"
                class="align-self-center"
                style="margin-right: 10px"
                v-if="diary.workout === '하체'"
              >
                <b-img
                  :src="require('@/assets/하체_white.png')"
                  center
                  class="mt-2"
                  width="40px"
                  height="40px"
                ></b-img>
              </div>
              <div
                id="img"
                class="align-self-center"
                style="margin-right: 10px"
                v-else-if="diary.workout === '상체'"
              >
                <b-img
                  :src="require('@/assets/상체_white.png')"
                  center
                  class="mt-2"
                  width="40px"
                  height="40px"
                ></b-img>
              </div>
              <div
                id="img"
                class="align-self-center"
                style="margin-right: 10px"
                v-else-if="diary.workout === '전신'"
              >
                <b-img
                  :src="require('@/assets/전신_white.png')"
                  center
                  class="mt-2"
                  width="40px"
                  height="40px"
                ></b-img>
              </div>
            </div>
            <hr style="margin-top: 1px" />
            <b-card-text class="text-truncate" style="max-height: 6rem">
              <em>{{ diary.content }}</em>
            </b-card-text>
            <b-row class="mb-1 fixed" style="margin-top: 50px">
              <b-col class="d-flex justify-content-end button">
                <b-button
                  pill
                  size="sm"
                  variant="outline-primary"
                  class="mr-1"
                  @click="updateDiary(diary)"
                  >수정</b-button
                >
                <b-button
                  pill
                  size="sm"
                  variant="outline-danger"
                  @click="deleteDiary(diary.diaryId)"
                  >삭제</b-button
                >
              </b-col>
            </b-row>
            <hr style="margin-bottom: 13px" />
            <b-form-rating
              id="rating-inline"
              inline
              no-border
              variant="warning"
              :value="diary.manjok"
            ></b-form-rating>
            <span style="margin-left: 50px"
              ><b-badge variant="success">{{ diary.workout }}</b-badge></span
            >
          </b-card>
        </div>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "DiaryPage",
  data() {
    return {
      value: "",
    };
  },
  methods: {
    zoomInCard(event) {
      const card = event.currentTarget;
      card.style.transform = "scale(1.1)";
    },
    zoomOutCard(event) {
      const card = event.currentTarget;
      card.style.transform = "scale(1)";
    },
    updateDiary(diary) {
      this.$router.push({ path: `/diary/list/${diary.diaryId}` });
    },
    deleteDiary(diaryId) {
      if (confirm("정말 삭제하시겠습니까?")) {
        this.$store.dispatch("deleteDiary", diaryId);
        // review에는 새로고침 기능이 없는데...?
        window.location.reload();
      } else {
        console.log("취소 했어요.");
      }
    },
    moveToDiaryList() {
      this.$router.push("/diary");
    },
  },
  computed: {
    ...mapState(["diarys"]),
  },
  created() {
    const userId = sessionStorage.getItem("userId");
    this.$store.dispatch("getDiarys", userId);
  },
};
</script>

<style scoped>
.b-icon.bi {
  width: 1rem;
  height: 1rem;
}

#img {
  background-color: black;
  border-radius: 50%;
  width: 60px;
  height: 60px;
}
</style>
