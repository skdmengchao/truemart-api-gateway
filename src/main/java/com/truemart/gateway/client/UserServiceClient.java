/**
 * 
 */
package com.truemart.gateway.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.truemart.common.response.Result;
import com.truemart.permissions.api.dto.UserInfo;



/**
 * @author zhoulei
 * 
 * 调用 用户服务暴露的接口 通过 feign ,只需要声明接口
 *
 */
@FeignClient(name = UserServiceClient.SERVICE_ID, fallback = UserServiceClientFallback.class)
public interface UserServiceClient {
	
	 /**
     * eureka service name
     */
    String SERVICE_ID = "service-user";
   
    
    @RequestMapping("/getUserByUserId")
	public Result<UserInfo> getUserByUserId(@RequestParam(value = "userId", required = true) String userId);
    
    @RequestMapping("/getUserByUserName")
	public Result<UserInfo> getUserByUserName(@RequestParam(value = "userName", required = true) String userName); 
    
    @RequestMapping("/login")
	public Result<UserInfo> login(@RequestParam(value = "userName", required = true) String userName,@RequestParam(value = "password", required = true) String password);
    
    @RequestMapping("/registerUser")
    public Result<UserInfo> registerUser(@ModelAttribute("user") UserInfo user);
    
    @RequestMapping("/updateUser")
	public Result<UserInfo> updateUser(@ModelAttribute("user") UserInfo user);
	
	

}
