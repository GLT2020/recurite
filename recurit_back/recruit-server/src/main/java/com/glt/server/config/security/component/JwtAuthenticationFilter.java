package com.glt.server.config.security.component;


import com.glt.server.pojo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //获取token
        String authHeader = request.getHeader(tokenHeader);
//        System.out.println("jwt过滤器中的authHeader->>>>>"+authHeader);
//        if (authHeader == null){
//            filterChain.doFilter(request,response);
//            return;
//        }
        if(authHeader != null) {

            //存在token
            if (null != authHeader && authHeader.startsWith(tokenHead)) {
                //放行
                String authToken = authHeader.substring(tokenHead.length());
//                System.out.println("jwt拦截类里的token->>>>>" + authToken);
                String username = jwtTokenUtil.getUserNameFromToken(authToken);
//                System.out.println("jwt拦截类里的username->>>>>" + username);
                Integer state = jwtTokenUtil.getStateFromToken(authToken);
//                System.out.println("jwt过滤器里的state->>>>>>" + state);


                //token存在用户名但未登录
                if (null != username && null == SecurityContextHolder.getContext().getAuthentication()) {
                    //解析token
                    int userid = jwtTokenUtil.getIdFromToken(authToken);
                    if (!jwtTokenUtil.validateTokenById(authToken, userid)) {
//                    throw new RuntimeException("登录过期,请重新登录");
                        filterChain.doFilter(request, response);
                        return;
                    }

                    //从redis中获取用户信息
                    LoginUser loginUser = ((LoginUser) redisTemplate.opsForValue().get("login" + state + ":" + userid));
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
                    System.out.println(">>>>>>>>>>>>>>>>>>jwt过滤器中的loginUser:" + loginUser);

                    if (Objects.isNull(loginUser)) {
//                    throw new RuntimeException("用户未登录");
                        filterChain.doFilter(request, response);
                        return;
                    }

                    //存入SecurityContextHolder
                    //TODO 获取权限信息封装到Authentication中
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

        }

        //放行
        filterChain.doFilter(request,response);
    }
}
