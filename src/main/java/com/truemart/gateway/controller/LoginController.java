/**
 * 
 */
package com.truemart.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.truemart.authorization.manager.TokenManager;
import com.truemart.authorization.model.TokenModel;
import com.truemart.common.enums.ResultCodeEnums;
import com.truemart.common.response.Result;
import com.truemart.common.response.ResultGenerator;
import com.truemart.gateway.client.IUserServiceClient;
import com.truemart.permissions.api.dto.UserInfoDTO;

/**
 * @author ZENGXP2
 *
 */
@RestController
public class LoginController {

    @Autowired
    private IUserServiceClient userServiceClient;

    @Autowired
    private TokenManager tokenManager;

    @PostMapping(value = "/{version}/user/login")
    public Result<UserInfoDTO> login(@PathVariable("version") String version,
            @RequestBody(required = true) UserInfoDTO userInfoDTO) {
        /*
         * String userName=user.getUserName(); String
         * password=user.getPassword();
         */
        Assert.notNull(userInfoDTO.getUserName(), "username can not be empty");
        Assert.notNull(userInfoDTO.getPassword(), "password can not be empty");
        Result<UserInfoDTO> result = userServiceClient.login(version, userInfoDTO);
        if (result.getCode().intValue() == ResultCodeEnums.SUCCESS.code().intValue()) {
            // 生成一个token，保存用户登录状态
            TokenModel model = tokenManager.createToken(result.getData().getUserId());
            if (model == null) {
                return ResultGenerator.failure(ResultCodeEnums.SYSTEM_TOKEN_ERROR);
            }
            result.getData().setToken(model.getToken());

        } else {
            return ResultGenerator.failure(ResultCodeEnums.USER_LOGIN_ERROR);
        }
        return result;
    }

}
