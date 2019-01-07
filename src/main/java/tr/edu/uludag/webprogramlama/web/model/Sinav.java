package tr.edu.uludag.webprogramlama.web.model;

import java.util.List;

public class Sinav {
    private String sinavAdi;
    private String dersAdi;
    private String dersKodu;
    private List<Soru> soruList;

    public Sinav() {

    }

    public Sinav(String sinavAdi, String dersAdi, String dersKodu, List<Soru> soruList) {
        this.sinavAdi = sinavAdi;
        this.dersAdi = dersAdi;
        this.dersKodu = dersKodu;
        this.soruList = soruList;
    }

    @Override
    public String toString() {
        return "Sinav{" +
                "sinavAdi='" + sinavAdi + '\'' +
                ", dersAdi='" + dersAdi + '\'' +
                ", dersKodu='" + dersKodu + '\'' +
                '}';
    }
}
