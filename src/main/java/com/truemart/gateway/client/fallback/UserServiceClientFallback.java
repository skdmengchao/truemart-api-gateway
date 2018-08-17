/**
 * 
 */
package com.truemart.gateway.client.fallback;

import org.springframework.stereotype.Component;

import com.truemart.common.response.Result;
import com.truemart.gateway.client.IUserServiceClient;
import com.truemart.permissions.api.dto.UserInfoDTO;


/**
 * @author zhoulei
 * 
 * 服务熔断
 *
 */
@Component
public class UserServiceClientFallback implements IUserServiceClient{

    @Override
    public Result<UserInfoDTO> getUserByUserId(String version, String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result<UserInfoDTO> getUserByUserName(String version, String userName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result<UserInfoDTO> login(String version, UserInfoDTO userInfoDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result<UserInfoDTO> registerUser(String version, UserInfoDTO userInfoDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result<UserInfoDTO> updateUser(String version, UserInfoDTO userInfoDTO) {
        // TODO Auto-generated method stub
        return null;
    }}
