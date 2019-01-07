package tr.edu.uludag.webprogramlama.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tr.edu.uludag.webprogramlama.web.model.Urun;

import java.util.Arrays;
import java.util.List;
@Controller
@RequestMapping(value = "/urunler")
public class UrunlerController {

    @RequestMapping(value = "/hepsi", method = RequestMethod.GET)
    @ResponseBody
    public List<Urun> get() {
        List<Urun> urunList = Arrays.asList(
                new Urun("Bisey", 10),
                new Urun("Iki sey", 20),
                new Urun("ÜÇ ŞEY", 10),
                new Urun("DÖRT sey", 20)
        );

        return urunList;
    }
}
