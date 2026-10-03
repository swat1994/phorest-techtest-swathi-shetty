package com.phorest.fruitmachine.dto;

import com.phorest.fruitmachine.domain.FruitMachineConfiguration;

import java.math.BigDecimal;

public record MachineStateResponse(
        FruitMachineConfiguration fruitMachineConfiguration,
        BigDecimal currentFloat,
        int freePlays
) {
}
