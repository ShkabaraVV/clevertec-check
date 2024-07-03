package ru.clevertec.check;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCard {
    private int cardNumber;
    private double discountRate;
}