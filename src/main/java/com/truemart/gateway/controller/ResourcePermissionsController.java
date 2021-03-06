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
import com.truemart.gateway.client.IResourcePermissionsServiceClient;
import com.truemart.permissions.api.dto.ResourcePermissionsInfoDTO;

/**
 * @author zhoulei
 *
 */
@RestController
public class ResourcePermissionsController {
	
	@Autowired
    private IResourcePermissionsServiceClient resourcePermissionsServiceClient;
	
	
	@GetMapping(value="/{version}/resourcePermissions/{userId}")
	public Result<List<ResourcePermissionsInfoDTO>> findResourcePermissionsListByUserId(@PathVariable("version") String version,@PathVariable("userId") String userId){
		/*String userName=user.getUserName();
		String password=user.getPassword();*/
		Assert.notNull(userId, "userId can not be empty");
		if(StringUtils.isEmpty(userId)) {
			return ResultGenerator.failure(ResultCodeEnums.PARAM_IS_BLANK);
		}
	    Result<List<ResourcePermissionsInfoDTO>> result= resourcePermissionsServiceClient.findResourcePermissionsListByUserId(version,userId);
		return result;
	}
	
	

}
