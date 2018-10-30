package tr.edu.uludag.webprogramlama.config.util.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Random;

/**
 * Production da log dosyalarını ayır etmek için kullanılacak olan değeri sağlayan discriminator.
 * JNDI lookup ile Websphere application server ismini alır.
 * <p>
 * TODO implement ve test edilecek
 */
public class ProductionServerNameDiscriminator extends JvmProcessIdDiscriminator {

    private static final Logger logger = LoggerFactory.getLogger(JvmProcessIdDiscriminator.class);

    @Override
    protected String getJvmProcessId(String fallback) {
        try {
            InitialContext ic = new InitialContext();
            return ic.lookup("servername").toString();
        } catch (NamingException e) {
            logger.error("Error creating logging sift", e);
            return "ERROR_" + new Random().nextInt((int) System.currentTimeMillis());
        }
    }
}
