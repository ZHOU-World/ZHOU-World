<template>
  <div class="Page Confirm">
    <div class="Title">
      <h1 class="fl f18">订单确认</h1>
      <div class="clear"/>
    </div>
    <table class="GoodList">
      <tbody>
        <tr>
          <td colspan="3" class="teacher">讲师：{{ order.teacherName }}</td>
        </tr>
        <tr class="good">
          <td class="name First">
            <a :href="'/course/'+order.courseId" target="_blank">
              <img :src="order.courseCover">
            </a>
            <div class="goodInfo">
              <a :href="'/course/'+ order.courseId" target="_blank">{{ order.courseTitle }}</a>
            </div>
          </td>
          <td class="red priceNew Last">￥<strong>{{ order.totalFee/100 }}</strong></td>
        </tr>
      </tbody>
    </table>
    <div class="Finish">
      <div class="check fr">
        <el-checkbox v-model="agree">我已阅读并同意<a href="javascript:" target="_blank">《谷粒学院购买协议》</a></el-checkbox>
      </div>
      <div class="clear"/>
      <div class="Main fl">
        <div class="fl">
          <a :href="'/course/'+order.courseId">返回课程详情页</a>
        </div>
      </div>
      <el-button :disabled="!agree" type="danger" @click="toPay">去支付</el-button>
      <div class="clear"/>
    </div>
  </div>
</template>

<script>
import orderApi from '~/api/order'
import local from '~/utils/local'
export default {
  data() {
    return {
      order: {},
      agree: true
    }
  },
  mounted() {
    const token = local.fetch('GULI-JWT')
    orderApi.getOrder(this.$route.params.id, token).then(r => {
      // 回显页面详情信息
      this.order = r.data.item
    })
  },
  // 获取二维码的方法
  methods: {
    toPay() {
      // const token = local.fetch('GULI-JWT')
      // orderApi.toPay(this.$route.params.id, token).then(r => {
      // })
      // 直接进行跳转
      this.$router.push('/pay/' + this.$route.params.id)
    }
  }
}
</script>
