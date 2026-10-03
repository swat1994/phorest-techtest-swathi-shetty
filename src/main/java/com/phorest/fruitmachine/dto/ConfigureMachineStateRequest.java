package com.phorest.fruitmachine.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record ConfigureMachineStateRequest(
        @Positive(message = "Slots must be greater than zero")
        int slots,
        @NotEmpty(message = "colors must not be empty")
        List<String> colors,
        @Min(
                value = 2,
                message = "adjacentMatchCount must be at least 2"
        )
        int adjacentMatchCount,
        @DecimalMin(
                value = "0.0",
                inclusive = false,
                message = "playCost must be greater than zero"
        )
        BigDecimal playCost,
        @PositiveOrZero(message = "initialFloat must be zero or greater")
        BigDecimal initialFloat
) {
}
