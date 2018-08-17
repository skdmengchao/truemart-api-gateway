/**
 * 
 */
package com.truemart.gateway.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.truemart.common.response.Result;
import com.truemart.gateway.client.fallback.UserServiceClientFallback;
import com.truemart.permissions.api.dto.UserInfoDTO;
import com.truemart.permissions.api.interfaces.IUserService;



/**
 * @author zhoulei
 * 
 * 调用 用户服务暴露的接口 通过 feign ,只需要声明接口
 *
 */
@FeignClient(name = IUserServiceClient.SERVICE_ID, fallback = UserServiceClientFallback.class)
public interface IUserServiceClient extends IUserService{
    
}
