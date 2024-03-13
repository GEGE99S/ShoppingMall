package com.shop.controller;


import com.shop.cookie.RecentProduct;
import com.shop.service.RecentProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/clickProduct")
public class RecentProductController {
    private final RecentProductService recentProductService;

    @PostMapping(value = "/recent-products/{productCode}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResponseEntity<?> addRecentProduct(@RequestBody Map<String, String> requestData, HttpServletResponse res, HttpServletRequest request) throws IOException {
        System.out.println("요청옴!");
        try {
            int itemPrice = Integer.parseInt(requestData.get("itemPrice"));
            Long itemId = Long.valueOf(requestData.get("itemId"));
            String itemNm = requestData.get("itemNm");
            String itemHref = requestData.get("itemHref");
            String itemImage = requestData.get("imgUrl");
            RecentProduct newRecentPr = new RecentProduct(itemId, itemNm, itemImage, itemPrice, itemHref);
            recentProductService.saveRecentProductToCookie(res, request, newRecentPr);
            return ResponseEntity.ok().body(1);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("badRequest");
        }
    }
}
