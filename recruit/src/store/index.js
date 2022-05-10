import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import { asyncRoutes, Staticroutes } from '@/router'

// 这个方法是用来把角色和route.meta.role来进行匹配
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}


// 这个方法是通过递归来遍历路由，把有权限的路由给遍历出来
export function filterAsyncRoutes(routes, roles) {
  const res = []
  // console.log("store中的过滤路由方法的routes->>>", routes);
  for (let route of routes) {
    const tmp = { ...route };
    // console.log("store中的过滤路由方法的tmp->>>", tmp);
    // console.log("store中的过滤路由方法的权限判断->>>", hasPermission(roles, tmp));
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
    else {
      continue;
    }
  }
  // routes.forEach(route => {
  //   const tmp = { ...route }
  //   console.log("store中的过滤路由方法的tmp->>>", tmp);
  //   console.log("store中的过滤路由方法的权限判断->>>", hasPermission(roles, tmp));
  //   if (hasPermission(roles, tmp)) {
  //     if (tmp.children) {
  //       tmp.children = filterAsyncRoutes(tmp.children, roles)
  //     }
  //     res.push(tmp)
  //   }

  // })

  return res
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    // 这个地方维护了两个状态一个是addRouters，一个是routes
    state.addRoutes = routes
    state.routes = Staticroutes.concat(routes);
  },
  initRouter(state, value) {
    state.routes = value;
  },
}

const actions = {
  generateRoutes(context, roles) {
    let roleList = [];
    // console.log("store中的roles->>>", roles);
    for (let item of roles) {
      roleList.push(item.name)
    }

    console.log("store中的roleList->>>", roleList);
    console.log("store中的asyncRoutes->>>", asyncRoutes);
    let accessedRoutes
    if (roles.includes('ROLE_admin')) {
      accessedRoutes = asyncRoutes || []
    } else {
      // 核心代码，把路由和获取到的角色(后台获取的)传进去进行匹配
      accessedRoutes = filterAsyncRoutes(asyncRoutes, roleList)
    }
    // 把匹配完有权限的路由给set到vuex里面
    context.commit('SET_ROUTES', accessedRoutes)
    // return new Promise(resolve => {
    //   let accessedRoutes
    //   if (roles.includes('ROLE_admin')) {
    //     accessedRoutes = asyncRoutes || []
    //   } else {
    //     // 核心代码，把路由和获取到的角色(后台获取的)传进去进行匹配
    //     accessedRoutes = filterAsyncRoutes(asyncRoutes, roleList)
    //   }
    //   // 把匹配完有权限的路由给set到vuex里面
    //   context.commit('SET_ROUTES', accessedRoutes)
    //   resolve(accessedRoutes)
    // })
  }
}

export default new Vuex.Store({
  state,
  mutations,
  actions,



})
