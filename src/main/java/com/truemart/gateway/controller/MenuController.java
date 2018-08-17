/**
 * 
 */
package com.truemart.gateway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.truemart.common.enums.ResultCodeEnums;
import com.truemart.common.response.Result;
import com.truemart.common.response.ResultGenerator;
import com.truemart.common.util.StringUtils;
import com.truemart.gateway.client.IMenuServiceClient;
import com.truemart.permissions.api.dto.MenuInfoDTO;

/**
 * @author zhoulei
 *
 */
@RestController
public class MenuController {
	
	@Autowired
    private IMenuServiceClient menuServiceClient;
	
	
	@GetMapping(value="/{version}/menu/{userId}")
	public Result<List<MenuInfoDTO>> findMenuListByUserId(@PathVariable("version") String version,@PathVariable("userId") String userId){
		/*String userName=user.getUserName();
		String password=user.getPassword();*/
		Assert.notNull(userId, "userId can not be empty");
		if(StringUtils.isEmpty(userId)) {
			return ResultGenerator.failure(ResultCodeEnums.PARAM_IS_BLANK);
		}
	    Result<List<MenuInfoDTO>> result=menuServiceClient.findMenuListByUserId(version,userId);
		return result;
	}
	
	

}
