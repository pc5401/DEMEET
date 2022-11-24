<template>
<div id="frame" v-if="streamManager">
	<ov-video :streamManager="streamManager" ref="subVideo"/>
	<div class="videoNickName"><p id="userNickName">{{ userNickName.clientData }}</p></div>
	<button v-if="isSub" type="button" class="btn_muted" @click="mutedIcon">
		<span v-if="isMuted" style="color:#F1F6F9" class="material-symbols-outlined">volume_up</span>
		<span v-if="!isMuted" style="color:rgb(234, 71, 71)" class="material-symbols-outlined">volume_off</span>
	</button>
</div>
</template>

<script>
import OvVideo from './OvVideo'
import {  onMounted, ref } from 'vue'

export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	props: {
		streamManager: {
			type:Object,
			default: () => {
				return {}
			}
		},
		isSub:{
			type:Boolean,
		}
	},

	setup(props){
	const userNickName = ref(Object)
	const isMuted = ref(true)


	async function getConnectionData() {
		const { connection } = await props.streamManager.stream
		return  connection.data.split('%')
		}
	
	const mutedIcon = () => {
		isMuted.value = !isMuted.value
		props.streamManager.subscribeToAudio(isMuted.value)
	}

	onMounted(async() => {
		const clientData = await getConnectionData()
		userNickName.value = JSON.parse(clientData[0])

	})

		return {
			userNickName,
			isMuted,
			mutedIcon,
		}
	},
}
</script>
<style scoped>
	#frame{
  width: 100%;
  overflow: hidden;
  margin: 0px auto;
  position: relative;
	}

	div.videoNickName {
		margin: 0;
		position: absolute;
		width: 10rem;
		top: 84%;
		left: 50%;
		transform: translate(-50%,-50%);
	}

	#userNickName{
		margin: 0;
		border-radius: 20%;
		font-size: 1rem;
		color: #f1f6f9cc;
		text-align: center;
		background-color: #14274e86;
	}

	button.btn_muted{
		border-radius: 50%;
		font-size: 8px;
		cursor: pointer;
		padding: 0;
		border: none;
		background: none;
		margin: 0;
		position: absolute;
		width: 2rem;
		top: 84%;
		left: 92%;
		transform: translate(-50%,-50%);
	}

	@media all and (max-width: 1024px){
	#frame{
  width: 100%;
  overflow: hidden;
  margin: 0px auto;
  position: relative;
	}

	div.videoNickName {
		margin: 0;
		position: absolute;
		width: 10rem;
		top: 90%;
		left: 50%;
		transform: translate(-50%,-50%);
	}

	#userNickName{
		margin: 0;
		border-radius: 20%;
		font-size: 1rem;
		color: #f1f6f9cc;
		text-align: center;
		background-color: #14274e86;
	}
}
</style>>