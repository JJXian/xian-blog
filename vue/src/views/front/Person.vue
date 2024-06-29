<template>
  <div class="main-content" style="width: 50%">

    <el-tabs v-model="activeName" @tab-click="clickTab">
      <el-tab-pane label="个人资料" name="个人资料">
        <person-page @update:user="updateUser" />
      </el-tab-pane>
      <el-tab-pane label="我发表的博客" name="我发表的博客">
        <div class="card" style="padding: 5px"><el-button type="primary" @click="addBlog">发表新博客</el-button></div>
        <div style="margin-top: 10px">
          <blog-list type="user" :show-opt="true" />
        </div>
      </el-tab-pane>
      <el-tab-pane label="我报名的活动" name="我报名的活动">
        <activity-list type="user" :span="8" />
      </el-tab-pane>
      <el-tab-pane label="我的点赞" name="我的点赞">
        <div class="card" style="padding: 5px; display: flex">
          <div class="category-btn" :class="{ 'active' : likesCurrent === '博客' }" @click="likesCurrent = '博客'">博客</div>
          <div class="category-btn" :class="{ 'active' : likesCurrent === '活动' }" @click="likesCurrent = '活动'">活动</div>
        </div>
        <div style="margin-top: 10px">
          <blog-list v-if="likesCurrent === '博客'" type="like"></blog-list>
          <activity-list v-if="likesCurrent === '活动'" :span="8" type="like"></activity-list>
        </div>
      </el-tab-pane>
      <el-tab-pane label="我的收藏" name="我的收藏">
        <div class="card" style="padding: 5px; display: flex">
          <div class="category-btn" :class="{ 'active' : collectCurrent === '博客' }"  @click="collectCurrent = '博客'">博客</div>
          <div class="category-btn" :class="{ 'active' : collectCurrent === '活动' }" @click="collectCurrent = '活动'">活动</div>
        </div>
        <div style="margin-top: 10px">
          <blog-list v-if="collectCurrent === '博客'" type="collect"></blog-list>
          <activity-list v-if="collectCurrent === '活动'" :span="8" type="collect"></activity-list>
        </div>
      </el-tab-pane>
      <el-tab-pane label="我的评论" name="我的评论">
        <div class="card" style="padding: 5px; display: flex">
          <div class="category-btn" :class="{ 'active' : commentCurrent === '博客' }"  @click="commentCurrent = '博客'">博客</div>
          <div class="category-btn" :class="{ 'active' : commentCurrent === '活动' }" @click="commentCurrent = '活动'">活动</div>
        </div>
        <div style="margin-top: 10px">
          <blog-list v-if="commentCurrent === '博客'" type="comment"></blog-list>
          <activity-list v-if="commentCurrent === '活动'" :span="8" type="comment"></activity-list>
        </div>
      </el-tab-pane>
    </el-tabs>

    <Footer />
  </div>
</template>

<script>
import Footer from "@/components/Footer";
import PersonPage from "@/components/PersonPage";
import BlogList from "@/components/BlogList";
import ActivityList from "@/components/ActivityList";

export default {
  components: {
    ActivityList,
    BlogList,
    Footer,
    PersonPage
  },
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请确认密码'))
      } else if (value !== this.user.newPassword) {
        callback(new Error('确认密码错误'))
      } else {
        callback()
      }
    }
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      dialogVisible: false,

      rules: {
        password: [
          { required: true, message: '请输入原始密码', trigger: 'blur' },
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
        ],
        confirmPassword: [
          { validator: validatePassword, required: true, trigger: 'blur' },
        ],
      },
      activeName: '个人资料',
      likesCurrent: '博客',
      collectCurrent: '博客',
      commentCurrent: '博客',
    }
  },
  created() {

  },
  methods: {
    updateUser() {
      // 触发父级的数据更新
      this.$emit('update:user')
    },
    addBlog() {
      window.open('/front/newBlog')
    },
    clickTab(tab) {
      console.log(tab)
    },
    update() {
      // 保存当前的用户信息到数据库
      this.$request.put('/user/update', this.user).then(res => {
        if (res.code === '200') {
          // 成功更新
          this.$message.success('保存成功')
          // 更新浏览器缓存里的用户信息
          localStorage.setItem('xm-user', JSON.stringify(this.user))

          // 触发父级的数据更新
          this.$emit('update:user')
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleAvatarSuccess(response, file, fileList) {
      // 把user的头像属性换成上传的图片的链接
      this.$set(this.user, 'avatar', response.data)
    },
    // 修改密码
    updatePassword() {
      this.dialogVisible = true
    },
    save() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request.put('/updatePassword', this.user).then(res => {
            if (res.code === '200') {
              // 成功更新
              this.$message.success('修改密码成功')
              this.$router.push('/login')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
/deep/.el-form-item__label {
  font-weight: bold;
}
/deep/.el-upload {
  border-radius: 50%;
}
/deep/.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border-radius: 50%;
}
/deep/.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
  border-radius: 50%;
}
.avatar {
  width: 120px;
  height: 120px;
  display: block;
  border-radius: 50%;
}
.category-btn {
  width: fit-content;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}
.active {
  background-color: #2a60c9 !important;
  color: white !important;
}
</style>