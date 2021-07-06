<template>
  <div>
    <link rel="stylesheet" href="https://g.alicdn.com/de/prismplayer/2.8.2/skins/default/aliplayer-min.css" >
    <script charset="utf-8" src="https://g.alicdn.com/de/prismplayer/2.8.2/aliplayer-min.js"/>
    <!-- aliyun视频播放器组件 -->
    <script charset="utf-8" src="https://player.alicdn.com/aliplayer/presentation/js/aliplayercomponents.min.js"/>
    <div id="J_prismPlayer" class="prism-player"/>
  </div>
</template>
<script>
import videoApi from '~/api/video'
export default {
  // async asyncData(ctx) { // 上下文对象，包含访问页面的参数，路由数据
  //   console.log(ctx.params.vid)
  //   const response = await videoApi.getPlayAuth(ctx.params.vid)// 将id传入方法中
  //   console.log(response.data)
  //   return {
  //     playAuth: response.data.playAuth,
  //     vid: ctx.params.vid
  //   }
  // },
  mounted() {
    videoApi.getPlayAuth(this.$route.params.vid).then(r => {
      // 初始化阿里云播放器
    // eslint-disable-next-line no-undef
    /* var player =*/ new Aliplayer({
        id: 'J_prismPlayer',
        width: '100%',
        // 播放配置
        // 播放方式一：支持播放地址播放,此播放优先级最高，此种方式不能播放加密视频
        // source : '你的视频播放地址',
        // 播放方式二：加密视频和多数据源视频的播放
        vid: this.$route.params.vid, // 视频id
        // 视频播放凭证
        playauth: r.data.playAuth,
        encryptType: 1, // 当播放私有加密流时需要设置。
        cover: 'https://210108sh.oss-cn-shanghai.aliyuncs.com/cover2021/06/02/49d2a8bf07114b018b5758b2bdd44bed.jpg'
      // 如有需要，可以整合组件，实现跑马灯，弹幕，屏幕旋转等效果

      }, function(player) {
        console.log('播放器创建好了。')
      })
    })
  }
}
</script>
