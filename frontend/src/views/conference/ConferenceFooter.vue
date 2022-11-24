<template>
  <div id="footer">

    <!-- 마이크 음소거 -->
    <button class="btn_center_1" type="button" :title="[audioValue == true ? '마이크 끄기':'마이크 켜기']" @click="$emit('audioOnOff'), audioIcon()">
      <span v-if="audioValue" class="material-symbols-outlined">mic</span>
      <span v-if="!audioValue" style="color:red" class="material-symbols-outlined">mic_off</span>
    </button>  
    
    <!-- 내 얼굴 영상 공유 중지 -->
    <button class="btn_center_2" type="button" :title="[videoValue == true ? '비디오 끄기':'비디오 켜기']" @click="$emit('videoOnOff'), videoIcon()">
      <span v-if="videoValue" class="material-symbols-outlined">stop_circle</span>
      <span v-if="!videoValue" style="color:red" class="material-symbols-outlined">play_circle</span>
    </button>
    
    <!-- 컴퓨터 화면 공유 -->
    <button class="btn_center_3" type="button" :title="[isSharing == true ? '화면공유 끄기':'화면공유 켜기']" @click="$emit('shareScreen')">
      <span v-if="!isSharing" class="material-symbols-outlined">screen_share</span>
      <span v-if="isSharing" style="color:red" class="material-symbols-outlined">stop_screen_share</span>
    </button>

    <!-- 드로잉 버튼 -->
    <button class="btn_center_4" type="button" :title="[drawValue == true ? '그림공유 켜기':'그림공유 끄기']" @click="$emit('shareDrawing'), drawIcon()">
      <span v-if="drawValue" class="material-symbols-outlined">draw</span>
      <span v-if="!drawValue" style="color:red" class="material-symbols-outlined">edit_off</span>
    </button> 
    <!-- <button type="button" @click="$emit('shareDrawing')"><a href="http://127.0.0.1:8081/drawing" target="_blank">drawing</a></button>  -->

  
  <!-- 사이바 요소 , 유저 리스트 onoff, 채팅창 onoff-->
  <div id="sideBar">
    <button type="button" class="btn_right_1" :title="[usersValue == true ? '회원목록 켜기':'회원목록 끄기']" @click="$emit('userListOnOff'), valueOnOff(usersValue)">
      <span v-if="usersValue" class="material-symbols-outlined">group</span>
      <span  v-if="!usersValue" style="color:red" class="material-symbols-outlined">group_off</span>
    </button>  
    <button type="button" class="btn_right_2" :title="[chatValue == true ? '채팅창 켜기':'채팅창 끄기']" @click="$emit('chattingOnOff'), valueOnOff(chatValue)">
      <span v-if="chatValue" class="material-symbols-outlined">speaker_notes</span>
      <span  v-if="!chatValue" style="color:red" class="material-symbols-outlined">speaker_notes_off</span>
    </button>  
    <!-- 나가기 -->
    <button type="button" class="leave-meeting" title="나가기" @click="$emit('sessionExit')"><span class="material-symbols-outlined">logout</span></button>
  </div>
  </div>
</template>

<script>
import {ref} from 'vue'

export default {
  name: 'ConferenceFooter',
  components: {},

  props: {
		isSharing:{
			type:Boolean,
		},
    isDrawing:{
      type:Boolean,
    }
	},


  setup() {
    const audioValue = ref(false)
    const videoValue = ref(false)
    const micValue = ref(true)
    const drawValue = ref(true)
    const chatValue = ref(true)
    const usersValue = ref(true)

    const audioIcon = () =>{
      audioValue.value = !audioValue.value
    }

    const videoIcon = () =>{
      videoValue.value = !videoValue.value
    }

    const micIcon = () =>{
      micValue.value = !micValue.value
    }

    const drawIcon = () => {
      drawValue.value = !drawValue.value
    }

    const chatIcon = () => {
      chatValue.value = !chatValue.value
    }

    const usersIcon = () => {
      usersValue.value = !usersValue.value
    }

  
  return {
    audioValue,
    videoValue,
    micValue,
    drawValue,
    chatValue,
    usersValue,
    audioIcon,
    videoIcon,
    micIcon,
    drawIcon,
    chatIcon,
    usersIcon,
  }
},
  create() {},
  mounted() {},
  unmounted() {},
  methods: {},
}
</script>

<style scoped>
#footer{
  vertical-align:middle;
}

div #sideBar {
  float: right;
  margin-right: 70px;
}

button{
  margin: 0.5em 0.1em 0.5em 0.1em;
  border-radius: 30%;
  font-size: 16px;
  padding: 8px;
  cursor: pointer;
  background-color: #F1F6F9;
}


button:hover{
  background-color: #9BA4B4;
}

button.btn_center_1{
  color: rgba(231,19,217,0.7);
  box-shadow: 0 8px 16px rgba(231,19,217,0.3);
}

button.btn_center_2{
  color: rgba(158,83,228,0.7);
  box-shadow: 0 8px 16px rgba(158,83,228,0.3);
}


button.btn_center_3{
  color: rgba(72,159,242,0.7);
  box-shadow: 0 8px 16px rgba(72,159,242,0.3);
}


button.btn_center_4{
  color: rgba(0,234,254,0.7);
  box-shadow: 0 8px 16px rgba(0,234,254,0.3);
}

button.leave-meeting{
  font-size: 10px;
  color: rgb(242, 235, 235);
  background-color: rgb(234, 71, 71);
}

button.leave-meeting:hover{
  background-color: rgb(255, 0, 0);
}

button.btn_right_1{
  color: #394867;
}

button.btn_right_2{
  color: #394867;
}

</style>