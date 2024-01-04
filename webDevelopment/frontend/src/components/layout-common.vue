<template>
  <div class="home">
    <!-- header -->
    <custom-header role="common"></custom-header>
    <router-view></router-view>
  </div>
</template>

<script>
import CustomHeader from "@/components/header.vue"

export default {
  name: 'layout-common',
  components: {
    CustomHeader
  }
}
</script>
<style scoped>
.home {
  height: 100%;
  background-image: url(@/assets/background.jpg);
  background-size: 100% 100%;
  /*background-size: 100% 100%;*/
  /*background-color: rgba(255, 255, 255, 0.8);*/
}

/*.home::before {*/
/*  content: "";*/
/*  position: absolute;*/
/*  top: 0;*/
/*  left: 0;*/
/*  width: 100%;*/
/*  height: 100%;*/
/*  background-image: url(@/assets/background.jpg);*/
/*  background-size: 100% 100%;*/
/*  opacity: 0.85;*/
/*}*/
</style>