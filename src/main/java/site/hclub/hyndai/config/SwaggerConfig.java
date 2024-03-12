package site.hclub.hyndai.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * @author 김동욱
 * @description: Swagger Config File
 * ===========================
AUTHOR      NOTE
 * ---------------------------
 *    김동욱        최초생성
 * ===========================
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public SwaggerConfig applicationSwaggerConfig() {
        return new SwaggerConfig();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { // spring-security와 연결할 때 이 부분을 작성하지 않으면 404에러가 뜬다.
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    public Docket getDocket(String groupName, Predicate<String> predicate) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("site.hclub.hyndai"))
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("H-CLUB : IT&E 사내동호회 서비스")
                .description("H-CLUB Swagger API")
                .version("1.0.0")
                .build();
    }

    @Bean
    public UiConfiguration uiconfig() {
        return UiConfigurationBuilder
                .builder().operationsSorter(OperationsSorter.ALPHA)
                .build();
    }

    @Bean
    public Docket userApi() {
        return getDocket("경쟁", Predicates.or(PathSelectors.regex("/controller.*")));
    }


}
