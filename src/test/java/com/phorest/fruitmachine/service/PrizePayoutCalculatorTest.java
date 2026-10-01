package com.phorest.fruitmachine.service;

import com.phorest.fruitmachine.domain.PrizeType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class PrizePayoutCalculatorTest {
    private final PrizePayoutCalculator prizePayoutCalculator = new PrizePayoutCalculator();
    @Test
    void testPrizePayoutCalculatorForSmallPrize() {
      BigDecimal prizeAmount =  prizePayoutCalculator.calculatePrize(PrizeType.SMALL_PRIZE,
                new BigDecimal("2.00"),
              new BigDecimal("100.00"));
        assertEquals(new BigDecimal("10.00"), prizeAmount);
    }

    @Test
    void testPrizePayoutCalculatorForFullPrize() {
        BigDecimal prizeAmount = prizePayoutCalculator.calculatePrize(
                PrizeType.FULL_HOUSE,
                new BigDecimal("2.00"),
                new BigDecimal("100.00"));
        assertEquals(new BigDecimal("50.00"), prizeAmount);

    }

    @Test
    void testPrizePayoutCalculatorForJackPot() {
        BigDecimal prizeAmount = prizePayoutCalculator.calculatePrize(
                PrizeType.JACK_POT,
                new BigDecimal("2.00"),
                new BigDecimal("100.00"));
        assertEquals(new BigDecimal("100.00"), prizeAmount);
    }

    @Test
    void testReturnZeroForNoPrize() {
        BigDecimal prizeAmount = prizePayoutCalculator.calculatePrize(
                PrizeType.NO_PRIZE,
                new BigDecimal("2.00"),
                new BigDecimal("100.00"));
        assertEquals(BigDecimal.ZERO, prizeAmount);
    }

}
