
// import store from '../store'
// import { getRequest } from "./api"

// export const initMenu = (router, store) => {
//     //如果本身就有数据，不用再初始化
//     // console.log(store.state.routes.length);
//     if (store.state.routes.length > 0) {
//         return;
//     } else

//         return getRequest('/system/cfg/menu').then(data => {
//             if (data) {
//                 // 格式化router
//                 let fmtRoutes = formatRoutes(data);
//                 // 添加到router
//                 router.addRoutes(fmtRoutes);
//                 // 将数据存入vuex
//                 // console.log("将路由添加进去！！！");
//                 store.commit('initRouter', fmtRoutes);

//                 //连接socket
//                 store.dispatch('connect');
//             }
//         })
// }

// export const formatRoutes = (routes) => {
//     let fmtRoutes = [];
//     routes.forEach(router => {
//         let {
//             path,
//             component,
//             name,
//             iconCls,
//             children
//         } = router;
//         if (children && children instanceof Array) {
//             //递归生成
//             children = formatRoutes(children);
//         }
//         let fmRouter = {
//             path: path,
//             name: name,
//             iconCls: iconCls,
//             children: children,
//             component(resolve) {
//                 if (component.startsWith('Home')) {
//                     // 懒加载：
//                     require(['../views/' + component + '.vue'], resolve);
//                 }
//                 if (component.startsWith('Emp')) {
//                     require(['../views/emp/' + component + '.vue'], resolve);
//                 } else if (component.startsWith('Per')) {
//                     require(['../views/per/' + component + '.vue'], resolve);
//                 }
//                 else if (component.startsWith('Sal')) {
//                     require(['../views/sal/' + component + '.vue'], resolve);
//                 }
//                 else if (component.startsWith('Sta')) {
//                     require(['../views/sta/' + component + '.vue'], resolve);
//                 }
//                 else if (component.startsWith('Sys')) {
//                     require(['../views/sys/' + component + '.vue'], resolve);
//                 }

//             },

//         }
//         fmtRoutes.push(fmRouter);
//     });
//     // console.log(fmtRoutes);
//     return fmtRoutes;
// }