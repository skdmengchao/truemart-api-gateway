package com.truemart.gateway.client;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.truemart.gateway.client.fallback.IResourcePermissionsServiceClientFallback;
import com.truemart.permissions.api.interfaces.IResourcePermissionsService;


@FeignClient(name = IResourcePermissionsService.SERVICE_ID, fallback = IResourcePermissionsServiceClientFallback.class)
public interface IResourcePermissionsServiceClient extends IResourcePermissionsService {

}
