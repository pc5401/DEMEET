<template>
  <div id="userContainer">
    <div id="ConferenceUsers">
    <p>유저 목록입니다.</p>
    <div v-for="(user, index) in users" :key="index" :users="users">{{user.clientData.slice(15,-2)}}</div>
    </div>
  </div>

</template>

<script>

export default {
  name: 'ConferenceUsers',
  components: {},
    props:{ 
		publisher:{
			type:Object,
			default: () => {
				return {}
			}
		},
		subscribers:{
			type:Array,
			default: () => {
				return []
			}
		},
		users:{
			type:Array,
			default: () => {
				return []
			}
		}
	},
  setup() {


  
  return {

  }
  },
  create() {},
  mounted() {},
  unmounted() {},
  methods: {},
}
</script>

<style scoped>
#ConferenceUsers {
  background-color: #9BA4B4;
  margin:10px;
  width:300px;
  height: 710px;
  
  border-radius:10px;
  -ms-overflow-style: none; /* for Internet Explorer, Edge */
  scrollbar-width: none; /* for Firefox */
  overflow-y: scroll; 
}
div::-webkit-scrollbar {
  display: none; /* for Chrome, Safari, and Opera */
}

#userContainer {
  background-color:#394867;
  height: 740px;
  padding-top:3px;
  margin-top: 30px;
  border-radius:10px;
}

</style>
