package tr.edu.uludag.webprogramlama.config;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Arrays;

public class WebAppContextListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(WebAppContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String activeProfiles = Arrays.toString(WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext())
                .getEnvironment().getActiveProfiles());
        logger.info("Application context started: {}", activeProfiles);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Application context destroyed");
        // Clearing logback JMXConfigurator
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        lc.stop();
    }
}
