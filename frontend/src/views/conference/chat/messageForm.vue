<template>
  <div id="inputForm">
    <input
      class="input-text"
      type="text"
      placeholder="채팅을 입력하세요."
      v-model = "messageForm.message"
      v-on:keyup.enter="submitForm"
    >
    <button
      class="my-btn"
      type="button"
      v-on:click="submitForm"
      >입력
    </button>

  </div>
</template>

<script>
export default {
  name: 'MessageForm',
  data() {
    return {
      messageForm: {
        message: ""
      }
    }
  },

  props: {
    userName: String,
    default: ()=> {
      return ""
    }
  },

  methods: {
    submitForm(event) {

      const msg = this.messageForm.message.trim()

      if (msg != "") {

        event.preventDefault()
        // this.$emit("sendMsg", "[" + this.userName + "] : " + msg)
        var string = JSON.stringify({
          userName: this.userName,
          msg: msg
        })
        this.$emit("sendMsg", string)
      }
      this.messageForm.message = ""
    }
  }
};
</script>

<style scoped>
.my-btn {
  width:70px;
  height: 84px;
  padding:0px;
  margin: 0px 10px 10px 0px;

  border-radius:10px;
}
.input-text {
  width: 215px;
  margin: 0px 10px 0px 10px;
  height: 80px;
  padding:0;
  
  border-radius:10px;
}
#inputForm {
  display: flex;
  position : relative;
  height: 100px;
  background-color:#394867;
  border-bottom-left-radius:10px;
  border-bottom-right-radius:10px;
  margin-bottom: 16px;
}
</style>

