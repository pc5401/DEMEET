<template>
  <div class="modal">
    <div class="overlay"></div>
    <div class="modal-card">
      <button @click="$emit('close-modal')" id="close"><span class="material-symbols-outlined">close</span></button>
      <slot />
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>

#close {
  text-align: end;
  float: right;
  border-radius: 5px;
}
#close:hover {
  transform: scale(1.2);
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
  position: relative;
  width: 500px;
  margin: auto;
  margin-top: 30px;
  background-color: #111315;
  min-height: 500px;
  z-index: 10;
  opacity: 1;
}
</style>