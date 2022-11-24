<template>
		<video id="mainVideoID" ref="vidoeRoot"  autoplay/>
</template>

<script>
import { onMounted, onUpdated, ref } from 'vue'
export default {
	name: 'OvVideo',

	props: {
		streamManager: Object,
	},

	setup(props) {

	const vidoeRoot = ref(null)

	const openFullscreen = () => {
		var elem = document.getElementById("mainVideoID")
		if (elem.requestFullscreen) {
    elem.requestFullscreen()
  } else if (elem.webkitRequestFullscreen) { /* Safari */
    elem.webkitRequestFullscreen()
  } else if (elem.msRequestFullscreen) { /* IE11 */
    elem.msRequestFullscreen()
  }
	}

	onMounted(() => {
			props.streamManager.addVideoElement(vidoeRoot.value)
		})

	onUpdated(()=>{
		props.streamManager.addVideoElement(vidoeRoot.value)
	})



		return {
			vidoeRoot,
			openFullscreen
		}
	},
};
</script>
<style scoped>
	#mainVideoID {
		width: 58VW;
		margin-top: 4vh;
	}

	@media all and (max-width: 1024px){
			#mainVideoID {
		width: 90VW;
		margin-top: 8vh;
	}
}
</style>