<template>
  <div>
    <div class="front-notice"><i class="el-icon-bell" style="margin-right: 2px"></i>公告：{{ top }}</div>
    <!--头部-->
    <div class="front-header">
      <div class="front-header-left">
        <img src="@/assets/imgs/logo.png" alt="">
        <div class="title" >技术研习博客</div>
      </div>
      <div class="front-header-center">
        <div class="front-header-nav">
          <el-menu :default-active="$route.path" mode="horizontal" router>
						<el-menu-item index="/front/home">首页</el-menu-item>
						<el-menu-item index="/front/activity">活动中心</el-menu-item>
						<el-menu-item index="/front/person">个人中心</el-menu-item>
          </el-menu>
        </div>
      </div>
      <div>
        <el-input style="width: 260px; margin-right: 10px" placeholder="请输入博客关键字" v-model="title" clearable></el-input>
        <el-button icon="el-icon-search"  @click="goSearch" class="custom-button">搜 索</el-button>
      </div>
      <div class="front-header-right">
        <div v-if="!user.username">
          <el-button @click="$router.push('/login')">登录</el-button>
          <el-button @click="$router.push('/register')">注册</el-button>
        </div>
        <div v-else>
          <el-dropdown>
            <div class="front-header-dropdown">
              <img :src="user.avatar" alt="">
              <div style="margin-left: 10px; color: #000000">
                <span>{{ user.name }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
              </div>
            </div>

            <el-dropdown-menu slot="dropdown" >
              <el-dropdown-item>
                <div style="text-decoration: none; height: 40px;display: flex; justify-content: center; align-items: center;" @click="goToPerson">个人中心</div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div style="text-decoration: none; height: 40px;display: flex; justify-content: center; align-items: center;" @click="logout">退出</div>
              </el-dropdown-item>

            </el-dropdown-menu>

          </el-dropdown>
        </div>

      </div>

    </div>
    <!--主体-->
    <div class="main-body">
      <router-view ref="child" @update:user="updateUser" />
    </div>
  </div>

</template>

<script>

export default {
  name: "FrontLayout",

  data () {
    return {
      top: '',
      notice: [],
      user: JSON.parse(localStorage.getItem("xm-user") || '{}'),
      title: this.$route.query.title
    }
  },

  mounted() {
    this.loadNotice()
  },
  methods: {
    goSearch() {
      location.href = '/front/search?title=' + this.title
      if(this.title === ''){
        location.href = '/front/home'
      }
    },
    loadNotice() {
      this.$request.get('/notice/selectAll').then(res => {
        this.notice = res.data
        let i = 0
        if (this.notice && this.notice.length) {
          this.top = this.notice[0].content
          setInterval(() => {
            this.top = this.notice[i].content
            i++
            if (i === this.notice.length) {
              i = 0
            }
          }, 2500)
        }
      })
    },
    updateUser() {
      this.user = JSON.parse(localStorage.getItem('xm-user') || '{}')   // 重新获取下用户的最新信息
    },
    // 退出登录
    logout() {
      localStorage.removeItem("xm-user");
      this.$router.push("/login");
    },
    goToPerson(){
      location.href = '/front/person'
    },
  }

}
</script>

<style scoped>
  @import "@/assets/css/front.css";
  .custom-input .el-input__inner:hover {
    border-color: #409eff; /* 蓝色边框 */
  }

  .custom-input .el-input__inner {
    background-color: #e6f7ff; /* 浅蓝色背景 */
    border-color: #d3dce6; /* 默认边框颜色 */
  }

  .custom-button .el-button--success {
    background-color: #67c23a; /* 按钮背景色 */
    border-color: #67c23a; /* 按钮边框颜色 */
    color: #fff; /* 按钮文字颜色 */
  }


</style>