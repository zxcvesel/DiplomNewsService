package egor.pantushov.newsservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/uploads/**") // URL-путь в браузере
                .addResourceLocations("file:///C:/Users/Admin/Desktop/diplom/News-Service/uploads/");
        // ^^^ путь на диске (обязательно с 3-мя /)
    }
}