<template>
  <div class="message-outer">
    <a-list :data-source="messageList">
      <template #renderItem="{ item }">
        <a-list-item>
          <div class="message">
            <div :class="item.state? '':'gray'">{{ item.message }}</div>
          </div>

          <template #actions>
            {{ item.time }}
          </template>
        </a-list-item>

      </template>
      <template #footer>
        <!--        <div class="footer">-->
        <!--          <a-pagination-->
        <!--              v-model:current="pageNum"-->
        <!--              v-model:pageSize="pageSize"-->
        <!--              :total="messageList.length"-->
        <!--              show-less-items-->
        <!--              @change="pageChange"/>-->
        <!--        </div>-->
      </template>
    </a-list>
  </div>
</template>

<script>
export default {
  name: "MessageList",
  data() {
    return {
      messageList: [],
      pageNum: 1,
      pageSize: 10
    }
  },
  methods: {
    pageChange(page, pageSize) {
      console.log(page, pageSize)
    },
    async getMessageList() {
      const res = await this.$get("/user/getMessageList", {
        userId: localStorage.getItem("id")
      })

      this.messageList = res.data.data
      this.messageList = this.messageList.sort((a, b) => b.time.localeCompare(a.time))
      // const data = [
      //   'Racing car sprays burning fuel into crowd.',
      //   'Japanese princess to wed commoner.',
      //   'Australian walks 100km after outback crash.',
      //   'Australian walks 100km after outback crash.',
      //   'Australian walks 100km after outback crash.',
      //   'Australian walks 100km after outback crash.',
      //   'Australian walks 100km after outback crash.',
      //   'Man charged over missing wedding girl.',
      //   'Los Angeles battles huge wildfires.',
      //   'Los Angeles battles huge wildfires.',
      //   'Los Angeles battles huge wildfires.',
      //   'Los Angeles battles huge wildfires.',
      //   'Los Angeles battles huge wildfires.',
      //   'Los Angeles battles huge wildfires.',
      // ]
      //
      // this.messageList = data.map(item => ({
      //   message: item,
      //   time: "2023-03-18",
      //   read: true
      // }))
      //
      // this.messageList[0].read = false
      // this.messageList[1].read = false
    }
  },
  async created() {
    await this.getMessageList()
  }
}
</script>

<style scoped>
.message-outer {
  width: 100%;
  /*height: 300px;*/
  /*padding: 10px 30px;*/
  /*border: 2px solid red;*/
}

.gray {
  color: rgba(0, 0, 0, 0.45)
}

.message {
  cursor: pointer;
}

.footer {
  margin: 0 auto;
}
</style>