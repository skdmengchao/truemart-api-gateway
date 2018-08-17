/**
 * 
 */
package com.truemart.gateway.filter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import com.truemart.authorization.manager.TokenManager;
import com.truemart.authorization.model.TokenModel;
import com.truemart.common.enums.ResultCodeEnums;
import com.truemart.common.response.Result;
import com.truemart.config.Constants;
import com.truemart.gateway.client.IResourcePermissionsServiceClient;
import com.truemart.gateway.client.IUserServiceClient;
import com.truemart.permissions.api.dto.ResourcePermissionsInfoDTO;
import com.truemart.permissions.api.dto.UserInfoDTO;

/**
 * @author ZENGXP2
 * 
 * 统一拦截器
 * 
 * 请求头必须包含 平台编码  认知TOKEN
 * 
 * 从TOKEN 获取用户 userId,判断角色及其资源权限
 * 
 * 验证请求URL 是否有权限
 *
 */
public class AccessTokenFilter extends ZuulFilter {

	private static final Logger logger = LoggerFactory.getLogger(AccessTokenFilter.class);

	private static final String PARAM_TOKEN = "token";
	
	@Autowired
    private TokenManager tokenManager;
	
	@Autowired
    private IUserServiceClient userServiceClient;
	
	@Autowired
	private IResourcePermissionsServiceClient resourcePermissionsServiceClient;

