
package com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="b_AdImg")
@Getter
@Setter
@ToString
public class BannerAdImg  {
    @Id //기본키
    @GeneratedValue(strategy = GenerationType.AUTO) //
    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}

