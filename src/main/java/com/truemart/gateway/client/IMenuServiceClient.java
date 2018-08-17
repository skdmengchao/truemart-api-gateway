package com.truemart.gateway.client;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.truemart.gateway.client.fallback.MenuServiceClientFallback;
import com.truemart.permissions.api.interfaces.IMenuService;

@FeignClient(name = IMenuService.SERVICE_ID, fallback = MenuServiceClientFallback.class)
public interface IMenuServiceClient extends IMenuService {

}
