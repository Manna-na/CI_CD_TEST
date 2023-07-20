import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import router from "../router";

Vue.use(Vuex);

const REST_API = `http://localhost:9999`;

export default new Vuex.Store({
  state: {
    videos: [],
    dbLikeVideos: [],
    dbViewVideos: [],
    video: {},
    reviews: [],
    review: {},
    like: false,
    likeCnt: 0,
    likeVideos: [],
    diarys: [],
    diary: {},
    clickDate: "",
  },
  getters: {},
  mutations: {
    SEARCH_YOUTUBE(state, videos) {
      state.videos = videos;
    },
    GET_VIDEO(state, video) {
      state.video = video;
    },
    GET_REVIEWS(state, payload) {
      state.reviews = payload;
    },
    GET_REVIEW(state, payload) {
      state.review = payload;
    },
    UPDATE_REVIEW(state, payload) {
      state.review = payload;
    },
    INSERT_REVIEW(state, payload) {
      state.reviews.push(payload);
    },
    GET_DBLIKEVIDEOES(state, payload) {
      state.dbLikeVideos = payload;
    },
    GET_DBVIEWVIDEOES(state, payload) {
      state.dbViewVideos = payload;
    },
    UPDATE_LIKE(state, payload) {
      if (payload === "Yes") {
        state.like = true;
      } else if (payload === "No") {
        state.like = false;
      }
    },
    GET_LIKECNT(state, payload) {
      state.likeCnt = payload;
    },
    UPDATE_LIKECNT(state, payload) {
      state.likeCnt = state.likeCnt + payload;
    },
    GET_LIKEVIDEOS(state, payload) {
      state.likeVideos = payload;
    },
    INSERT_LIKEVIDEOS(state, payload) {
      state.likeVideos.push(payload);
    },
    SAVE_CLICK_DATE(state, payload) {
      state.clickDate = payload;
    },
    INSERT_DIARY(state, payload) {
      state.diarys.push(payload);
    },
    GET_DIARYS(state, payload) {
      state.diarys = payload;
    },
    GET_DIARY(state, payload) {
      state.diary = payload;
    },
    UPDATE_DIARY(state, payload) {
      state.diary = payload;
    },
  },
  actions: {
    // 비동기 통신은 여기서 진행시켜!!!
    searchYoutube({ commit }, payload) {
      const URL = "https://www.googleapis.com/youtube/v3/search";
      const API_KEY = process.env.VUE_APP_YOUTUBE_API_KEY;
      axios({
        url: URL,
        method: "GET",
        params: {
          key: API_KEY,
          part: "snippet",
          q: payload,
          type: "video",
          maxResults: 10,
        },
      })
        .then((res) => {
          commit("SEARCH_YOUTUBE", res.data.items);
        })
        .catch((err) => console.log(err));
    },
    getVideo({ commit }, videoId) {
      const URL = `${REST_API}/api-video/${videoId}`;
      axios({
        url: URL,
        method: "GET",
      })
        .then((res) => {
          commit("GET_VIDEO", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getReviews({ commit }, videoId) {
      const API_URL = `${REST_API}/api-comment/list/${videoId}`;
      axios({
        url: API_URL,
        method: "GET",
      }).then((res) => {
        commit("GET_REVIEWS", res.data);
      });
    },
    getReview({ commit }, reviewId) {
      const API_URL = `${REST_API}/api-comment/detail/${reviewId}`;
      axios({
        url: API_URL,
        method: "GET",
      }).then((res) => {
        commit("GET_REVIEW", res.data);
      });
    },
    updateReview({ commit }, review) {
      const API_URL = `${REST_API}/api-comment/update`;
      let loginUser = sessionStorage.getItem("loginUser");
      axios({
        url: API_URL,
        method: "PUT",
        params: review,
        data: loginUser,
      })
        .then((res) => {
          console.log(res);
          commit("UPDATE_REVIEW", review);
          router.go(-1);
        })
        .catch((res) => {
          console.log(res);
        });
    },
    deleteReview({ state }, reviewId) {
      const API_URL = `${REST_API}/api-comment/delete/${reviewId}`;
      let loginUser = sessionStorage.getItem("loginUser");
      axios({
        url: API_URL,
        method: "DELETE",
        data: loginUser,
      })
        .then(() => {
          let index;
          for (let i = 0; i < state.reviews.length; i++) {
            if (state.reviews[i].reviewId === reviewId) {
              index = i;
            }
          }
          state.reviews.splice(index, 1);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    insertReview({ commit }, review) {
      const API_URL = `${REST_API}/api-comment/write`;
      let loginUser = sessionStorage.getItem("loginUser");
      axios({
        url: API_URL,
        method: "POST",
        data: {
          loginUser,
          review,
        },
      })
        .then((res) => {
          if (res.data === "OK") {
            commit("INSERT_REVIEW", review);
          }
        })
        .catch((res) => {
          if (
            res.response.data === "NeedLogin" ||
            res.response.data === "NoSuchUser"
          ) {
            alert("로그인이 필요합니다");
          } else if (res.response.data === "Error") {
            alert("오류발생");
          } else {
            alert("잘못된 접근입니다.");
          }
        });
    },
    getDbLikeVideos({ commit }, params) {
      const API_URL = `${REST_API}/api-video/like`;
      axios({
        url: API_URL,
        method: "GET",
        params,
      })
        .then((res) => {
          commit("GET_DBLIKEVIDEOES", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getDbViewVideos({ commit }, params) {
      const API_URL = `${REST_API}/api-video/view`;
      axios({
        url: API_URL,
        method: "GET",
        params,
      })
        .then((res) => {
          commit("GET_DBVIEWVIDEOES", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    insertVideo({ commit }, video) {
      commit;
      const API_URL = `${REST_API}/api-video/create`;
      axios({
        url: API_URL,
        method: "POST",
        data: video,
      });
    },
    createUser({ commit }, user) {
      const API_URL = `${REST_API}/api-user/insert`;
      commit;
      axios({
        url: API_URL,
        method: "POST",
        data: user,
      })
        .then((res) => {
          if (res.data === "OK") {
            alert("회원가입이 완료되었습니다.");
            router.push("/login");
          }
        })
        .catch((res) => {
          if (res.response.data === "IdDuplication") {
            alert("중복된 아이디입니다.");
          }
          if (res.response.data === "BadPassword") {
            alert("올바르지 못한 비밀번호 입니다.");
          }
        });
    },
    updateUser(user) {
      const API_URL = `${REST_API}/api-user/update`;
      let loginUser = sessionStorage.getItem("loginUser");
      axios({
        url: API_URL,
        method: "PUT",
        data: {
          user,
          loginUser,
        },
      })
        .then(() => {
          alert("수정 완료!");
          router.push("/myPage");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    deleteUser({ commit }, set) {
      commit;
      const API_URL = `${REST_API}/api-user/delete`;
      let loginUser = sessionStorage.getItem("loginUser");
      let userId = set.userId;
      let chkPassword = set.chkPassword;
      axios({
        url: API_URL,
        method: "DELETE",
        data: {
          userId,
          chkPassword,
          loginUser,
        },
      })
        .then((res) => {
          if (res.data === "OK") {
            alert("회원 탈퇴가 완료되었습니다.");
            sessionStorage.removeItem("loginUser");
            sessionStorage.removeItem("userId");
            router.push("/login");
          }
        })
        .catch((res) => {
          console.log(res);
        });
    },
    userLogin({ commit }, user) {
      const API_URL = `${REST_API}/api-user/login`;
      commit;
      axios({
        url: API_URL,
        method: "POST",
        data: user,
      })
        .then((res) => {
          if (res.data.message === "OK") {
            alert("로그인 성공!");
            sessionStorage.setItem("loginUser", res.data.accessToken);
            sessionStorage.setItem("userId", res.data.userId);
            router.push("/");
            window.location.reload();
          }
        })
        .catch((res) => {
          if (res.response.data.message === "BadPassword") {
            alert("비밀번호를 다시 확인해주세요.");
          } else if (res.response.data.message === "NoSuchUser") {
            alert("아이디를 다시 확인해주세요.");
          } else if (res.response.data.message === "Error") {
            alert("오류발생!");
            router.push("/");
          }
        });
    },
    emailChk({ commit }, email) {
      commit;
      const API_URL = `${REST_API}/api-user/mail/${email}`;
      axios({
        url: API_URL,
        method: "POST",
      }).then((res) => {
        console.log(res.data);
        alert("이메일 인증 코드가 입력하신 이메일로 전송되었습니다");
      });
    },
    getLikeCnt({ commit }, videoId) {
      const API_URL = `${REST_API}/api-video/${videoId}/like`;
      axios({
        url: API_URL,
        method: "GET",
      })
        .then((res) => {
          commit("GET_LIKECNT", res.data);
        })
        .catch((res) => {
          if (res.response.data === "NoSuchVideo") {
            alert("존재하지 않는 영상입니다.");
            router.push("/");
          } else {
            router.go(0);
          }
        });
    },
    insertLike({ commit, state }, set) {
      const API_URL = `${REST_API}/api-video/${set.videoId}/${set.userId}/like`;
      axios({
        url: API_URL,
        method: "POST",
      }).then((res) => {
        commit("UPDATE_LIKECNT", res.data);
        if (res.data === 1) {
          commit("INSERT_LIKEVIDEOS", set.videoId);
          commit("UPDATE_LIKE", "Yes");
        } else if (res.data === -1) {
          let index = 0;
          for (let i = 0; i < state.likeVideos.length; i++) {
            if (state.likeVideos[i].videoId === set.videoId) {
              index = i;
            }
          }
          state.likeVideos.splice(index, 1);
          commit("UPDATE_LIKE", "No");
        }
      });
    },
    getLike({ commit }, set) {
      const API_URL = `${REST_API}/api-video/${set.videoId}/${set.userId}/like`;
      axios({
        url: API_URL,
        method: "GET",
      }).then((res) => {
        commit("UPDATE_LIKE", res.data);
      });
    },
    getLikeVideos({ commit }, userId) {
      const API_URL = `${REST_API}/api-user/${userId}/like`;
      axios({
        url: API_URL,
        method: "GET",
      }).then((res) => {
        commit("GET_LIKEVIDEOS", res.data);
      });
    },
    saveClickDate({ commit }, clickDate) {
      commit("SAVE_CLICK_DATE", clickDate);
    },
    getDiarys({ commit }, userId) {
      const API_URL = `${REST_API}/api-diary/list/${userId}`;
      let loginUser = sessionStorage.getItem("loginUser");
      axios({
        url: API_URL,
        method: "POST",
        data: loginUser,
      })
        .then((res) => {
          commit("GET_DIARYS", res.data);
        })
        .catch(() => {
          alert("잘못된 접근입니다.");
          router.push("/");
        });
    },
    getDiary({ commit }, diaryId) {
      const API_URL = `${REST_API}/api-diary/${diaryId}`;
      let loginUser = sessionStorage.getItem("loginUser");
      axios({
        url: API_URL,
        method: "POST",
        data: loginUser,
      })
        .then((res) => {
          commit("GET_DIARY", res.data);
        })
        .catch(() => {
          alert("잘못된 접근입니다.");
          router.push("/");
        });
    },
    createDiary({ commit }, diary) {
      const API_URL = `${REST_API}/api-diary/insert`;
      let loginUser = sessionStorage.getItem("loginUser");
      axios({
        url: API_URL,
        method: "POST",
        data: {
          diary,
          loginUser,
        },
      })
        .then(() => {
          commit("INSERT_DIARY", diary);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    deleteDiary({ state }, diaryId) {
      const API_URL = `${REST_API}/api-diary/${diaryId}`;
      let loginUser = sessionStorage.getItem("loginUser");
      axios({
        url: API_URL,
        method: "DELETE",
        data: loginUser,
      })
        .then(() => {
          let index;
          for (let i = 0; i < state.diarys.length; i++) {
            if (state.diarys[i].diaryId === diaryId) {
              index = i;
            }
          }
          state.reviews.splice(index, 1);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    updateDiary({ commit }, diary) {
      const API_URL = `${REST_API}/api-diary/update`;
      let loginUser = sessionStorage.getItem("loginUser");
      axios({
        url: API_URL,
        method: "PUT",
        data: {
          diary,
          loginUser,
        },
      })
        .then(() => {
          commit("UPDATE_DIARY", diary);
          alert("수정 완료!");
          router.push("/diary/list");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getPause() {},
  },
});
