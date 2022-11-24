<template>
  <div class="profile-view">
    <MainNav/>
    <div class='profile-box'>
      <AlertView v-if="isError" @close-modal="isError=false">
        <h3>{{ alertText }}</h3>
      </AlertView>
      <div class='profile-bg'>
        <img class='bg-image' src="@/assets/profile_bg.jpg" alt="">
      </div>
      <div class='profile-id'>
        <div class='profile-image' @click="isInput=true">
          <input v-if="isInput" type="file" accept=".jpg,.png" class="ex_file" @change="fileUpload">
          <div class="image-btn" v-if="isInput">
            <button @click="cancel">취소</button>
            <button @click="profileDelete">삭제</button>
          </div>
          <img 
          v-if="account.profile.profileImagePath && !isInput" 
          :src="`${ account.profile.profileImagePath }`" 
          >
          <img  v-if="account.profile.profileImagePath===null && !isInput" src="@/assets/기본프로필.jpg" alt="">
        </div>
        <div class='profile-detail'>
          <div class='profile-rough'>
            <div class="name">
              <h1 v-if="!isEdit">{{ account.profile.nickname }} 
              <span class="material-symbols-outlined" id="edit" v-if="!isEdit" @click="isEdit=true">edit</span>
              </h1>
              <div class="name-edit">
                <input v-if="isEdit" v-model="name" @input="limitNickname" type="text">
                <div class="name-btn">
                  <span class="material-symbols-outlined" id="done" v-if="isEdit" @click="onUpdate(name)">done</span>
                  <span class="material-symbols-outlined" id="close" v-if="isEdit" @click="isEdit=false">close</span>
                </div>
              </div>
            </div>
            <div>
              <h3>{{ account.profile.email }}</h3>
            </div>
          </div>
          <div class='change-password'>
            <button class="pwedit-btn" @click="isModalViewed=true">Change Password</button>
            <ModalView v-if="isModalViewed" @close-modal="isModalViewed=false">
              <ChangePassword />
            </ModalView>
          </div>
        </div>
      </div>

    </div>
    <div class='endproject'>
      <EndprojectList 
      v-for="endProject in account.endProjects"
      :key="endProject.pid"
      :endProject="endProject"
      />
    </div>
    <div class="hidden">
      <p class="signout" @click="signout()">회원탈퇴</p>
    </div>
  </div>
</template>

<script>
import { defineComponent,ref } from "vue"
import { useAccountStore } from "@/stores/account"
import MainNav from '@/views/main/MainNav'
import ModalView from '@/views/main/ModalView'
import ChangePassword from '@/views/account/ChangePassword'
import EndprojectList from '@/views/account/EndProjectList'
import router from "@/router"
import AlertView from "@/views/main/AlertView"
export default defineComponent({
  components: {
    MainNav,
    ModalView,
    AlertView,
    ChangePassword,
    EndprojectList
  },
  data() {
    return{
      isModalViewed: false,
      isEdit: false,
      isInput: false
    }
  },
  setup() {
    const alertText = ref('')
    const isError = ref(false)
    const profileImage = ''
    const account = useAccountStore()
    const name = ref('')
    const isNicknameError = ref(false)


    const onUpdate = (data) => {
      if (data === '') {
        alertText.value = '변경할 닉네임을 입력하세요'
        isError.value = true
      }else {
        if (isNicknameError.value) {
          alertText.value = '닉네임을 10글자 이하로 작성해 주세요.'
          isError.value = true
        }else{
          account.changeName(data)
        }
      }
    }
    const signout = () => {
      if (confirm("회원탈퇴 하시겠습니까?")){
        account.signout()
      }
    }
    const fileUpload = (e) =>{
      const fileInput = ref(e.target.files[0])
      if (fileInput.value !== null) {
        account.changeImage(fileInput.value)
      }else {
        alertText.value = '이미지를 업로드해주세요.'
        isError.value = true
      }
    }
    const profileDelete = () => {
      if(confirm('프로필사진을 삭제하시겠습니까?')){
        account.profileDeleteImage()
      }
    }
    const cancel = () => {
      router.go({name : 'ProfileView'})
    }
    const limitNickname = () => {
      if(name.value.length <= 10) {
        isNicknameError.value = false
      }else{
        isNicknameError.value = true
      }
    }
    account.fetchProfile()
    return {
      profileImage,
      account,
      name,
      onUpdate,
      signout,
      fileUpload,
      cancel,
      profileDelete,
      limitNickname,
      isNicknameError,
      alertText,
      isError,
    }
  },
})
</script>

<style scoped>
@media (max-width: 996px){
  .endproject{
    justify-content: center;
  }
}
@media (max-width: 768px){
  .profile-id {
    display: flex;
    flex-direction: column;
  }
  .profile-detail {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
}

.pw-error {
  color: red;
  font-size: 8px;
  margin: 0%;
  text-align: start;
  margin-left:12px;
}

.image-btn {
  display: flex;
  justify-content: space-around;
  margin: 10px;
}
.image-btn button{
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
  width: 50px;
  height: 30px;
}
.image-btn button:hover {
  transform: scale(1.2);
}
.profile-view {
  width:80%; 
  margin:auto;
}
.profile-image img {
  width: 142px;
  height: 142px;
  border-radius: 50%;
}

h1 {
  color: white;
  margin-left: 28px;
}

h3 {
  color: white;
}
.name {
  display: flex;
}
.name-edit {
  display: flex;
}
.name-btn {
  margin-top: 30px;
  margin-left: 8px;
}
.bg-image {
  width: 100%;
  height: 40vh;
}
.profile-id {
  display: flex;
  border: solid;
  margin-bottom: 2rem;
  padding: 24px;
}
.profile-detail {
  width: 100%;
  display: flex;
  justify-content: space-between;
}
.pjt {
  width: 20%;
  height: 20rem;
  background-color: white;
}

.endproject {
  border: solid;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-evenly;
}

input {
  margin-top: 30px;
  box-sizing: border-box;
  width: 200px;
  height: 30px;

  border: 1px solid #A9A9A9;
  border-radius: 8px;
}

.pwedit-btn {
  width: 148px;
  height: 50px;
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
}
.pwedit-btn:hover {
  transform: scale(1.1);
}
#edit {
  color: white;
}

#edit:hover {
  color: #2D68FE;
  transform: scale(1.4);
}

#done {
  color: white;
}

#done:hover {
  color: green;
  transform: scale(1.4);
}

#close {
  color: white;
}

#close:hover {
  color: red;
  transform: scale(1.4);
}
.change-password {
  display: flex;
  align-items: center;
}

.hidden {
  display: flex;
  justify-content: flex-end;
}
.signout {
  color: black;
}

.signout:hover {
  color: red;
}
</style>