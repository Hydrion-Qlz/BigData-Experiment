<template>
  <div class="history-outer">
    <div class="filter-area">
      <a-radio-group v-model:value="selectedTable" button-style="solid" @change="radioChange">
        <a-radio-button value="all">全部</a-radio-button>
        <a-radio-button value="processing">处理中</a-radio-button>
        <a-radio-button value="processed">历史记录</a-radio-button>
      </a-radio-group>
    </div>

    <!--  处理中  -->
    <a-table :data-source="onshowData" :columns="columns">
      <template #bodyCell="{ column, text, index, record }">
        <template v-if="column.dataIndex === 'idx'">
          {{ index + 1 }}
        </template>
        <template v-if="column.dataIndex === 'url'">
          <a>{{ text }}</a>
        </template>
        <template v-if="column.dataIndex === 'percentage'">
          <a-progress :percent="text"/>
        </template>
        <template v-if="column.dataIndex === 'action'">
          <a-space v-if="record.percentage!==100">
            <a disabled>预览</a>
            <!--            <a disabled>导出</a>-->
          </a-space>
          <a-space v-else>
            <a>预览</a>
            <!--            <a>导出</a>-->
          </a-space>
        </template>
      </template>
    </a-table>
  </div>
</template>

<script>
export default {
  name: "SearchHistory",
  data() {
    return {
      selectedTable: "all",
      data: [],
      columns: [],
      onshowData: []
    }
  },
  methods: {
    radioChange() {
      if (this.selectedTable === 'all') {
        this.onshowData = this.data
      } else if (this.selectedTable === 'processing') {
        this.onshowData = this.data.filter(item => item.percentage < 100)
      } else {
        this.onshowData = this.data.filter(item => item.percentage === 100)
      }
    },
    async getSearchHistory() {
      const res = await this.$get("/user/getSearchHistory", {
        userId: localStorage.getItem("id")
      })

      this.data = res.data.data
    }
  },
  async created() {
    await this.getSearchHistory()
    this.onshowData = this.data
    this.columns = [
      {
        title: '序号',
        dataIndex: 'idx',
        key: 'idx',
        width: 60
      },
      {
        title: '网页链接',
        dataIndex: 'webUrl',
        key: 'url',
        width: 200,
        ellipsis: true,
      },
      {
        title: '开始时间',
        dataIndex: 'startTime',
        key: 'startTime'
      },
      {
        title: '进度',
        dataIndex: 'percentage',
        key: 'percentage'
      },
      {
        title: '操作',
        dataIndex: 'action',
        key: 'action'
      }
    ]
  }
}
</script>

<style scoped>
.history-outer {
}

.filter-area {
  margin-bottom: 20px;
}
</style>