package com.phorest.fruitmachine.service;

import com.phorest.fruitmachine.domain.FruitMachineConfiguration;
import com.phorest.fruitmachine.domain.FruitMachineState;
import com.phorest.fruitmachine.domain.PlayOutcome;
import com.phorest.fruitmachine.domain.PrizeType;
import com.phorest.fruitmachine.dto.MachineStateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class FruitMachineServiceTest {



    private RandomSpinGenerator randomSpinGenerator;
    private  FruitMachineService fruitMachineService;
    private FruitMachineState fruitMachineState;
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
        fruitMachineState =
                new FruitMachineState(configuration.initialFloat());
        fruitMachineService = new FruitMachineService(
                new PrizeOutcomeEvaluator(),
                new PrizePayoutCalculator(),
                randomSpinGenerator,
                configuration,
                fruitMachineState
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

    @Test
    void shouldCreaditFreePlaysWhenFloatCannotCoverSmallPrize(){
        FruitMachineConfiguration fruitMachineConfiguration = new FruitMachineConfiguration(
                4,
                List.of("BLACK", "RED", "GREEN", "YELLOW"),
                2,
                new BigDecimal("2.00"),
                new BigDecimal("2.00")
        );
        fruitMachineState = new FruitMachineState(fruitMachineConfiguration.initialFloat());
        FruitMachineService service = new FruitMachineService(
                new PrizeOutcomeEvaluator(),
                new PrizePayoutCalculator(),
                randomSpinGenerator,
                fruitMachineConfiguration,
                fruitMachineState
        );

        List<String> slots =
                List.of("BLACK", "RED", "RED", "GREEN");
        when(randomSpinGenerator.generateRandomSpins(
                4,
                fruitMachineConfiguration.colors()
        )).thenReturn(slots);

        PlayOutcome outcome = service.play();
        assertEquals(PrizeType.SMALL_PRIZE, outcome.prizeType());
        assertEquals(new BigDecimal("4.00"),outcome.payout());
        assertEquals(6, outcome.freePlaysCredited());
        assertEquals(new BigDecimal("0.00"),outcome.currentFloat());
        assertEquals(6,fruitMachineState.getFreePlays());
    };

    @Test
    void shouldUseFreePlayWithoutAddingPlayCost(){
        fruitMachineState.addFreePlays(1);

        List<String> slots =
                List.of("BLACK", "RED", "BLACK", "GREEN");

        when(randomSpinGenerator.generateRandomSpins(
                4,
                List.of("BLACK", "RED", "GREEN", "YELLOW")
        )).thenReturn(slots);

        PlayOutcome outcome = fruitMachineService.play();

        assertEquals(PrizeType.NO_PRIZE, outcome.prizeType());
        assertEquals(new BigDecimal("100.00"), outcome.currentFloat());
        assertEquals(0, fruitMachineState.getFreePlays());
    }

    @Test
    void shouldResetMachineStateWhenReconfigured() {


        fruitMachineState.addPlayCost(new BigDecimal("20.00"));
        fruitMachineState.addFreePlays(3);

        FruitMachineConfiguration newConfiguration =
                new FruitMachineConfiguration(
                        6,
                        List.of("RED", "GREEN", "BLUE"),
                        3,
                        new BigDecimal("3.00"),
                        new BigDecimal("200.00")
                );

        fruitMachineService.configure(newConfiguration);

        MachineStateResponse state =
                fruitMachineService.getToMachineStateResponse();

        assertEquals(newConfiguration, state.fruitMachineConfiguration());
        assertEquals(new BigDecimal("200.00"), state.currentFloat());
        assertEquals(0, state.freePlays());
    }
}
