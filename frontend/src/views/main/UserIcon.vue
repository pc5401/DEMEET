<template>
  <AlertView AlertView v-if="isError" @close-modal="isError=false">
    <h3>{{ alertText }}</h3>
  </AlertView>
  <div class="modal" v-if="isBool">
    <div class="overlay"></div>
    <div class="modal-card">
      <h3 class="confirm-text">{{ confirmText }}</h3>
      <div class="confirm-btn">
        <button @click="changeBool(true)" id="check">확인</button>
        <button @click="changeBool(false)" id="check">취소</button>
      </div>
    </div>
  </div>
  <div 
  v-if="user.member.profileImagePath!==null" 
  class="user-icon" 
  :style="`background-image: url(${user.member.profileImagePath});`">
    <span 
    v-if="project.project.projectOwner===project.profile.uid && project.project.activation" 
    @click="isBool=true, confirmText='추방시키겠습니까?'" 
    class="material-symbols-outlined" 
    id="close"
    >
    close</span>
    <div 
    v-if="project.project.projectOwner!==project.profile.uid || !project.project.activation" 
    class="none-box"
    >
    </div>
    <p class="username">{{ user.member.nickname }}</p>
  </div>
  <div 
  v-if="user.member.profileImagePath===null" 
  class="user-icon"
  >
    <span 
    v-if="project.project.projectOwner===project.profile.uid && project.project.activation" 
    @click="isBool=true, confirmText='추방시키겠습니까?'" 
    class="material-symbols-outlined" 
    id="close"
    >
    close</span>
    <div 
    v-if="project.project.projectOwner!==project.profile.uid || !project.project.activation" 
    class="none-box"
    >
    </div>
    <p class="username">{{ user.member.nickname }}</p>
  </div>
</template>

<script>
import { defineComponent, ref } from "vue"
import router from "@/router"
import { useAccountStore } from "@/stores/account"
import AlertView from "@/views/main/AlertView"
export default defineComponent({
  components: {
    AlertView
  },
  props: ['member','host'],
  setup(props) {
    const isBool = ref(false)
    const confirmText = ref('')
    const alertText = ref('')
    const isError = ref(false)
    const user = props
    const hostdata = user.host
    const project = useAccountStore()
    const pjt = ref(project.project)
    const remove = (user) => {
      if (user.member.uid===project.profile.uid) {
        alertText.value = '호스트를 추방할 수 없습니다.'
        isError.value = true
      }else {
        project.removeUser({pid:pjt.value.pid,uid:user.member.uid})
        router.go({name:'DetailView'})
      }
    }
    const changeBool = (res) => {
      if(res){
        remove(user)
        isBool.value = false
      }else {
        isBool.value = false
      }
    }
    return {
      user,
      isBool,
      project,
      hostdata,
      alertText,
      confirmText,
      isError,
      remove,
      changeBool
    }
  }
})
</script>

<style scoped>
h3 {
  margin: 25px;
  color: white;
}
#close {
  font-size: 16px;
  color: black;
}
.none-box{
  width: 40px;
  height: 16px;
  margin-bottom: 18px;
}
.user-icon {
  margin-left: 8px;
  margin-top: 2px;
  background-image: url(@/assets/기본프로필.jpg);
  background-size: cover;
  width: 40px;
  height: 40px;
  border-radius: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  text-align: end;
}
.username {
  width: 44px;
  height: 14px;
  font-size: 8px;
  color: black;
  word-break:break-all;
  text-align: center;
}
#check {
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
  margin: 20px;
  width: 80px;
  height: 30px;
}
#check:hover {
  transform: scale(1.2);
}
.confirm-text {
  margin: 25px;
  color: white;
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
.confirm-btn {
  display: flex;
  justify-content: center;
}
</style>