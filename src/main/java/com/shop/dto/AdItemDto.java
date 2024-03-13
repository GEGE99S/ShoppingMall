package com.shop.dto;

import com.shop.entity.Item;
import lombok.Data;

@Data
public class AdItemDto {
    private Long itemId;
    private String itemNm;
    public AdItemDto(Item item){
        itemId = item.getId();
        itemNm = item.getItemNm();
    }
}
