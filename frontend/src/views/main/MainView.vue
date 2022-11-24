<template>
    <MainNav class="nav"/>
    <MainHeader />
</template>

<script>
import MainNav from '@/views/main/MainNav'
import MainHeader from '@/views/main/MainHeader'
export default {
  components: {
    MainNav,
    MainHeader
  }
}
</script>

<style>
.nav {
  width:80%; 
  margin:auto;
}
</style>