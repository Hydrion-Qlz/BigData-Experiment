<template>
  <a-card hoverable style="width: 200px" :bodyStyle="{padding: 0}">
    <template #cover>
      <div class="img-container">
        <a-image alt="example" :src="item.imgSrc"/>
      </div>
    </template>
    <a-card-meta>
      <template #description>
        <div class="desc-container">
          <div class="card-title">
            {{ item.url }}
          </div>
          <a-rate :value="item.score" disabled allow-half/>
          <heart-filled @click="starChange(item, idx)" class="extra-icon"
                        :class="item.star? 'active-star': 'inactive-star'"
                        v-if="type==='star'"/>
          <delete-outlined class="extra-icon" @click="deletePublish(item, idx)" v-else/>
        </div>
      </template>
    </a-card-meta>
  </a-card>
</template>

<script>
import {HeartFilled, DeleteOutlined} from "@ant-design/icons-vue";
import {message, Modal} from "ant-design-vue";

export default {
  name: "WebCard",
  components: {
    HeartFilled,
    DeleteOutlined
  },
  props: {
    item: {
      type: Object,
      default: {}
    },
    idx: {
      type: Number,
      default: 0
    },
    type: {
      type: String,
      default: "star"
    }
  },
  methods: {
    starChange(item, idx) {
      console.log(item)
      if (item.star) {
        message.success("取消收藏成功")
        // this.data.splice(idx, 1)
      } else {
        message.success("添加收藏成功")
      }
      // todo: 请求接口
      item.star = !item.star
      // todo: 通知父组件删除事件
      this.$emit("deleteCard", this.idx)
    },
    deletePublish(item, idx) {
      console.log(item, idx)
      const that = this
      Modal.confirm({
        title: '确定删除该页面？',
        content: "页面删除后无法恢复，确定删除请点击确认按钮",
        onOk() {
          console.log("删除成功")
          that.$emit("deleteCard", that.idx)

          message.success("删除成功")
        },
      });
    }
  }
}
</script>

<style scoped>
.card-title {
  color: #000;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.img-container {
  height: 250px;
  overflow: hidden;
  display: flex;
  flex-direction: row;
  align-items: center;
}

img {
  width: 100%;
}

.desc-container {
  width: 100%;
  padding: 20px 10px;
  position: relative;
}

.extra-icon {
  position: absolute;
  bottom: 26px;
  right: 16px;
  font-size: 20px;
}

.inactive-star {
  color: rgba(140, 140, 140);
}

.active-star {
  color: rgb(255, 57, 57);
}
</style>