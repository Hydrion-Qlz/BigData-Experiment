<template>
  <div>
    <div class="filter">
      <div class="filter-content">
        排序方式：
        <a-select
            v-model:value="orderBy"
            class="select"
            :options="orderOptions"
            @change="selectChange">
        </a-select>
      </div>
    </div>
    <div class="content">
      <a-row :gutter="[24,16]">
        <a-col :span="8" v-for="(item, idx) in data">
          <web-card :item="item" :idx="idx" :type="type" @deleteCard="deleteCard"></web-card>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script>
import WebCard from "@/components/WebCard";

export default {
  name: "WebCardList",
  components: {
    WebCard
  },
  props: {
    data: {
      type: Array,
      default: []
    },
    orderOptions: {
      type: Array,
      default: []
    },
    type: {
      type: String,
      default: "star"
    }
  },
  data() {
    return {
      orderBy: "publishTime",
    }
  },
  methods: {
    selectChange() {
      console.log(this.orderBy)
      if (this.orderBy === "score" || this.orderBy === "starNum") {
        this.data.sort((a, b) => a[this.orderBy] - b[this.orderBy])
      } else {
        this.data.sort((a, b) => {
          return a[this.orderBy].unix() - b[this.orderBy].unix()
        })
      }
      console.log(this.data)
    },
    deleteCard(idx) {
      console.log(idx)
      this.data.splice(idx, 1)
    }
  }
}
</script>

<style scoped>

.filter {
  position: relative;
  height: 35px;
}

.filter-content {
  position: absolute;
  right: 20px;
}

.select {
  margin-left: 5px;
  width: 120px;
}

.content {
  margin-top: 10px;
}
</style>