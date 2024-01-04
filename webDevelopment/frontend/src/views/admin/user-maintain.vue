<template>
  <div class="filter">
    <a-space direction="vertical">
      <a-space size="large">
        <a-space>
          用户名:
          <a-input v-model:value="filterData.username" placeholder="请输入用户名"/>
        </a-space>

        <a-space>
          邮箱:
          <a-input v-model:value="filterData.username" placeholder="请输入邮箱"/>
        </a-space>

        <a-space>
          年龄:
          <a-input-number v-model:value="filterData.age" style="width: 200px" placeholder="请输入年龄"/>
        </a-space>
      </a-space>
      <a-space>
        <a-space>
          性别:
          <a-select v-model:value="filterData.gender" style="width: 200px" placeholder="请输入性别">
            <a-select-option value="男">男</a-select-option>
            <a-select-option value="女">女</a-select-option>
          </a-select>
        </a-space>

        <a-space>
          状态:
          <a-select v-model:value="filterData.state" style="width: 200px" placeholder="请输入性别">
            <a-select-option :value="0">正常</a-select-option>
            <a-select-option :value="1">禁用</a-select-option>
          </a-select>
        </a-space>
        <a-button type="primary" @click="confirmFilter">确定</a-button>
      </a-space>
    </a-space>
  </div>

  <a-table :columns="columns" :data-source="onshowData">
    <template #bodyCell="{ column, record, index }">
      <template v-if="column.key === 'id'">
        {{ index + 1 }}
      </template>

      <template v-if="column.key === 'hobbies'">
        <a-tag
            v-for="tag in record.hobbies"
            :key="tag"
            :color="'blue'"
        >
          {{ tag }}
        </a-tag>
      </template>
      <template v-if="column.key === 'state'">
        <span v-if="record.state === 0">正常</span>
        <span v-else-if="record.state === 1">禁用</span>
      </template>
      <template v-if="column.key === 'action'">
        <a-popconfirm
            title="确定执行该操作?"
            ok-text="确认"
            cancel-text="取消"
            @confirm="confirm(record)"
        >
          <a v-if="record.state === 0">禁用</a>
          <a v-else>解除</a>
        </a-popconfirm>
      </template>
    </template>
  </a-table>
</template>

<script>
import {message} from "ant-design-vue";

export default {
  name: "user-maintain",
  data() {
    return {
      columns: [],
      userInfoList: [],
      onshowData: [],
      filterData: {
        username: "",
        email: "",
        age: "",
        gender: "",
        state: ""
      }
    }
  },
  methods: {
    async getAllUserInfo() {
      const res = await this.$get("/admin/getAllUserInfo")
      this.userInfoList = res.data.data
      this.userInfoList.forEach(item => {
        item.hobbies = item.hobbies.split(",")
        console.log()
      })
      this.onshowData = this.userInfoList
      console.log(this.userInfoList)
    },
    confirm(record) {

      record.state = record.state === 0 ? 1 : 0
      // todo: 接口
      const res = this.$post("/admin/setUserState", {
        userId: record.id,
        state: record.state
      })
      if (record.state === 1) {
        message.success("解除禁用成功")
      } else {
        message.success("禁用成功")
      }
    },
    confirmFilter() {
      console.log(this.filterData)
      this.onshowData = this.userInfoList.filter(item => {
        if (this.filterData.username !== "" && this.filterData.username === item.username) {
          return true
        }
        if (this.filterData.email !== "" && this.filterData.email === item.email) {
          return true
        }
        if (this.filterData.age !== "" && this.filterData.age === item.age) {
          return true
        }
        if (this.filterData.gender !== "" && this.filterData.gender === item.gender) {
          return true
        }
        if (this.filterData.state !== "" && this.filterData.state === item.state) {
          return true
        }
        return false
      })
      console.log(this.onshowData)
    }
  },
  async created() {
    this.columns = [{
      title: "序号",
      key: 'id',
      dataIndex: 'id',
      width: 80
    }, {
      title: '用户名',
      dataIndex: 'username',
      key: 'username',
    }, {
      title: '邮箱',
      dataIndex: 'email',
      key: 'email',
    }, {
      title: '年龄',
      dataIndex: 'age',
      key: 'age',
      width: 80
    }, {
      title: '性别',
      dataIndex: 'gender',
      key: 'gender',
      width: 80
    }, {
      title: '爱好',
      dataIndex: 'hobbies',
      key: 'hobbies',
    }, {
      title: '状态',
      dataIndex: 'state',
      key: 'state',
    }, {
      title: 'Action',
      key: 'action',
    }];
    await this.getAllUserInfo()
  }
}
</script>

<style scoped>
.filter {
  margin-bottom: 24px;
}
</style>