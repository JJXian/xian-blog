<template>
  <div class="container">
    <div style="width: 380px; padding: 50px 30px; background-color: rgba(255,255,255,.8); border-radius: 5px;">
      <div style="text-align: center; font-size: 24px; margin-bottom: 30px; color: #333">欢迎注册技术研习博客</div>
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input size="medium" prefix-icon="el-icon-user" placeholder="请输入账号" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" prefix-icon="el-icon-lock" placeholder="请输入密码" show-password  v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPass">
          <el-input size="medium" prefix-icon="el-icon-lock" placeholder="请确认密码" show-password  v-model="form.confirmPass"></el-input>
        </el-form-item>
<!--        邮箱验证登陆-->
        <el-form-item prop="email">
          <el-input size="medium" prefix-icon="el-icon-message" placeholder="请输入邮箱" v-model="form.email"></el-input>
        </el-form-item>

        <el-form-item>
          <el-row :gutter="20">
            <el-col :span="14">
              <el-input size="medium" prefix-icon="el-icon-message" placeholder="请输入验证码" v-model="form.mailVerify"></el-input>
            </el-col>
            <el-col :span="10">
<!--              <el-button :disabled="isButtonDisabled" size="medium" @click="sendVerificationCode" style="width: 100%">{{ buttonText }}</el-button>-->
              <el-button  size="medium" @click="sendVerificationCode" style="width: 100%">{{ buttonText }}</el-button>
            </el-col>
          </el-row>
        </el-form-item>

        <el-form-item>
          <el-button size="medium" style="width: 100%; background-color: #2a60c9; border-color: #2a60c9; color: white" @click="register">注 册</el-button>
        </el-form-item>
        <div style="display: flex; align-items: center">
          <div style="flex: 1"></div>
          <div style="flex: 1; text-align: right">
            已有账号？请 <a href="/login">登录</a>
          </div>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    // 验证码校验
    const validatePassword = (rule, confirmPass, callback) => {
      if (confirmPass === '') {
        callback(new Error('请确认密码'))
      } else if (confirmPass !== this.form.password) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }
    return {
      isButtonDisabled: false,
      buttonText: '发送验证码',
      countdown: 60, // 倒计时时间，单位为秒
      form: { role: 'USER' },
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        confirmPass: [
          { validator: validatePassword, trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
        ],
        mailVerify: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ]
      }
    }
  },
  created() {

  },
  methods: {
    sendVerificationCode() {
      if (!this.form.email) {
        this.$message.error('请输入邮箱地址');
        return;
      }
      this.$request.post('/send/mail', { email: this.form.email }).then(res => {
        if (res.code === '200') {
          this.$message.closeAll();
          this.$message.success('验证码已发送至邮箱');
          // 开始倒计时
          this.startCountdown();
        } else {
          this.$message.closeAll();
          this.$message.error(res.msg);
        }
      });
    },
    startCountdown() {
      this.isButtonDisabled = true;
      this.buttonText = `${this.countdown} 秒后重试`;

      const countdownInterval = setInterval(() => {
        this.countdown--;
        this.buttonText = `${this.countdown} 秒后重试`;

        if (this.countdown === 0) {
          clearInterval(countdownInterval);
          this.isButtonDisabled = false;
          this.buttonText = '发送验证码';
          this.countdown = 60; // 重置倒计时时间
        }
      }, 1000);
    },
    register() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          // 验证通过
          this.$request.post('/register', this.form).then(res => {
            if (res.code === '200') {
              this.$router.push('/login')  // 跳转登录页面
              this.$message.success('注册成功');
            } else {
              this.$message.closeAll();
              this.$message.error(res.msg);
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.container {
  height: 100vh;
  overflow: hidden;
  background-image: url("@/assets/imgs/bg1.jpg");
  background-size: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #666;
}
a {
  color: #2a60c9;
}
</style>