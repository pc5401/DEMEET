<template>
  <div id="chatbox">
    <div id="chat" v-if="!ismy">
      <span id="msgs">{{ sendname }} : {{ chat }}</span>
    </div>
    </div>
  <div id="mychatbox">
    <div id="mychat" v-if="ismy">
      <span id="msgs">{{ chat }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "ConferenceMessage",
  props: {
    msg: {
      type: String,
			default: () => {
				return ""
			}      
    },
    myId: {
      type: String,
			default: () => {
				return ""
			}      
    },
    fromId: {
      type: String,
			default: () => {
				return ""
			}      
    }
  },
  data() {
    return {
      ismy: this.equal(),
      sendname: "",
      chat: ""
    }
  },
  mounted() {
    this.sendname = JSON.parse(this.msg).userName
    this.chat = JSON.parse(this.msg).msg
  },
  methods: {
    equal() {
      if (this.myId == this.fromId) {
        return true
      } else {
        return false
      }
    }
  }
}
</script>

<style scoped>


#msgs {
  font-size: 17px;
  margin: 2px;
  padding-left: 5px;
  padding-right: 5px;
  white-space: pre-line;
  width:230px;
  word-break: break-all;
  word-wrap: break-word;
  color: white;
  
}
#chat {

  margin: 5px;
  padding: 5px;
  border-radius: 15px;
  width: fit-content;
  text-align: left;
  display: inline-block;
  white-space: pre-line;
}
#mychat {

  margin: 5px;
  padding: 5px;
  border-radius: 15px;
  width: fit-content;
  text-align: left;
  display: inline-block;
}
#mychatbox {
  float: right;
}
#chatbox {
  background-color: #14274E;
  border-radius: 10px;
  width: 96%;
  margin: 5px;
  text-align: left;
}
</style>

