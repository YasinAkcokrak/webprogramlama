package tr.edu.uludag.webprogramlama.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UrunlerService {

    @Autowired
    private UserService userService;

    public void urunleriKisiyeGoreGetir() {
        //TODO burası olmamıs gibi değiştir
        UserDetails userDetails =
                userService.getByTcNo("363263272323");
    }
}
