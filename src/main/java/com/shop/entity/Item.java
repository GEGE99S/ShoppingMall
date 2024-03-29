package com.shop.entity;

 import com.shop.constant.ClothesSize;
import com.shop.constant.ItemCategory;
import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import com.shop.exception.OutOfStockException;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name="item")
@Getter
@Setter
@ToString
public class Item extends BaseEntity{
    @Id //기본키
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO) // 자동을 1씩 증가
    private Long id; // 상품코드

    @Column(nullable = false, length = 50)
    private String itemNm; // 상품명

    @Column(name="price", nullable = false)
    private int price; // 가격


    @Lob
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

    @Column(nullable = false)
    private int stockNumber; // 재고 수량

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // 상품 판매 상태

    @NotNull
    @Enumerated (EnumType.STRING)
    private ItemCategory itemCategory;
    @Enumerated (EnumType.STRING)
    private ClothesSize clothesSize;
    private String kg;
    private String color;


//    private LocalDateTime regTime; // 등록 시간
//
//    private LocalDateTime updateTime; // 수정 시간

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "member_item",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Member> member;

    public void updateItem(ItemFormDto itemFormDto){
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
        this.itemCategory = itemFormDto.getItemCategory();
        this.kg = itemFormDto.getKg();
        this.color = itemFormDto.getColor();
        this.clothesSize = itemFormDto.getClothesSize();
    }

    //경고에요 산수 경고 경고
    public void removeStock(int stockNumber){
        int restStock = this.stockNumber - stockNumber; // 10,  5 / 10, 20
        if(restStock<0){
            throw new OutOfStockException("상품의 재고가 부족합니다.(현재 재고 수량: "+this.stockNumber+")");
        }
        this.stockNumber = restStock; // 5
    }

    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    }

}
