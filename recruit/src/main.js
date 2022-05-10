import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

//引入elementui
import ElementUI from 'element-ui';
//引入element的css
import 'element-ui/lib/theme-chalk/index.css';
import 'font-awesome/css/font-awesome.css';

//将api封装的四个请求放入VM中
import { postRequest, getRequest, putRequest, deleteRequest, postFileRequest } from "@/utils/api.js";
import { initMenu } from './utils/menus';
import { downloadRequest } from '@/utils/download';

Vue.use(ElementUI);

Vue.prototype.postRequest = postRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.deleteRequest = deleteRequest;
Vue.prototype.postFileRequest = postFileRequest;
Vue.prototype.downloadRequest = downloadRequest;

Vue.config.productionTip = false

router.beforeEach((to, from, next) => {
  console.log("路由守卫的to", to, "->>>路由守卫的from:", from);
  //判断是否有token
  if (window.sessionStorage.getItem('tokenStr')) {
    //判断是否有user

    if (!window.sessionStorage.getItem('user')) {
      // 判断用户信息是否存在
      return getRequest('/admin/info').then(resp => {
        // console.log("路由守卫中的获取用户信息->>>>>>>>");
        if (resp) {
          // 存入用户信息
          window.sessionStorage.setItem('user', JSON.stringify(resp));
          // next({ ...to, replace: true });
          let roles = [];
          roles = resp.roles;
          // console.log("登录获取用户的roles-》》》》", roles);
          //把获取到的role传进去匹配，生成可以访问的路由
          // store.dispatch('generateRoutes', roles).then(() => {
          //   // 动态添加可访问路由表
          //   router.addRoutes(store.getters.addRoutes);
          //   next();
          // })
          store.dispatch('generateRoutes', roles);
          // console.log("登录时的addRoutes->>>", store.state.addRoutes);

          //给动态路由加上404
          let rs = store.state.addRoutes;
          // rs.push({
          //   path: '/*',
          //   name: 'Not',
          //   redirect: '/404',
          //   // component: () => import('/views/NotFound.vue'),
          //   meta: {
          //     title: 'NotFound',
          //     //通过这个来判断是否有权限显示菜单
          //     roles: ['ROLE_company', 'ROLE_company_not', 'ROLE_admin', 'ROLE_admin_service', 'ROLE_worker']
          //   },
          //   hidden: true
          // });
          // router.addRoutes(store.state.addRoutes);
          // console.log("添加的路由->>>>>", rs);
          router.addRoutes(rs);

          next({ ...to, replace: true })
          // next();
        }
      })
    }
    else {
      // if (to.matched.length === 0) {
      //   next('/404') // 判断此跳转路由的来源路由是否存在，存在的情况跳转到来源路由，否则跳转到404页面
      // }
      next();
    }

  } else {
    if (to.path == '/' || to.path == '/AdminLogin') {
      next();
    } else {
      next('/?redirect=' + to.path);

    }

  }
})

new Vue({
  router,
  store,
  render: h => h(App),
  created() {
    if (window.sessionStorage.getItem('user')) {
      //当user已经存在的时候需要添加路由
      let user = JSON.parse(window.sessionStorage.getItem('user'));
      // console.log("获取登录用户的user->>>", user);
      let roles = [];
      roles = user.roles;


      store.dispatch('generateRoutes', roles);
      // console.log("登录时的addRoutes->>>", store.state.addRoutes);
      //给动态路由加上404
      let rs = store.state.addRoutes;
      // rs.push({
      //   path: '/*',
      //   name: 'Not',
      //   redirect: '/404',
      //   // component: () => import('/views/NotFound.vue'),
      //   meta: {
      //     title: 'NotFound',
      //     //通过这个来判断是否有权限显示菜单
      //     roles: ['ROLE_company', 'ROLE_company_not', 'ROLE_admin', 'ROLE_admin_service', 'ROLE_worker']
      //   },
      //   hidden: true
      // });
      // console.log("Create中添加的路由->>>>>", rs);
      // router.addRoutes(store.state.addRoutes);
      router.addRoutes(rs);

    }
  }
}).$mount('#app')


