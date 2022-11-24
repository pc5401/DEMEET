<template>
  <router-link :to="{ name: 'DetailView', params: {pid: pid}}" class="project-box">
    <div class="box">
      <span class="material-symbols-outlined" id="terminal">terminal</span>
      <span style="font-size: 16px">{{ demeet.project.pjtName }}</span>
    </div>
    <div class="box">
      <span class="material-symbols-outlined" id="group">group</span>
      <span style="font-size: 16px">Members</span>
      <span class="member">{{ member.length }}</span>
    </div>
    <div class="box" v-if="!demeet.project.sessionActivate">
      <span class="material-symbols-outlined" id="cancel">cancel_presentation</span>
      <div class="off">OFF</div>
    </div>
    <div class="box" v-if="demeet.project.sessionActivate">
      <span class="material-symbols-outlined" id="video">video_chat</span>
      <div class="on">ON</div>
    </div>
    <div class="host-box">
      <img class="host-img" v-if="host.profileImagePath!==null" :src="`${host.profileImagePath}`" alt="">
      <img class="host-img" v-else src="@/assets/기본프로필.jpg" alt="">
      <div class="host-data">
        <p>{{ host.nickname }}</p>
        <p>{{ host.email }}</p>
      </div>
    </div>
  </router-link>
</template>

<script>
import { defineComponent,ref } from "vue"
import { useAccountStore } from "@/stores/account"
export default defineComponent({
  props: ['project'],
  setup(props) {
    const demeet = ref(props)
    const account = useAccountStore()
    const pid = ref(demeet.value.project.pid)
    const member = ref(demeet.value.project.member)
    const host = ref(member.value.find(user => user.uid === demeet.value.project.projectOwner))
    return {
      demeet,
      host,
      member,
      pid,
      account,
      
    }
  }

})
</script>

<style scoped>
#terminal {
  font-size: 24px;
  color: white;
  margin-right: 20px;
  margin-bottom: 10px;
  overflow: hidden
}

#group {
  font-size: 24px;
  color: white;
  margin-right: 20px;
  margin-bottom: 10px;
}

#cancel{
  font-size: 24px;
  margin-right: 20px;
}

#video {
  font-size: 24px;
  margin-right: 20px;
}
.project-box {
  margin-bottom: 50px;
  margin-left: 25px;
  margin-right: 25px;
  text-decoration: none;
  width: 280px;
  height: 280px;
  background: #111315;
  border-radius: 10px;
  display: flex;
  color: white;
  display: flex;
  justify-content: flex-end;
  flex-direction: column;
  float: right;
}
.project-box:hover {
  transform: scale(1.1);
}
.on {
  width: 28px;
  height: 20px;
  background: #E73939;
  border-radius: 5px;
  font-weight: 600;
  font-size: 16px;
  line-height: 120%;
  color: #FFFFFF;
}

.off {
  width: 36px;
  height: 20px;
  border-radius: 5px;
  background: #9E9E9E;
  font-family: 'Inter';
  font-style: normal;
  font-weight: 600;
  font-size: 16px;
  line-height: 120%;
  color: #FFFFFF;
}

.member {
  width: 24px;
  height: 20px;
  background: #BFB2FF;
  border-radius: 5px;
  font-weight: 600;
  font-size: 16px;
  line-height: 120%;
  color: #FFFFFF;
  margin-left: 28px;
}

.host-img {
  height: 80px;
  width: 80px;
  margin: 6px;
  border-radius: 50%;
}

.box {
  margin-top: 20px;
  margin-left: 50px;
  display: flex;
}

.host-box {
  background: #9E9E9E;
  border-radius: 5px;
  width: 280px;
  height: 92px;
  margin-top: 28px;
  display: flex;
}

.host-data {
  text-align: start;
  margin-left: 10px;
}
</style>