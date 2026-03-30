package com.example.bai5_qlsp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Long productId;
    private String name;
    private long price;
    private int quantity;
    private String image;

    // Tính tổng tiền của 1 sản phẩm (giá * số lượng)
    public long getTotal() {
        return price * quantity;
    }
}