package com.stackroute.keepnote.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*As in this class we are implementing Swagger So annotate the class with @Configuration and 
 * @EnableSwagger2
 * 
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

	
	

	/*
	 * Annotate this method with @Bean . This method will return an Object of Docket.
	 * This method will implement logic for swagger
	 */
    
	@Bean
	public Docket api() {
		// Also it is configuring the Swagger Docket
		return new Docket(DocumentationType.SWAGGER_2).select()
				// .apis(RequestHandlerSelectors.any())
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any())
				.build().apiInfo(metaData());

	}

	private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Reminder API")
                .description("This service can used to access and modify reminders to  user specific")
                .version("1.0.0")
                .license("CTC License Version 2.0")
                .build();
    }

	
}
