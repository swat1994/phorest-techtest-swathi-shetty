package com.phorest.fruitmachine.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FruitMachineConfigurationTest {
    @Test
    void shouldCreateValidConfiguration(){
        FruitMachineConfiguration fruitMachineConfiguration = new
                FruitMachineConfiguration(4,
                List.of("BLACK","WHITE","GREEN","RED"),
                2,
                new BigDecimal("2.00"),
                new BigDecimal("100.00"));

        assertEquals(4,fruitMachineConfiguration.slots());
        assertEquals(4,fruitMachineConfiguration.colors().size());
        assertEquals(2,fruitMachineConfiguration.adjacentMatchCount());
        assertEquals(new BigDecimal("2.00"),fruitMachineConfiguration.playCost());
        assertEquals(new BigDecimal("100.00"),fruitMachineConfiguration.initialFloat());
    }

    @Test
    void shouldValidateConfgurationWhenSlotsAreZero(){
        assertThrows(IllegalArgumentException.class,()-> new FruitMachineConfiguration(
                0,List.of("BLACK","WHITE","GREEN","RED"),
                2,
                new BigDecimal("2.00"),
                new BigDecimal("100.00"))
        );
    }

    @Test
    void shouldValidateConfigurationWhenColorsAreEmpty() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new FruitMachineConfiguration(
                        4,
                        List.of(),
                        2,
                        new BigDecimal("2.00"),
                        new BigDecimal("100.00")
                )
        );
    }

    @Test
    void shouldValidateWhenColorsAreBlank(){
        assertThrows(IllegalArgumentException.class,()-> new FruitMachineConfiguration(4,
                List.of(),
                2,
                new BigDecimal("2.00"),
                new BigDecimal("100.00")));
    }

    @Test
    void shouldValidateWhenAdjacentMatchCountIsLessThan2(){
        assertThrows(IllegalArgumentException.class,()-> new FruitMachineConfiguration(
                4,List.of("BLACK","WHITE","GREEN","RED"),
                1,
                new BigDecimal("2.00"),
                new BigDecimal("100.00")
        ));
    }

    @Test
    void shouldValidateWhenAdjacentMatchCountIsMoreThanSlots(){
        assertThrows(IllegalArgumentException.class,()-> new FruitMachineConfiguration(
                4,List.of("BLACK","WHITE","GREEN","RED"),
                5,
                new BigDecimal("2.00"),
                new BigDecimal("100.00")
        ));
    }

    @Test
    void shouldValidateConfigurationWhenPlayCostIszero(){
        assertThrows(IllegalArgumentException.class,()-> new FruitMachineConfiguration(
                4,List.of("BLACK","WHITE","GREEN","RED"),
                5,
                new BigDecimal("0.00"),
                new BigDecimal("100.00")
        ));
    }

    @Test
    void shouldValidateConfigurationWhenPlayCostIsNegative(){
        assertThrows(IllegalArgumentException.class,()-> new FruitMachineConfiguration(
                4,List.of("BLACK","WHITE","GREEN","RED"),
                5,
                new BigDecimal("-2.00"),
                new BigDecimal("100.00")
        ));
    }

    @Test
    void shouldValidateWhenIntialFloastIsNegative(){
        assertThrows(IllegalArgumentException.class,()-> new FruitMachineConfiguration(
                4,List.of("BLACK","WHITE","GREEN","RED"),
                5,
                new BigDecimal("-2.00"),
                new BigDecimal("-100.00")
        ));
    }

    @Test
    void shouldAlloweZeroIntialFloast(){
     FruitMachineConfiguration fruitMachineConfiguration =  new FruitMachineConfiguration(
                4,List.of("BLACK","WHITE","GREEN","RED"),
                2,
                new BigDecimal("2.00"),
                BigDecimal.ZERO
        );
     assertEquals(BigDecimal.ZERO,fruitMachineConfiguration.initialFloat());
    }

}
