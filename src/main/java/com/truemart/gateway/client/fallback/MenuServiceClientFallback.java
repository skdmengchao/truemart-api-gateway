package com.truemart.gateway.client.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.truemart.common.response.Result;
import com.truemart.gateway.client.IMenuServiceClient;
import com.truemart.permissions.api.dto.MenuInfoDTO;

@Component
public class MenuServiceClientFallback implements IMenuServiceClient {

    @Override
    public Result<List<MenuInfoDTO>> findMenuListByUserId(String version, String userId) {
        // TODO Auto-generated method stub
        return null;
    }

 

}
