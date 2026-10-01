package com.phorest.fruitmachine.service;

import com.phorest.fruitmachine.domain.PrizeType;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PrizeOutcomeEvaluatorTest {
    private final PrizeOutcomeEvaluator prizeOutcomeEvaluator = new PrizeOutcomeEvaluator();
    @Test
    void shouldReturnJackPotWhenAllColrsAreSame(){
      List<String> slots =  List.of("BLACK", "BLACK", "BLACK", "BLACK");
        PrizeType result =prizeOutcomeEvaluator.evaluatePrize(slots,2);
        assertEquals(PrizeType.JACK_POT,result);

    }

    @Test
    void shouldNotReturnJackPotWhenAtleastOneColorISdifferent(){
        List<String> slots = List.of("BLACK", "RED", "BLUE", "BLACK");
        PrizeType result = prizeOutcomeEvaluator.evaluatePrize(slots, 2);
        assertEquals(PrizeType.NO_PRIZE, result);
    }
    @Test
    void shouldReturnFullHouseAllColorsAreUnique(){
        List<String> slots =  List.of("BLACK", "RED", "BLUE", "GREEN");
        PrizeType result =prizeOutcomeEvaluator.evaluatePrize(slots,2);
        assertEquals(PrizeType.FULL_HOUSE,result);
    }

    @Test
    void shouldNotReturnFullHouseAllColorsAreNotUnique(){
        List<String> slots =  List.of("BLACK", "RED", "BLACK", "BLUE");
        PrizeType result =prizeOutcomeEvaluator.evaluatePrize(slots,2);
        assertEquals(PrizeType.NO_PRIZE,result);

    }

    @Test
    void shouldReturnSmallPrizeWhenAdjacentColrsAreSame2Times(){
        List<String> slots =  List.of("BLACK", "RED", "BLACK", "BLACK");
        PrizeType result =prizeOutcomeEvaluator.evaluatePrize(slots,2);
        assertEquals(PrizeType.SMALL_PRIZE,result);
    }

    @Test
    void shouldNotReturnSmallPrizeWhenAdjacentColrsMatchThreeTimes(){
        List<String> slots =  List.of("BLACK", "RED", "BLACK", "BLACK");
        PrizeType result =prizeOutcomeEvaluator.evaluatePrize(slots,3);
        assertEquals(PrizeType.NO_PRIZE,result);
    }

    @Test
    void shouldPrioritizeJackPotOverSmallPrize(){
        List<String> slots =  List.of("BLACK", "BLACK", "BLACK", "BLACK");
        PrizeType result =prizeOutcomeEvaluator.evaluatePrize(slots,3);
        assertEquals(PrizeType.JACK_POT,result);
    }





















}
