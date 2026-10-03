package com.phorest.fruitmachine.service;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RandomSpinGeneratorTest {
    @Test
    public void shouldGenerateConfguredNumberOfslots() {
        RandomGenerator randomGenerator = mock(RandomGenerator.class);
        when(randomGenerator.nextInt(4)).thenReturn(0,1,2,3);

        RandomSpinGenerator randomSpinGenerator = new RandomSpinGenerator(randomGenerator);
        List<String> colors = List.of("BLACK", "RED", "GREEN", "BLUE");
        List<String> result = randomSpinGenerator.generateRandomSpins(4, colors);
        assertEquals(List.of("BLACK", "RED", "GREEN", "BLUE"), result);
    }

    @Test
    void shouldGenerateConfiguredNumberOfSlots() {
        RandomGenerator randomGenerator = mock(RandomGenerator.class);
        List<String> colors =
                List.of("BLACK", "RED", "GREEN");

        when(randomGenerator.nextInt(3))
                .thenReturn(0, 1, 2, 0, 1, 2);
        RandomSpinGenerator randomSpinGenerator = new RandomSpinGenerator(randomGenerator);
        List<String> slots =
                randomSpinGenerator.generateRandomSpins(
                        6,
                        colors
                );

        assertEquals(6, slots.size());
    }
}
