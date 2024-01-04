<template>
  <div class="home">
    <!-- header -->
    <a-affix :offset-top="0">
      <custom-header role="user"></custom-header>
    </a-affix>
    <div class="user-client-content">
      <div class="display-detail">
        <div class="menu-list">
          <a-menu @select="menuItemSelect" v-model:selectedKeys="activeKeys">
            <a-menu-item key="userInfo">
              个人信息
            </a-menu-item>
            <a-menu-item key="messageList">
              消息列表
            </a-menu-item>
            <a-menu-item key="changePwd">
              修改密码
            </a-menu-item>
            <a-menu-item key="searchHistory">
              处理记录
            </a-menu-item>
            <a-menu-item key="favoritePage">
              收藏页面
            </a-menu-item>
            <a-menu-item key="publishPage">
              我的发布
            </a-menu-item>
          </a-menu>
        </div>
        <div>
          <div class="title">
            {{ currentTitle }}
          </div>
          <a-divider class="divider"></a-divider>
          <router-view></router-view>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import CustomHeader from "@/components/header.vue"

export default {
  name: "layout-user",
  components: {
    CustomHeader
  },
  data() {
    return {
      currentTitle: "个人信息",
      activeKeys: [],
      titleMap: {
        userInfo: "个人信息",
        messageList: "消息列表",
        changePwd: "修改密码",
        searchHistory: "历史检索",
        favoritePage: "收藏页面",
        publishPage: "我的发布"
      }
    }
  },
  methods: {
    menuItemSelect(data) {
      console.log(data)
      this.currentTitle = this.titleMap[data.key]
      this.$router.push({
        name: data.key
      })
    },
  },
  created() {
    this.activeKeys.push(this.$route.name)
    this.currentTitle = this.titleMap[this.$route.name]
  }
}
</script>

<style scoped>
.home {
  background-color: rgb(245, 245, 245);
  min-height: 100%;
}

.user-client-content {
  width: 100%;
  padding: 30px 10px;
}

.menu-list {
  float: left;
  width: 200px;
  height: 200px;
  /*border: 2px solid red;*/
  background-color: #fff;
  margin-right: 20px;
  text-align: center;

  position: absolute;
  left: -220px;
  top: 0;
}

.display-detail {
  margin: 0 auto;
  width: 750px;
  background-color: #fff;
  min-height: 540px;
  padding: 20px;
  transform: translateX(12%);

  position: relative;
}

.title {
  font-size: 20px;
  line-height: 1;
  font-weight: bold;
}

.divider {
  margin-top: 10px;
}
</style>