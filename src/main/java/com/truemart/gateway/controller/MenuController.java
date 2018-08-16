/**
 * 
 */
package com.truemart.gateway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.truemart.common.enums.ResultCodeEnums;
import com.truemart.common.response.Result;
import com.truemart.common.response.ResultGenerator;
import com.truemart.common.util.StringUtils;
import com.truemart.permissions.api.dto.MenuInfo;
import com.truemart.permissions.api.interfaces.MenuServiceClient;

/**
 * @author zhoulei
 *
 */
@RestController
public class MenuController {
	
	@Autowired
    private MenuServiceClient menuServiceClient;
	
	
	@RequestMapping(value="/findMenuListByUserId/{userId}",method = RequestMethod.POST)
	public Result<List<MenuInfo>> findMenuListByUserId(@PathVariable("userId") String userId){
		/*String userName=user.getUserName();
		String password=user.getPassword();*/
		Assert.notNull(userId, "userId can not be empty");
		if(StringUtils.isEmpty(userId)) {
			return ResultGenerator.failure(ResultCodeEnums.PARAM_IS_BLANK);
		}
	    Result<List<MenuInfo>> result=menuServiceClient.findMenuListByUserId(userId);
		return result;
	}
	
	

}
