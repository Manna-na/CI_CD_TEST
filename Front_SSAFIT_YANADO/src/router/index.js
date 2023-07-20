import Vue from "vue";
import VueRouter from "vue-router";
import VideoView from "../views/VideoView.vue";
import ReviewView from "../views/ReviewView.vue";
import DiaryView from "../views/DiaryView.vue";

import VideoList from "@/components/video/VideoList.vue";
import VideoSearch from "@/components/video/VideoSearch.vue";
import VideoDetail from "@/components/video/VideoDetail.vue";

import ReviewList from "@/components/review/ReviewList.vue";
import ReviewDetail from "@/components/review/ReviewDetail.vue";

import WorkoutLocation from "@/components/workout/WorkoutLocation.vue";

import DiaryList from "@/components/diary/DiaryList.vue";
import DiaryCreate from "@/components/diary/DiaryCreate.vue";
import DiaryCalendar from "@/components/diary/DiaryCalendar.vue";
import DiaryUpdate from "@/components/diary/DiaryUpdate.vue";

import UserLogin from "@/components/user/UserLogin.vue";
import UserRegist from "@/components/user/UserRegist.vue";
import MyPage from "@/components/user/MyPage.vue";

import NotFound from "@/components/404.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    component: VideoView,
    children: [
      {
        path: "",
        name: "videoList",
        component: VideoList,
      },
      {
        path: "/search",
        name: "videoSearch",
        component: VideoSearch,
      },
      {
        path: "/search/:videoId",
        name: "videoView",
        component: VideoDetail,
      },
    ],
  },
  {
    path: "/review",
    component: ReviewView,
    children: [
      {
        path: "",
        name: "reviewList",
        component: ReviewList,
      },
      {
        path: ":videoId/:reviewId",
        name: "reviewDetail",
        component: ReviewDetail,
      },
    ],
  },
  {
    path: "/login",
    name: "login",
    component: UserLogin,
  },
  {
    path: "/regist",
    name: "regist",
    component: UserRegist,
  },
  {
    path: "/workout-location",
    name: "workoutLocation",
    component: WorkoutLocation,
  },
  {
    path: "/diary",
    component: DiaryView,
    beforeRouteLeave(to, from, next) {
      const loginUser = sessionStorage.getItem("loginUser");

      // 로그인되지 않은 사용자가 이전 페이지로 이동하는 것을 막습니다.
      if (to.path === "/diary" && !loginUser) {
        alert("로그인이 필요합니다.");
        next(false); // 이전 페이지로의 이동을 막습니다.
      } else {
        next(); // 이전 페이지로 이동을 허용합니다.
      }
    },
    meta: { requiresAuth: true },
    children: [
      {
        path: "/",
        name: "diaryCalendar",
        component: DiaryCalendar,
      },
      { path: "list", name: "diaryList", component: DiaryList },
      {
        path: "create",
        name: "diaryCreate",
        component: DiaryCreate,
      },
      {
        path: "list/:diaryId",
        name: "diaryUpdate",
        component: DiaryUpdate,
      },
    ],
  },
  {
    path: "/myPage",
    name: "myPage",
    component: MyPage,
  },
  {
    path: "/:catchAll(.*)",
    component: NotFound,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});
// from이 없는데 작동을 하네요?
router.beforeEach((to, from, next) => {
  const loginUser = sessionStorage.getItem("loginUser");

  if (to.matched.some((route) => route.meta.requiresAuth) && !loginUser) {
    // 로그인이 필요한 페이지에 접근하려고 할 때 로그인하지 않은 경우
    next("/");
  } else {
    next();
  }
});

export default router;
