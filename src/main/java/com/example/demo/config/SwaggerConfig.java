package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@EnableSwagger2
@Configuration
//@EnableSwagger
public class SwaggerConfig extends WebMvcConfigurationSupport 
//implements WebMvcConfigurer 
{
//	 private  String baseUrl;
//
//	  public SwaggerConfig(String baseUrl) {
//	    this.baseUrl = baseUrl;
//	  }
//
//	  @Override
//	  public void addResourceHandlers(ResourceHandlerRegistry registry) {
//	    String baseUrl = StringUtils.trimTrailingCharacter(this.baseUrl, '/');
//	    registry.
//	        addResourceHandler(baseUrl + "/swagger-ui/**")
//	        .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
//	        .resourceChain(false);
//	  }
//
//	  @Override
//	  public void addViewControllers(ViewControllerRegistry registry) {
//	    registry.addViewController(baseUrl + "/swagger-ui/")
//	        .setViewName("forward:" + baseUrl + "/swagger-ui/index.html");
//	  }
	  @Override
	    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("swagger-ui.html")
	                .addResourceLocations("classpath:/META-INF/resources/");
	        registry.addResourceHandler("/webjars/**")
	                .addResourceLocations("classpath:/META-INF/resources/webjars/");
	    }
	  @Bean
		public Docket api() { 
		  // http://localhost:8080/swagger-ui.html
		  
			return new Docket(DocumentationType.SWAGGER_2).select().apis(
					RequestHandlerSelectors.any())
					.paths(PathSelectors.any()).build().apiInfo(metaData());
		}

		private ApiInfo metaData() {
			return new ApiInfoBuilder().title("Spring Boot REST API").description("\"Spring Boot REST API SPS-Labs\"")
					.version("1.0.0").license("Apache License Version 2.0")
					.licenseUrl("https://spslabs.com/licenses/LICENSE-2.0\"")
					.contact(new Contact("Antonio De Fazio",
							"https://www.linkedin.com/in/antoniodefazio-java-python-developer-sviluppatore-trainer-docente/",
							"antonio.defazio@virgilio.it"))
					.build();
		}

//		@Bean
//		BeanValidatorPluginsConfiguration beanValidatorPluginsConfiguration() {
//			return new BeanValidatorPluginsConfiguration();
//		}
	  
	  
}


