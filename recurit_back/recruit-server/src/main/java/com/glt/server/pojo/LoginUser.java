package com.glt.server.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glt.server.config.security.component.CustomAuthorityDeserializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUser implements UserDetails, Serializable {
    private Long id;
    private String username;
    private String phone;
    private String icon;
    private String password;
    private List<String> permissions;
    private List<Role> roles;
    private int state;//0系统用户 1企业 2工人
    //由于simple这个对象redis序列化会出问题，让redis不序列化并不存储它
//    @JSONField(serialize = false)
    @JsonIgnore
    private List<SimpleGrantedAuthority> authorities;



//    自定义构造方式 系统用户
    public LoginUser(Admin admin){
        this.id = admin.getId();
        this.username = admin.getName();
        this.icon = admin.getUserface();
        this.password = admin.getPassword();
        this.roles = admin.getRoles();
        this.state = 0;
    }

    //    自定义构造方式 企业用户
    public LoginUser(Company admin){
        this.id = admin.getId();
        this.username = admin.getName();
        this.phone = admin.getPhone();
        this.icon = admin.getUserface();
        this.password = admin.getPassword();
        this.roles = admin.getRoles();
        this.state = 1;
    }

    //    自定义构造方式 工人用户
    public LoginUser(Worker admin){
        this.id = admin.getId();
        this.username = admin.getName();
        this.phone = admin.getPhone();
        this.icon = admin.getUserface();
        this.password = admin.getPassword();
        this.roles = admin.getRoles();
        this.state = 2;
    }

    public LoginUser(List<String> permissions){
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




    public Long getId() {
        return id;
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
