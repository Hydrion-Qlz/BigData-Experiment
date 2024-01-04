<template>
  <a-layout class="layout">
    <a-affix :offset-top="0">
      <custom-header role="admin" style="z-index: 2"></custom-header>
    </a-affix>
    <a-layout>
      <a-layout-sider class="sider" width="200">
        <a-menu
            v-model:selectedKeys="selectedKeys"
            mode="inline"
            class="menu"
            :style="{ height: '100%', borderRight: 0 }"
            @click="menuClick"
        >
          <a-menu-item key="overview">
            <notification-outlined/>
            系统总览
          </a-menu-item>
          <a-menu-item key="user-maintain">
            <user-outlined/>
            用户维护
          </a-menu-item>
          <a-menu-item key="page-verify">
            <safety-outlined/>
            页面审核
          </a-menu-item>
        </a-menu>
      </a-layout-sider>
      <a-layout style="padding: 0 24px 24px">
        <a-layout-content
            :class="getContentClass"
        >
          <router-view/>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-layout>
</template>

<script>
import {UserOutlined, SafetyOutlined, NotificationOutlined} from '@ant-design/icons-vue';
import CustomHeader from "@/components/header.vue"

export default {
  name: "admin-layout",
  components: {
    UserOutlined,
    SafetyOutlined,
    NotificationOutlined,
    CustomHeader
  },
  data() {
    return {
      selectedKeys: ['overview'],
      collapsed: false,
    }
  },
  computed: {
    getContentClass() {
      for (const key of this.selectedKeys) {
        if ('overview' === key) {
          return "content"
        }
      }
      return "content white-background"
    }
  },
  methods: {
    menuClick(data) {
      this.$router.push({
        name: data.key
      })
    }
  },
  created() {
    console.log(this.$route)
    const selectKey = this.$route.name
    this.selectedKeys = [selectKey]
  }
}
</script>

<style scoped>
.layout {
  height: 100%;
}

.menu {
  padding-top: 20px;
  height: 100%;
}

.content {
  margin-top: 24px;
  /*background: rgb(242, 247, 255);*/
  padding: 24px;
}

.white-background {
  background-color: #fff;
}
</style>