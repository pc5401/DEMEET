<template>
  <div class='backdrop'>
    <AlertView v-if="isError" @close-modal="isError=false">
      <h3>{{ alertText }}</h3>
    </AlertView>
    <div class='account-box'>
      <div class='account-left'>
        <div style='display:flex; justify-content:center;'>
      <h1 class='sign-head'>Sign In</h1>
    </div>
    <form @submit.prevent="login(credentials)" class='account-info'>
      <p><input v-model.trim="credentials.email" type="email" placeholder="Email" class="input-prop"></p>
      <p><input v-model.trim="credentials.password" type="password" placeholder="password" class="input-prop"></p>
      <div class="btn-box">
        <button class="login-btn">Sign In</button>
        <div class="createuser">
          <router-link :to="{ name: 'SignupView' }">회원가입</router-link>
        </div>
      </div>
    </form>
    <p class="text">비밀번호를 잊으셨다면 <router-link :to="{ name: 'FindPassword' }">여기</router-link>를 클릭하세요</p>
    </div>
      <div class='account-right'>
        <h1 class="main-logo">DEMEET</h1>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent,ref } from "vue"
import { useAccountStore } from "@/stores/account"
import AlertView from "@/views/main/AlertView"
export default defineComponent({
  components: {
    AlertView
  },
  setup() {
    const alertText = ref('')
    const isError = ref(false)
    const credentials = {
      email: '',
      password: ''
    }
    const account = useAccountStore()
    const login = (data) => {
      if (data.email === ''){
        alertText.value = '이메일를 입력해주세요.'
        isError.value = true
      }else {
        if (data.password) {
          account.login(data)
        }else {
          alertText.value = '비밀번호를 입력해주세요.'
          isError.value = true
        }
      }
    }
    return {
      account,
      credentials,
      alertText,
      isError,
      login
    }
  },
})
</script>

<style scoped>
@media (max-width: 1024px){
  .account-box{
    flex-direction: column-reverse;
    align-items: center;
  }
}
h3 {
  margin: 25px;
  color: white;
}
.btn-box {
  display: flex;
  justify-content: space-between;
}
.text {
  color: white;
  font-size: 12px;
}
.text a {
  color: #6dcef5;
}
.main-logo {
      /* width: 5rem;
      height: 5rem; */
  color: rgba(26, 15, 31, 0.3);
  font-size: 4rem;
  background: linear-gradient(to right, rgba(255, 0, 214, 0.9), rgba(0, 224, 255, 0.9));
  -webkit-text-stroke: transparent;
  margin: 0%;
  -webkit-background-clip: text;
  letter-spacing: -0.25rem;
}

.backdrop {
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  background: linear-gradient( to bottom,  rgba(255, 1, 214, 0.3), rgba(7, 219, 254, 0.3));
  width: 100%;
  height: 100vh;
}

.account-box {
  position: absolute;
  background-color : rgba(26, 15, 31, 0.3);
  background-clip: content-box;
  display: flex;
  width: 60vw;
  height: 60vh;
  flex-grow: 1;
  justify-content: space-evenly;
}

.account-left {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.account-right {
  display: flex;
  justify-content: center;
  align-items: center;
}

input::placeholder {color:white;}

.logo {
    color: rgba(26, 15, 31, 0.3);
    font-size: 4rem;
    background: linear-gradient(to right, rgba(255, 0, 214, 0.9), rgba(0, 224, 255, 0.9));
    -webkit-text-stroke: transparent;
    /* -webkit-background-clip: text; */
    letter-spacing: -0.25rem;
    }

  
.input-prop {
  color: white;
  background: transparent;
  border-top: none;
  border-bottom: solid;
  border-left: none;
  border-right: none;
  border-color: rgba(255, 1, 214, 1);
  font-size : 1.2rem;
  line-height: 2;
}

.sign-head {
  color:rgba(255, 1, 214, 0);

  -webkit-text-stroke: 0.125rem rgb(255, 255, 255);
  letter-spacing: -0.25rem;
  word-spacing: -0.75rem;
  margin: 1rem;
  
  font-family: 'Poppins';
  font-style: italic;
  font-weight: 900;
  font-size: 4rem;
  line-height: 5rem;
  display: flex;
}

.createuser {
  margin-top: 4px;
}
.createuser a {
  color: white;
  text-decoration: none;
  margin-left: 8px;
}
.createuser a:hover {
  color: rgba(255, 1, 214, 1);
}

.login-btn {
  width: 60px;
  height: 30px;
  background: linear-gradient(90deg, #FF00D6 8.81%, #00E0FF 94.11%);
  border-radius: 10px;
  
}
</style>