	@Override
	public Object run() {  

		try {

			RequestContext context = RequestContext.getCurrentContext();
			HttpServletRequest request = context.getRequest();
			
			String requestUrl=request.getRequestURL().toString();
			if(requestUrl.contains("/login")) {
				context.setSendZuulResponse(true);
				context.setResponseStatusCode(200);
				return null;
			}
			if(requestUrl.contains(".htm")) {
				context.setSendZuulResponse(true);
				context.setResponseStatusCode(200);
				return null;
			}
			String authorization = request.getHeader(Constants.AUTHORIZATION);
			String platformCode = request.getHeader(Constants.PLATFORM_CODE);
			if(StringUtils.isEmpty(platformCode)){
				platformCode  = "101";
			}
			context.getResponse().setContentType("application/json;charset=UTF-8"); 
			
			if(StringUtils.isBlank(authorization)) {
				logger.warn("请求头没有authorization字段");  
		        //过滤该请求，不往下级服务去转发请求，到此结束  
				context.setSendZuulResponse(false);  
				context.setResponseStatusCode(401);  
				Integer code=ResultCodeEnums.PARAM_NOT_COMPLETE.code();
				context.setResponseBody("{\"code\":"+code+",\"msg\":\"请求头没有authorization字段\"}");  
		        return null;  
			}
			
			if(StringUtils.isBlank(platformCode)) {
				logger.warn("请求头没有platformCode字段");  
		        //过滤该请求，不往下级服务去转发请求，到此结束  
				context.setSendZuulResponse(false);  
				context.setResponseStatusCode(401);  
				Integer code=ResultCodeEnums.PARAM_NOT_COMPLETE.code();
				context.setResponseBody("{\"code\":"+code+",\"msg\":\"请求头没有platformCode字段\"}");  
		        return null;  
			}
			
			
			 TokenModel model = tokenManager.getToken(authorization);
		     if(model==null) {
		    	 logger.warn("认证失败,authentication不合法");
			     //过滤该请求，不往下级服务去转发请求，到此结束  
				context.setSendZuulResponse(false);  
				context.setResponseStatusCode(401);  
				Integer code=ResultCodeEnums.PARAM_IS_INVALID.code();
				context.setResponseBody("{\"code\":"+code+",\"msg\":\"认证失败,authorization不合法\"}");  
			    return null;  
		      }
		        
		    String userId=model.getUserId();
		    Result<UserInfoDTO> result=userServiceClient.getUserByUserId("v1",userId);
		    if(result.getCode()!=ResultCodeEnums.SUCCESS.code()){
		    	logger.warn("认证失败,非法用户");
			     //过滤该请求，不往下级服务去转发请求，到此结束  
				context.setSendZuulResponse(false);  
				context.setResponseStatusCode(401);  
				Integer code=ResultCodeEnums.USER_NOT_EXIST.code();
				context.setResponseBody("{\"code\":"+code+",\"msg\":\"认证失败,用户不合法\"}");  
			    return null;  
		    }
		    //资源访问权限控制
		   /* Result<List<ResourcePermissionsInfoDTO>> resourcePermissionsInfoListResult = resourcePermissionsServiceClient.findResourcePermissionsListByUserId("v1",userId);
		    if(resourcePermissionsInfoListResult.getCode()!=ResultCodeEnums.SUCCESS.code()){
		    	logger.warn("认证失败,资源权限服务查询错误");
			     //过滤该请求，不往下级服务去转发请求，到此结束  
				context.setSendZuulResponse(false);  
				context.setResponseStatusCode(401);  
				Integer code=ResultCodeEnums.INTERFACE_INNER_INVOKE_ERROR.code();
				context.setResponseBody("{\"code\":"+code+",\"msg\":\"认证失败,内部系统接口调用异常\"}");  
			    return null;  
		    }*/
		    boolean authorizationResult = true;
		  /*  List<ResourcePermissionsInfo> resourcePermissionsInfoList = resourcePermissionsInfoListResult.getData();
		    for (ResourcePermissionsInfo resourcePermissionsInfo : resourcePermissionsInfoList) {
				if(requestUrl.contains(resourcePermissionsInfo.getResourceUrl()) && platformCode.equals(resourcePermissionsInfo.getPlatformCode().toString())){
					authorizationResult = true;
					break;
				}
			}*/
		    if(!authorizationResult){
		    	logger.warn("认证失败,该用户没有当前资源访问权限 ，资源URL : "+requestUrl);
			     //过滤该请求，不往下级服务去转发请求，到此结束  
				context.setSendZuulResponse(false);  
				context.setResponseStatusCode(401);  
				Integer code=ResultCodeEnums.PERMISSION_NO_ACCESS.code();
				context.setResponseBody("{\"code\":"+code+",\"msg\":\"认证失败,无访问权限\"}");  
			    return null;  
		    }
		    String token=model.getToken();
		    
			context.addZuulRequestHeader("X-USER-ID",userId);
			context.addZuulRequestHeader("X-USER-TOKEN",token);
			context.addZuulRequestHeader("X-PLATFORM-CODE",platformCode);
			logger.info("accessToken:" + token);
			logger.info("params:" + context.getRequestQueryParams());
			logger.info("contentLength:" + request.getContentLength());
			logger.info("contentType:" + request.getContentType());

			
			logger.info(String.format("%s AccessTokenFilter request to %s", request.getMethod(),
					request.getRequestURL().toString()));

			InputStream in = (InputStream) context.get("requestEntity");
			if (in == null) {
				in = context.getRequest().getInputStream();
			}
			
			// if (authResult.isSuccess()) {
			String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
			logger.info("body:" + body);
			body = StringUtils.replace(body, PARAM_TOKEN + "=" + token, PARAM_TOKEN + "=" + "");
			logger.info("转换后的body：" + body);
			// context.set("requestEntity", new
			// ByteArrayInputStream(body.getBytes("UTF-8")));
			final byte[] reqBodyBytes = body.getBytes();
			context.setRequest(new HttpServletRequestWrapper(context.getRequest()) {
				@Override
				public ServletInputStream getInputStream() throws IOException {
					return new ServletInputStreamWrapper(reqBodyBytes);
				}

				@Override
				public int getContentLength() {
					return reqBodyBytes.length;
				}

				@Override
				public long getContentLengthLong() {
					return reqBodyBytes.length;
				}
			});
			return null;
			// }

			/*context.setSendZuulResponse(true);
			context.setResponseStatusCode(200);
			context.set("isSuccess", true);*/

		} catch (IOException e) {

		}

		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return "pre";// 在请求被处理之后，会进入该过滤器
	}
}
