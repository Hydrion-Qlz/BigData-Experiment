<template>
  <div class="publish-page-outer">
    <a-button type="primary" class="new-page-btn" @click="publishNewPage">发布新页面</a-button>
    <web-card-list ref="webCardList" :data="data" :orderOptions="orderOptions" type="publish"></web-card-list>

    <a-modal v-model:visible="modalVisible" title="添加新页面" @ok="submit">
      <div class="form">
        <a-form
            :model="newPageInfo"
            :label-col="{ span: 6 }"
            :wrapper-col="{ span: 18 }"
            autocomplete="off"
        >
          <a-form-item
              label="网页链接"
              :rules="[{ required: true, message: 'Please input the url' }]"
          >
            <a-input v-model:value="newPageInfo.pageUrl" placeholder="请输入网页链接"/>
          </a-form-item>

          <a-form-item
              label="描述"
              :rules="[{ required: false}]"
          >
            <a-textarea v-model:value="newPageInfo.desc" auto-size placeholder="请输入网页描述"/>
          </a-form-item>
        </a-form>
      </div>
    </a-modal>
  </div>
</template>

<script>
import WebCardList from "@/components/WebCardList";
import dayjs from "dayjs";
import {message} from "ant-design-vue";

export default {
  name: "PublishPage",
  components: {
    WebCardList
  },
  data() {
    return {
      data: [],
      orderOptions: [],
      modalVisible: false,
      newPageInfo: {
        pageUrl: "",
        desc: ""
      }
    }
  },
  methods: {
    publishNewPage() {
      // message.success("发布成功")
      this.modalVisible = true
    },
    submit() {
      if (!this.newPageInfo.pageUrl.startsWith("http")) {
        message.error("请输入正确的网页链接")
        return;
      }

      message.success("已加入处理队列中")
      this.modalVisible = false
    }
  },
  created() {
    this.data = [
      {
        key: 1,
        imgSrc: "https://img.zcool.cn/community/01611f59bf380ea801212fb724bce3.png@1280w_1l_2o_100sh.png",
        url: "http://www.geihutu.com",
        score: 4,
        publishTime: "2022-03-12 12:12",
        starTime: "2022-03-15 11:33",
        starNum: 10,
        star: true
      }, {
        key: 4,
        imgSrc: "https://img.zcool.cn/community/010d705707268e6ac72579488cc3a6.jpg@1280w_1l_2o_100sh.jpg",
        url: "http://www.qtmy.com",
        score: 1.6,
        publishTime: "2022-02-12 12:12",
        starTime: "2022-03-15 19:33",
        starNum: 7,
        star: true
      }, {
        key: 5,
        imgSrc: "https://img.zcool.cn/community/01464955f6b6ab32f875a132dd461d.jpg@1280w_1l_2o_100sh.jpg",
        url: "http://www.cont.cn",
        score: 2.4,
        publishTime: "2022-03-15 19:12",
        starTime: "2022-03-19 17:33",
        starNum: 98,
        star: true
      }, {
        key: 6,
        imgSrc: "https://img.zcool.cn/community/015f265631f6af6ac72548784f7f9e.jpg@1280w_1l_2o_100sh.jpg",
        url: "http://www.mqy.com",
        score: 3.3,
        publishTime: "2022-03-14 12:12",
        starTime: "2022-03-20 12:33",
        starNum: 24,
        star: true
      },]

    this.data.forEach(item => {
      item.publishTime = dayjs(item.publishTime)
      item.starTime = dayjs(item.starTime)
    })
    this.data.sort((a, b) => a['publishTime'].unix() - b['publishTime'].unix())

    this.orderOptions = [
      {
        label: "发布时间",
        value: 'publishTime'
      }, {
        label: "收藏量",
        value: "starNum"
      }, {
        label: "评分",
        value: "score"
      },
    ]
  }
}
</script>

<style scoped>
.publish-page-outer {
  position: relative;
}

.new-page-btn {
  position: absolute;
  left: 0;
  top: 0;
  width: 120px;
  height: 32px;
  z-index: 2;
}

.form {
  width: 300px;
  margin: 0 auto;
}
</style>