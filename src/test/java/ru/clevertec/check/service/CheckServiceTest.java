package ru.clevertec.check.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.Product;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CheckServiceTest {

    @Mock
    private ProductService productService;

    @Mock
    private DiscountService discountService;

    private CheckService checkService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        checkService = new CheckService(productService, discountService);
    }
    @Test
    public void testGenerateReceipt_InvalidProductId() {
        // Mock data
        String[] args = {"999-1", "discountCard=1111", "balanceDebitCard=100"};

        // Stubbing ProductService (no need to stub getProductById() because it will return null by default)

        // Test method
        assertDoesNotThrow(() -> checkService.generateReceipt(args, "receipt.csv"));
    }

    @Test
    public void testGenerateReceipt_NoDiscountCard() {
        // Mock data
        String[] args = {"1-1"};

        // Stubbing ProductService
        Product product = new Product(1, "Test Product", new BigDecimal("10.00"), 10, true);
        when(productService.getProductById(1)).thenReturn(product);

        // Test method
        assertDoesNotThrow(() -> checkService.generateReceipt(args, "receipt.csv"));
    }

    @Test
    public void testGenerateReceipt_NoBalanceDebitCard() {
        // Mock data
        String[] args = {"1-1", "discountCard=1111"};

        // Stubbing ProductService
        Product product = new Product(1, "Test Product", new BigDecimal("10.00"), 10, true);
        when(productService.getProductById(1)).thenReturn(product);

        // Stubbing DiscountService
        DiscountCard discountCard = new DiscountCard(1111, 5.0);
        when(discountService.getDiscountCard(1111)).thenReturn(discountCard);

        // Test method
        assertDoesNotThrow(() -> checkService.generateReceipt(args, "receipt.csv"));
    }

    @Test
    public void testGenerateReceipt_ProductNotFound() {
        // Mock data
        String[] args = {"1-1", "discountCard=1111", "balanceDebitCard=100"};

        // ProductService returns null for getProductById()
        when(productService.getProductById(1)).thenReturn(null);

        // Test method
        assertDoesNotThrow(() -> checkService.generateReceipt(args, "receipt.csv"));
    }

}
