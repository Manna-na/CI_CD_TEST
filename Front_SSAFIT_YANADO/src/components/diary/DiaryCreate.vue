<template>
  <div>
    <b-form
      shadow="lg"
      class="diary-form container text-center shadow"
      style="margin-top: 100px; margin-bottom: 150px"
    >
      <div class="mb-3 pt-5">
        <h3 class="text-center">Diary</h3>
        <label for="today" class="form-label">일정</label>
        <p>{{ clickDate }}</p>
        <!-- <input type="text" class="form-control" id="title" v-model="title" /> -->
      </div>
      <div class="row pt-5">
        <div class="col-md-6">
          <label for="start" class="form-label">시작 시간</label>
          <input
            type="time"
            class="form-control time"
            id="start"
            placeholder="hour"
            v-model="start"
          />
        </div>
        <div class="col-md-6 ps-2">
          <label for="finish" class="form-label">종료 시간</label>
          <input
            type="time"
            class="form-control time"
            id="finish"
            placeholder="minute"
            v-model="finish"
          />
        </div>
      </div>
      <div class="mb-3 pt-5">
        <label
          for="manjok"
          class="form-label text-center"
          style="font-size: 1.5rem"
          >만족도</label
        >
        <!-- <input type="text" class="form-control" id="manjok" v-model="manjok" /> -->
        <!-- <label for="rating-inline">Inline rating:</label> -->

        <b-form-rating
          variant="warning"
          id="rating-lg"
          no-border
          v-model="manjok"
          size="lg"
          style="padding-left: 15rem; padding-right: 15rem"
        ></b-form-rating>
      </div>
      <p class="mt-2">Value: {{ manjok }}</p>
      <div class="mb-3">
        <!-- <label for="workout" class="form-label">운동부위</label> -->
        <b-form-group label="workout" v-slot="{ ariaDescribedby }">
          <b-form-radio-group
            v-model="workout"
            :aria-describedby="ariaDescribedby"
            class="mb-3"
            value-field="item"
            text-field="name"
            disabled-field="notEnabled"
          >
            <b-form-radio value="상체">
              <b-img
                :src="require('@/assets/상체.png')"
                width="40px"
                height="40px"
                style="justify-content: center"
              ></b-img>
              <!-- <b-img :src="require('@/assets/bears.png')" width="80px" height="40px" style="justify-content: center;"></b-img> -->
            </b-form-radio>
            <b-form-radio value="하체">
              <b-img
                :src="require('@/assets/하체.png')"
                width="40px"
                height="40px"
                style="justify-content: center"
              ></b-img>
            </b-form-radio>
            <b-form-radio value="전신">
              <b-img
                :src="require('@/assets/전신.png')"
                width="40px"
                height="40px"
                style="justify-content: center"
              ></b-img>
            </b-form-radio>
          </b-form-radio-group>
        </b-form-group>
        <div class="mt-3">
          Selected: <strong>{{ workout }}</strong>
        </div>
      </div>
      <div class="mb-3">
        <label for="content" class="form-label">content</label>
        <textarea
          class="form-control"
          id="content"
          rows="3"
          v-model="content"
        ></textarea>
      </div>
      <b-button
        fullWidth
        variant="gradient"
        type="submit"
        class="btn btn-secondary mb-5"
        :disabled="!content || !manjok || !start || !finish || !workout"
        @click.prevent="add()"
      >
        등록하기
      </b-button>
    </b-form>
  </div>
</template>
<script>
import { mapState } from "vuex";
export default {
  name: "DiaryCreate",
  data() {
    return {
      manjok: null,
      workout: "",
      start: "",
      finish: "",
      content: "",
    };
  },
  methods: {
    add() {
      let diary = {
        userId: sessionStorage.getItem("userId"),
        start: this.start,
        finish: this.finish,
        date: this.clickDate,
        manjok: this.manjok,
        workout: this.workout,
        content: this.content,
      };
      console.log(diary);
      this.$store.dispatch("createDiary", diary);
      this.$router.push("/diary/list");
      // 새로고침
      window.location.reload();
    },
  },
  computed: {
    ...mapState(["clickDate"]),
  },
};
</script>

<style scoped>
.b-icon.bi {
  width: 2rem;
  height: 2rem;
}
.shadow {
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2); /* 그림자 스타일 설정 */
}
</style>
