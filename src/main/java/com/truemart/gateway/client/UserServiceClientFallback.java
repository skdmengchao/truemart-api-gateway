/**
 * 
 */
package com.truemart.gateway.client;

import org.springframework.stereotype.Component;

import com.truemart.common.response.Result;
import com.truemart.permissions.api.dto.UserInfo;


/**
 * @author zhoulei
 * 
 * 服务熔断
 *
 */
@Component
public class UserServiceClientFallback implements UserServiceClient{

	@Override
	public Result<UserInfo> getUserByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<UserInfo> getUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<UserInfo> login(String userName, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<UserInfo> registerUser(UserInfo user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<UserInfo> updateUser(UserInfo user) {
		// TODO Auto-generated method stub
		return null;
	}

}
