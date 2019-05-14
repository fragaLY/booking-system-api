package by.vk.bookingsystem.configuration.swagger;

import java.util.Collections;

import by.vk.bookingsystem.BookingSystemApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The config for swagger2
 *
 * @author Vadzim_Kavalkou
 */
@Configuration
@ComponentScan("by.vk.bookingsystem.controller")
@EnableSwagger2
public class SwaggerConfiguration {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(
            RequestHandlerSelectors.basePackage(
                BookingSystemApplication.class.getPackage().getName()))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfo(
        "Booking System REST API",
        "The documentation of booking system.",
        "Ubuntu-Lite",
        null,
        new Contact(
            "Vadzim Kavalkou",
            "https://www.linkedin.com/in/vadzimkavalkou/",
            "vadzim.kavalkou@gmail.com"),
        null,
        null,
        Collections.emptyList());
  }
}
