package tr.edu.uludag.webprogramlama.config.util.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.sift.Discriminator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.edu.uludag.webprogramlama.service.management.ManagementService;

/**
 * Logback SiftAppender larının kullandığı Discriminator.
 * Farklı JVM lerin farklı isimdeki dosyalara loglaması sağlanır.
 */
public class JvmProcessIdDiscriminator implements Discriminator<ILoggingEvent> {

    private static final Logger logger = LoggerFactory.getLogger(JvmProcessIdDiscriminator.class);

    private static final String KEY = "jvmId";
    public static final String DEFAULT_JVM_ID_VALUE = "unknownJvmId";
    private static String jvmId;
    private boolean started;
    private ManagementService managementService;

    public JvmProcessIdDiscriminator() {
        this.managementService = new ManagementService();
    }

    public JvmProcessIdDiscriminator(ManagementService managementService) {
        this.managementService = managementService;
    }

    /**
     * SiftAppender ların kullandığı discriminator değerini döndürür.
     * Bu değer ile her JVM için farklı isimdeki dosyalara log alınır.
     * <p>
     * JVM in process id si JMX üzerinden alınmaya çalışılır.
     * Ancak implementasyon farklılıklarına göre PID bulunamaz ise default değer kullanılır.
     * <p>
     * Değer ilk alındıktan sonra static bir değişkende uygulama yaşam döngüsü boyunca tutulur ve bu değişken kullanılır.
     *
     * @param iLoggingEvent logging event
     * @return ayrıştırıcı değer olarak kullanıcak olan jvmId değeri
     */
    @Override
    public String getDiscriminatingValue(ILoggingEvent iLoggingEvent) {
        if (jvmId != null) {
            return jvmId;
        }
        jvmId = getJvmProcessId(DEFAULT_JVM_ID_VALUE);
        return jvmId;
    }

    protected String getJvmProcessId(final String fallback) {
        final String jvmName = managementService.getRuntimeMXBean().getName();
        final int index = jvmName.indexOf('@');
        if (index < 1) {
            return fallback;
        }
        try {
            return Long.toString(Long.parseLong(jvmName.substring(0, index)));
        } catch (NumberFormatException e) {
            logger.trace("NumberFormatException parsing jvmId for log discriminator value: {}, error: {}", jvmName.substring(0, index), e.getMessage());
        }
        return fallback;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public void start() {
        started = true;
    }

    @Override
    public void stop() {
        started = false;
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    static void clearValue() {
        jvmId = null;
    }
}
