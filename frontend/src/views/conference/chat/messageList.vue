<template>
  <div id="chatForm">
    <div id="chat_list" class="container" >
      <ConferenceMessage
        v-for="(msg, index) in msgs"
        :key="index"
        :msg="msg"
        :myId="myId"
        :fromId="fromId"
      />
    </div>

  </div>
</template>

<script>
import ConferenceMessage from "./message"
export default {
  name: "MessageList",
  components: {
    ConferenceMessage
  },
props: {
    msgs: {
      type: Array,
    default: ()=> {
      return []
    }
    },
    myId: {
      type: String,
    default: ()=> {
      return ""
    }
    },
    fromId: {
      type: String,
    default: ()=> {
      return ""
    }
    }
  },
  data() {
    return {

    }
  },
  updated() {
    if (!this.isScrolled) {
      var objDiv = document.getElementById("chat_list")
      objDiv.scrollTop = objDiv.scrollHeight + 500
    }
  },
}
</script>

<style scoped>
#chat_list {
  height: 720px;
  width: 300px;
  background: #9BA4B4;
  overflow-y: scroll;
  -ms-overflow-style: none; /* for Internet Explorer, Edge */
  scrollbar-width: none; /* for Firefox */
  border-radius: 10px;
  align-content: center;
  margin:0px 10px 10px 10px;

}
div::-webkit-scrollbar {
  display: none; /* for Chrome, Safari, and Opera */
}
#chatForm {
  height:750px;
  background-color:#394867;
  border-top-left-radius:10px;
  border-top-right-radius:10px;
  padding-top:10px;
  margin-top:30px
}
</style>
