package com.glt.server.config.security.component;


import com.glt.server.pojo.Menu;
import com.glt.server.pojo.Role;
import com.glt.server.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 权限控制
 * 根据请求url分析请求所需的角色
 * 通过这样获取当前访问路径拥有的角色的
 */
//@Component
//public class CustomerFilter  implements FilterInvocationSecurityMetadataSource {
//
//    @Autowired
//    private MenuService menuService;
//
//    // ant路径格式匹配器
//    AntPathMatcher antPathMatcher = new AntPathMatcher();
//
//
//    /**
//     * 方便以后，将请求url获得的角色与登录用户拥有的角色进行匹配
//     * @param object
//     * @return
//     * @throws IllegalArgumentException
//     */
//    @Override
//    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
////        获取请求的url
//        String requestUrl = ((FilterInvocation) object).getRequestUrl();
//        List<Menu> menus = menuService.getMenusWithRole();
//        for(Menu menu : menus){
////            判断请求url与菜单角色是否匹配
//            if(antPathMatcher.match(menu.getUrl(),requestUrl)){
//                String[] str= menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
////                System.out.println("从url获取的角色列表:" + str);
//                return SecurityConfig.createList(str);
//            }
//        }
////        没匹配的url默认登录即可访问
//        return SecurityConfig.createList("ROLE_LOGIN");
//    }
//
//    @Override
//    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        return null;
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return false;
//    }
//}
