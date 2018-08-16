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
import com.truemart.permissions.api.dto.ResourcePermissionsInfo;
import com.truemart.permissions.api.interfaces.ResourcePermissionsServiceClient;

/**
 * @author zhoulei
 *
 */
@RestController
public class ResourcePermissionsController {
	
	@Autowired
    private ResourcePermissionsServiceClient resourcePermissionsServiceClient;
	
	
	@RequestMapping(value="/findResourcePermissionsListByUserId/{userId}",method = RequestMethod.POST)
	public Result<List<ResourcePermissionsInfo>> findResourcePermissionsListByUserId(@PathVariable("userId") String userId){
		/*String userName=user.getUserName();
		String password=user.getPassword();*/
		Assert.notNull(userId, "userId can not be empty");
		if(StringUtils.isEmpty(userId)) {
			return ResultGenerator.failure(ResultCodeEnums.PARAM_IS_BLANK);
		}
	    Result<List<ResourcePermissionsInfo>> result= resourcePermissionsServiceClient.findResourcePermissionsListByUserId(userId);
		return result;
	}
	
	

}
