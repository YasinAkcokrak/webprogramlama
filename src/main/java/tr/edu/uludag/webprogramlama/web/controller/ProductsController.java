package tr.edu.uludag.webprogramlama.web.controller;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tr.edu.uludag.webprogramlama.web.model.Product;

import java.util.List;

@Controller
@RequestMapping(value = "/products")
public class ProductsController {

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Product> getProducts() {
        List<Product> productList =
                Lists.newArrayList();
        productList.add(new Product("Mouse", "250"));
        productList.add(new Product("Keyboard", "300"));
        return productList;
    }

}
