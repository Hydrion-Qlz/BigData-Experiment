<template>
  <div :class="getHeaderClass">
    <div class="header-background"></div>
    <div class="logo" @click="goToMainPage">Assistance</div>
    <div class="info">
      <user-info-drop-down :color="color"/>
    </div>
  </div>
</template>

<script>
import UserInfoDropDown from "@/components/UserInfoDropDown";

export default {
  name: "Header",
  components: {
    UserInfoDropDown
  },
  props: {
    role: String
  },
  data() {
    return {
      color: "#000"
    }
  },
  computed: {
    getHeaderClass() {
      if (this.role === 'common') {
        this.color = '#fff'
      }
      console.log("header " + this.role + "-header")
      return "header " + this.role + "-header"
    }
  },
  methods: {
    goToMainPage() {
      let gotoName = "query"
      if (this.role === "admin") {
        gotoName = "overview"
      }
      this.$router.push({
        name: gotoName
      })

    }
  }
}
</script>

<style scoped>
.header {
  position: relative;
  width: 100%;
  height: 55px;
  box-shadow: 5px 0 10px 2px rgba(31, 31, 31, 0.4);
}

.common-header {
  backdrop-filter: blur(10px);
  color: #fff;
}

.user-header {
  background-color: #fff;
  color: #000;
}

.header .header-background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;

  filter: blur(5px);
  pointer-events: none; /* 添加这一行 */
}

.header .logo {
  margin-left: 60px;
  line-height: 55px;
  font-size: 40px;
  font-weight: bold;
  cursor: pointer;
  width: 230px;
  height: 100%;
}

.header .info {
  position: absolute;
  right: 40px;
  top: 0;
  height: 55px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}
</style>