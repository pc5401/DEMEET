<template>
  <div class="modal">
    <div class="overlay"></div>
    <div class="modal-card">
      <slot />
      <button @click="$emit('close-modal')" id="close">확인</button>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
#close {
  background: radial-gradient(95% 60% at 50% 75%, #005FD6 0%, #209BFF 100%);
  border: 1px solid #54A1FD;
  box-shadow: 0px 8px 20px -8px #1187FF, inset 0px 1px 8px -4px #FFFFFF;
  border-radius: 12px;
  color: white;
  font-size: 16px;
  line-height: 22px;
  font-weight: 600;
  letter-spacing: .02em;
  transition: all .2s ease;
  -webkit-tap-highlight-color: rgba(255,255,255,0);
  margin-bottom: 8px;
  margin-right: 8px;
}
slot {
  margin: auto;
}
.modal{
  display: flex;
}
.modal,
.overlay {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
}
.overlay {
  opacity: 0.5;
  background-color: #C4C4C4;
}
.modal-card {
  background: #2b2b2b !important;
  border-radius: 5px;
  position: relative;
  width: 400px;
  margin: auto;
  margin-top: 30px;
  background-color: #111315;
  min-height: 100px;
  z-index: 10;
  opacity: 1;
}
</style>