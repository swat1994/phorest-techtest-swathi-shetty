package com.phorest.fruitmachine.service;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RandomSprinGeneratorTest {
    @Test
    public void shouldGenerateConfguredNumberOfslots() {
        RandomGenerator randomGenerator = mock(RandomGenerator.class);
        when(randomGenerator.nextInt(4)).thenReturn(0,1,2,3);

        RandomSpinGenerator randomSpinGenerator = new RandomSpinGenerator(randomGenerator);
        List<String> colors = List.of("BLACK", "RED", "GREEN", "BLUE");
        List<String> result = randomSpinGenerator.generateRandomSpins(4, colors);
        assertEquals(List.of("BLACK", "RED", "GREEN", "BLUE"), result);
    }
}
