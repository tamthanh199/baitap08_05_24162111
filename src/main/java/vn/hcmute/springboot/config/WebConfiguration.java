package vn.hcmute.springboot.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    public WebConfiguration(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> characterEncodingFilterRegistration() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceRequestEncoding(true);
        filter.setForceResponseEncoding(true);

        FilterRegistrationBean<CharacterEncodingFilter> bean = new FilterRegistrationBean<>(filter);
        bean.addUrlPatterns("/*");
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Bean(name = "sitemesh")
    public FilterRegistrationBean<CustomSiteMeshFilter> siteMeshFilter() {
        FilterRegistrationBean<CustomSiteMeshFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new CustomSiteMeshFilter());
        bean.addUrlPatterns("/*");
        bean.setName("sitemesh");
        bean.addInitParameter("dispatchMode", "include");
        bean.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
        return bean;
    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setAlwaysInclude(true);
        resolver.setContentType("text/html;charset=UTF-8");
        resolver.setOrder(Ordered.LOWEST_PRECEDENCE);
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/admin/**", "/profile");
    }
}
