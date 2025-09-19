package com.rido.shop.controller;

import com.rido.shop.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;

    @PostMapping("/order")
    public String orders(@RequestParam Long itemId,
                         @RequestParam Integer count,
                         @RequestParam Integer price,
                         Authentication auth) {

        //로그인이 안되어 있다면 로그인페이지로 보낸다.
        if(auth == null && !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        User user = (User)auth.getPrincipal();

        salesService.addOrder(user.getUsername(), itemId, count, price);

        return "redirect:/list";
    }

    @GetMapping("/order-list")
    public String orderList(Model model) {
        model.addAttribute("orderList", salesService.getOrderList());

        return "orderList";
    }
}
