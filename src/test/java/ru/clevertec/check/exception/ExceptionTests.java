package ru.clevertec.check.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExceptionTests {

    @Test
    public void testBadRequestException() {
        String message = "Bad request!";
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            throw new BadRequestException(message);
        });
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testInternalServerErrorException() {
        String message = "Internal server error!";
        Throwable cause = new RuntimeException("Root cause");
        InternalServerErrorException exception = assertThrows(InternalServerErrorException.class, () -> {
            throw new InternalServerErrorException(message, cause);
        });
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testNotEnoughMoneyException() {
        String message = "Not enough money!";
        NotEnoughMoneyException exception = assertThrows(NotEnoughMoneyException.class, () -> {
            throw new NotEnoughMoneyException(message);
        });
        assertEquals(message, exception.getMessage());
    }
}