<template>
  <div style="padding:15px;">
    <el-input
      v-model="filterText"
      placeholder="输入关键字进行过滤"/>
    <el-tree
      ref="tree"
      :filter-node-method="filterNode"
      :data="subjects"
      :props="defaultProps"
      default-expand-all="true"
      @node-click="handleNodeClick"/>
  </div>
</template>
<script>
import subjectApi from '@/api/subject'
export default {
  data() {
    return {
      filterText: '',
      defaultProps: {// el-tree的自定义配置，解析数据
        children: 'children',
        label: 'title' // 数据显示到key中的标题
      },
      subjects: []
    }
  },
  watch: { // 动态监听data数据，如果数据的值改变，回调watch中的方法并传入改变后的值
    // 本例中如果filterText的值val发生改变，回调filter方法并传入filterText改变后的值
    filterText(val) {
      // filter：el-tree的过滤方法，可以对el-tree的数据进行筛选
      this.$refs.tree.filter(val) // vue渲染的页面范围内，定义了refs属性的所有标签集合，通过定义的refs的值筛选标签
    }
  },
  created() {
    this.getSubjects()
  },
  methods: { // 发起异步请求
    filterNode(value, data) { // value:代表filterText最新的值，data代表节点对象
      // filterNode在filter改变时会调用，vue的el-tree会使用每个节点调用filterNode放方法并传入value，data
      if (!value) return true
      // 使用节点属性值查找value值的索引，查询到返回>=0的数，和-1比较返回true,表示显示
      // 返回-1表示title的属性值中不包含value值，和-1比较返回false,表示不显示
      return data.title.toUpperCase().indexOf(value.toUpperCase()) !== -1
    },
    handleNodeClick(data) {
      console.log(data)
    },
    getSubjects() {
      subjectApi.listNestedSubjects().then(r => {
        console.log(r.data)
        this.subjects = r.data.items
      })
    }
  }
}
</script>
