<template>
  <div class="container">
    <div>
      <div class="text-center mt-5">
        <b-img
          :src="require('@/assets/sport/sport.png')"
          class="mb-2 mr-1"
          width="30px"
          height="40px"
        ></b-img>
        <b-img
          :src="require('@/assets/sport/exercise.png')"
          class="mb-2 mr-1"
          width="30px"
          height="40px"
        ></b-img>
        <b-img
          :src="require('@/assets/sport/female-fitness.png')"
          class="mb-2 mr-1"
          width="30px"
          height="40px"
        ></b-img>
        <b-img
          :src="require('@/assets/sport/fitness.png')"
          class="mb-2 mr-1"
          width="30px"
          height="40px"
        ></b-img>
        <b-img
          :src="require('@/assets/sport/trainer.png')"
          class="mb-2 mr-1"
          width="30px"
          height="40px"
        ></b-img>
        <b-img
          :src="require('@/assets/sport/warrior.png')"
          class="mb-2 mr-1"
          width="30px"
          height="40px"
        ></b-img>
        <b-img
          :src="require('@/assets/sport/sport.png')"
          class="mb-2"
          width="30px"
          height="40px"
        ></b-img>
        <h2>운동을 기록해보자!</h2>
        <p>오늘 하루도 열심히 운동했네요! 수고했어요!!!</p>
      </div>
      <b-row class="text-center mb-5 mt-4">
        <b-col>
          <b-button
            class="mr-3"
            style="width: 150px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2)"
            @click="moveToDiaryList"
            variant="Primary"
            >목록으로</b-button
          >
          <b-button
            style="width: 150px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2)"
            @click="moveToDiaryCreate"
            variant="info"
            >작성하기</b-button
          >
        </b-col>
      </b-row>
    </div>
    <hr />
    <b-calendar
      block
      v-model="clickDate"
      :date-info-fn="dateClass"
      locale="en"
      style="margin-top: 50px; margin-bottom: 50px"
      class="calendar-with-shadow"
    ></b-calendar>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "DiaryCalendar",
  data() {
    return {
      clickDate: "",
    };
  },
  // props: {
  //   clickDate: {
  //     type: String,
  //     required: true,
  //   },
  // },
  computed: {
    ...mapState(["diarys"]),
  },
  created() {
    const userId = sessionStorage.getItem("userId");
    this.$store.dispatch("getDiarys", userId);
    console.log(this.diarys);
  },
  methods: {
    dateClass(ymd, date) {
      const day = date.getDate();
      const month = date.getMonth() + 1;
      const year = date.getFullYear();
      for (let i = 0; i < this.diarys.length; i++) {
        if (
          year == this.diarys[i].date.substr(0, 4) &&
          month == this.diarys[i].date.substr(5, 2) &&
          day == this.diarys[i].date.substr(8)
        ) {
          return "table-info";
        }
      }
    },
    moveToDiaryCreate() {
      if (this.clickDate === "") {
        alert("일기를 입력할 날짜를 클릭해주세요!");
        return;
      }
      console.log(this.clickDate);
      this.$store.dispatch("saveClickDate", this.clickDate);
      this.$router.push("/diary/create");
    },
    moveToDiaryList() {
      this.$router.push("/diary/list");
    },
  },
};
</script>
<style>
.calendar-with-shadow {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}
</style>
