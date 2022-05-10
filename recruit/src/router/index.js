import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

//基本路由
export const Staticroutes = [
  {
    path: '/',
    name: 'Login',
    component: () => import('../views/Login.vue'),
    hidden: true
  },
  {
    path: '/AdminLogin',
    name: 'AdminLogin',
    component: () => import('../views/AdminLogin.vue'),
    hidden: true
  },
  // 404
  {
    path: '/404',
    name: 'NotFound',
    component: () => import('../views/NotFound.vue'),
    meta: {
      title: 'NotFound',
      //通过这个来判断是否有权限显示菜单
      roles: ['ROLE_company', 'ROLE_company_not', 'ROLE_admin', 'ROLE_admin_service', 'ROLE_worker']
    },
    hidden: true
  },


]

// 动态路由
export const asyncRoutes = [

  //公司用户
  {
    path: '/CompanyHome',
    name: 'CompanyHome',
    redirect: '/CompanyHome/HomePage',
    component: () => import('../views/CompanyHome.vue'),
    meta: {
      title: 'CompanyHome',
      //通过这个来判断是否有权限显示菜单
      roles: ['ROLE_company', 'ROLE_company_not']
    },
    children: [
      // 公司信息
      {

        path: 'Companyinfo',
        name: 'Companyinfo',
        component: () => import('../views/company/Companyinfo.vue'),
        alwaysShow: true,
        meta: {
          title: 'Companyinfo',
          //通过这个来判断是否有权限显示菜单
          roles: ['ROLE_company', 'ROLE_company_not']
        }
      },
      // 工地模块
      {

        path: 'AllSiteinfo',
        name: 'AllSiteinfo',
        component: () => import('../views/company/AllSiteinfo.vue'),

        meta: {
          title: 'Siteinfo',
          roles: ['ROLE_company', 'ROLE_company_not']
        },


      },
      // 工地具体信息
      {

        path: 'SiteinfoDetail/:id',
        name: 'SiteinfoDetail',
        component: () => import('../views/company/SiteinfoDetail.vue'),
        props($route) {
          return {
            id: $route.query.id,

          }
        },
        meta: {
          title: 'SiteinfoDetail',
          roles: ['ROLE_company', 'ROLE_company_not']
        },
      },
      // 找人模块
      {

        path: 'FindWorker',
        name: 'FindWorker',
        component: () => import('../views/company/FindWorker.vue'),

        meta: {
          title: 'FindWorker',
          roles: ['ROLE_company']
        },
      },
      // 工人信息模块
      {

        path: 'ComWorkerInfo',
        name: 'ComWorkerInfo',
        component: () => import('../views/company/ComWorkerInfo.vue'),

        meta: {
          title: 'ComWorkerInfo',
          roles: ['ROLE_company']
        },
      },
      // 邮箱模块
      {

        path: 'Message',
        name: 'Message',
        component: () => import('../views/company/CompanyMessage.vue'),

        meta: {
          title: 'Message',
          roles: ['ROLE_company', 'ROLE_company_not']
        },
      },
      //主页公告
      {

        path: 'HomePage',
        name: 'HomePage',
        component: () => import('../components/HomePage.vue'),

        meta: {
          title: 'HomePage',
          roles: ['ROLE_company', 'ROLE_company_not']
        },
      },


    ]
  },

  //工人用户
  {
    path: '/WorkerHome',
    name: 'WorkerHome',
    redirect: '/WorkerHome/HomePage',
    component: () => import('../views/WorkerHome.vue'),
    meta: {
      title: 'WorkerHome',
      //通过这个来判断是否有权限显示菜单
      roles: ['ROLE_worker']
    },
    children: [
      // 个人信息
      {
        path: 'Workerinfo',
        name: 'Workerinfo',
        component: () => import('../views/worker/Workerinfo.vue'),
        alwaysShow: true,
        meta: {
          title: 'Workerinfo',
          //通过这个来判断是否有权限显示菜单
          roles: ['ROLE_worker']
        }
      },
      // 找工地模块
      {
        path: 'FindSite',
        name: 'FindSite',
        component: () => import('../views/worker/FindSite.vue'),

        meta: {
          title: 'FindSite',
          roles: ['ROLE_worker']
        },


      },
      // 找工地具体信息
      {

        path: 'WorkSiteinfo/:cid/:sid',
        name: 'WorkSiteinfo',
        component: () => import('../views/worker/WorkSiteinfo.vue'),
        props($route) {
          return {
            cid: $route.query.cid,
            sid: $route.query.sid,
          }
        },
        meta: {
          title: 'WorkSiteinfo',
          roles: ['ROLE_worker']
        },

      },
      // 找招聘信息模块
      {

        path: 'FindReMes',
        name: 'FindReMes',
        component: () => import('../views/worker/FindReMes.vue'),

        meta: {
          title: 'FindReMes',
          roles: ['ROLE_worker']
        },
      },
      // 邮箱模块
      {

        path: 'Message',
        name: 'Message',
        component: () => import('../views/worker/Message.vue'),

        meta: {
          title: 'Message',
          roles: ['ROLE_worker']
        },
      },
      //主页公告
      {

        path: 'HomePage',
        name: 'HomePage',
        component: () => import('../components/HomePage.vue'),

        meta: {
          title: 'HomePage',
          roles: ['ROLE_worker']
        },
      },
      //个人应聘
      {

        path: 'Application',
        name: 'Application',
        component: () => import('../views/worker/Application.vue'),

        meta: {
          title: 'Application',
          roles: ['ROLE_worker']
        },
      },




    ]
  },


  //系统用户
  {
    path: '/Home',
    name: 'Home',
    redirect: '/Home/VerifyCompany',
    component: () => import('../views/Home.vue'),
    meta: {
      title: 'Home',
      //通过这个来判断是否有权限显示菜单
      roles: ['ROLE_admin', 'ROLE_admin_service']
    },
    children: [

      // 验证公司模块
      {
        path: 'VerifyCompany',
        name: 'VerifyCompany',
        component: () => import('../views/admin/VerifyCompany.vue'),

        meta: {
          title: 'VerifyCompany',
          roles: ['ROLE_admin', 'ROLE_admin_service']
        },


      },
      // 修改工种
      {

        path: 'WorkTypeList',
        name: 'WorkTypeList',
        component: () => import('../views/admin/WorkTypeList.vue'),
        meta: {
          title: 'WorkTypeList',
          roles: ['ROLE_admin']
        },

      },
      // 查看所有公司信息
      {
        path: 'FindAllCompany',
        name: 'FindAllCompany',
        component: () => import('../views/admin/FindAllCompany.vue'),

        meta: {
          title: 'FindAllCompany',
          roles: ['ROLE_admin']
        },
      },
      // 查看所有工地信息
      {
        path: 'FindAllSite',
        name: 'FindAllSite',
        component: () => import('../views/admin/FindAllSite.vue'),

        meta: {
          title: 'FindAllSite',
          roles: ['ROLE_admin']
        },
      },
      // 查看所有工人信息
      {
        path: 'FindAllWorker',
        name: 'FindAllWorker',
        component: () => import('../views/admin/FindAllWorker.vue'),

        meta: {
          title: 'FindAllWorker',
          roles: ['ROLE_admin']
        },
      },
      // 添加客服账户
      {
        path: 'AddAdmin',
        name: 'AddAdmin',
        component: () => import('../views/admin/AddAdmin.vue'),

        meta: {
          title: 'AddAdmin',
          roles: ['ROLE_admin']
        },
      },
      // 管理公告
      {
        path: 'Announcement',
        name: 'Announcement',
        component: () => import('../views/admin/Announcement.vue'),

        meta: {
          title: 'Announcement',
          roles: ['ROLE_admin']
        },
      },
      // 查看反馈
      {
        path: 'AdminMessage',
        name: 'AdminMessage',
        component: () => import('../views/admin/AdminMessage.vue'),

        meta: {
          title: 'AdminMessage',
          roles: ['ROLE_admin']
        },
      },



    ]
  },
  // 404
  {
    path: '*',
    name: 'NotFound',
    redirect: '/404',
    // component: () => import('../views/NotFound.vue'),
    meta: {
      title: 'NotFound',
      //通过这个来判断是否有权限显示菜单
      roles: ['ROLE_company', 'ROLE_company_not', 'ROLE_admin', 'ROLE_admin_service', 'ROLE_worker']
    },
    hidden: true
  },






]


// const router = new VueRouter({
//   base: process.env.BASE_URL,
//   Staticroutes
// })



const createRouter = () => new VueRouter({
  base: process.env.BASE_URL,
  routes: Staticroutes
})

const router = createRouter()

// 这个是重置路由用的
export function resetRouter() {
  console.log("调用重置路由");
  const newRouter = createRouter()
  router.matcher = newRouter.matcher;
}

export default router
