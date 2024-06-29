<template>
  <div style="width: 50%; margin: 5px auto">
    <div class="card" style="margin-bottom: 100px">
      <div style="font-weight: bold; font-size: 24px; margin-bottom: 50px">发表新博客/编辑博客</div>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="标题" prop="title" :rules="[{ required: true, message: '标题不能为空', trigger: 'blur' }]">
          <el-input v-model="form.title" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="descr">
          <el-input type="textarea" v-model="form.descr" placeholder="简介"></el-input>
        </el-form-item>
        <el-form-item label="封面" prop="cover">
          <el-upload
              :action="$baseUrl + '/files/upload'"
              :headers="{ token: user.token }"
              list-type="picture"
              :on-success="handleCoverSuccess"
          >
            <el-button type="primary">上传封面</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="分类" prop="categoryId">
          <el-select v-model="form.categoryId" style="width: 100%">
            <el-option v-for="item in categoryList" :key="item.id" :value="item.id" :label="item.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="标签" prop="tags">
          <el-select v-model="tagsArr" multiple filterable allow-create default-first-option style="width: 100%">
            <el-option value="后端"></el-option>
            <el-option value="Java"></el-option>
            <el-option value="面试"></el-option>
            <el-option value="Vue"></el-option>
            <el-option value="前端"></el-option>
            <el-option value="大数据"></el-option>
            <el-option value="算法"></el-option>
            <el-option value="程序员"></el-option>
            <el-option value="小白"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <div id="editor"></div>
        </el-form-item>
      </el-form>
<!--      <div style="text-align: center"><el-button type="primary" size="medium" style="width: 100px" @click="save">保 存</el-button></div>-->
      <div style="text-align: center"><el-button type="primary" size="medium" style="width: 100px" @click="showConfirmDialog">保 存</el-button></div>

    </div>
  </div>
</template>

<script>
import E from "wangeditor";
import hljs from "highlight.js";

export default {
  name: "NewBlog",
  data() {
    return {
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {},
      tagsArr: [],
      categoryList: [],
      editor: null,
      blogId: this.$route.query.blogId
    }
  },
  mounted() {
    this.$request.get('/category/selectAll').then(res => {
      this.categoryList = res.data || []
    })

    this.$request.get('/blog/selectById/' + this.blogId).then(res => {
      this.form = res.data || {}
      if (this.form.id) {
        this.tagsArr = JSON.parse(this.form.tags || '[]')
        setTimeout(() => {
          this.editor.txt.html(this.form.content)
        }, 0)
      }
    })

    this.setRichText()
  },
  methods: {
    showConfirmDialog() {
      this.$confirm('确认保存吗?', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 用户点击确认按钮的逻辑
        this.save(); // 执行保存操作

      }).catch(() => {
        // 用户点击取消按钮的逻辑
        // 可以选择关闭弹窗或执行其他操作
      });
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      if (!this.form.title) {
        this.$message.error('标题不能为空');
        return; // 返回，不执行保存操作
      }else{
        this.$router.push('/front/person'); // 跳转到个人页面，这里假设使用了 Vue Router
      }

      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.form.tags = JSON.stringify(this.tagsArr)  // 把json数组转换成json字符串存到数据库
          this.form.content = this.editor.txt.html()
          this.$request({
            url: this.form.id ? '/blog/update' : '/blog/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
            } else {
              // 表单验证失败，不执行保存操作，显示验证提示信息

              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },
    handleCoverSuccess(res) {
      this.form.cover = res.data
    },
    setRichText() {
      this.$nextTick(() => {
        this.editor = new E(`#editor`)
        this.editor.highlight = hljs
        this.editor.config.uploadImgServer = this.$baseUrl + '/files/editor/upload'
        this.editor.config.uploadFileName = 'file'
        this.editor.config.uploadImgHeaders = {
          token: this.user.token
        }
        this.editor.config.uploadImgParams = {
          type: 'img',
        }
        this.editor.config.zIndex = 0
        this.editor.create()  // 创建
      })
    },
  }
}
</script>

<style scoped>

</style>