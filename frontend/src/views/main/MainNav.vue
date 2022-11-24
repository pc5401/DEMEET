<template>
  <nav>
    <a href="/">
      <img src="@/assets/DEMEET_logo.png" alt="">
    </a>
    <div>
      <input v-model.trim="search" @input="setData" type="text" placeholder="Search">
    </div>
    <div class="list-box">
      <a href="/profile/me" class="list-box">
        <span class="material-symbols-outlined" id="account">account_box</span>
        <a>{{ account.profile.nickname }}님</a>
      </a>
      <div class="list-box" @click="isBool=true, confirmText='로그아웃 하시겠습니까?'">
        <span @click="isBool=true, confirmText='로그아웃 하시겠습니까?'" class="material-symbols-outlined" id="logout">logout</span>
        <a >Logout</a>
      </div>
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
    </div>
  </nav>
</template>

<script>
import { defineComponent, ref } from "vue"
import { useAccountStore } from "@/stores/account"
export default defineComponent({
  setup() {
    const isBool = ref(false)
    const confirmText = ref('')
    const account = useAccountStore()
    const search = ref('')
    const setData = () => {
      account.search = search.value
      if (account.search){
        account.projects = account.projects.filter(res => res.pjtName.includes(account.search))
        account.endProjects = account.endProjects.filter(res => res.pjtName.includes(account.search))
      }else{
        account.fetchProjects()
        account.fetchProfile()
      }
    }
    const changeBool = (res) => {
      if(res){
        account.logout()
        isBool.value = false
      }else {
        isBool.value = false
      }
    }
    return {
      account,
      search,
      confirmText,
      setData,
      isBool,
      changeBool
    }
  },
  async created() {
    await this.account.fetchProfile()
  },
})
</script>

<style scoped>
@media (max-width: 1024px){
  nav input{
		display: none;
  }
}
@media (max-width: 1170px){
  nav div a {
    display: none;
  }
  #account {
    margin-right: 30px;
  }

}

#account {
  font-size: 32px;
  color: white;
}

#logout {
  font-size: 32px;
  color: white;
}

.list-box {
  margin-top: 12px;
  display: flex;
}


nav {
  height: 70px;
  display: flex;
  justify-content: space-between;
  background: #1A1D1F;
  margin-bottom: 100px;
}

nav img {
  margin-left: 20px;
  width: 200px;
  height: 100px;
}

nav input {
  width: 375px;
  height: 50px;
  margin-top: 16px;
  background: #111315;
  border-radius: 10px;
  font-style: normal;
  font-weight: 600;
  font-size: 32px;
  line-height: 48px;
  text-align: start;
  color: white; 
}

nav input[type=text]::placeholder {
  font-family: 'Material Icons Outlined';
}


nav div a {
  font-weight: bold;
  color: white;
  text-decoration-line: none;
  margin-right: 20px;
  font-family: 'Poppins';
  font-style: normal;
  font-weight: 600;
  font-size: 24px;
}
#check {
  margin-left: 8px;
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