package tr.edu.uludag.webprogramlama.config.util.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.sift.Discriminator;

/**
 * Development ve test profillerinde kullanılan Discriminator.
 * Static olarak "development" değerini döndürür ve log dosyalarının isminde bu değer bulunur.
 */
public class DevelopmentDiscriminator implements Discriminator<ILoggingEvent> {

    private static final String KEY = "jvmId";
    private static final String JVM_ID_VALUE = "development";
    private boolean started;

    @Override
    public String getDiscriminatingValue(ILoggingEvent iLoggingEvent) {
        return JVM_ID_VALUE;
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
}
