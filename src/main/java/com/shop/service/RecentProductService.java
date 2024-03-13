package com.shop.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.cookie.RecentProduct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecentProductService {
    private ObjectMapper objectMapper = new ObjectMapper();  //  JSON <->Java 객체 deserialization  / serialization
    public static final String RECENT_PRODUCTS = "recentProducts";
    private static final int MAX_RECENT_PRODUCTS_SIZE = 5;


    public HttpServletResponse saveRecentProductToCookie(HttpServletResponse response, HttpServletRequest request, RecentProduct newRecentProduct) throws IOException {
        List<RecentProduct> recentProducts = getRecentProductsFromCookie(request);  // 클라이언트 쿠키 값 : JSON -> JAVA Object
        // 쿠키
        if (!recentProducts.contains(newRecentProduct)) { //
            recentProducts.add(0, newRecentProduct);
            if (recentProducts.size() > MAX_RECENT_PRODUCTS_SIZE) {
                recentProducts.remove(recentProducts.size() - 1);
            }
            String recentProductsJson = objectMapper.writeValueAsString(recentProducts); // 자바 객체 -> JSON
            String encodedRecentProductsJson = URLEncoder.encode(recentProductsJson, StandardCharsets.UTF_8.toString());
            Cookie cookie = new Cookie(RECENT_PRODUCTS, encodedRecentProductsJson);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60); // Cookie 유효기간 1일로 설정
            response.addCookie(cookie);
        }
        return response;
    }
/*
    public void saveRecentProductToCookie(HttpServletResponse response, HttpServletRequest request, Long productId, String recentImage, String recentItemNm, int price, String itemHref) throws IOException {
        List<RecentProduct> recentProducts = getRecentProductsFromCookie(request);  // 클라이언트 쿠키 값 : JSON -> JAVA Object
        // 쿠키
        if (!recentProducts.contains(recentImage)) {
            recentProducts.add(0, new RecentProduct(productId, recentItemNm, recentImage, price, itemHref));
            if (recentProducts.size() > MAX_RECENT_PRODUCTS_SIZE) {
                recentProducts.remove(recentProducts.size() - 1);
            }
            String recentProductsJson = objectMapper.writeValueAsString(recentProducts); // 자바 객체 -> JSON
            String encodedRecentProductsJson = URLEncoder.encode(recentProductsJson, StandardCharsets.UTF_8.toString());

            Cookie cookie = new Cookie(RECENT_PRODUCTS, encodedRecentProductsJson);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60); // Cookie 유효기간 1일로 설정
            response.addCookie(cookie);
        }
    }*/
    // JSON -> JAVA Object
    public List<RecentProduct> getRecentProductsFromCookie(HttpServletRequest request) throws IOException {
        Cookie[] cookies = request.getCookies();
        String recentProductCookieValue = getCookieValue(cookies, RECENT_PRODUCTS);

        if (recentProductCookieValue != null) {
            String decodedCookieValue = URLDecoder.decode(recentProductCookieValue, StandardCharsets.UTF_8.toString());
            return objectMapper.readValue(decodedCookieValue, new TypeReference<>() {
            });
        }
        // JSON -> JAVA Object
        return new ArrayList<RecentProduct>();
    }

    private String getCookieValue(Cookie[] cookies, String cookieName) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}