<template>
  <div class="replace-outer">
    <!--  图片展示区  -->
    <div class="top-img-area">
      <div class="left-source">
        <div class="banner">源网页</div>
        <div class="display-img">
          <img :src="sourceImgUrl" alt="原网页" id="sourceImg">
        </div>
      </div>
      <div class="preview-center">
        <div class="banner">替换结果</div>
        <div class="display-img">
          <a-spin :spinning="spinning">
            <img :src="getPreviewImgUrl" alt="预览网页" id="previewImg">
          </a-spin>
        </div>
      </div>
      <div class="right-target">
        <div class="banner">目标网页</div>
        <div class="display-img">
          <img :src="targetImgUrl" alt="目标网页" id="targetImg">
        </div>
      </div>
    </div>

    <!--  操作区  -->
    <div class="bottom-action">
      <div class="bottom-left-action">
        <div>
          <a-pagination :default-page-size="1"
                        v-model:current="activeReplaceIdx"
                        showLessItems
                        showQuickJumper
                        :total="replaceDetail.replaceInfo.length"
                        @change="activeReplaceIdxChange"
          ></a-pagination>
          <div style="margin-top: 10px">
            <a-space>
              已选替换：{{ this.confirmedReplace.length }}
              <a-button type="primary" @click="saveResult">保存替换结果</a-button>
            </a-space>
          </div>
        </div>


      </div>
      <div class="bottom-right-action">
        <div>
          <a-pagination :default-page-size="1"
                        v-model:current="activeTargetIdx"
                        showLessItems
                        :total="currentReplaceInfo.target.length"
                        @change="activeTargetIdxChange"
          ></a-pagination>
          <div style="margin-top: 10px">
            <a-space>
              <!--              <a-button @click="previewReplace">预览</a-button>-->
              <a-button @click="selectByPerson">手动选择</a-button>
              <a-button type="primary" @click="confirmReplace">确认选择</a-button>
            </a-space>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {getImgUrl} from "@/common/utils";
import {Modal} from 'ant-design-vue';
import axios from "axios";
import {stringify} from "qs";

