package com.jwtauth.jwtauth.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;


public class SwaggerConfig {

    @Bean
    public Docket swaggerConfiguration(){
              return new Docket(DocumentationType.SWAGGER_2)
                      .select()
                      .paths(PathSelectors.ant("/api/*"))
                      .apis(RequestHandlerSelectors.basePackage("com.jwtauth"))
                      .build();
    }

    private ApiInfo apiDetails (){
        return new ApiInfo(
                "JWT authentication",
                "Sample Application to try out JWT authentication",
                "1.0" ,
                "Free to use",
                new Contact("Babita Bisht","http://github.com/babitabisht","babitabisht060@gmail.com"),
                "API License",
                "http://github.com/babitabisht",
                 Collections.emptyList()

        )            ;
    }

}
