package tr.edu.uludag.webprogramlama.config.profile;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@Profile("jetty-development")
public class JettyDevelopmentConfig {
    private static Logger logger = LoggerFactory.getLogger(JettyDevelopmentConfig.class);

    @Autowired
    private Environment environment;


    @Bean
    public DataSource dataSource() throws IOException {
        BasicDataSource ds = new BasicDataSource();
        String dbUrl = environment.getProperty("db.url");
        logger.info("****** VERİ TABANI : " + dbUrl + " ********");
        ds.setUrl(dbUrl);
        ds.setDriverClassName(environment.getProperty("db.driverClass"));
        ds.setValidationQuery(environment.getProperty("db.validationQuery"));
        return ds;
    }


    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDefaultTimeout(Integer.parseInt(environment.getProperty("transaction.timeout")));
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }
}

