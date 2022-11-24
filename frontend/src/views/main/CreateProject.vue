<template>
<div class="bar"></div>
<AlertView v-if="isError" @close-modal="isError=false">
  <h3>{{ alertText }}</h3>
</AlertView>
<div class="name-bar">
  <input class="name-input" v-model="projectData.pjt_name" type="text" placeholder="pjt-name">
</div>
  <div class="create-container">
    <div>
      <input 
      type="text" 
      class="search" 
      placeholder="Search User"
      v-model.trim="searchUser" 
      @input="findData()"
      >
      <div class="user-list">
        <div 
        v-for="user in searchList"
        :key="user.uid"
        :user="user"
        >
          <div class="user-box" v-if="user.uid !== account.profile.uid">
            <div class="user-data">
              <img class="user-img" v-if="user.profileImagePath===null" src="@/assets/기본프로필.jpg" alt="">
              <img class="user-img" v-else-if="user.profileImagePath!==null" :src="`${user.profileImagePath}`" alt="">
              <div>
                <div class="text-type">{{ user.nickname }}</div>
                <div class="text-type">{{ user.email }}</div>
              </div>
            </div>
            <button
            v-if="projectData.memberList.some(res => res === user.uid)"  
            @click="remove(user)"  
            class="cancle-btn"
            >
              <span class="cancel">취소</span>
            </button>
            <button
            v-else
            @click="add(user)" 
            class="plus-btn"
            >
              <span class="plus">초대</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <button class="create-btn" @click="startProject(projectData)">start project</button>
</template>

<script scoped>
import { ref,defineComponent } from "vue"
import { useAccountStore } from "@/stores/account"
import AlertView from "@/views/main/AlertView"
export default defineComponent({
  components: {
    AlertView
  },
  setup() {
    const alertText = ref('')
    const isError = ref(false)
    const projectData = ref({
      pjt_name: '',
      memberList: [],
    })
    const add = (addUser) => {
      projectData.value.memberList.push(addUser.uid)
    }
    const remove = (removeUser) => {
      projectData.value.memberList.splice(projectData.value.memberList.findIndex(res => res===removeUser.uid),1)
    }
    const account = useAccountStore()
    const userData = ref(account.userList)
    const searchUser = ref('')
    const searchList = ref([]) 
    const findData = () => {
      if (searchUser.value.length != 0){
        const result = account.userList.filter(user => user.nickname.includes(searchUser.value))
        result.push(...account.userList.filter(user => user.email.includes(searchUser.value)))
        const res = new Set(result)
        const resData = [...res]
        searchList.value = resData.slice(0,3)
      }
    }
    const startProject = (data) => {
      if (data.pjt_name === '') {
        alertText.value = '프로젝트 이름을 작성해주세요'
        isError.value = true
      }else {
        account.createProject(data)
      }
    }
    return {
      account,
      userData,
      searchUser,
      searchList,
      projectData,
      alertText,
      isError,
      findData,
      add,
      remove,
      startProject
    }
  },
  async created () {
    await this.account.fetchUserList()
  }
})
</script>

<style>
.bar {
  width: 300px;
  height: 34px;
}

h3 {
  margin: 25px;
  color: white;
}

.name-input {
  margin-top: 10px;
  width: 320px;
  height: 24px;
  font-family: 'Inter';
  font-style: normal;
  font-weight: 600;
  font-size: 24px;
  line-height: 120%;
  border-style: solid;
  border-color: linear-gradient(90deg, #FF00D6 8.81%, #00E0FF 94.11%);
  background: #333333;
  color: white;
  border-radius: 5px;
}

.name-bar{
  display: flex;
  justify-content: center;
}

.user-list {
  margin-top: 50px;
  width: 328px;
  height: 250px;
  background: #333333;
  display: flex;
  flex-direction: column;
  border-style: solid;
  border-color: linear-gradient(90deg, #FF00D6 8.81%, #00E0FF 94.11%);
  border-radius: 10px;
}
.create-container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.search {
  margin-top: 50px;
  width: 320px;
  height: 24px;
  font-family: 'Inter';
  font-style: normal;
  font-weight: 600;
  font-size: 24px;
  line-height: 120%;
  border-style: solid;
  border-color: linear-gradient(90deg, #FF00D6 8.81%, #00E0FF 94.11%);
  background: #333333;
  color: white;
  border-radius: 5px;
}

#mail {
  margin: 4px;
}

.user-box {
  margin: 14px;
  width: 300px;
  height: 50px;
  background: linear-gradient(0deg, rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0.2)), rgba(45, 140, 255, 0.1);
  border-style: solid;
  border-color: linear-gradient(90deg, #FF00D6 8.81%, #00E0FF 94.11%);
  border-radius: 15px;
  display: flex;
  justify-content: space-between;
}

.user-img {
  margin: 5px;
  width: 40px;
  height: 40px;
  border-radius: 100%;
}

.text-type {
  margin: 2px;
  text-align: start;
  color: white;
}

.plus {
  font-size: 16px;
  margin: auto;
}

.cancel {
  font-size: 16px;
  margin: auto;
}
.user-data {
  font-size: 16px;
  display: flex;
}

.plus-btn{
  display: flex;
  align-items: flex-start;
  padding: 0px;
  width: 60px;
  height: 36px;
  border-radius: 10px;
  margin-top: 6px;
  background: #4C4E50;
  color: white;
  margin: 5px;
}
.plus-btn:hover {
  color: green;
}

.cancle-btn {
  display: flex;
  align-items: flex-start;
  padding: 0px;
  width: 60px;
  height: 36px;
  border-radius: 10px;
  margin-top: 6px;
  background: #4C4E50;
  color: white;
  margin: 5px;
}

.cancle-btn:hover {
  color: red;
}
.create-btn {
  margin: 20px;
  width: 120px;
  height: 30px;
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
.create-btn:hover{
  transform: scale(1.1);
  color: greenyellow;
}
</style>