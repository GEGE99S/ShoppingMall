package com.shop.dto;

import com.shop.constant.ClothesSize;
import com.shop.constant.ItemCategory;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import jakarta.websocket.OnMessage;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemFormDto {
    // Item
    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "상품 상세설명을 입력해주세요.")
    private  String itemDetail;


    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;
    @NotNull (message = "카테고리는 필수 선택입니다.")
    private ItemCategory itemCategory;
    private ClothesSize clothesSize;
    private String kg;
    private String color;




//----------------------------------------------------------------------------
    //ItemImg
    private List<ItemImgDto> itemImgDtoList = new ArrayList<>(); //상품 이미지 정보

    private List<Long> itemImgIds = new ArrayList<>(); //상품 이미지 아이디

//--------------------------------------------------------------------------------------
    // ModelMapper
    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem(){
        // ItemFormDto -> Item 연결
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto of(Item item){
        // Entity -> ItemFormDto 연결
        return modelMapper.map(item, ItemFormDto.class);
    }
}
