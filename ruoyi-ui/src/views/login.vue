<template>
  <div class="login">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">奈斯后台管理系统</h3>
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="code" v-if="captchaOnOff">
        <el-input
          v-model="loginForm.code"
          auto-complete="off"
          placeholder="谷歌验证码，如果没有绑定不要输入"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </el-input>
        <!--        <div class="login-code">
                  <img :src="codeUrl" @click="getCode" class="login-code-img"/>
                </div>-->
      </el-form-item>
      <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleLogin"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2023 赛博朋克 All Rights Reserved.</span>
    </div>

    <el-dialog :title="title" :visible.sync="open" :width="dialogWidth" :close-on-click-modal="false" :close-on-press-escape="false" :show-close="false" :center="true">
      <el-form ref="form" :model="loginForm" :rules="codeRules">
        <el-form-item  prop="googleCode">
          <span><vue-qr :text="codeUrl" :width="qrCodeWidth"></vue-qr></span>
        </el-form-item>
        <el-form-item  prop="code">
          <el-input maxlength="6" v-model="loginForm.code" placeholder="请输入绑定后的谷歌验证码"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click.native.prevent="codeLogin">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from '@/utils/jsencrypt'
import VueQr from "vue-qr";

export default {
  name: "Login",
  components: {
    VueQr
  },
  data() {
    return {
      title:"",
      codeUrl: "",
      cookiePassword: "",
      open: false,
      loginForm: {
        username: "",
        password: "",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "用户名不能为空" }
        ],
        password: [
          { required: true, trigger: "blur", message: "密码不能为空" }
        ],
        // code: [{ required: true, trigger: "change", message: "验证码不能为空" }]
      },
      codeRules: {
        code: [{ required: true, trigger: "change", message: "谷歌验证码不能为空" }]
      },
      loading: false,
      captchaOnOff: true,
      redirect: undefined,
      dialogWidth: 0,
      qrCodeWidth:0,
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getCode();
    this.getCookie();
  },
  mounted() {
    this.setDialogWidth();
    window.onresize = () => {
      return (() => {
        this.setDialogWidth()
      })()
    }
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaOnOff = res.captchaOnOff === undefined ? true : res.captchaOnOff;
        if (this.captchaOnOff) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove('rememberMe');
          }
          this.$store.dispatch("Login", this.loginForm).then((tag) => {
            if(tag.isGoogle){
              //调出谷歌验证码
              this.open = true;
              this.codeUrl = tag.googleCode;
              this.title = "谷歌验证码绑定"
            } else{
              this.$router.push({ path: this.redirect || "/" }).catch(()=>{});
            }
          }).catch(() => {
            this.loading = false;
          });
        }
      });
    },
    codeLogin(){
      this.$refs.form.validate(valid => {
        if(valid){
          this.loading = true;
          this.title = "谷歌验证码绑定"
          this.$store.dispatch("Login", this.loginForm).then((tag) => {
            this.$router.push({ path: this.redirect || "/" }).catch(()=>{});
          }).catch(() => {
            this.loading = false;
          });
        }
      })
    },
    setDialogWidth() {
      const val = document.body.clientWidth;
      const def = 450 // 默认宽度
      if (val < def) {
        this.dialogWidth = '100%'
        this.qrCodeWidth = '100%'
      } else {
        this.dialogWidth = def + 'px'
        this.qrCodeWidth = (def-50) + 'px'
      }
    },
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.login-code-img {
  height: 38px;
}
</style>
