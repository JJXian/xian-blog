<template>
  <div style="display: flex; flex-wrap: wrap;">
    <div class="card" style="padding: 15px;width: 100% ">
      您好，{{ user?.name }}！欢迎使用本系统
      <br /><br />
      领取阿里云通用云产品1888优惠券
      <br />
      <el-link
          href="https://www.aliyun.com/minisite/goods?userCode=brki8iof"
          type="primary"
          target="_blank"
      >https://www.aliyun.com/minisite/goods?userCode=brki8iof</el-link
      >
      <br />
    </div>

    <div style="width: 50%; display: flex; margin: 10px 0">
      <div style="width: 95%;" class="card">
        <div style="margin-bottom: 30px; font-size: 20px; font-weight: bold">公告列表</div>
        <div >
          <el-timeline  reverse slot="reference">
            <el-timeline-item v-for="item in notices" :key="item.id" :timestamp="item.time">
              <el-popover
                  placement="right"
                  width="200"
                  trigger="hover"
                  :content="item.content">
                <span slot="reference">{{ item.title }}</span>
              </el-popover>
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>
    </div>

    <div style="width: 50%;display: flex; margin: 10px 0">
      <div style="width: 95%;" class="card">
        <div style="margin-bottom: 30px; font-size: 20px; font-weight: bold">技术选型</div>
        <div style="margin-left: 30px">
          <el-row>
            <el-col :span="6" >
              <h4 style="margin-bottom: 10px">后端技术</h4>
              <ul class="tech-list">
                <li>SpringBoot</li>
                <li>Spring Security</li>
                <li>JWT</li>
                <li>MyBatis</li>
                <li>Druid</li>
                <li>Fastjson</li>
                <li>...</li>
              </ul>
            </el-col>
            <el-col :span="6" style="margin-left: 50px">
              <h4 style="margin-bottom: 10px">前端技术</h4>
              <ul class="tech-list">
                <li>Vue</li>
                <li>Element-ui</li>
                <li>Axios</li>
                <li>Sass</li>
                <li>Quill</li>
                <li>...</li>
              </ul>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'Home',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      notices: []
    }
  },
  created() {
    this.$request.get('/notice/selectAll').then(res => {
      this.notices = res.data || []
    })
  }
}
</script>
<style>
.tech-list {
  //list-style-type: none; /* 去掉列表项的默认符号 */
  padding: 0;
  margin: 0;
}

.tech-list li {
  margin-bottom: 8px; /* 调整列表项之间的行间距 */
}
</style>
