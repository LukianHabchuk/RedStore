package com.example.redstore.controller;

import com.example.redstore.dto.OrderDTO;
import com.example.redstore.dto.UserDTO;
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

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.redstore.constants.Constants.PRODUCT_COUNT_PER_PAGE;
import static com.example.redstore.constants.Constants.PRODUCT_LIST_ATTRIBUTE;

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
        model.addAttribute(PRODUCT_LIST_ATTRIBUTE, productService.getAll()
                .stream().filter(p -> p.getTag() == Tag.FEATURED).collect(Collectors.toList()));
        return "index";
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "admin";
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
        double subtotal;
        var userId = userService.getByEmail(principal.getName()).getId();
        List<Order> orders = orderService.getByUserId(userId);
        Map<Product, Order> productOrderMap = new HashMap<>();

        orders.forEach(o -> productOrderMap.put(productService.getById(o.getProductId()), o));
        //subtotal equals sum of product price multiplied in product count
        subtotal = orders.stream().mapToDouble(o ->
                o.getProductCount() * productOrderMap.keySet().iterator().next().getPrice()
        ).sum();

        model.addAttribute("tax", 30);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("products", productOrderMap.keySet());
        model.addAttribute("orders", productOrderMap);
        return "cart";
    }

    @GetMapping("/product/{page}")
    public String getProduct(Model model, @PathVariable("page") int page) {
        int productCount = productService.getAll().size();

        List<Product> productList = getPageProducts(productCount, page);

        model.addAttribute("sortAlgorithm");
        model.addAttribute("current_page", page);
        model.addAttribute("page_count", getPageCount(productCount));
        model.addAttribute(PRODUCT_LIST_ATTRIBUTE, productList);
        return "product";
    }

    @GetMapping("/product-details/{id}")
    public String getProductDetails(Model model, @PathVariable("id") long id) {
        var product = productService.getById(id);
        model.addAttribute("orderDTO", new OrderDTO());
        model.addAttribute("currentProduct", product);
        model.addAttribute(PRODUCT_LIST_ATTRIBUTE, productService.getAll().stream()
                .filter(p -> p.getType() == product.getType())
                .filter(p -> p.getId()!=product.getId())
                .limit(4).collect(Collectors.toList()));
        return "product-details";
    }

    private int getPageCount(int productCount) {
        return productCount % PRODUCT_COUNT_PER_PAGE == 0
                ? productCount / PRODUCT_COUNT_PER_PAGE
                : productCount / PRODUCT_COUNT_PER_PAGE + 1;
    }

    //determines how many products will be on each page
    private List<Product> getPageProducts(int productCount, int page) {
        return productCount >= PRODUCT_COUNT_PER_PAGE * page
                ? productService.getAll().subList((page - 1) * PRODUCT_COUNT_PER_PAGE, page * PRODUCT_COUNT_PER_PAGE)
                : productService.getAll().subList((page - 1) * PRODUCT_COUNT_PER_PAGE, productCount);
    }
}
