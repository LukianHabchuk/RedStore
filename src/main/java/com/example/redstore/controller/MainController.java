package com.example.redstore.controller;

import com.example.redstore.entity.Order;
import com.example.redstore.entity.Product;
import com.example.redstore.enums.Tag;
import com.example.redstore.service.OrderService;
import com.example.redstore.service.ProductService;
import com.example.redstore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        model.addAttribute(PRODUCT_LIST_ATTRIBUTE, productService.getAll().stream()
                .filter(p -> p.getTag() == Tag.FEATURED)
                .collect(Collectors.toList()));
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
    public String getProduct(Model model, @PathVariable("page") int page) {
        model.addAttribute("current_page", page);
        model.addAttribute("page_count", getPageCount());
        model.addAttribute(PRODUCT_LIST_ATTRIBUTE, productService.getPageProducts(page));
        return "product";
    }

    @PostMapping("/product/{page}")
    public String getProduct(Model model, String algorithm, @PathVariable("page") int page) {
        model.addAttribute("current_page", page);
        model.addAttribute("page_count", getPageCount());
        model.addAttribute(PRODUCT_LIST_ATTRIBUTE, productService.sort(algorithm));
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
                .mapToDouble(p -> p.getPrice() * orders.stream()
                        .filter(o -> o.getProductId() == p.getId())
                        .findFirst().get().getProductCount()).sum();
    }

    /** if there are enough products to completely cover the countable number of pages:
     * @return = number of fully covered pages
        if the last page does not occupy all the space:
                number of fully covered pages + 1
     */
    private int getPageCount() {
        int productCount = productService.getAll().size();
        return productCount % PRODUCT_COUNT_PER_PAGE == 0
                ? productCount / PRODUCT_COUNT_PER_PAGE
                : productCount / PRODUCT_COUNT_PER_PAGE + 1;
    }
}
