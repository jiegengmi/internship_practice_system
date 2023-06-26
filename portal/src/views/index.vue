<template>
  <div class="app-container">
    <el-row :gutter="10">
      <el-col :span="8">
        <div class="grid-content bg-purple">
          <!-- 首页user信息 -->
          <el-card shadow='always' style="height: 300px">
            <div class="userCard">
              <el-avatar :size="120" :src=imgUrl></el-avatar>
              <div class="userInfo">
                <p class="nameText">{{ period }}好！{{ nickName }}({{ username }})</p>
                <p class="">{{ nowTime }}</p>
              </div>
            </div>
            <ul class="lastLogin">
              <li><span>上次登录IP:</span><span class="loginInfo">{{ lastLoginIp }}</span></li>
              <li><span>上次登录地址:</span><span class="loginInfo">{{ lastLoginAddress }}</span></li>
              <li><span>上次登录时间:</span><span class="loginInfo">{{ lastLoginTime }}</span></li>
            </ul>
          </el-card>
        </div>
      </el-col>
      <el-col :span="16">
        <!-- 柱状图 -->
        <el-card style="height: 300px;">
          <div style="height:260px;" id="notice">
            <Message></Message>
          </div>
        </el-card>
      </el-col>

    </el-row>
    <el-card style="height: auto;margin-top: 10px">
      <div style="height:280px;" id="firstLevel"></div>
    </el-card>
  </div>
</template>

<script setup>

import {onMounted, ref} from "vue";
import * as echarts from 'echarts';
import {getUserInfo, getPracticeInfo, getDeptType, getDeptTypeList} from "@/api";
import {getDate, getDayOfPeriod} from "@/utils/formatDate";
import "../css/index.less"
import Message from "@/views/index/Message.vue";

onMounted(() => {
  getAllType();
  initBarEcharts();
  getUser();
  setInterval(() => {
    getNow()
  }, 1000);
})

const username = ref("");
const nickName = ref("");
const lastLoginTime = ref("");
const lastLoginIp = ref("");
const lastLoginAddress = ref("");
const imgUrl = ref("");
const nowTime = ref("");
const period = ref(getDayOfPeriod());
const deptType = ref([]);

const getNow = function () {
  nowTime.value = getDate()
}

// 用户卡片
/** 用户登录信息 */
function getUser() {
  getUserInfo().then(data => {
    username.value = data.username;
    nickName.value = data.nickname;
    lastLoginTime.value = data.lastLoginTime;
    lastLoginIp.value = data.lastLoginIp;
    lastLoginAddress.value = data.lastLoginAddress;
  });
}

function getAllType() {
  getDeptTypeList().then(data => {
    deptType.value = data;
  });
}

function initBarEcharts() {
  let xData = ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子'];
  // 基于准备好的dom，初始化echarts实例
  const myChart = echarts.init(document.getElementById('firstLevel'));
  // 指定图表的配置项和数据
  const option = {
    title: {
      text: '数据总览'
    },
    tooltip: {},
    legend: {
      data: ['销量','利润']
    },
    xAxis: {
      data: xData
    },
    yAxis: {},
    series: [
      {
        name: '销量',
        type: 'bar',
        data: [5, 20, 36, 10, 10, 20]
      },
      {
        name: '利润',
        type: 'bar',
        data: [5, 20, 36, 10, 10, 20]
      }
    ]
  };
  // 使用刚指定的配置项和数据显示图表。
  myChart.setOption(option);
}
</script>

