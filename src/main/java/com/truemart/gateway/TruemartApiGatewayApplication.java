package com.truemart.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.truemart.gateway.filter.AccessTokenFilter;

@ComponentScan(basePackages="com.truemart")
@EnableZuulProxy
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class TruemartApiGatewayApplication {
	
	@Bean  
    public AccessTokenFilter accessTokenFilter(){  
        return new AccessTokenFilter();  
    } 

	public static void main(String[] args) {
		SpringApplication.run(TruemartApiGatewayApplication.class, args);
	}
}
