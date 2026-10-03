package com.phorest.fruitmachine.service;

import com.phorest.fruitmachine.domain.PrizeType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PrizePayoutCalculator {

    public BigDecimal calculatePrize(PrizeType prizeType,
                                      BigDecimal playCost,
                                      BigDecimal currentFloat){
        if(PrizeType.SMALL_PRIZE.equals(prizeType)){
           return playCost.multiply(BigDecimal.valueOf(5));
        }else if(PrizeType.FULL_HOUSE.equals(prizeType)){
            return currentFloat.divide(BigDecimal.valueOf(2));
        }else if(PrizeType.JACK_POT.equals(prizeType)){
            return currentFloat;
        }


        return BigDecimal.ZERO;

    }
}
