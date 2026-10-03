package com.phorest.fruitmachine.config;


import com.phorest.fruitmachine.domain.FruitMachineConfiguration;
import com.phorest.fruitmachine.domain.FruitMachineState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

@Configuration
public class FruitMachineBeanConfiguration {

    @Bean
    public RandomGenerator randomGenerator() {

        return RandomGenerator.of("Random");
    }

    @Bean
    public FruitMachineConfiguration fruitMachineConfiguration() {
        return new FruitMachineConfiguration(
                4,
                List.of("BLACK", "WHITE", "GREEN", "YELLOW"),
                2,
                new BigDecimal("2.00"),
                new BigDecimal("100.00")
        );
    }

    @Bean
    public FruitMachineState fruitMachineState(FruitMachineConfiguration fruitMachineConfiguration) {
        return new FruitMachineState(
                fruitMachineConfiguration.initialFloat()
        );
    }
}
