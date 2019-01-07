package tr.edu.uludag.webprogramlama.web.model;

public class Cevap {
    private String icerik;
    private boolean secili;

    public Cevap() {
    }

    public Cevap(String icerik, boolean secili) {
        this.icerik = icerik;
        this.secili = secili;
    }

    @Override
    public String toString() {
        return "Cevap{" +
                "icerik='" + icerik + '\'' +
                ", secili=" + secili +
                '}';
    }
}
