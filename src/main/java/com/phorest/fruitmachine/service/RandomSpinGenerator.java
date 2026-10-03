package com.phorest.fruitmachine.service;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

public class RandomSpinGenerator {
    private final RandomGenerator randomGenerator;
    public RandomSpinGenerator(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public List<String> generateRandomSpins(int slots,List<String> colors) {
        List<String> spinResult = new ArrayList<>();
        for(int i = 0; i < slots; i++) {
            int colorIndex = randomGenerator.nextInt(colors.size());
            spinResult.add(colors.get(colorIndex));
        }
        return spinResult;
    }
}
