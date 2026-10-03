package com.phorest.fruitmachine.service;

import com.phorest.fruitmachine.domain.FruitMachineConfiguration;
import com.phorest.fruitmachine.domain.FruiteMachineState;
import com.phorest.fruitmachine.domain.PlayOutcome;
import com.phorest.fruitmachine.domain.PrizeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class FruitMachineServiceTest {



    private RandomSpinGenerator randomSpinGenerator;
    private  FruitMachineService fruitMachineService;
    private FruiteMachineState fruiteMachineState;
    @BeforeEach
    void setUp(){
        randomSpinGenerator = mock(RandomSpinGenerator.class);

        FruitMachineConfiguration configuration = new FruitMachineConfiguration(
                4,
                List.of("BLACK","RED","GREEN","YELLOW"),
                2,
                new BigDecimal("2.00"),
                new BigDecimal("100.00")
        );
        fruiteMachineState =
                new FruiteMachineState(configuration.initialFloat());
        fruitMachineService = new FruitMachineService(
                new PrizeOutcomeEvaluator(),
                new PrizePayoutCalculator(),
                randomSpinGenerator,
                configuration,
                fruiteMachineState
        );
    }

    @Test
    void shouldChargePlayCostwhenPlayingWithoutFreePlay(){
        List<String> slots =
                List.of("BLACK","RED","BLACK","YELLOW");
        when(randomSpinGenerator.generateRandomSpins(
                4,List.of("BLACK", "RED", "GREEN", "YELLOW")
        )).thenReturn(slots);
        var outcome = fruitMachineService.play();
        assertEquals(new BigDecimal("102.00"),outcome.currentFloat());
        assertEquals(PrizeType.NO_PRIZE, outcome.prizeType());
    }

    @Test
    void shouldDeductSmallPrizeFromMachineFloat(){
        List<String> slots =
                List.of("BLACK","RED","RED","YELLOW");
        when(randomSpinGenerator.generateRandomSpins(
                4,
            List.of("BLACK", "RED", "GREEN", "YELLOW")
        )).thenReturn(slots);
        PlayOutcome outcome = fruitMachineService.play();

        assertEquals(PrizeType.SMALL_PRIZE, outcome.prizeType());
        assertEquals(new BigDecimal("10.00"),outcome.payout());
        assertEquals(new BigDecimal("92.00"),outcome.currentFloat());
    }

    @Test
    void shouldPayHalfOfMachineFloatForFullHouse(){
        List<String> slots =
                List.of("BLACK","RED","GREEN","YELLOW");
        when(randomSpinGenerator.generateRandomSpins(
                4,
                List.of("BLACK", "RED", "GREEN", "YELLOW")
        )).thenReturn(slots);
        PlayOutcome outcome = fruitMachineService.play();

        assertEquals(PrizeType.FULL_HOUSE, outcome.prizeType());
        assertEquals(new BigDecimal("51.00"),outcome.payout());
        assertEquals(new BigDecimal("51.00"),outcome.currentFloat());
    }

    @Test
    void shouldPayCompleteFloatAmountForJackPot(){
        List<String> slots =
                List.of("BLACK","BLACK","BLACK","BLACK");
        when(randomSpinGenerator.generateRandomSpins(
                4,
                List.of("BLACK", "RED", "GREEN", "YELLOW")
        )).thenReturn(slots);
        PlayOutcome outcome = fruitMachineService.play();

        assertEquals(PrizeType.JACK_POT, outcome.prizeType());
        assertEquals(new BigDecimal("102.00"),outcome.payout());
        assertEquals(new BigDecimal("0.00"),outcome.currentFloat());
    }
}
