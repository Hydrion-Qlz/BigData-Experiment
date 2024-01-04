<template>
  <a-button type="primary" @click="setVisible(true, 'http://mangaraw.to')">show image preview</a-button>
  <a-image
      :width="200"
      :style="{ display: 'none' }"
      :preview="{
        visible,
        onVisibleChange: setVisible,
      }"
      :src="imgUrl"
  />
</template>

<script>
import {getImgUrl} from "@/common/utils";

export default {
  name: "test",
  data() {
    return {
      visible: false,
      imgUrl: ""
    }
  },
  methods: {
    setVisible(value, url = '') {
      this.visible = value
      if (url) {
        this.imgUrl = getImgUrl(url)
      }
    }
  }
}
</script>

<style scoped>

</style>