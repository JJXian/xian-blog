<template>
  <div>
    <el-row :gutter="10" v-if="total">
      <el-col :span="span" v-for="item in tableData" :key="item.id">
        <div class="card" style="margin-bottom: 10px" @click="goDetail(item.id)">
          <img :src="item.cover" alt="" style="width: 100%; height: 150px; border-radius: 5px">
          <div style="margin: 10px 0; font-weight: bold">{{ item.name }}</div>
          <div style="display: flex; align-items: center">
            <div style="flex: 1; color: #666"><i class="el-icon-date"></i> {{ item.start }}</div>
            <el-button type="primary" disabled v-if="item.isEnd" key="已结束">已结束</el-button>
            <el-button type="primary" disabled v-else-if="item.isSign" key="已报名">已报名</el-button>
            <el-button type="primary" v-else>报 名</el-button>
          </div>
        </div>
      </el-col>
    </el-row>
    <div class="card" v-if="total === 0" style="text-align: center; font-size: 16px; color: #666">暂无数据</div>
    <div class="card" style="padding: 10px" v-if="total">
      <el-pagination
          background
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-size="pageSize"
          layout="total, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>

    <Footer />
  </div>
</template>

<script>

export default {
  name: "ActivityList",
  props: {
    type: null,
    span: null
  },
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 8,  // 每页显示的个数
      total: 0,
      name: null,
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    goDetail(id) {
      window.open('/front/activityDetail?activityId=' + id)
    },
    load(pageNum) {  // 分页查询
      if (pageNum) this.pageNum = pageNum
      let url
      switch (this.type) {
        case 'user': url = '/activity/selectUser'; break;
        case 'like': url = '/activity/selectLike'; break;
        case 'collect': url = '/activity/selectCollect'; break;
        case 'comment': url = '/activity/selectComment'; break;
        default: url = '/activity/selectPage'
      }
      this.$request.get(url, {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.name = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
  }
}
</script>

<style scoped>

</style>