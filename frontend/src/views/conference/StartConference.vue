<template>
  <div id="join">
			<a href="/">
				<div id="img-div"><img class='bg-image' src="@/assets/DEMEET_logo.png" alt="" ></div>
			</a>
			<div id="join-dialog" class="jumbotron vertical-center">
				<h1>Join a video session</h1>
				<div class="form-group">
					<p class="text-style">{{demeet.profile.nickname}}</p>	
					<p class="text-style">PROJECT NAME : {{demeet.project.pjtName}}</p>
					
					<p class="text-center">
						<button class="join-btn" @click="$emit('joinSession')">Join!</button>
					</p>
				</div>
			</div>
		</div>
</template>

<script>
import { defineComponent,ref } from 'vue'
import { useRoute } from 'vue-router'
import { useAccountStore } from "@/stores/account"
import axios from 'axios'
export default defineComponent({
  async setup() {
    const demeet = useAccountStore()
    const route = useRoute()  
    const project_pk = ref(route.params.pid)
    await axios(process.env.VUE_APP_API_URL + "projects/" + project_pk.value, {
      method: "get",
      headers: demeet.authHeader
    }).then(res => demeet.project = res.data.project)
    demeet.fetchProfile()
    return {
      demeet
    }
  }
})
</script>

<style>
h1 {
  color: white;
}
.bg-image {
  width: 400px;
  height: 150px;
}
.text-style {
  color: white;
}
.join-btn{
  width: 80px;
  height: 35px;
  background: linear-gradient(90deg, #FF00D6 8.81%, #00E0FF 94.11%);
  border-radius: 10px;
}
.join-btn:hover {
  transform: scale(1.2);
}

</style>