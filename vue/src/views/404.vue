<template>
  <div class="wscn-http404-container">
    <div class="pic-404">
      <img class="pic-404__parent" src="@/assets/404_images/404.png" alt="404">
      <img class="pic-404__child left" src="@/assets/404_images/404_cloud.png" alt="404">
      <img class="pic-404__child mid" src="@/assets/404_images/404_cloud.png" alt="404">
      <img class="pic-404__child right" src="@/assets/404_images/404_cloud.png" alt="404">
    </div>
    <div class="error-content">
      <div class="bullshit">
        <div class="bullshit__oops">
          404错误!
        </div>
        <div class="bullshit__headline">
          {{ message }}
        </div>
        <div class="bullshit__info">
          对不起，您正在寻找的页面不存在。尝试检查URL的错误，然后按浏览器上的刷新按钮或尝试在我们的应用程序中找到其他内容。
        </div>
        <router-link to="/" class="router-link">
          返回首页
        </router-link>
      </div>
    </div>
  </div>
</template>



<style scoped>
.wscn-http404-container {
  position: absolute;
  top: 40%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  align-items: center;
}

.pic-404 {
  width: 50%; /* 左侧图片占据页面宽度的一半 */
  padding-right: 20px; /* 右侧内容和图片之间的间距 */
  box-sizing: border-box; /* 让padding计算在内 */
}

.error-content {
  width: 50%; /* 右侧内容占据页面宽度的一半 */
}

.pic-404__parent {
  width: 100%;
}

.pic-404__child {
  position: absolute;
  opacity: 0;
  animation-duration: 2s;
  animation-timing-function: linear;
  animation-fill-mode: forwards;
}

.pic-404__child.left {
  width: 80px;
  top: 17px;
  left: 220px;
  animation-name: cloudLeft;
  animation-delay: 1s;
}

.pic-404__child.mid {
  width: 46px;
  top: 10px;
  left: 420px;
  animation-name: cloudMid;
  animation-delay: 1.2s;
}

.pic-404__child.right {
  width: 62px;
  top: 100px;
  left: 500px;
  animation-name: cloudRight;
  animation-delay: 1s;
}

@keyframes cloudLeft {
  0% {
    top: 17px;
    left: 220px;
    opacity: 0;
  }
  20% {
    top: 33px;
    left: 188px;
    opacity: 1;
  }
  80% {
    top: 81px;
    left: 92px;
    opacity: 1;
  }
  100% {
    top: 97px;
    left: 60px;
    opacity: 0;
  }
}

@keyframes cloudMid {
  0% {
    top: 10px;
    left: 420px;
    opacity: 0;
  }
  20% {
    top: 40px;
    left: 360px;
    opacity: 1;
  }
  70% {
    top: 130px;
    left: 180px;
    opacity: 1;
  }
  100% {
    top: 160px;
    left: 120px;
    opacity: 0;
  }
}

@keyframes cloudRight {
  0% {
    top: 100px;
    left: 500px;
    opacity: 0;
  }
  20% {
    top: 120px;
    left: 460px;
    opacity: 1;
  }
  80% {
    top: 180px;
    left: 340px;
    opacity: 1;
  }
  100% {
    top: 200px;
    left: 300px;
    opacity: 0;
  }
}

.bullshit {
  padding: 30px 0;
}

.bullshit__oops {
  font-size: 32px;
  font-weight: bold;
  line-height: 40px;
  color: #1482f0;
  opacity: 0;
  margin-bottom: 20px;
  animation-name: slideUp;
  animation-duration: 0.5s;
  animation-fill-mode: forwards;
}

.bullshit__headline {
  font-size: 20px;
  line-height: 24px;
  color: #222;
  font-weight: bold;
  opacity: 0;
  margin-bottom: 10px;
  animation-name: slideUp;
  animation-duration: 0.5s;
  animation-delay: 0.1s;
  animation-fill-mode: forwards;
}

.bullshit__info {
  font-size: 13px;
  line-height: 21px;
  color: grey;
  opacity: 0;
  margin-bottom: 30px;
  animation-name: slideUp;
  animation-duration: 0.5s;
  animation-delay: 0.2s;
  animation-fill-mode: forwards;
}

.router-link {
  display: inline-block;
  padding: 8px 16px;
  border: 2px solid #007bff; /* 蓝色边框 */
  border-radius: 20px; /* 圆角 */
  background-color: #007bff; /* 蓝色背景 */
  color: #ffffff; /* 白色字体 */
  text-decoration: none; /* 去除下划线 */
  text-align: center; /* 文字居中 */
}

@keyframes slideUp {
  0% {
    transform: translateY(60px);
    opacity: 0;
  }
  100% {
    transform: translateY(0);
    opacity: 1;
  }
}
</style>
