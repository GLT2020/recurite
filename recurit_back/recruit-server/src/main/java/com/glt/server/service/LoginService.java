package com.glt.server.service;

import com.glt.server.pojo.Admin;
import com.glt.server.pojo.LoginParam;
import com.glt.server.pojo.NameHelper;
import com.glt.server.pojo.RespBean;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


public interface LoginService {
    RespBean adminLogin(LoginParam loginParam, NameHelper nameHelper, HttpServletRequest httpRequest);
}
