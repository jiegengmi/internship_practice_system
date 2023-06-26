<template>
  <div style="float: left;width: 50%;">
    <el-table
        :data="noticeData">
      <el-table-column
          prop="notice"
          label="通知公告"
          class="notice">
        <template #default="scope">
          <a href="https://ikikyou.com" target="_blank" class="buttonText">{{ scope.row.notice }}</a>
        </template>
      </el-table-column>
      <el-table-column
          prop="date1"
          width="100">
        <template v-slot:header style="text-align: right">
          <a href=""  target="_blank">More</a>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <div style="margin-left: 51%; width: 49%;">
    <el-table
        :data="messageData"
        stripe
        style="width: 100%">
      <el-table-column
          prop="msg"
          label="个人消息">
        <template #default="scope">
          <a href="" target="_blank" class="buttonText">{{ scope.row.msg }}</a>
        </template>
      </el-table-column>
      <el-table-column
          prop="date1"
          width="100">
        <template v-slot:header #default="scope">
          <a href="" target="_blank">More</a>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import {onMounted, ref} from "vue";


const noticeData = ref([])
const messageData = ref([])

onMounted(() => {
  getNotice();
  getMessage();
})

function getNotice() {
  noticeData.value = [
    {
      notice: 'u算法比较深刻！',
      date1: '2020/03/06'
    }, {
      notice: '是否还能萨洛克！',
      date1: '2020/03/05'
    }, {
      notice: '绿卡给我发几款校内天梯选拔赛！',
      date1: '2020/03/04'
    }, {
      notice: '校内天梯选拔赛还有4天校内天梯选拔赛校内天梯选拔赛！',
      date1: '2020/03/03'
    }, {
      notice: '校内天梯选拔赛还有5天！',
      date1: '2020/03/02'
    }
  ]
}

function getMessage() {
  messageData.value = [
    {
      msg: 'XXX发布了一条招聘！',
      date1: '2020-3-6'
    }, {
      msg: '校内天梯选拔赛开始报名啦！',
      date1: '2020-3-6'
    }, {
      msg: '校内天梯选拔赛开始报名啦！',
      date1: '2020-3-6'
    }, {
      msg: '校内天梯选拔赛开始报名啦！',
      date1: '2020-3-6'
    }, {
      msg: '校内天梯选拔赛开始报名啦！',
      date1: '2020-3-6'
    }
  ]
}
</script>

<style scoped lang="scss">
.notice {
  width: 50%;
}
</style>