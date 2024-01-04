<template>
  <div class="avatar">
    <a-avatar style="background-color: #1890ff">
      <template #icon>
        <user-outlined/>
      </template>
    </a-avatar>
  </div>
  <div class="dropdown">
    <a-dropdown placement="bottom">
      <div class="ant-dropdown-link">
        <a-space>
          <a-badge :dot="unReadMessageCount>0">
            <a :style="{color: color}" class="dropdown-text" @click.prevent>
              {{ username }}
            </a>
          </a-badge>
          <down-outlined/>
        </a-space>
      </div>

      <template #overlay>
        <a-menu>
          <a-menu-item>
            <a href="javascript:;" @click="openPersonalInfo">个人信息</a>
          </a-menu-item>
          <a-menu-item>
            <a-badge :count="unReadMessageCount" :offset="[3, -1]">
              <a href="javascript:;" @click="openMessage">系统消息</a>
            </a-badge>
          </a-menu-item>
          <a-menu-item>
            <a href="javascript:;" @click="openChangePwd">修改密码</a>
          </a-menu-item>
          <a-menu-item>
            <a href="javascript:;" @click="quit">退出登录</a>
          </a-menu-item>
        </a-menu>
      </template>
    </a-dropdown>
  </div>
</template>

<script>
import {DownOutlined, UserOutlined} from "@ant-design/icons-vue";
import {message} from "ant-design-vue";

export default {
  name: "UserInfoDropDown",
  components: {
    DownOutlined,
    UserOutlined,
  },
  props: {
    color: {
      type: String,
      default: "#fff"
    }
  },
  data() {
    return {
      unReadMessageCount: 0,
      username: "username"
    }
  },
  methods: {
    openPersonalInfo() {
      message.success("打开个人信息")
      this.$router.push({
        name: 'userInfo'
      })
    },
    openMessage() {
      message.success("打开系统消息")
      this.$router.push({
        name: 'messageList'
      })
    },
    openChangePwd() {
      message.success("修改密码")
      this.$router.push({
        name: 'changePwd'
      })
    },
    quit() {
      // todo: 删除cookie
      message.success("退出成功")
      console.log("退出登录")
    },
    async getUnReadMessageCount() {
      const res = await this.$get("/user/getMessageList", {
        userId: localStorage.getItem("id")
      })
      const message = res.data.data
      console.log(message)
      message.forEach(item => {
        if (item.state === 0) {
          this.unReadMessageCount++
        }
      })
    },
    async getUserName() {
      const email = localStorage.getItem("email")
      const res = await this.$get("/user/getUserInfo", {
        email: email
      })
      console.log(res)

      const userinfo = res.data.data
      localStorage.setItem("id", userinfo.id)
      if (userinfo.username === null) {
        this.username = "user " + userinfo.id
      } else {
        this.username = userinfo.username
      }
    }
  },
  created() {
    this.getUnReadMessageCount()
    this.getUserName()
  }
}
</script>

<style scoped>
.avatar {
  margin-right: 10px;
}

.dropdown {
  font-size: 14px;
}

.dropdown-text {
  color: inherit;
  font-weight: normal;
}

a {
  color: inherit;
}
</style>
<style>
.ant-badge-count {
  height: 16px;
  width: 16px;
  min-width: 16px;
  line-height: 15px;
  text-align: center;
  padding: 0;
}
</style>