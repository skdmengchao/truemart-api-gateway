/**
 * 
 */
package com.truemart.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.truemart.authorization.manager.TokenManager;
import com.truemart.authorization.model.TokenModel;
import com.truemart.common.enums.ResultCodeEnums;
import com.truemart.common.response.Result;
import com.truemart.common.response.ResultGenerator;
import com.truemart.gateway.client.UserServiceClient;
import com.truemart.permissions.api.dto.UserInfo;

/**
 * @author ZENGXP2
 *
 */
@RestController
public class LoginController {
	
	@Autowired
    private UserServiceClient userServiceClient;
	
	@Autowired
    private TokenManager tokenManager;
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public Result<UserInfo> login(@RequestParam("userName") String userName, @RequestParam("password") String password){
		/*String userName=user.getUserName();
		String password=user.getPassword();*/
		Assert.notNull(userName, "username can not be empty");
	    Assert.notNull(password, "password can not be empty");
	    Result<UserInfo> result=userServiceClient.login(userName, password);
	    if(result.getCode().intValue()==ResultCodeEnums.SUCCESS.code().intValue()){
	    	 //生成一个token，保存用户登录状态
	        TokenModel model = tokenManager.createToken(result.getData().getUserId());
	        if(model==null) {
	        	return ResultGenerator.failure(ResultCodeEnums.SYSTEM_TOKEN_ERROR);
	        }
	        result.getData().setToken(model.getToken());
	        
	    }else {
	    	return ResultGenerator.failure(ResultCodeEnums.USER_LOGIN_ERROR);
	    }
		return result;
	}
	
	

}
