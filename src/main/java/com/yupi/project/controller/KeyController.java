package com.yupi.project.controller;


import cn.hutool.core.util.RandomUtil;
import com.yupi.yuapicommon.model.entity.User;
import com.yupi.yuapicommon.model.vo.UserSettingVo;
import com.yupi.project.common.BaseResponse;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.common.ResultUtils;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.model.dto.user.UserSettingRequest;

import com.yupi.project.service.UserService;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.yupi.project.constant.UserConstant.SALT;

/**
 *
 * 用户钥匙
 *
 * @author 李俊豪
 *
 */
@RestController
@RequestMapping("/key")
public class KeyController {


    @Resource
    private UserService userService;


    /**
     *
     * 获取用户个人信息
     * @param request
     * @return
     */
   @GetMapping("/get")
    public BaseResponse<User> getUser(HttpServletRequest request){

       User user = userService.getLoginUser(request);
       return ResultUtils.success(user);

   }

   @PostMapping("/modify")
    public BaseResponse<Boolean> updateSetting( HttpServletRequest request){




       User user = userService.getLoginUser(request);
       Long userId = user.getId();
       User realUser = userService.getById(userId);


       String accessKey = DigestUtils.md5DigestAsHex((SALT + System.currentTimeMillis() / 1000 + RandomUtil.randomNumbers(5) ).getBytes());
       String secretKey = DigestUtils.md5DigestAsHex((SALT + System.currentTimeMillis() / 1000 + RandomUtil.randomNumbers(8) ).getBytes());
       realUser.setAccessKey(accessKey);
       realUser.setSecretKey(secretKey);

       boolean result = userService.updateById(realUser);

       return ResultUtils.success(result);

   }








}
