package tr.edu.uludag.webprogramlama.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment environment;
    @Autowired
    private MessageSource messageSource;

    public static final int MAX_UPLOAD_SIZE = 250000000;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        // id security module
        SimpleModule webuygulamaSecurityModule = new SimpleModule("webuygulamaSecurity");
        mapper.registerModule(webuygulamaSecurityModule);

        return mapper;
    }

    /**
     * Controller methodlarında ResponseBody annotasyonu kullanıldığında html sayfası dönmez
     * Buradaki message converter ile obje çevirilir.
     * Burada Jackson Json converter configure edilmiştir.
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        jacksonConverter.setObjectMapper(objectMapper());
        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        byteArrayHttpMessageConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_OCTET_STREAM,
                MediaType.parseMediaType("application/pdf")));
        converters.add(byteArrayHttpMessageConverter);

        converters.add(jacksonConverter);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
    }

    private boolean notAcceptanceProfile() {
        return !Lists.newArrayList(environment.getActiveProfiles()).contains("acceptance-test");
    }

    /**
     * Spring controller methodlarından string döndürüldüğünde ve ResponseBody annotasyonu
     * kullanılmadığında html sayfası döndürmek için Thymelaef view engine kullanılır.
     * HTML sayfası döner.
     */
    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        viewResolver.setViewNames(new String[]{"*"});
        viewResolver.setCache(false);
        viewResolver.setContentType("text/html;charset=UTF-8");
        return viewResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        return engine;
    }

    @Bean
    public ServletContextTemplateResolver templateResolver() {
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setPrefix("/");
        resolver.setSuffix(".html");
        // Selecting HTML5 as the template mode.
        resolver.setTemplateMode("HTML5");
        resolver.setCacheable(Boolean.parseBoolean(environment.getProperty("html.cache")));
        return resolver;

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/fonts/**").addResourceLocations("/resources/fonts/").setCachePeriod(60);
        registry.addResourceHandler("/resources/i18n/**").addResourceLocations("/resources/i18n/").setCachePeriod(60);
        registry.addResourceHandler("/resources/images/**").addResourceLocations("/resources/images/").setCachePeriod(60);
        registry.addResourceHandler("/resources/bower_components/**").addResourceLocations("/resources/bower_components/").setCachePeriod(60);
        registry.addResourceHandler("/resources/js/**").addResourceLocations("/resources/js/").setCachePeriod(604800);
        registry.addResourceHandler("/resources/css/**").addResourceLocations("/resources/css/").setCachePeriod(604800);
        registry.addResourceHandler("/resources/styles/**").addResourceLocations("/resources/styles/").setCachePeriod(604800);
        registry.addResourceHandler("/html-parts/**").addResourceLocations("/html-parts/");
        registry.addResourceHandler("**/favicon.gif").addResourceLocations("/");
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource);
        return validator;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new AcceptHeaderLocaleResolver();
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(MAX_UPLOAD_SIZE);
        multipartResolver.setDefaultEncoding("utf-8");
        return multipartResolver;
    }


}
