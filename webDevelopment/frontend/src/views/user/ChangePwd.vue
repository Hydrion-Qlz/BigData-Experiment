<template>
  <div class="changePwd-outer">
    <div class="steps">
      <a-steps v-model:current="currentStep" type="navigation">
        <a-step title="邮箱验证" :disabled="true">
        </a-step>
        <a-step title="输入新密码" :disabled="true"/>
      </a-steps>
    </div>
    <div class="form">
      <div class="step-content" v-if="currentStep===0">
        <a-form
            :model="emailInfo"
            :label-col="{ span: 6 }"
            :wrapper-col="{ span: 18 }"
        >
          <a-form-item
              label="邮箱"
              name="email"
              :rules="[{ required: true, message: '请输入邮箱地址!' }]"
          >
            <a-input v-model:value="emailInfo.email" placeholder="请输入邮箱"></a-input>
          </a-form-item>

          <a-form-item
              label="验证码"
              name="verificationCode"
              :rules="[{ required: true, message: '请输入验证码' }]"
          >
            <a-input-search
                v-model:value="emailInfo.verificationCode"
                placeholder="输入验证码"
                enter-button="发送"
                @search="sendVerification"
                :disabled="verificationDisable"
            />
          </a-form-item>
          <a-form-item :wrapper-col="{ offset: 12, span: 16 }">
            <a-button type="primary" @click="submitValidateEmail" :disabled="emailInfo.correctVerificationCode === ''">
              下一步
            </a-button>
          </a-form-item>
        </a-form>
      </div>
      <div class="step-content" v-else>
        <a-form
            :model="pwdInfo"
            :label-col="{ span: 6 }"
            :wrapper-col="{ span: 18 }"
        >
          <a-form-item
              label="密码"
              name="password"
              :rules="[{ required: true, message: '请输入密码!' }]"
          >
            <a-input-password v-model:value="pwdInfo.password" placeholder="请输入密码"></a-input-password>
          </a-form-item>

          <a-form-item
              label="确认密码"
              name="confirmPwd"
              :rules="[{ required: true, message: '请再次输入密码!' }]"
          >
            <a-input-password v-model:value="pwdInfo.confirmPwd" placeholder="请输入密码"></a-input-password>
          </a-form-item>
          <a-form-item :wrapper-col="{ offset: 12, span: 16 }">
            <a-button type="primary" @click="submitChangePwd">提交</a-button>
          </a-form-item>
        </a-form>
      </div>
    </div>
  </div>
</template>

<script>
import {message} from "ant-design-vue";
import {encrypt} from "@/common/utils";

export default {
  name: "ChangePwd",
  data() {
    return {
      currentStep: 0,
      emailInfo: {
        email: "qlz_huo@163.com",
        verificationCode: "",
        correctVerificationCode: ""
      },
      pwdInfo: {
        password: "",
        confirmPwd: ""
      }
    }
  },
  computed: {
    verificationDisable() {
      return !this.validateEmail()
    }
  },
  methods: {
    submitValidateEmail() {
      if (this.emailInfo.verificationCode !== this.emailInfo.correctVerificationCode) {
        message.error("验证码错误，请输入正确的验证码")
        return;
      }

      message.success("验证成功")
      this.currentStep++
      console.log(this.emailInfo)
    },
    async submitChangePwd() {
      if (!this.validatePassword()) {
        message.error('密码最少8位，需包含数字和小写字母');
        return;
      }

      if (this.pwdInfo.password !== this.pwdInfo.confirmPwd) {
        message.error("两次输入密码不一致")
        return;
      }

      await this.$post("/user/changePwd", {
        email: this.emailInfo.email,
        password: encrypt(this.pwdInfo.password)
      })

      message.success("密码修改成功")
    },
    async sendVerification() {
      if (this.emailInfo.email !== localStorage.getItem("email")) {
        message.error("输入邮箱与当前用户信息不符，请重新输入")
        return
      }
      const res = await this.$get("/login/sendVerificationCode", {
        email: localStorage.getItem("email")
      })
      message.success("发送成功")
      this.emailInfo.correctVerificationCode = res.data.data
      console.log(this.emailInfo.correctVerificationCode)
    },
    validateEmail() {
      const pattern = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      return pattern.test(this.emailInfo.email)
    },
    validatePassword() {
      const pattern = /^(?=.*\d)(?=.*[a-z]).{8,}$/;
      return pattern.test(this.pwdInfo.password);
    },
  },
  created() {
    this.emailInfo.email = localStorage.getItem("email")
  }
}
</script>

<style scoped>
.changePwd-outer {
  /*border: 1px solid red;*/
}

.steps {
  width: 500px;
  margin: 0 auto;
}

.form {
  width: 400px;
  margin: 50px auto;

}
</style>