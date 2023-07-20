<template>
  <div>
    <b-form
      shadow="lg"
      class="diary-form container text-center shadow"
      style="margin-top: 100px; margin-bottom: 150px; width: 830px"
    >
      <div class="mb-3 pt-5">
        <h3 class="text-center">Diary</h3>
        <label for="today" class="form-label">일정</label>
        <!-- <div class="col-sm-10">
       <input type="text" readonly v-model="diary.date" class="form-control-plaintext" id="today" />
     </div> -->
        <p>{{ diary.date }}</p>
      </div>
      <div class="row pt-5 justify-content-center">
        <div class="col-md-5">
          <label for="start" class="form-label">시작 시간</label>
          <input
            type="time"
            class="form-control time"
            id="start"
            placeholder="hour"
            v-model="diary.start"
          />
        </div>
        <div class="col-md-5 ps-2">
          <label for="finish" class="form-label">종료 시간</label>
          <input
            type="time"
            class="form-control time"
            id="finish"
            placeholder="minute"
            v-model="diary.finish"
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
          v-model="diary.manjok"
          size="lg"
          style="padding-left: 15rem; padding-right: 15rem"
        ></b-form-rating>
      </div>
      <!-- <p class="mt-2">Value: {{ diary.manjok }}</p> -->
      <div class="mb-3">
        <!-- <label for="workout" class="form-label">운동부위</label> -->
        <b-form-group label="workout" v-slot="{ ariaDescribedby }">
          <b-form-radio-group
            v-model="diary.workout"
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
        <!-- <div class="mt-3">
          Selected: <strong>{{ diary.workout }}</strong>
        </div> -->
      </div>
      <div class="mb-3">
        <label for="content" class="form-label">content</label>
        <textarea
          class="form-control"
          rows="3"
          v-model="diary.content"
        ></textarea>
      </div>
      <b-button
        fullWidth
        variant="gradient"
        type="submit"
        class="btn btn-secondary mb-5"
        :class="{ disabled: !diary.content }"
        @click.prevent="updateDiary"
      >
        수정하기
      </b-button>
    </b-form>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "DiaryUpdate",
  methods: {
    updateDiary() {
      this.$store.dispatch("updateDiary", this.diary);
      this.$swal({
        title: "알림",
        text: "SweetAlert2를 사용하여 알림을 보여줍니다.",
        icon: "success",
      });
    },
  },
  computed: {
    ...mapState(["diary"]),
  },
  created() {
    const pathName = new URL(document.location).pathname.split("/");
    const diaryId = pathName[pathName.length - 1];
    console.log(diaryId);
    this.$store.dispatch("getDiary", diaryId);
  },
};
</script>

<style></style>
