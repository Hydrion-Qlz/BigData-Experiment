<template>
  <!--  用户名, 邮箱, 年龄, 性别, 生日,  -->
  <div class="info-outer">
    <a-form
        :model="userinfo"
        @submit="submit"
        :label-col="{ span: 8 }"
        :wrapper-col="{ span: 16 }"
    >
      <a-form-item
          label="用户名"
          name="username"
          placeholder="请输入用户名"
          :rules="[{ required: true, message: 'Please input your username!' }]"
      >
        <a-typography-text v-model:content="userinfo.username" :editable="{triggerType: ['icon','both']}"/>
      </a-form-item>

      <a-form-item
          label="邮箱"
          name="email"
          :rules="[{ required: true }]"
      >
        <!--        <a-input v-model:value="userinfo.username"/>-->
        <a-typography-text>{{ userinfo.email }}</a-typography-text>
      </a-form-item>

      <a-form-item
          label="年龄"
          name="age"
          :rules="[{ required: false }]"
      >
        <a-typography-text v-model:content="userinfo.age" :editable="{triggerType: ['icon','both']}"/>
      </a-form-item>

      <a-form-item
          label="性别"
          name="gender"
          :rules="[{ required: false }]"
      >
        <a-typography-text v-model:content="userinfo.gender" :editable="{triggerType: ['icon','both']}"/>
      </a-form-item>

      <a-form-item
          label="生日"
          name="birthday"
          :rules="[{ required: false }]"
      >
        <a-date-picker v-model:value="userinfo.birthday" :bordered="false" placeholder="请输入你的生日"
                       :locale="locale"/>
      </a-form-item>

      <a-form-item
          name="hobby"
          :rules="[{ required: false }]"
      >
        <template #label>
          爱好
          <a-tooltip title="在下拉框中选择或直接输入，输入时以逗号或空格分隔">
            <question-circle-outlined style="font-size: 12px; color:gray"/>
          </a-tooltip>
        </template>
        <a-select :allowClear="true" :maxTagTextLength="5" mode="tags" :token-separators="[',', ' ']"
                  :options="hobbyOptions" v-model:value="userinfo.hobbies"></a-select>
      </a-form-item>

      <a-form-item :wrapper-col="{ offset: 8, span: 16 }">
        <a-button type="primary" @click="submit">提交</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import {QuestionCircleOutlined} from "@ant-design/icons-vue";
import {message} from "ant-design-vue";
import dayjs from 'dayjs'
import locale from 'ant-design-vue/es/date-picker/locale/zh_CN';

export default {
  name: "UserInfo",
  components: {
    QuestionCircleOutlined,
  },
  data() {
    return {
      userinfo: {},
      hobbyOptions: [],
      locale
    }
  },
  methods: {
    async submit() {
      message.success("提交成功")
      console.log(this.userinfo)
      // todo: 生日有问题, 组件报错
      // this.userinfo.birthday = this.userinfo.birthday.unix()
      this.userinfo.hobbies = this.userinfo.hobbies.join(",")
      // todo: 提交给服务器修改的信息
      const res = await this.$post("/user/updateUserInfo", this.userinfo)
      this.userinfo.hobbies = this.userinfo.hobbies.split(",")

      if (res.data.code === 200) {
        message.success("修改成功")
      }
    },
    async getUserInfo() {
      const email = localStorage.getItem("email")
      const res = await this.$get("/user/getUserInfo", {
        email: email
      })
      console.log(res)

      this.userinfo = res.data.data
      this.userinfo.gender = this.userinfo.gender === 0 ? undefined : this.userinfo.gender
      this.userinfo.age = this.userinfo.age === 0 ? undefined : String(this.userinfo.age)
      this.userinfo.birthday = this.userinfo.birthday === null ? undefined : dayjs(this.userinfo.birthday)
      this.userinfo.hobbies = this.userinfo.hobbies === null ? undefined : this.userinfo.hobbies.split(",")
      // {
      //   username: "hydrion",
      //   email: "qtv@163.com",
      //   age: "12",
      //   birthday: dayjs("2022-01-26"),
      //   gender: "男",
      //   hobby: "1,2,3".split(",")
      // }
    },
    makeOptions() {
      const hobbies = ['游泳', '羽毛球', '射箭', '跑步', '骑行', '乒乓球', '篮球']
      this.hobbyOptions = hobbies.map(item => ({
        value: item,
        label: item
      }))
    },
  },
  async created() {
    await this.getUserInfo()
    this.makeOptions()
  }
}
</script>

<style scoped>
.info-outer {
  width: 350px;
  margin: 0 auto;
}
</style>