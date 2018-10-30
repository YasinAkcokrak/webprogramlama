package tr.edu.uludag.webprogramlama.web.model;

public class Urun {
    private String adi;
    private int fiyat;

    public Urun(String adi, int fiyat) {
        this.adi = adi;
        this.fiyat = fiyat;
    }

    public String getAdi() {
        return adi;
    }

    public int getFiyat() {
        return fiyat;
    }
}
