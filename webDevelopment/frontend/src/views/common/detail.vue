<template>
  <div class="detail-outer">
    <div class="source-web">
      <img :src="sourceImgUrl" alt="原始图片">
    </div>
    <div class="similar-web-list">
      <a-tabs v-model:activeKey="activeTabs" class="tabs">
        <a-tab-pane v-for="(algorithmInfo, index) in similarWebInfoList" :key="index"
                    :tab="getTabName(algorithmInfo.algorithmName) "
                    class="tab-pane"
                    forceRender>
          <div class="tab-content">
            <div class="similar-web-item" v-for="item in algorithmInfo.webList" :key="item.id"
                 @click="previewImage(item)">
              <img :src="item.imgUrl" alt="相似图片">
              <div class="web-item-detail-info">
                <div class="web-name">{{ item.targetUrl }}</div>
                <div class="similarity">similarity: {{ item.similarity }}</div>
              </div>
            </div>
          </div>
        </a-tab-pane>
      </a-tabs>
    </div>
    <a-modal v-model:visible="modalVisible"
             :destroyOnClose="true"
             title="是否使用该页面进行替换"
             width="700px"
             centered
             :bodyStyle="{
               height: '600px',
               overflow: 'auto'
             }"
    >
      <template #footer>
        <a-button type="primary" @click="handleReplaceSelect">确定</a-button>
      </template>
      <img :src="previewImgUrl" alt="预览图片">
    </a-modal>
  </div>
</template>

<script>
import {getImgUrl} from "@/common/utils";

export default {
  name: "detail",
  data() {
    return {
      sourceImgUrl: '',
      similarWebInfoList: [],
      modalVisible: false,
      previewImgUrl: '',
      selectedWebInfo: {},
      activeTabs: 0
    }
  },
  methods: {
    async getSimilarWebInfo() {
      const result = await this.$get("/similarWeb/getSimilarWebInfo", {
        url: this.$route.query.url,
        color: this.$route.query.color,
        category: this.$route.query.category,
        userId: localStorage.getItem("id")
      })
      console.log(result)
      let res = result.data.data

      this.sourceImgUrl = getImgUrl(res.inputUrl)
      let algorithmDict = {}
      res.similarWebInfoList.forEach(item => {
        if (algorithmDict[item.algorithm] === undefined) {
          algorithmDict[item.algorithm] = this.similarWebInfoList.length
          this.similarWebInfoList.push({
            algorithmName: item.algorithm,
            webList: []
          })
        }
        item.imgUrl = getImgUrl(item.targetUrl)
        this.similarWebInfoList[algorithmDict[item.algorithm]].webList.push(item)
      })
      // this.similarWebInfoList = res.similarWebInfoList
      console.log(this.similarWebInfoList)
    },
    handleReplaceSelect() {
      this.modalVisible = false
      console.log(this.selectedWebInfo)
      this.$router.push({
        name: "replace",
        query: {
          sourceId: this.selectedWebInfo.sourceId,
          targetId: this.selectedWebInfo.targetId
        }
      })
    },
    previewImage(item) {
      this.modalVisible = true
      this.previewImgUrl = item.imgUrl
      this.selectedWebInfo = item
    },
    getTabName(algorithmName) {
      return algorithmName === 'HTMLSimilarity' ? '文档内容筛选' : '文档结构筛选'
    }
  },
  async created() {
    await this.getSimilarWebInfo()
  }
}
</script>

<style scoped>
.detail-outer {
  width: 100%;
  height: calc(100% - 55px);
  display: flex;
  flex-direction: row;
  padding: 20px 40px;
}

.source-web {
  width: 45%;
  height: 100%;
  background-color: rgba(255, 255, 255, .6);
  border-radius: 10px;
  z-index: 2;
  margin-right: 20px;
  overflow: auto;
}

.similar-web-list {
  flex: 1;
  height: 100%;
  background-color: rgba(255, 255, 255, .6);
  border-radius: 10px;
  padding: 10px 30px 20px;
  display: flex;
  flex-direction: column;
}

.tab-content {
  height: 100%;
  overflow: auto;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-between;
}

.similar-web-item {
  width: 165px;
  height: 225px;
  /*border: 1px solid #000;*/
  margin-bottom: 20px;
  overflow: hidden;
  position: relative;

}

.similar-web-item:hover {
  box-shadow: 2px 2px 8px 2px rgb(31 31 31 / 40%);
}

.similar-web-item .web-item-detail-info {
  position: absolute;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 35px;
  background-color: rgba(0, 0, 0, .5);
  color: #fff;
  font-size: 12px;
  text-align: center;
}

img {
  width: 100%;
}
</style>
<style>
.ant-tabs-content-holder {
  overflow: auto;
}
</style>