package tr.edu.uludag.webprogramlama.service;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import tr.edu.uludag.webprogramlama.web.model.Cevap;
import tr.edu.uludag.webprogramlama.web.model.Sinav;
import tr.edu.uludag.webprogramlama.web.model.Soru;

import java.util.List;

@Service
public class OnlineSinavService {

    public Sinav sinavOlustur() {
        Soru soru1 = new Soru("Aşağıdaki altı çizili sözcüklerden hangisi " +
                "diğerlerine göre daha genel anlamlıdır?");
        Cevap cevap1 = new Cevap("Bahçedeki gülleri ben yetiştirdim.", false);
        Cevap cevap2 = new Cevap("Bahçedeki çiçekleri sen yetiştirdin.", false);
        Cevap cevap3 = new Cevap("Bahçedeki ağaçları o yetiştirdi.", false);
        Cevap cevap4 = new Cevap("Bahçedeki fidanları ben yetiştirdim.", false);
        Cevap cevap5 = new Cevap("Bahçedeki çimenleri ben yetiştirdim.", false);

        List<Cevap> cevapList = Lists.newArrayList();
        cevapList.add(cevap1);
        cevapList.add(cevap2);
        cevapList.add(cevap3);
        cevapList.add(cevap4);
        cevapList.add(cevap5);

        soru1.setCevaplar(cevapList);
        soru1.setDogruCevap(cevap2);

        List<Soru> soruList = Lists.newArrayList();
        soruList.add(soru1);

        return new Sinav("ALES", "Türkçe", "2018", soruList);
    }
}
