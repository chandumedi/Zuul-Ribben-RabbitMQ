package com.stackroute.keepnote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

import com.stackroute.keepnote.config.RibbenConfiguration;

/*
 * The @SpringBootApplication annotation is equivalent to using @Configuration, @EnableAutoConfiguration 
 * and @ComponentScan with their default attributes
 */

@SpringBootApplication
@EnableDiscoveryClient
@RibbonClient(name = "ping-server", configuration = RibbenConfiguration.class)
public class CategoryServiceApplication {

	/*
	 * Define the bean for Filter registration. Create a new FilterRegistrationBean
	 * object and use setFilter() method to set new instance of JwtFilter object.
	 * Also specifies the Url patterns for registration bean.
	 */
	/*@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean regBean = new FilterRegistrationBean<>();
		regBean.setFilter(new JwtFilter());
		regBean.addUrlPatterns("/api/*");
		return regBean;
	}*/

	/*
	 * 
	 * You need to run SpringApplication.run, because this method start whole spring
	 * framework. Code below integrates your main() with SpringBoot
	 */

	public static void main(String[] args) {
		SpringApplication.run(CategoryServiceApplication.class, args);
	}
}
