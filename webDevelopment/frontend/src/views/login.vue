<template>
  <div class="login-outer">
    <div class="login-box">
      <div class="left">
        <a-tabs v-model:activeKey="activeTab">
          <a-tab-pane key="login">
            <template #tab>
              <div class="tab-name">
                登录
              </div>
            </template>
            <div class="login-content">
              <a-space direction="vertical" style="width:100%" size="large">
                <a-space direction="vertical" style="width:100%">
                  <div class="label">邮箱</div>
                  <a-input v-model:value="loginState.email" placeholder="Please input your email" class="input-box">
                    <template #prefix>
                      <user-outlined type="user"/>
                    </template>
                  </a-input>
                </a-space>
                <a-space direction="vertical" style="width:100%">
                  <div class="label">密码</div>
                  <a-input-password v-model:value="loginState.password" placeholder="Please input your password"
                                    class="input-box">
                    <template #prefix>
                      <lock-outlined/>
                    </template>
                  </a-input-password>
                  <div>
                    <a-checkbox v-model:checked="loginState.remember" class="remember-pwd">记住密码</a-checkbox>
                    <a-button type="link" class="forget-pwd">Forget Your Password?</a-button>
                  </div>
                </a-space>
                <a-button type="primary" class="action-btn" @click="login">登录</a-button>
              </a-space>
            </div>
          </a-tab-pane>

          <a-tab-pane key="register">
            <template #tab>
              <div class="tab-name">
                注册
              </div>
            </template>
            <div class="register-content">
              <a-space direction="vertical" style="width:100%">
                <a-space direction="vertical" style="width:100%">
                  <div class="label">邮箱</div>
                  <a-input v-model:value="registerState.email" placeholder="Please input your email" class="input-box">
                    <template #prefix>
                      <user-outlined type="user"/>
                    </template>
                  </a-input>
                </a-space>
                <a-space direction="vertical" style="width:100%">
                  <div class="label">验证码</div>
                  <a-input-search
                      v-model:value="registerState.verificationCode"
                      placeholder="input verification code"
                      enter-button="发送"
                      @search="sendVerification"
                      class="input-box"
                      :disabled="verificationDisable"
                  />
                </a-space>
                <a-space direction="vertical" style="width:100%">
                  <div class="label">
                    密码
                    <a-tooltip placement="top">
                      <question-circle-outlined/>
                      <template #title>
                        密码长度至少8位，需至少包含小写字母和数字
                      </template>
                    </a-tooltip>
                  </div>
                  <a-input-password v-model:value="registerState.password" placeholder="Please input your password"
                                    class="input-box">
                    <template #prefix>
                      <lock-outlined/>
                    </template>
                  </a-input-password>
                </a-space>
                <a-space direction="vertical" style="width:100%">
                  <div class="label">确认密码</div>
                  <a-input-password v-model:value="registerState.confirmPwd" placeholder="Please input your password"
                                    class="input-box">
                    <template #prefix>
                      <lock-outlined/>
                    </template>
                  </a-input-password>
                </a-space>

                <a-button type="primary" class="action-btn register-btn" @click="register">注册</a-button>
              </a-space>
            </div>
          </a-tab-pane>
        </a-tabs>
      </div>
      <div class="right">
        <div class="top-label">
          New at <span>Assistance?</span>
        </div>
        <div class="underline"></div>
        <div class="comment">
          <div>
            You can look through excellent design example and similar webpage retrieval here!
          </div>
          <div class="feature">
            <check-outlined class="check"/>
            Explore Web Design Inspiration
          </div>
          <div class="feature">
            <check-outlined class="check"/>
            Manage Your Favorite Conveniently
          </div>
          <div class="feature">
            <check-outlined class="check"/>
            Discover Similar Design To Your Work
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {CheckOutlined, UserOutlined, LockOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue";
import {message} from 'ant-design-vue';
import {encrypt} from "@/common/utils";

export default {
  name: "login",
  components: {
    CheckOutlined,
    UserOutlined,
    LockOutlined,
    QuestionCircleOutlined
  },
  data() {
    return {
      loginState: {
        email: 'huo@163.com',
        password: 'aaa123123',
        remember: true
      },
      registerState: {
        email: 'huo@163.com',
        verificationCode: '123123',
        correctVerificationCode: "",
        password: 'aaa123123',
        confirmPwd: 'aaa123123'
      },
      activeTab: "login"
    }
  },
  computed: {
    verificationDisable() {
      return !this.validateEmail()
    }
  },
  methods: {
    async login() {
      console.log(this.loginState)
      console.log(encrypt(this.loginState.password))
      if (this.loginState.email === '' || !this.validateEmail()) {
        message.error("请输入正确的邮箱地址")
        return;
      }

      const res = await this.$post("/login/login", {
        email: this.loginState.email,
        password: encrypt(this.loginState.password)
      })

      console.log(res)
      if (res.data.code === 200) {
        message.success("登陆成功")
        // console.log(this.loginState.password, encrypt(this.loginState.password))
        this.$router.push({
          name: "query"
        })

        localStorage.setItem("email", this.loginState.email)
      } else {
        message.error(res.data.message)
      }
    },
    async sendVerification() {
      console.log("发送验证码")
      const res = await this.$get("/login/sendVerificationCode", {
        email: this.registerState.email
      })

      this.registerState.correctVerificationCode = res.data.data
      this.registerState.verificationCode = res.data.data
      console.log(this.registerState)

      message.success("发送成功")
    },
    async register() {
      console.log("注册")
      if (this.registerState.correctVerificationCode === "") {
        message.error("请获取邮箱验证码")
        return;
      }

      if (this.registerState.verificationCode !== this.registerState.correctVerificationCode) {
        message.error("验证码错误，请输入正确的验证码")
        return;
      }

      if (!this.validatePassword()) {
        message.error('密码最少8位，需包含数字和小写字母');
        return;
      }

      if (this.registerState.password !== this.registerState.confirmPwd) {
        message.info("两次输入密码不一致")
        return;
      }

      const res = await this.$post("/login/register", {
        email: this.registerState.email,
        password: encrypt(this.registerState.password)
      })
      console.log(res)
      if (res.data.code === 200) {
        message.success("注册成功, 请登录")

        this.activeTab = 'login'
        this.loginState.email = this.registerState.email
        this.loginState.password = ""
      } else {
        message.error(res.data.message)
      }
    },
    validatePassword() {
      const pattern = /^(?=.*\d)(?=.*[a-z]).{8,}$/;
      return pattern.test(this.registerState.password);
    },
    validateEmail() {
      const pattern = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      return pattern.test(this.registerState.email)
    },
  }
}
</script>

<style scoped>
.login-outer {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url(@/assets/background.jpg);
  background-size: 100% 100%;
}

.login-box {
  width: 950px;
  height: 550px;
  /*border: 2px solid red;*/
  border-radius: 20px;
  backdrop-filter: blur(10px);
  display: flex;
  flex-direction: row;
  background: rgba(0, 0, 0, 0.4);
}

.left, .right {
  flex: 1;
  height: 100%;
  border-radius: 20px;
  padding: 40px 40px;
}

.left {
  padding: 30px 40px;
}

.login-outer .left {
  border-top-right-radius: 0;
  border-bottom-right-radius: 0;
  background: rgba(255, 255, 255, 0.6);
}

.login-outer .right {
  border-top-left-radius: 0;
  border-bottom-left-radius: 0;
}

.left .tab-name {
  width: 182px;
  font-size: 30px;
  font-weight: bold;
  text-align: center;
}

.left .login-content {
  width: 100%;
  height: 100%;
  padding: 50px 40px;
  /*border: 2px solid red;*/
}

.left .forget-pwd {
  float: right;
}

.left .remember-pwd {
  float: left;
  line-height: 32px;
}

.left .action-btn {
  width: 100%;
  font-weight: bold;
  color: #fff;
  font-size: 16px;
  height: 40px;
}

.left .label {
  color: rgba(0, 0, 0, .6);
  font-size: 14px;
}

.left .input-box {
  /*background-color: rgba(255, 255, 255, 0.6);*/
  height: 35px;
}

.left .register-content {
  width: 100%;
  height: 100%;
  padding: 20px 40px;
}

.left .register-btn {
  margin-top: 20px;
}

.right .top-label {
  font-size: 26px;
  color: #fff;
  font-weight: bold;
}

.right .top-label span {
  font-size: 44px;
}

.right .underline {
  margin-top: 10px;
  width: 100px;
  height: 12px;
  background: linear-gradient(-45.00deg, rgba(73, 143, 242, 1.00) 47%, rgba(73, 101, 242, 1.00) 100%);
}

.right .comment {
  margin-top: 50px;
  color: rgba(255, 255, 255, 0.6);
}

.right .comment div:nth-child(1) {
  font-size: 18px;
  margin-bottom: 20px;
}

.right .comment .feature {
  font-size: 16px;
  line-height: 50px;
}

.right .comment .check {
  font-size: 18px;
  color: rgba(73, 101, 242, 1.00);
  margin-right: 10px;
}
</style>
<style>
.ant-tabs {
  height: 100%;
}

.ant-tabs-content {
  height: 100%;
}


</style>