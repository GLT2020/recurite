package com.glt.server.config.security.component;


import com.glt.server.pojo.NameHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    private static final String CLAIM_KEY_USERNAME="sub";
    private static final String CLAIM_KEY_CREATED="created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据用户信息生成token
     */
    public String generateToken(UserDetails userDetails, NameHelper nameHelper,Long id){
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_CREATED,new Date());
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put("phone",nameHelper.getPhone());
        claims.put("state",nameHelper.getState());
        claims.put("id",id);
        return generateToken(claims);
    }

    //根据id生成token
    public String generateToken(String userid,int state){
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_CREATED,new Date());
        claims.put(CLAIM_KEY_USERNAME,userid);
        claims.put("state",state);
        return generateToken(claims);
    }

    /**
     * 从token中获取登录名
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token){
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception exception) {
            username = null;
        }
        return username;
    }

    /**
     * 从token中获取id
     * @param token
     * @return
     */
    public int getIdFromToken(String token){

        if(StringUtils.isEmpty(token)){
            return -1;
        }

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
//        System.out.println("jwt工具栏里的获取state->>>>>"+claimsJws);
        return (int) claims.get("id");
    }

    /**
     * 从token中获取state
     * @param token
     * @return
     */
    public Integer getStateFromToken(String token){
        System.out.println("获取state工具栏里的token->>>>>"+token);
        if(StringUtils.isEmpty(token)){
            return -1;
        }

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        System.out.println("jwt工具栏里的获取state->>>>>"+claimsJws);
        return (int)claims.get("state");

    }

    /**
     * 从token中获取荷载
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 验证token是否有效
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token,UserDetails userDetails){
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 验证token是否有效
     * @param token
     * @param id
     * @return
     */
    public boolean validateTokenById(String token,int id){
        int ids = getIdFromToken(token);
        return ids == id && !isTokenExpired(token);
    }


    /**
     * 判断token是否可以被刷新
     * @param token
     * @return
     */
    public boolean canRefreash(String token){
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String refreshToken(String token){
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }


    /**
     * 判断token是否失效
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Date expireDate = getExpiredDateFromToken(token);
        return expireDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     * @param token
     * @return
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }


    /**
     * 根据荷载生成JWT TOKEN
     * @param claims
     * @return
     */
    private String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    /**
     * 生成token失效时间
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis()+expiration*1000);
    }
}
