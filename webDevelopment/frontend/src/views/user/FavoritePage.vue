<template>
  <div class="favorite-outer">
    <web-card-list ref="webCardList" :data="data" :orderOptions="orderOptions"></web-card-list>
  </div>
</template>

<script>
import dayjs from "dayjs";
import WebCardList from "@/components/WebCardList";

export default {
  name: "FavoritePage",
  components: {
    WebCardList
  },
  data() {
    return {
      data: [],
      orderOptions: []
    }
  },
  methods: {},
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
        key: 2,
        imgSrc: "https://img.zcool.cn/community/01094d5b3d6f41a80120b9596ec4ef.jpg@1280w_1l_2o_100sh.jpg",
        url: "http://www.zonta.com",
        score: 3.5,
        publishTime: "2022-03-13 12:12",
        starTime: "2022-03-15 13:33",
        starNum: 11,
        star: true
      }, {
        key: 3,
        imgSrc: "https://img01.haolizi.net/2020/11/29/f2/9/f/f29f8e599bef863a04e9e3f9934291ca.png",
        url: "http://pa.ptasx.com",
        score: 2,
        publishTime: "2022-03-12 12:12",
        starTime: "2022-03-18 12:33",
        starNum: 15,
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

    // this.$refs.webCardList.selectChange()
    this.orderOptions = [
      {
        label: "发布时间",
        value: 'publishTime'
      }, {
        label: '收藏时间',
        value: 'starTime',
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
</style>