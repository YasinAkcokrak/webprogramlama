package tr.edu.uludag.webprogramlama.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tr.edu.uludag.webprogramlama.service.OnlineSinavService;
import tr.edu.uludag.webprogramlama.web.model.Sinav;

@Controller
@RequestMapping(value = "/online-sinav")
public class OnlineSinavController {
    @Autowired
    OnlineSinavService onlineSinavService;

    @RequestMapping(value = "/ales-sozel", method = RequestMethod.GET)
    @ResponseBody
    public Sinav getir() {
        return onlineSinavService.sinavOlustur();
    }
}
