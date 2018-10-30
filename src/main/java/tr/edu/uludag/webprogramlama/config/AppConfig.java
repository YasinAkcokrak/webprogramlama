package tr.edu.uludag.webprogramlama.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import tr.edu.uludag.webprogramlama.config.profile.JettyDevelopmentConfig;

import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement
@EnableWebMvc
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@ComponentScan(basePackages = {"tr.edu.uludag.webprogramlama"},
        excludeFilters = @ComponentScan.Filter({Configuration.class}))
@Import({SecurityConfig.class,
        WebConfig.class,
        RestClientConfig.class,
        JettyDevelopmentConfig.class})
@PropertySource("classpath:/config/application.${spring.profiles.active:jetty-development}.properties")
public class AppConfig {

    @Autowired
    private Environment environment;
    @Autowired
    private DataSource datasource;

    /**
     * SpEl de $property gibi yerleri doldurur. Kaynak olarak PropertySource larını kullanır.
     * {@code @Value} annotasyonlarında kullanılır
     *
     * @return propertySourcesPlaceholderConfigurer
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(datasource);
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("localization/messages", "localization/ValidationMessages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }


}
