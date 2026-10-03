package com.phorest.fruitmachine.service;

import com.phorest.fruitmachine.domain.PrizeType;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PrizeOutcomeEvaluator {
    public PrizeType evaluatePrize(List<String> slots,int adjacentMatchCount){
        String firstColor = slots.get(0);
        boolean isJackPot = slots.stream()
                .allMatch(color -> color.equals(firstColor));
        if(isJackPot){
            return PrizeType.JACK_POT;
        }

        Set<String> distinctColors = new HashSet<>(slots);
        if(distinctColors.size()==slots.size()){
            return PrizeType.FULL_HOUSE;
        }

        int adjcentCount =1;
        for(int i=1;i<slots.size();i++){
            if(slots.get(i).equals(slots.get(i-1))){
                adjcentCount++;
            }else {
                adjcentCount=1;
            }
            if(adjcentCount>=adjacentMatchCount){
                return PrizeType.SMALL_PRIZE;
            }
        }

        return PrizeType.NO_PRIZE;
    }
}
