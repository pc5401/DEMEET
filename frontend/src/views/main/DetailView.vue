<template>
<div>
  <MainNav class="nav"/>
    <div class="detail-container">
      <Suspense>
          <template #default>
            <ProjectImage />
          </template>
          <template #fallback>
            Product is loading...
          </template>
        </Suspense>
      <div class="detail-box">
        <Suspense>
          <template #default>
            <ProjectData/>
          </template>
          <template #fallback>
            Product is loading...
          </template>
        </Suspense>
      </div>
    </div>
</div>
</template>

<script>
import { defineComponent,ref } from "vue"
import { useAccountStore } from "@/stores/account"
import { useRoute } from 'vue-router'
import MainNav from '@/views/main/MainNav'
import ProjectData from '@/views/main/ProjectData'
import ProjectImage from '@/views/main/ProjectImage.vue'
export default defineComponent({
  components: {
    MainNav,
    ProjectData,
    ProjectImage
  },
  setup() {
    const route = useRoute()  
    const project_pk = ref(route.params.pid)
    const projectData = useAccountStore()
    projectData.fetchProject(project_pk.value)
    projectData.fetchProfile()
    return {
      projectData,
      project_pk,
    }
    
  },
  async created() {
    await this.projectData.fetchProject(this.project_pk)
  },
})
</script>

<style scoped>
@media (max-width: 1024px){
  .detail-container{
		display: flex;
    flex-direction: column;
    align-items: center;
  }
}
.nav {
  width:80%; 
}

#person {
  font-size: 60px;
  margin-left: 16px;
  margin-top: 16px;
  margin-right: 200px;
}

#edit {
  color:#9E9E9E;
  margin: 16px;
}

.detail-container {
  display: flex;
  justify-content: space-evenly;
}

.detail-box{
  width: 650px;
  background: #111315;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
}


.host-box {
  background: #9E9E9E;
  border-radius: 10px;
  width: 816px;
  height: 92px;
  display: flex;
  align-items: flex-start;
  text-align: start;
}

h1 {
  color: white;
}

</style>