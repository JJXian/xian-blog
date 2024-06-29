<template>
  <div class="main-content">

    <activity-list :span="6" />

    <Footer />
  </div>
</template>

<script>
import Footer from "@/components/Footer";
import ActivityList from "@/components/ActivityList";

export default {
  name: "Activity",
  components: {
    ActivityList,
    Footer
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
      this.$request.get('/activity/selectPage', {
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