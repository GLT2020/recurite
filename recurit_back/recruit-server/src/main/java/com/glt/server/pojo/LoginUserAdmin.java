package com.glt.server.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glt.server.config.security.component.CustomAuthorityDeserializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUserAdmin implements UserDetails {

    private Admin admin;
    private List<String> permissions;
    private List<Role> roles;

    //由于simple这个对象redis序列化会出问题，让redis不序列化并不存储它
//    @JSONField(serialize = false)
    @JsonIgnore
    private List<SimpleGrantedAuthority> authorities;


    public LoginUserAdmin(List<String> permissions){
        this.permissions = permissions;
    }

    @Override
    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //如果本身有了权限集合，就不用再转化一次
        if(authorities != null){
            return authorities;
        }
        //将permisssion中String类型的权限信息封装成simpleGrantedAuthority对象
        //第一种方法的封装
//        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
//        for (String permission : permissions){
//            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
//            authorities.add(authority);
//        }

        //第二种方法：流式编程
        List<SimpleGrantedAuthority> authorities = roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return authorities;


    }

    @Override
    public String getPassword() {
        return this.getPassword();
    }

    @Override
    public String getUsername() {
        return this.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
