<template>
  <div class="cart py-container">
    <!--主内容-->
    <div class="checkout py-container  pay">
      <div class="checkout-tit" style="width: 1050px; margin: 0 auto; padding: 10px 0;">
        <h4 class="fl tit-txt"><span class="success-info">支付申请提交成功，请您及时付款！订单号：{{ payObj.out_trade_no }}</span>
        </h4>
        <span class="fr"><em class="sui-lead">应付金额：</em><em class="orange money">￥{{ payObj.total_fee/100 }}</em></span>
        <div class="clearfix"/>
      </div>
      <div class="checkout-steps">
        <div class="fl weixin">微信支付</div>
        <div class="fl sao">
          <div class="fl code">
            <!-- <img id="qrious" src="~/assets/img/erweima.png" alt=""> -->
            <qriously :value="payObj.code_url" :size="338"/>
          </div>
          <div style="color: red; text-align:center;">请使用微信扫一扫</div>
        </div>
        <div class="clearfix"/>
        <!-- <p><a href="pay.html" target="_blank"> 其他支付方式</a></p> -->
      </div>
    </div>

  </div>
</template>
<script>
import orderApi from '~/api/order'
import local from '~/utils/local'
export default {
  data() {
    return {
      payObj: {},
      timer: {}
    }
  },
  mounted() {
    // const token = local.fetch('GULI-JWT')
    // 路径中的参数可以有route来获取
    orderApi.toPay(this.$route.params.id, local.fetch('GULI-JWT')).then(r => {
      this.payObj = r.data // r.data 包含四对kv的map集合
      // 支付二维码显示后，发起定时任务，每隔2000毫秒查询一次订单状态，查询如果已支付成功跳转到支付页面
      this.timer = setInterval(() => {
        orderApi.getPayStatus(this.$route.params.id, local.fetch('GULI-JWT')).then(r => {
        // 进行判断
          if (r.data.status === 1) {
            clearInterval(this.timer)// 清除定时任务
            this.$router.push('/course/' + this.payObj.course_id)// 跳转到课程详情页
          }
        })
      }, 2000)
    })
  },
  destroyed() { // 页面突然关闭时，清除定时任务
    clearInterval(this.timer)
  }
}
</script>
