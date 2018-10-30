package tr.edu.uludag.webprogramlama.service.management;

import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * Management ile ilgili hizmetleri içerir. Genellikle uygulamanın sistem admin konsol sayfalarındaki işlemler için kullanılır.
 */
@Component
public class ManagementService {

    /**
     * Mevcut JVM in JMX {@link RuntimeMXBean} ini döndürür.
     *
     * @return RuntimeMXBean
     */
    public RuntimeMXBean getRuntimeMXBean() {
        return ManagementFactory.getRuntimeMXBean();
    }
}
