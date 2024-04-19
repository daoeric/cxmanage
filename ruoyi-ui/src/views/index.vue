<template>
  <div class="dashboard-editor-container">

    <panel-group @handleSetLineChartData="handleSetLineChartData" :panel-data="panelData" />

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData" />
    </el-row>
  </div>
</template>

<script>
import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'
import { info,chatData } from "@/api/dashboard/dashboard.js";

const lineChartData = {

  dateStr: ['2022-05-21', '2022-05-23'],
  successAmount: [100,200]

}

export default {
  name: 'Index',
  components: {
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart
  },
  data() {
    return {
      lineChartData: lineChartData,
      panelData:{
        totalCount:0,
        totalAmount:0,
        successCount:0,
        successAmount:0
      }
    }
  },
  created() {
    //加载统计数据
    this.queryInfo();
    this.queryChatData();
  },
  methods: {
    queryChatData(){
      chatData().then(response => {
        this.lineChartData = response.data;
      });
    },
    queryInfo(){
      info().then(response => {
        this.panelData=response.data;
      });
    },

    handleSetLineChartData(type) {
      // this.lineChartData = lineChartData[type]
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
