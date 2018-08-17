package com.truemart.gateway.client.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.truemart.common.response.Result;
import com.truemart.gateway.client.IResourcePermissionsServiceClient;
import com.truemart.permissions.api.dto.ResourcePermissionsInfoDTO;
import com.truemart.permissions.api.interfaces.IResourcePermissionsService;

@Component
public class IResourcePermissionsServiceClientFallback implements IResourcePermissionsServiceClient {

    @Override
    public Result<List<ResourcePermissionsInfoDTO>> findResourcePermissionsListByUserId(String version, String userId) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
