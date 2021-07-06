<template>
  <div style="padding:20px">
    <el-form
      :inline="true"
      class="demo-form-inline">
      <el-form-item>
        <el-input v-model="teacherQuery.name" placeholder="讲师"/>
      </el-form-item>
      <el-form-item>
        <el-select v-model="teacherQuery.level" placeholder="头衔">
          <el-option label="高级讲师" value="1"/>
          <el-option label="首席讲师" value="2"/>
        </el-select>
      </el-form-item>
      入驻时间
      <el-date-picker
        v-model="teacherQuery.joinDateBegin"
        type="date"
        value-format="yyyy-MM-dd"
        placeholder="开始时间"/>
      -
      <el-date-picker
        v-model="teacherQuery.joinDateEnd"
        type="date"
        value-format="yyyy-MM-dd"
        placeholder="结束时间"/>
      <el-button type="primary" icon="el-icon-search" @click="queryByCondition">查询</el-button>
      <el-form-item>
        <el-button @click="clear">清空</el-button>
      </el-form-item>
    </el-form>
    <!-- 批量删除 -->
    <el-button type="danger" @click="handleBatchDel">批量删除</el-button>
    <el-table
      :data="teachers"
      stripe
      border
      style="width: 100%"
      @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        width="55"/>
      <el-table-column
        type="index"
        width="50"/>
      <el-table-column
        prop="name"
        label="姓名"
        width="100"/>
      <el-table-column
        prop="avatar"
        label="头像"
        width="90">
        <template slot-scope="scope">
          <img
            v-if="scope.row.avatar"
            :src="scope.row.avatar"
            style="width:60px;height:60px">
          <img
            v-else
            src="https://210108sh.oss-cn-shanghai.aliyuncs.com/avatar/default.jpg"
            style="width:60px;height:60px">
        </template>
      </el-table-column>

      <el-table-column
        prop="level"
        label="头衔"
        width="100">
        <template slot-scope="scope">
          <el-tag
            v-if="scope.row.level===1"
            type="primary"
            disable-transitions>高级讲师</el-tag>
          <el-tag
            v-else
            type="succeed"
            disable-transitions>首席讲师</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="career"
        label="资历"
        show-overflow-tooltip/>
      <el-table-column
        prop="sort"
        label="排序"
        width="50"/>
      <el-table-column
        prop="joinDate"
        label="入驻时间"
        width="100"/>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>

    </el-table>
    <div class="block" style="text-align:center;margin-top:15px">
      <el-pagination
        :current-page="pageNum"
        :page-sizes="[3, 5, 7, 9]"
        :page-size="pageSize"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"/>
    </div>
  </div>
</template>
<script>
import teacherApi from '@/api/teacher'
export default {
  data() {
    return {
      teacherQuery: {},
      teachers: [],
      pageNum: 1,
      pageSize: 3,
      total: 0,
      multipleSelection: []
    }
  },
  created() {
    // this.getTeachers()
    this.getTeacherPage()
  },
  methods: {
    // 批量删除
    handleBatchDel() {
      if (this.multipleSelection.length === 0) { // 没有选中任何数据
        this.$message.warning('需要选择要删除的数据')
        return
      }
      const idsArr = [] // 定义新数组
      this.multipleSelection.forEach((item) => {
        idsArr.push(item.id) // 向数组添加元素
      })
      const ids = idsArr.join(',') // 用,将数组中的两个元素拼接为字符串
      teacherApi.batchDel(ids).then(r => {
        this.$message.success('批量删除成功')
        this.getTeacherPage() // 刷新页面
      })
    },
    // 批量选择
    handleSelectionChange(val) { // val代表当前选中行的记录json对象集合
      this.multipleSelection = val
      // console.log(val)
    },
    // 清空数据
    clear() {
      // 清空条件
      this.teacherQuery = {}
      this.pageNum = 1
      this.pageSize = 3
      this.getTeacherPage() // 查询第一页的前三条记录
    },
    // 带条件的分页查询
    queryByCondition() {
      this.getTeacherPage()
    },
    // 当前页码改变回调函数
    handleCurrentChange(val) {
      this.pageNum = val
      this.getTeacherPage()
    },
    // 选择的PageSize改变时回调函数
    handleSizeChange(val) {
      this.pageSize = val
      this.getTeacherPage()
    },
    // 编辑
    handleEdit(idx, row) {
      // console.log(idx, row)// 当前记录的json对象,idx代表下标位置，row代表当前记录的数据
      // 携带ID跳转到编辑页面（通过路由）
      this.$router.push(`/teacher/edit/${row.id}`)
    },
    // 删除
    handleDelete(idx, row) {
      this.$confirm(`此操作将永久删除讲师【${row.name}】, 是否继续?`, '删除提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        teacherApi.deleteById(row.id).then(r => {
          this.$message.success('删除成功')
          // 刷新当前页面
          this.getTeacherPage()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    getTeachers() {
      teacherApi.list().then(r => {
        console.log(r)
        this.teachers = r.data.items
        console.log(this.teachers)
      })
    },
    // 分页查询数据
    getTeacherPage() {
      teacherApi.listPage(this.pageNum, this.pageSize, this.teacherQuery).then(r => {
        // r.data.page分页对象
        console.log(r.data)
        // 分页数据列表
        this.teachers = r.data.page.records
        // 总记录数
        this.total = r.data.page.total
      })
    }
  }
}
</script>
