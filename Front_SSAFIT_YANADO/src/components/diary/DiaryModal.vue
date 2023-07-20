<template>
 <transition class="modal">
    <div class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container">
          <div class="close-btn" @click="closeModal()">
            <b-icon icon="x-circle" scale="2" variant="danger" type="button"/>
          </div>
          <slot name="container">

            <div class="modal-header">
                <slot name="header">
                </slot>
            </div>

            <div class="modal-body">
              <slot name="body">
              </slot>
            </div>

            <div class="modal-footer">
                <slot name="footer">
                </slot>
            </div>
          </slot>
      </div>
    </div>
   </div>
  </transition>
</template>

<script>
export default {
  name: "DiaryModal",

}
</script>

<style>

</style>