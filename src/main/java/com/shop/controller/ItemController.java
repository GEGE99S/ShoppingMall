package com.shop.controller;

import com.shop.constant.ItemCategory;
import com.shop.dto.ItemFormDto;
import com.shop.dto.ItemSearchDto;
import com.shop.entity.Item;
import com.shop.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.thymeleaf.util.StringUtils.isEmptyOrWhitespace;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model){
        model.addAttribute("itemFormDto",new ItemFormDto());
        return "item/itemForm";
    }


    @PostMapping(value = "/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model,
                          @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {


        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }
        ItemCategory c = itemFormDto.getItemCategory();
        if (c == ItemCategory.FOOD || c == ItemCategory.MACHINE) {
            if (isEmptyOrWhitespace(itemFormDto.getKg())) {
                model.addAttribute("weightError", "무게는 필수 입력 값입니다.");
                return "item/itemForm";
            }
        }
        if (c == ItemCategory.CLOTHES || c == ItemCategory.MACHINE) {
            if (isEmptyOrWhitespace(itemFormDto.getColor())) {
                model.addAttribute("colorError", "색상은 필수 입력 값입니다.");
                return "item/itemForm";
            }
        }
        if (c == ItemCategory.CLOTHES) {
            if (itemFormDto.getClothesSize() == null) {
                model.addAttribute("sizeError", "사이즈는 필수 입력 값입니다.");
                return "item/itemForm";
            }
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("errorMessage",
                    "첫번째 상품 이미지는 필수 입력 값입니다.");
            return "item/itemForm";
        }
        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        return "redirect:/";
    }
    @GetMapping(value = "/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId")Long itemId, Model model){
        try {
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDto", itemFormDto);
        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage","존재하지 않는 상품입니다.");
            model.addAttribute("itemFormDto",new ItemFormDto());
           // return "item/itemForm";
        }

        return "item/itemForm";
    }

    @PostMapping(value = "/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList,
                             @PathVariable("itemId")Long itemId,
                             Model model){

        ItemFormDto item = itemService.getItemDtl(itemId);

        if(bindingResult.hasErrors()){
            model.addAttribute("itemFormDto",item);
            return "item/itemForm";
        }

        ItemCategory c = itemFormDto.getItemCategory();
        if (c == ItemCategory.FOOD || c == ItemCategory.MACHINE) {
            if (isEmptyOrWhitespace(itemFormDto.getKg())) {
                model.addAttribute("itemFormDto",item);
                model.addAttribute("weightError", "무게는 필수 입력 값입니다.");
                return "item/itemForm";
            }
        }
        if (c == ItemCategory.CLOTHES || c == ItemCategory.MACHINE) {
            if (isEmptyOrWhitespace(itemFormDto.getColor())) {
                model.addAttribute("itemFormDto",item);
                model.addAttribute("colorError", "색상은 필수 입력 값입니다.");
                return "item/itemForm";
            }
        }
        if (c == ItemCategory.CLOTHES) {
            if (itemFormDto.getClothesSize() == null) {
                model.addAttribute("itemFormDto",item);
                model.addAttribute("sizeError", "사이즈는 필수 입력 값입니다.");
                return "item/itemForm";
            }
        }
        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("itemFormDto",item);
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값입니다.");
            return "item/itemForm";
        }
        try {
            itemService.updateItem(itemFormDto, itemImgFileList);
        }catch (Exception e){
            model.addAttribute("itemFormDto",item);
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            System.out.println(e.getMessage());
            return "item/itemForm";
        }
        return "redirect:/"; // 다시 실행 /
    }

    @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
    public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page,
                             Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 20);
        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto",itemSearchDto);
        model.addAttribute("maxPage",5);
        return "item/itemMng";
    }


    // 메인에서 아이템 클릭시 넘어가기전에 뷰에 뿌릴 데이터
    @GetMapping(value = "/item/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId")Long itemId){
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
        model.addAttribute("item",itemFormDto);
        return "item/itemDtl";
    }

    @PostMapping(value = "/admin/item/delete/{itemId}")
    public String deleteItem(@PathVariable("itemId") Long itemId){
        System.out.println("삭제할 상품 번호 : " + itemId);
        itemService.deleteItem(itemId);
        return "redirect:/admin/items";
    }

}
