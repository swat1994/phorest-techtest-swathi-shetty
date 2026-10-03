package com.phorest.fruitmachine.domain;

import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;



public record FruitMachineConfiguration(int slots,
                                        List<String> colors,
                                        int adjacentMatchCount,
                                        BigDecimal playCost,
                                        BigDecimal initialFloat) {


    public FruitMachineConfiguration{

        if(slots<=0){
            throw new IllegalArgumentException("Slots counts must be greate than zero");
        }
        if(colors==null || colors.isEmpty()){
            throw  new IllegalArgumentException("Colurs Must not be empty");
        }
        if(adjacentMatchCount<2 || adjacentMatchCount>slots){
            throw  new IllegalArgumentException("Adajacent match count must be between 2 and slot counts");
        }
        if(playCost==null || playCost.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException("Play cost must be greate than zero");
        }
        if(initialFloat == null || initialFloat.compareTo(BigDecimal.ZERO)<0){
            throw  new IllegalArgumentException(" Initial float amount must not be negative");
        }
        colors = List.copyOf(colors);
    }


}
