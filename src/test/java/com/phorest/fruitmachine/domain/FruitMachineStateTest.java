package com.phorest.fruitmachine.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class FruitMachineStateTest {
    @Test
    void shouldAddPlayCostToCurrentFloat(){
        FruiteMachineState fruiteMachineState = new FruiteMachineState(new BigDecimal("100.00"));
        fruiteMachineState.addPlayCost(new BigDecimal("2.00"));
        assertEquals(new BigDecimal("102.00"), fruiteMachineState.getCurrentFloat());
    }

    @Test
    void shouldSubtractPrizeAmountFromCurrentFloat(){
        FruiteMachineState fruiteMachineState = new FruiteMachineState(new BigDecimal("100.00"));
        fruiteMachineState.payOut(new BigDecimal("10.00"));
        assertEquals(new BigDecimal("90.00"), fruiteMachineState.getCurrentFloat());
    }

    @Test
    void shouldAddFreePlays(){
        FruiteMachineState fruiteMachineState = new FruiteMachineState(new BigDecimal("100.00"));
        fruiteMachineState.addFreePlays(10);
        assertEquals(10, fruiteMachineState.getFreePlays());
    }

    @Test
    void shouldSubtractOneFreePlay(){
        FruiteMachineState fruiteMachineState = new FruiteMachineState(new BigDecimal("100.00"));
        fruiteMachineState.addFreePlays(10);
        fruiteMachineState.useFreePlays();
        assertEquals(9, fruiteMachineState.getFreePlays());
    }
}
