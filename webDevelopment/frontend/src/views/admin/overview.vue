<template>
  <div class="card-outer">
    <div class="card" v-for="(item, idx) in dataList" :key="idx">
      <img :src="item.img" alt="" class="card-img">
      <a-statistic class="card-statistic" :title="item.name" :value="item.data" style="margin-right: 50px"/>
    </div>
  </div>
  <div class="chart" id="chart">
  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: "home",
  data() {
    return {
      dataList: [
        {
          img: require("@/assets/visits.png"),
          name: "用户总数",
          data: 893
        }, {
          img: require("@/assets/purchases.png"),
          name: "活跃用户数量",
          data: 183
        }, {
          img: require("@/assets/shoppings.png"),
          name: "当前在线",
          data: 74
        }, {
          img: require("@/assets/messages.png"),
          name: "页面总数",
          data: 11233
        },
      ]
    }
  },
  methods: {
    getChartXAxis() {
      // 获取当前时间
      const currentDate = new Date();

      // 获取一周前的时间
      const oneWeekAgo = new Date(currentDate.getTime() - (7 * 24 * 60 * 60 * 1000));

      // 初始化日期数组
      const datesArr = [];

      // 循环生成每个日期的字符串
      for (let i = 1; i <= 7; i++) {
        // 计算每个日期的时间戳
        const timestamp = oneWeekAgo.getTime() + (i * 24 * 60 * 60 * 1000);

        // 将时间戳转换为日期对象
        const dateObj = new Date(timestamp);

        // 格式化日期为 "MM-DD" 的字符串
        const dateString = `${dateObj.getMonth() + 1}-${dateObj.getDate()}`;

        // 将日期字符串添加到数组中
        datesArr.push(dateString);
      }

      // 输出日期数组
      console.log(datesArr);
      return datesArr
    }
  },
  mounted() {
    let datesArr = this.getChartXAxis()

    let myChart = echarts.init(document.getElementById("chart"));
    // 绘制图表
    myChart.setOption({
      tooltip: {
        trigger: 'axis'
      },
      legend: {
        data: ['用户总数', '活跃用户数量', '在线人数', '页面总数/百个']
      },
      xAxis: {
        data: datesArr
      },
      yAxis: {},
      series: [
        {
          name: "用户总数",
          type: "line",
          data: [97, 97, 99, 107, 114, 128, 129]
        }, {
          name: "活跃用户数量",
          type: "line",
          data: [45, 47, 42, 54, 44, 67, 58]
        }, {
          name: "在线人数",
          type: "line",
          data: [7, 12, 6, 18, 14, 32, 21]
        }, {
          name: "设计总数/百个",
          type: "line",
          data: [60, 61, 61, 63, 66, 68, 65]
        }
      ]
    });
    window.onresize = function () { // 自适应大小
      myChart.resize();
    };
  }
}
</script>

<style scoped>
.card-outer {
  display: flex;
  flex-direction: row;
}

.card {
  flex: 1;
  height: 120px;
  background-color: #fff;
  border-radius: 20px;
  margin-right: 30px;
  position: relative;
}

.card:nth-child(4) {
  margin-right: 0;
}

.card-img {
  position: absolute;
  left: 20px;
  top: 30px;
  width: 60px;
}

.card-statistic {
  position: absolute;
  right: -20px;
  top: 30px
}

.chart {
  margin-top: 24px;
  padding-top: 20px;
  width: 100%;
  height: 420px;
  border-radius: 20px;
  background-color: #fff;

}
</style>