package tr.edu.uludag.webprogramlama.web.model;

import java.util.List;

public class Soru {
    private String metin;
    private List<Cevap> cevapList;
    private Cevap dogruCevap;

    public Soru(String metin) {
        this.metin = metin;
    }

    public void setCevaplar(List<Cevap> cevaplar) {
        this.cevapList = cevaplar;
    }

    public void setDogruCevap(Cevap dogruCevap) {
        this.dogruCevap = dogruCevap;
    }

    @Override
    public String toString() {
        return "Soru: " + this.metin;
    }
}
