package com.shop.dto;

import com.shop.constant.ClothesSize;
import com.shop.constant.ItemCategory;
import com.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDto {
    private Long id;
    private String itemNm;
    private Integer price;
    private String itemDetail;
    private String sellStatCd;

    private ItemCategory itemCategory;
    private ClothesSize clothesSize;
    private String kg;
    private String color;

    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    //--------------------------------------------------------------------------------------
    // ModelMapper
    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem(){
        // ItemFormDto -> Item 연결
        return modelMapper.map(this, Item.class);
    }
    public static ItemDto of(Item item){
        // Entity -> ItemFormDto 연결
        return modelMapper.map(item, ItemDto.class);
    }
}
