package com.shop.dto;

import com.shop.constant.ClothesSize;
import com.shop.constant.ItemCategory;
import com.shop.constant.OrderStatus;
import com.shop.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistDto {
    private Long orderId;
    private String orderDate;
    private OrderStatus orderStatus;

    private ItemCategory itemCategory;
    private Integer kg;
    private ClothesSize clothesSize;
    private String color;


    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();
    public OrderHistDto(Order order){
        this.orderId = order.getId();

        this.orderDate = order.getOrderDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }
    public void addOrderItemDto(OrderItemDto orderItemDto){
        orderItemDtoList.add(orderItemDto);
    }
}