export default {
  name: "replace3Column",
  data() {
    return {
      picLoadedCount: 0,
      sourceImgUrl: "",
      targetImgUrl: "",
      previewImgUrl: "",
      displayMode: 1,
      replaceDetail: {
        replaceInfo: [
          {
            target: [],
            source: {}
          }
        ]
      },
      currentReplaceInfo: {
        target: [],
        source: {}
      },
      // 使用pagination组件，默认从1开始，使用时候注意减1
      activeReplaceIdx: 1,
      activeTargetIdx: 1,
      confirmedReplace: [],
      sourceSelectSegmentIdSet: new Set(),
      targetSelectSegmentIdSet: new Set(),
      replaceMap: new Map(),
      frameList: [],
      spinning: false
    }
  },
  methods: {
    async getReplaceInfo() {
      const res = await this.$get("/similarWeb/getReplaceInfo", {
        sourceId: this.$route.query.sourceId,
        targetId: this.$route.query.targetId,
      })
      this.replaceDetail = res.data.data
      console.log(this.replaceDetail)
      this.sourceImgUrl = getImgUrl(this.replaceDetail.sourceInfo.url)
      this.targetImgUrl = getImgUrl(this.replaceDetail.targetInfo.url)
      this.currentReplaceInfo = this.replaceDetail.replaceInfo[0]
      // this.activeTargetIdxChange()
    },
    activeReplaceIdxChange(page, pagesize) {
      this.currentReplaceInfo = this.replaceDetail.replaceInfo[page - 1]
      this.drawBounds()
    },
    activeTargetIdxChange(page, pagesize) {
      this.drawBounds()
    },
    previewReplace() {
      console.log("预览替换效果")
    },
    confirmReplace() {
      const that = this
      let sourceSegmentId = this.currentReplaceInfo.source.segmentId
      let targetSegmentId = this.currentReplaceInfo.target[this.activeTargetIdx - 1].segmentId

      if (this.sourceSelectSegmentIdSet.has(sourceSegmentId)) {
        console.log("source segment处理过")
        // 如果原网页当前Segment已经处理过则提醒是否覆盖
        Modal.confirm({
          title: "提示",
          content: "原网页该 Segment 已经进行过替换，是否覆盖原替换？",
          onOk() {
            // todo: 在this.confirmedReplace也删掉该信息
            that.sourceSelectSegmentIdSet.delete(sourceSegmentId)
            that.targetSelectSegmentIdSet.delete(targetSegmentId)
            that.replaceMap.delete(sourceSegmentId)
            that.handleConfirm(sourceSegmentId, targetSegmentId)
          }
        })
      } else if (this.targetSelectSegmentIdSet.has(targetSegmentId)) {
        // todo: 如果目标网页当前Segment已经处理过则提醒是否覆盖
        console.log("source segment处理过")
      } else {
        this.handleConfirm(sourceSegmentId, targetSegmentId)
      }
    },
    async handleConfirm(sourceSegmentId, targetSegmentId) {
      this.sourceSelectSegmentIdSet.add(sourceSegmentId)
      this.targetSelectSegmentIdSet.add(targetSegmentId)
      this.replaceMap.set(sourceSegmentId, targetSegmentId)
      console.log(this.sourceSelectSegmentIdSet, this.targetSelectSegmentIdSet)
      this.confirmedReplace.push({
        source: this.currentReplaceInfo.source,
        target: this.currentReplaceInfo.target[this.activeTargetIdx - 1]
      })
      this.spinning = true
      const res = await this.$post("/similarWeb/makeNewReplace", {
        sourceId: this.$route.query.sourceId,
        targetId: this.$route.query.targetId,
        replaceReqItemList: this.confirmedReplace
      })
      console.log(res)
      this.previewImgUrl = getImgUrl(this.replaceDetail.saveFolder, 'replace')
      this.spinning = false

      if (this.activeReplaceIdx !== this.replaceDetail.replaceInfo.length - 1) {
        this.activeReplaceIdx++
        this.activeTargetIdx = 1
        this.currentReplaceInfo = this.replaceDetail.replaceInfo[this.activeReplaceIdx - 1]
      }
    },
    selectByPerson() {
      console.log("手动选择")
    },
    drawBounds() {
      // 清除上次画的框
      if (this.frameList.length) {
        for (let ele of this.frameList) {
          ele.parentNode.removeChild(ele)
        }
        this.frameList = []
      }

      // 在原网页和目标网页上画出框
      let sourceInfo = this.currentReplaceInfo.source
      let targetInfo = this.currentReplaceInfo.target[this.activeTargetIdx - 1]

      let sourceEle = document.querySelector("#sourceImg")
      let newSourceBound = this.boundTrans(sourceInfo.bound,
          this.replaceDetail.sourceInfo.width,
          this.replaceDetail.sourceInfo.height,
          sourceEle.scrollWidth,
          sourceEle.scrollHeight)
      this.drawBound(newSourceBound, "#sourceImg")

      let targetEle = document.querySelector("#sourceImg")
      let newTargetBound = this.boundTrans(targetInfo.bound,
          this.replaceDetail.targetInfo.width,
          this.replaceDetail.targetInfo.height,
          targetEle.scrollWidth,
          targetEle.scrollHeight)
      this.drawBound(newTargetBound, "#targetImg")
    },
    drawBound(bound, selector) {
      console.log(bound)
      let ele = document.querySelector(selector)
      let frame = document.createElement("div")
      this.frameList.push(frame)
      ele.parentNode.appendChild(frame)

      frame.style.border = '1px solid red'
      frame.style.position = 'absolute'
      frame.style.width = bound.width + 'px'
      frame.style.height = bound.height + 'px'
      frame.style.left = bound.x + 'px'
      frame.style.top = bound.y + 'px'
      frame.style.zIndex = 10
      ele.parentNode.scrollTo({top: bound.y - 100, behavior: 'smooth'})
    },
    boundTrans(bound, oldWidth, oldHeight, newWidth, newHeight) {
      // console.log(bound, oldWidth, oldHeight, newWidth, newHeight)
      // 将所有都等比例变换
      return {
        "x": bound.x / oldWidth * newWidth,
        "y": bound.y / oldHeight * newHeight,
        "width": bound.width / oldWidth * newWidth,
        "height": bound.height / oldHeight * newHeight
      }
    },
    onLoadFunc(selector) {
      const that = this
      document.querySelector(selector).onload = function () {
        that.picLoadedCount++
        console.log(selector, that.picLoadedCount)
        if (that.picLoadedCount === 2) {
          that.activeTargetIdxChange()
        }
      }
    },
    saveResult() {
      // 获取源代码文件
      console.log(this.previewImgUrl)
      this.$get("/similarWeb/saveReplaceResult", {
            sourceId: this.$route.query.sourceId,
            targetId: this.$route.query.targetId
          }, {
            responseType: 'blob'
          }
      ).then(response => {
        const blob = new Blob([response.data], {type: 'text/html'});
        const downloadUrl = URL.createObjectURL(blob);

        const link = document.createElement('a');
        link.href = downloadUrl;
        link.download = 'index.html';

        document.body.appendChild(link);
        link.click();

        URL.revokeObjectURL(downloadUrl);
      });

      // 发送 HTTP 请求获取图片的二进制数据
      console.log(this.previewImgUrl)
      axios({
        url: this.previewImgUrl,
        method: 'GET',
        responseType: 'blob'
      }).then(response => {
        const blob = new Blob([response.data], {type: 'image/png'});
        const downloadUrl = URL.createObjectURL(blob);

        const link = document.createElement('a');
        link.href = downloadUrl;
        link.download = 'screenshot.png';

        document.body.appendChild(link);
        link.click();

        URL.revokeObjectURL(downloadUrl);
      });
    }
  },
  computed: {
    getPreviewImgUrl() {
      return this.previewImgUrl === "" ? this.sourceImgUrl : this.previewImgUrl
    }
  },
  async created() {
    console.log(this.$route.query.sourceId)
    console.log(this.$route.query.targetId)

    await this.getReplaceInfo()

    this.onLoadFunc("#sourceImg")
    this.onLoadFunc("#targetImg")
  }
}
</script>

<style scoped>
.replace-outer {
  width: 100%;
  height: calc(100% - 55px);
  display: flex;
  flex-direction: column;
  padding: 10px 30px;
}

.top-img-area {
  flex: 1;
  height: 100px;
  width: 100%;
  display: flex;
  flex-direction: row;
  margin-bottom: 15px;
}

.bottom-action {
  height: 90px;
  width: 100%;
  /*border: 2px solid red;*/
  /*flex-shrink: 0;*/
  display: flex;
  flex-direction: row;
}

.banner {
  height: 30px;
  color: #000;
  font-size: 20px;
  font-weight: bold;
  text-align: center;
}

.left-source, .preview-center, .right-target {
  flex: 1;
  height: 100%;
  /*border: 2px solid red;*/
  background-color: #eee;
}

.left-source, .preview-center {
  margin-right: 10px;
}

.display-img {
  height: calc(100% - 30px);
  overflow: auto;
  position: relative;
}

.bottom-left-action, .bottom-right-action {
  flex: 1;
  background-color: #fff;
  text-align: center;
  padding-top: 5px;
}

.bottom-left-action {
  margin-right: 20px;
}

img {
  width: 100%;
}
</style>