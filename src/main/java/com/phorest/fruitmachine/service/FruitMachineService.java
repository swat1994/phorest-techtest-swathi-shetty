package com.phorest.fruitmachine.service;

import com.phorest.fruitmachine.domain.FruitMachineConfiguration;
import com.phorest.fruitmachine.domain.FruiteMachineState;
import com.phorest.fruitmachine.domain.PlayOutcome;
import com.phorest.fruitmachine.domain.PrizeType;

import java.math.BigDecimal;
import java.util.List;


public class FruitMachineService {
    private final PrizeOutcomeEvaluator prizeOutcomeEvaluator;
    private final PrizePayoutCalculator prizePayoutCalculator;
    private final RandomSpinGenerator randomSpinGenerator;
    private final FruitMachineConfiguration fruitMachineConfiguration;
    private final FruiteMachineState fruiteMachineState;

    public FruitMachineService(PrizeOutcomeEvaluator prizeOutcomeEvaluator,
                               PrizePayoutCalculator  prizePayoutCalculator,
                               RandomSpinGenerator randomSpinGenerator,
                               FruitMachineConfiguration fruitMachineConfiguration,
                               FruiteMachineState fruiteMachineState) {
        this.prizeOutcomeEvaluator = prizeOutcomeEvaluator;
        this.prizePayoutCalculator = prizePayoutCalculator;
        this.randomSpinGenerator = randomSpinGenerator;
        this.fruitMachineConfiguration = fruitMachineConfiguration;
        this.fruiteMachineState = fruiteMachineState;
    }

    public PlayOutcome play(){

        if(fruiteMachineState.getFreePlays()>0){
            fruiteMachineState.useFreePlays();
        }else{
            fruiteMachineState.addPlayCost(
                    fruitMachineConfiguration.playCost()
            );
        }
        List<String> slots = randomSpinGenerator.generateRandomSpins(
                fruitMachineConfiguration.slots(),
                fruitMachineConfiguration.colors()
        );

        PrizeType prizeType = prizeOutcomeEvaluator.evaluatePrize(slots,
                fruitMachineConfiguration.adjacentMatchCount());

        BigDecimal prizeAmount = prizePayoutCalculator.calculatePrize(
                prizeType,
                fruitMachineConfiguration.playCost(),
                fruiteMachineState.getCurrentFloat()
        );

        if(prizeAmount.compareTo(BigDecimal.ZERO)>0 &&
        fruiteMachineState.getCurrentFloat().compareTo(prizeAmount)>=0){
            fruiteMachineState.payOut(prizeAmount);
        }

        int freePlaysCreadited =0;


       return  new PlayOutcome(
            slots,
            prizeType,
            prizeAmount,
            freePlaysCreadited,
            fruiteMachineState.getCurrentFloat()
       );
    }
}