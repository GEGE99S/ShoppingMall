package com.shop.controller;

import com.shop.service.BannerAdService;
import com.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class AdController {
    private final ItemService itemService;
    private final BannerAdService bannerAdService;

    @GetMapping("/admin/banner")
    public String editAd(Model model) {
        model.addAttribute("itemList", itemService.findByItemSellStatus());
        model.addAttribute("adList", bannerAdService.getList());
        return "advertise/editAd";
    }

    @PostMapping("/admin/banner/upload")
    public ResponseEntity<String> itemNew(@RequestParam("file") MultipartFile file, @RequestParam("category") Long itemId) {
        bannerAdService.updateBanner(file, itemId);
        return ResponseEntity.ok().body("업로드에 성공하였습니다.");
    }

    @DeleteMapping("/admin/banner/delete/{imgId}")
    public ResponseEntity<String> deleteAd(@PathVariable("imgId") Long bannerId){
        return bannerAdService.delete(bannerId);
    }
}

