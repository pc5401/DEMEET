<template>
  <div class="container">
    <div>
      <input 
      type="text" 
      class="search" 
      placeholder="Search User"
      v-model="searchUser" 
      @input="findData(searchUser)"
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
            v-if="member.some(res => res.uid === user.uid)"  
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
</template>

<script>
import { defineComponent, ref } from "vue"
import { useAccountStore } from "@/stores/account"
import router from "@/router"

export default defineComponent({
  props: ['project'],
  setup(props) {
    const account = useAccountStore()
    account.fetchProfile()
    const demeet = ref(props)
    const searchUser = ''
    const searchList = ref([]) 
    const userData = ref(account.userList)
    const member = ref(demeet.value.project.member)
    const payload = ref({
      pid: demeet.value.project.pid,
      uid: -1
    })
    const add = (addUser) => {
      payload.value.uid = addUser.uid
      if (payload.value.uid !== -1){
        account.addUser(payload.value)
      }
    }
    const remove = (addUser) => {
      payload.value.uid = addUser.uid
      if (payload.value.uid !== -1){
        account.removeUser(payload.value)
        router.go({name: 'DetailView'})
      }
    }
    const findData = (inputData) => {
      if (inputData.length != 0){
        const result = account.userList.filter(user => user.nickname.includes(inputData))
        result.push(...account.userList.filter(user => user.email.includes(inputData)))
        const res = new Set(result)
        const resData = [...res]
        searchList.value = resData.slice(0,3)
      }
    }
    
    return {
      demeet,
      searchUser,
      searchList,
      account,
      member,
      userData,
      findData,
      add,
      remove,
    }
  },
  async created() {
    await this.account.fetchUserList()
  }
})
</script>

<style scoped>
.user-list {
  margin-top: 50px;
  width: 328px;
  height: 250px;
  background: #333333;
  display: flex;
  flex-direction: column;
  border-radius: 10px;
}
.container {
  margin: 30px;
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
  background: #333333;
  color: white;
  border-radius: 5px;
}
.plus {
  font-size: 16px;
  margin: auto;
}

.cancel {
  font-size: 16px;
  margin: auto;
}
</style>