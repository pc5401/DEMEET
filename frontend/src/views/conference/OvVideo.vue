<template>
	<video id="videoID" class="videoDefault" :class="{ active: userSpeakStatus }"></video>
</template>

<script>
import {ref} from 'vue'

export default {
	name: 'OvVideo',

	props: {
		streamManager:{
			type:Object,
			default: () => {
				return {}
			}
		},
	},

	setup(props){
		const userSpeakStatus = ref(false)

		props.streamManager.on('publisherStartSpeaking', () => {  // 말할때
			userSpeakStatus.value = true
		})

		props.streamManager.on('publisherStopSpeaking', () => {  // 말 안 할때
			userSpeakStatus.value = false
		})

		return {
			userSpeakStatus,
			}
	},

	mounted () {
		this.streamManager.addVideoElement(this.$el)
	},
};
</script>
<style scoped>

.videoDefault {
	height: 100%;
	max-width: 160px;
	width: 100%;
	max-width: 240px;
	margin: 0.5rem 0.5rem 0.5rem 0.5rem;
	cursor: pointer;
	border: 2px solid rgb(255, 255, 255,0);
}
.active{
	border: 2px solid #9BA4B4;
}

</style>>