package com.PowerBike.utils.resourcesConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/productImages/**")
                .addResourceLocations("file:src/main/resources/static/productImages/")
                .setCachePeriod(0); // Desactiva la caché
    }
}
