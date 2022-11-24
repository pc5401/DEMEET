<template>
<div id="mainFrame" v-if="streamManager">
	<main-ov-video :streamManager="streamManager" :isDrawing="isDrawing" ref="mainVideo"/>
	<div id="fullScreenIcon"><span  @click="fullScreenFunc" class="material-symbols-outlined">fullscreen</span></div>
	<div id="mainVideoFrame"><p>"{{ mainUserNickName.clientData }}" 님의 비디오</p></div>
</div>
</template>

<script>
import MainOvVideo from './MainOvVideo'
import { onMounted, ref, watch } from 'vue'

export default {
	name: 'UserVideo',

	components: {
		MainOvVideo,
	},

	props: {
		streamManager: {
			type:Object,
			default: () => {
				return {}
			}
		},
		isDrawing:{
			type:Boolean
		}
	},

	setup(props){
	const mainUserNickName = ref(Object)
		
	async function getConnectionData() {
		const { connection } = await props.streamManager.stream
		return  JSON.parse(connection.data.split('%')[0])
		}

	onMounted(async() => {
			const clientInf = await getConnectionData()
			mainUserNickName.value = clientInf
	})

	const clientData =(async() => {
			const clientData = await getConnectionData()
			mainUserNickName.value = clientData
	})

	watch(props.streamManager, () =>{
		clientData()
	})
		return {
			clientData,
			mainUserNickName
		}
	},

	methods:{
		fullScreenFunc(){
			this.$refs.mainVideo.openFullscreen()
		}
	}
}
</script>

<style scoped>
	#mainFrame{
  width: 100%;
  overflow: hidden;
  margin: 0px auto;
  position: relative;
	}

	#fullScreenIcon{
		margin: 0;
		position: absolute;
		width: 10rem;
		top: 93%;
		left: 97%;
		transform: translate(-50%,-50%);
		cursor: pointer;
	}

	#fullScreenIcon .material-symbols-outlined{
		margin: 0;
		font-size: 2rem;
		font-weight:200;
		color: #f1f6f9cc;
		text-align: center;
	}

	#mainVideoFrame{
		background-color: #394867c4;
	}

	#mainVideoFrame p {
		color: #f1f6f9bd;
		margin: 0;
	}

	@media all and (max-width: 1024px){
	#mainVideoFrame{
		background-color: #394867c4;
	}

	#mainVideoFrame p {
		color: #f1f6f9bd;
		margin: 0;
	}
}
</style>>