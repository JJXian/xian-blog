<template>
  <div class="main-content">
    <div style="display: flex; grid-gap: 10px">

      <div style="flex: 1; width: 0">
        <div class="card" style="padding: 30px; margin-bottom: 10px">
          <div style="font-weight: bold; font-size: 24px; margin-bottom: 20px">{{ blog.title }}</div>
          <div style="color: #666; margin-bottom: 20px">
            <span style="margin-right: 20px"><i class="el-icon-user"></i> {{ blog.userName }}</span>
            <span style="margin-right: 20px"><i class="el-icon-date"></i> {{ blog.date }}</span>
            <span style="margin-right: 20px"><i class="el-icon-eye"></i> {{ blog.readCount }}</span>
            <span>
              <el-tag v-for="item in tagsArr" :key="item" type="primary" style="margin-right:5px">{{ item }}</el-tag>
            </span>
          </div>

          <div class="w-e-text">
            <div v-html="blog.content"></div>
          </div>

        </div>

        <!--     点赞和收藏数据   -->
        <div class="card" style="text-align: center; font-size: 20px; color: #666; margin-bottom: 10px">
          <span style="margin-right: 20px; cursor: pointer;" @click="setLikes" :class="{ 'active' : blog.userLike }"><i class="el-icon-like"></i> {{ blog.likesCount }}</span>
          <span style=" cursor: pointer"  @click="setCollect" :class="{ 'active' : blog.userCollect }"><i class="el-icon-star-off"></i> {{ blog.collectCount }}</span>
        </div>


<!--        评论-->
        <Comment :fid="blogId" module='博客' />

      </div>

      <div style="width: 260px">
        <div class="card" style="margin-bottom: 10px">

          <div style="display: flex; align-items: center; grid-gap: 10px; margin-bottom: 10px">
            <img :src="blog.user?.avatar" alt="" style="width: 50px; height: 50px; border-radius: 50%">
            <div style="flex: 1;">
              <div style="font-weight: bold; margin-bottom: 5px">{{ blog.user?.name }}</div>
              <div style="color: #666; font-size: 13px" class="line2">{{ blog.user?.info }}</div>
            </div>
          </div>

          <div style="display: flex">
            <div style="flex: 1; text-align: center">
              <div style="margin-bottom: 5px">文章</div>
              <div style="color: #888">{{ blog.user?.blogCount }}</div>

            </div>
            <div style="flex: 1; text-align: center">
              <div style="margin-bottom: 5px">点赞</div>
              <div style="color: #888">{{ blog.user?.likesCount }}</div>
            </div>
            <div style="flex: 1; text-align: center">
              <div style="margin-bottom: 5px">收藏</div>
              <div style="color: #888">{{ blog.user?.collectCount }}</div>
            </div>
          </div>
        </div>

        <div class="card" style="margin-bottom: 10px">
          <div style="font-weight: bold; font-size: 20px; padding-bottom: 10px; border-bottom: 1px solid #ddd; margin-bottom: 10px">相关推荐</div>

          <div>
            <div style="margin-bottom: 15px" v-for="item in recommendList" :key="item.id">
              <a :href="'/front/blogDetail?blogId=' + item.id" target="_blank"><div class="recommend-title line2">{{ item.title }}</div></a>
              <div style="color: #888">
                <span>阅读</span> <span>{{ item.readCount }}</span>
                <span style="margin-left: 10px">点赞</span> <span>{{ item.likesCount }}</span>
              </div>
            </div>
          </div>
        </div>

        <div class="card">
          <div style="display: flex; grid-gap: 10px; ">
            <div style="flex: 1; line-height: 25px">
              找对属于你的学习圈子
              扫码学习精品项目
            </div>
            <img src="@/assets/imgs/广告.png" alt="" style="width: 50px; height: 50px; border-radius: 5px">
          </div>
        </div>

      </div>



    </div>

    <Footer />
  </div>
</template>

<script>
import Footer from "@/components/Footer";
import Comment from "@/components/Comment";

export default {
  name: "BlogDetail",
  components: {
    Comment,
    Footer
  },
  data() {
    return {
      blogId: this.$route.query.blogId,
      blog: {},
      tagsArr: [],
      recommendList: [],
    }
  },
  created() {
    this.load()

    this.$request.put('/blog/updateReadCount/' + this.blogId)
  },
  methods: {

    setLikes() {
      this.$request.post('/likes/set', {  fid: this.blogId, module: '博客' }).then(res => {
        if (res.code === '200') {
          this.$message.success('操作成功')
          this.load()  // 重新加载数据
        }
      })
    },
    setCollect() {
      this.$request.post('/collect/set', {  fid: this.blogId, module: '博客' }).then(res => {
        if (res.code === '200') {
          this.$message.success('操作成功')

          this.load()  // 重新加载数据
        }
      })
    },
    load() {
      this.$request.get('/blog/selectById/' + this.blogId).then(res => {
        this.blog = res.data || {}

        this.tagsArr = JSON.parse(this.blog.tags || '[]')
      })

      this.$request.get('/blog/selectRecommend/' + this.blogId).then(res => {
        this.recommendList = res.data || []
      })
    }
  }
}
</script>

<style>
/* blockquote 样式 */
blockquote {
  display: block;
  border-left: 8px solid #d0e5f2;
  padding: 20px 10px;
  margin: 10px 0;
  line-height: 1.4;
  font-size: 100%;
  background-color: #f1f1f1;
}

/* code 样式 */
code {
  display: inline-block;
  *display: inline;
  *zoom: 1;
  background-color: #f1f1f1;
  border-radius: 3px;
  padding: 3px 5px;
  margin: 0 3px;
}
pre code {
  display: block;
}
p {
  line-height: 30px
}

.active {
  color: orange !important;
  //color: #ff187c !important;
}
.recommend-title {
  margin-bottom: 5px;
}
.recommend-title:hover {
  color: #2a60c9;
}
.comment-active {
  color: #2a60c9;
}
pre {
  white-space: pre-wrap; /*css-3*/
  white-space: -moz-pre-wrap; /*Mozilla,since1999*/
  white-space: pre-wrap; /*Opera4-6*/
  white-space: -o-pre-wrap; /*Opera7*/
  word-wrap: break-word; /*InternetExplorer5.5+*/
}
</style>