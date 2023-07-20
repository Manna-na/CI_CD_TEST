<template>
  <div class="container">
    <div class="d-flex flex-column text-center">
      <h1
        style="
          padding-top: 0px;
          padding-bottom: 0px;
          margin-bottom: 0px;
          margin-top: 0px;
          top: 13rem;
          font-weight: bolder;
        "
      >
        404 error
      </h1>
      <div>
        <img :src="require('@/assets/404error.png')" />
      </div>
      <p style="font-size: 25px; font-weight: 30%">
        The page you're looking for does not exist.
      </p>
    </div>
  </div>
</template>

<script>
export default {
  name: "NotFound",
};
</script>

<style scoped>
.container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
}
</style>
