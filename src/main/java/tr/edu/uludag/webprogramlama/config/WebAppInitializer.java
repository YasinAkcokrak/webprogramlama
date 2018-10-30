package tr.edu.uludag.webprogramlama.config;

import net.sf.ehcache.constructs.web.filter.GzipFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Properties;

public class WebAppInitializer implements WebApplicationInitializer {

    private static final Logger logger = LoggerFactory.getLogger(WebAppInitializer.class);

    private static final String PROFILE_CONFIG_FILE = "/config/profile.config";


    public void onStartup(ServletContext servletContext) throws ServletException {
        try {
            // Load profile config
            Properties properties = new Properties();
            properties.load(this.getClass().getResourceAsStream(PROFILE_CONFIG_FILE));
            // Set active profile for spring
            String profile = properties.getProperty("spring.profiles.active");
            servletContext.setInitParameter("spring.profiles.active", profile);
            // Create spring app context
            AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
            applicationContext.register(AppConfig.class);
            servletContext.addListener(new ContextLoaderListener(applicationContext));
            // Create spring dispatcher servlet
            ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet("dispatcherServlet", new DispatcherServlet(applicationContext));
            dispatcherServlet.setLoadOnStartup(1);
            dispatcherServlet.addMapping("/*");
            // Add Gzip filter
            FilterRegistration.Dynamic gzipFilter = servletContext.addFilter("gzipFilter", new GzipFilter());
            gzipFilter.addMappingForUrlPatterns(null, false, "*.js");
            // Add character encoding filter
            FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("characterEncodingFilter", CharacterEncodingFilter.class);
            encodingFilter.setInitParameter("encoding", "UTF-8");
            encodingFilter.setInitParameter("forceEncoding", "true");
            encodingFilter.addMappingForUrlPatterns(null, false, "/*");
            // Add spring security servlet
            FilterRegistration.Dynamic springSecurityFilter = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain"));
            springSecurityFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ERROR,
                    DispatcherType.ASYNC, DispatcherType.INCLUDE), true, "/*");
            // Used to get HttpServletRequest injected into beans
            servletContext.addListener(RequestContextListener.class);
            // Add app specific servlet context listener
            servletContext.addListener(WebAppContextListener.class);
        } catch (IOException e) {
            logger.error("IOException booting up servlet context", e);
        }


    }
}
