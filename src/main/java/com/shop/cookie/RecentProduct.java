package com.shop.cookie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecentProduct {
    private Long productId;
    private String recentItemNm;
    private String recentImage;
    private int price;
    private String itemHref;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        RecentProduct otherProduct = (RecentProduct) obj;
        return Objects.equals(productId, otherProduct.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
