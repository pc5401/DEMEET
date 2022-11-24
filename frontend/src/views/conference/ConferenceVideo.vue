<template>
  <div class="dump">
		<div id="session">
			<div id="video-container" class="col-md-6">
				<!-- 내얼굴 -->
				<user-video v-if="publisher==secondPublisher" 
				:streamManager="publisher" 
				:isSub="false" 
				@click="$emit('mainVideoChange', publisher)"/>   
				
				<user-video v-for="sub in subscribers" 
				:key="sub.stream.connection.connectionId" 
				:streamManager="sub" 
				:isSub="true"   
				@click="$emit('mainVideoChange', sub)"/>
			</div>
		</div>
  </div>
</template>

<script>
// 튜토리얼 복붙
import UserVideo from './UserVideo'
import { ref } from 'vue'


export default {
  
  name: 'ConferenceVideo',
  components: {UserVideo},
	
	props:{ 
		session:{
			type:Object,
			default: () => {
				return {}
			}
		},
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
		secondPublisher:{
			type:Array,
			default: () => {
				return []
			}
		},
	},

	setup(){
		const isSub = ref(Boolean)

	return {
		isSub
		}
	},
}
</script>

<style scoped>

	#video-container {
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		align-items: stretch;
		overflow: auto;
	}

@media all and (max-width: 1024px){
	#video-container {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: stretch;
		overflow: auto;
	}
}

</style>