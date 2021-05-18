package com.example.redStore.controller;

import com.example.redStore.dto.OrderDTO;
import com.example.redStore.dto.UserDTO;
import com.example.redStore.entity.Order;
import com.example.redStore.entity.Product;
import com.example.redStore.entity.User;
import com.example.redStore.enums.Tag;
import com.example.redStore.service.OrderService;
import com.example.redStore.service.ProductService;
import com.example.redStore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.redStore.constants.Constants.PRODUCT_COUNT_PER_PAGE;

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
        model.addAttribute("productList", productService.getAll()
                .stream().filter(p -> p.getTag() == Tag.FEATURED).collect(Collectors.toList()));
        return "index";
    }

    @GetMapping("/account")
    public String getAccount(Model model, Principal principal) {
        if (principal!=null)
            model.addAttribute("currentuser", userService.getByEmail(principal.getName()));
        model.addAttribute("userDTO", new UserDTO());
        return "account";
    }

    @GetMapping("/cart")
    public String getCart(Model model, Principal principal) {
        var user = userService.getByEmail(principal.getName());
        double subtotal = 0;
        List<Order> orders = orderService.getByUserId(user.getId());
        List<Product> productList = new ArrayList<>();
        orders.forEach(o -> productList.add(productService.getById(o.getProductId())));
        Map<Product, Order> productIntegerMap = new HashMap<>();

        for(var i = 0; i < orders.size(); i++) {
            productIntegerMap.put(productList.get(i), orders.get(i));
            subtotal += orders.get(i).getProductCount()*productList.get(i).getPrice();
        }

        model.addAttribute("tax", 30);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("products", productIntegerMap.keySet());
        model.addAttribute("orders", productIntegerMap);
        return "cart";
    }

    @GetMapping("/product/{page}")
    public String getProduct(Model model, @PathVariable("page") int page) {
        int productCount = productService.getAll().size();
        int pageCount = productCount % PRODUCT_COUNT_PER_PAGE == 0
                ? productCount / PRODUCT_COUNT_PER_PAGE
                : productCount / PRODUCT_COUNT_PER_PAGE + 1;

        List<Product> productList = productCount >= PRODUCT_COUNT_PER_PAGE * page
                ? productService.getAll().subList((page - 1) * PRODUCT_COUNT_PER_PAGE, page * PRODUCT_COUNT_PER_PAGE)
                : productService.getAll().subList((page - 1) * PRODUCT_COUNT_PER_PAGE, productCount);

        model.addAttribute("sortAlgorithm");
        model.addAttribute("current_page", page);
        model.addAttribute("page_count", pageCount);
        model.addAttribute("productList", productList);
        return "product";
    }

    @GetMapping("/product-details/{id}")
    public String getProductDetails(Model model, @PathVariable("id") long id) {
        Product product = productService.getById(id);
        model.addAttribute("orderDTO", new OrderDTO());
        model.addAttribute("currentProduct", product);
        model.addAttribute("productList", productService.getAll().stream().filter(p -> p.getType() == product.getType()).limit(4).collect(Collectors.toList()));
        return "product-details";
    }
}
