<template>
  <header>
    <div class="container">
      <div class="create">
        <div class="create-box" @click="isModalViewed=true">
            <span class="material-symbols-outlined" id="add">add</span>
        </div>
        <ModalView v-if="isModalViewed" @close-modal="isModalViewed=false">
          <CreateProject />
        </ModalView>
      </div>
        <ProjectList 
        v-for="project in projects.projects"
        :key="project.pid"
        :project="project"
        />
    </div>
  </header>
</template>

<script>
import { defineComponent } from "vue"
import { useAccountStore } from "@/stores/account"
import ModalView from '@/views/main/ModalView'
import ProjectList from '@/views/main/ProjectList'
import CreateProject from '@/views/main/CreateProject'
export default defineComponent({
  components: {
    ProjectList,
    ModalView,
    CreateProject
  },
  data() {
    return {
      isModalViewed: false,
    }
  },
  setup() {
    const projects = useAccountStore()
    projects.fetchProjects()
    projects.fetchUserList()
    return {
      projects,
    }
  }
})
</script>

<style scoped>
@media (max-width: 678px){
  .container{
		justify-content: center;
  }
}
header {
  width: 90%;
  margin-left: 5%;
}
#add {
  font-size: 64px;
  color: white;
}

.create {
  text-decoration: none;
}

.create-box {
  margin-bottom: 50px;
  margin-left: 25px;
  margin-right: 25px;
  width: 280px;
  height: 280px;
  background: #111315;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.create-box:hover {
  transform: scale(1.1);
}
.create-box:hover #add{
  transform: scale(1.1);
  color: #54A1FD;
}

.container {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-evenly;
}
</style>