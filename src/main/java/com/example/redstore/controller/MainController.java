package com.example.redstore.controller;

import com.example.redstore.entity.Order;
import com.example.redstore.entity.Product;
import com.example.redstore.enums.Tag;
import com.example.redstore.service.OrderService;
import com.example.redstore.service.ProductService;
import com.example.redstore.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

import static com.example.redstore.constants.Constants.*;

@Controller
public class MainController {

    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;

    public MainController(ProductService productService, UserService userService, OrderService orderService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/index")
    public String getIndex(Model model) {
        model.addAttribute(PRODUCT_LIST_ATTRIBUTE, productService.getByTag(Tag.FEATURED));
        return "index";
    }

    @GetMapping("/admin")
    public String getAdmin(Model model) {
        model.addAttribute(PRODUCT_LIST_ATTRIBUTE, productService.getAll());
        model.addAttribute("userList", userService.getAll());
        model.addAttribute("orderList", orderService.getAll());
        return "admin";
    }

    @GetMapping("/account")
    public String getAccount(Model model, Principal principal) {
        if (principal != null)
            model.addAttribute("currentuser", userService.getByEmail(principal.getName()));
        return "account";
    }

    @GetMapping("/cart")
    public String getCart(Model model, Principal principal) {
        long userId = userService.getByEmail(principal.getName()).getId();
        Map<Product, Order> cartInfo = getCartInfo(userId);
        model.addAttribute("tax", TAX_AMOUNT);
        model.addAttribute("subtotal", getSubtotal(cartInfo));
        model.addAttribute("products", cartInfo.keySet());
        model.addAttribute("orders", cartInfo);
        return "cart";
    }

    @GetMapping("/product/{page}")
    public String getProduct(Model model, @RequestParam("algorithm") String algorithm, @PathVariable("page") int page) {
        Page<Product> productPage = productService.getPaginated(page, algorithm);
        model.addAttribute("algorithm", "");
        model.addAttribute("currentPage", page);
        model.addAttribute("pageTotal", productPage.getTotalPages());
        model.addAttribute(PRODUCT_LIST_ATTRIBUTE, productPage.getContent());
        return "product";
    }

    @GetMapping("/product-details/{id}")
    public String getProductDetails(Model model, @PathVariable("id") long id) {
        model.addAttribute("currentProduct", productService.getById(id));
        model.addAttribute(PRODUCT_LIST_ATTRIBUTE, productService.getSimilarProducts(id));
        return "product-details";
    }

    private Map<Product, Order> getCartInfo(long userId) {
        List<Order> orders = orderService.getByUserId(userId);
        Map<Product, Order> productOrderMap = new HashMap<>();
        orders.forEach(o -> productOrderMap.put(productService.getById(o.getProductId()), o));
        return productOrderMap;
    }

    private double getSubtotal(Map<Product, Order> productOrderMap) {
        List<Order> orders = new ArrayList<>(productOrderMap.values());
        //subtotal equals sum of product price multiplied in product count
        return productOrderMap.keySet().stream()
                .mapToDouble(p -> p.getPrice() * getProductCount(orders, p.getId())).sum();
    }

    private int getProductCount(List<Order> orders, long productId) {
        Optional<Order> orderOptional = orders.stream()
                .filter(o -> o.getProductId() == productId)
                .findFirst();
        return orderOptional.map(Order::getProductCount).orElse(0);
    }
}
