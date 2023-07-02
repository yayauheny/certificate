package ru.clevertec.ecl.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.clevertec.ecl.inercetor.GenericInterceptor;
import ru.clevertec.ecl.inercetor.OrderInterceptor;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final GenericInterceptor genericInterceptor;
    private final OrderInterceptor orderInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(genericInterceptor).addPathPatterns("/**/certificates/**", "/**/tags/**", "/**/users/**");
        registry.addInterceptor(orderInterceptor).addPathPatterns("/**/orders/**");
    }

}
