<template>
  <div class="filter">
    <a-space direction="vertical">
      <a-space size="large">
        <a-space>
          发布用户:
          <a-input v-model:value="filterData.username" placeholder="请输入用户名"/>
        </a-space>

        <a-space>
          网页链接:
          <a-input v-model:value="filterData.url" placeholder="请输入网页链接"/>
        </a-space>

        <a-space>
          一级分类:
          <a-input v-model:value="filterData.primaryClass" style="width: 200px" placeholder="请输入分类"/>
        </a-space>
      </a-space>
      <a-space>
        <a-space>
          二级分类:
          <a-input v-model:value="filterData.secondClass" style="width: 200px" placeholder="请输入分类"/>
        </a-space>
        <a-space>
          状态:
          <a-select v-model:value="filterData.state" style="width: 200px" placeholder="请选择状态">
            <a-select-option :value="0">未通过</a-select-option>
            <a-select-option :value="1">已通过</a-select-option>
          </a-select>
        </a-space>
        <a-button type="primary" @click="confirmFilter">确定</a-button>
      </a-space>
    </a-space>
  </div>

  <a-table :columns="columns"
           :data-source="onshowData"
           :pagination="{
             pageSize: 6,
             showSizeChanger: false,
             showQuickJumper: true
           }">
    <template #bodyCell="{ column, record, index }">
      <template v-if="column.key === 'id'">
        {{ index + 1 }}
      </template>

      <template v-if="column.key === 'hobbies'">
        <a-tag
            v-for="tag in record.hobbies"
            :key="tag"
            color="blue"
        >
          {{ tag }}
        </a-tag>
      </template>
      <template v-if="column.key === 'category'">
        {{ record.primaryClass }} / {{ record.secondClass }}
      </template>
      <template v-if="column.key === 'state'">
        <span v-if="record.state === 0">
          <a-tag color="red">
            未通过
          </a-tag>
        </span>
        <span v-else-if="record.state === 1">
          <a-tag color="green">
            通过
          </a-tag>
        </span>
      </template>
      <template v-if="column.key === 'action'">
        <a-space>
          <a @click="setVisible(true, record.url)">查看截图</a>

          <a-popconfirm
              title="确定执行该操作?"
              ok-text="确认"
              cancel-text="取消"
              @confirm="confirm(record, 1)"
          >
            <a>通过</a>
          </a-popconfirm>
          <a-popconfirm
              title="确定执行该操作?"
              ok-text="确认"
              cancel-text="取消"
              @confirm="confirm(record, 2, index)"
          >
            <a>删除</a>
          </a-popconfirm>
        </a-space>
      </template>
    </template>
  </a-table>
  <a-image
      :style="{ display: 'none' }"
      :preview="{
        visible,
        onVisibleChange: setVisible,
      }"
      :src="imgUrl"
  />

</template>

<script>
import {message} from "ant-design-vue";
import {getImgUrl} from "@/common/utils";

export default {
  name: "page-verify",
  data() {
    return {
      columns: [],
      verifyPageList: [],
      onshowData: [],
      filterData: {
        username: "",
        url: "",
        primaryClass: "",
        secondClass: "",
        state: ""
      },
      visible: false,
      imgUrl: ""
    }
  },
  methods: {
    async getWebInfoList() {
      const res = await this.$get("/admin/getVerifyPageList")
      this.verifyPageList = res.data.data

      this.onshowData = this.verifyPageList
      console.log(this.verifyPageList)
    },
    confirm(record, state, index) {
      record.state = state
      const res = this.$post("/admin/setWebPageState", {
        id: record.id,
        state: record.state
      })
      if (record.state === 1) {
        message.success("审核成功")
      } else if (record.state === 2) {
        message.success("删除成功")
        this.verifyPageList.splice(index, 1)
      }
    },
    confirmFilter() {
      console.log(this.filterData)
      this.onshowData = this.verifyPageList.filter(item => {
        let flag = true
        if (this.filterData.username !== "" && this.filterData.username !== item.username) {
          flag = false
        }
        if (this.filterData.url !== "" && this.filterData.url !== item.url) {
          flag = false
        }
        if (this.filterData.primaryClass !== "" && this.filterData.primaryClass !== item.primaryClass) {
          flag = false
        }
        if (this.filterData.secondClass !== "" && this.filterData.secondClass !== item.secondClass) {
          flag = false
        }
        if (this.filterData.state !== "" && this.filterData.state !== item.state) {
          flag = false
        }
        return flag
      })
      console.log(this.onshowData)
    },
    setVisible(value, url = '') {
      this.visible = value
      if (url) {
        this.imgUrl = getImgUrl(url)
      }
    }
  },
  async created() {
    this.columns = [{
      title: "序号",
      key: 'id',
      dataIndex: 'id',
      width: 80
    }, {
      title: '发布用户',
      dataIndex: 'username',
      key: 'username',
    }, {
      title: '网页链接',
      dataIndex: 'url',
      key: 'url',
    }, {
      title: '分类',
      dataIndex: 'category',
      key: 'category',
    }, {
      title: '状态',
      dataIndex: 'state',
      key: 'state',
    }, {
      title: 'Action',
      key: 'action',
    }];
    await this.getWebInfoList()
  }
}
</script>

<style scoped>
.filter {
  margin-bottom: 24px;
}
</style>