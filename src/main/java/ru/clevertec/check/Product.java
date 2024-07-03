package ru.clevertec.check;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int id;
    private String description;
    private BigDecimal price;
    private boolean isWholesale;
}
