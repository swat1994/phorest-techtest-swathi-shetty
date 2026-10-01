package com.phorest.fruitmachine.domain;

import java.math.BigDecimal;

public class FruiteMachineState {
    private BigDecimal currentFloat;
    private int freePlays;

    public FruiteMachineState(BigDecimal initialFloat) {
        currentFloat = initialFloat;
        freePlays = 0;
    }

    public void addPlayCost(BigDecimal playCost){
        currentFloat=currentFloat.add(playCost);
    }
    public void payOut(BigDecimal amount){
        currentFloat=currentFloat.subtract(amount);
    }

    public void addFreePlays(int freePlaysCredited){
        freePlays+=freePlaysCredited;
    }

    public  void useFreePlays(){
        if(freePlays<=0){
            throw new IllegalStateException("No free plays available");
        }
        freePlays--;
    }

    public BigDecimal getCurrentFloat() {
        return currentFloat;
    }

    public int getFreePlays() {
        return freePlays;
    }
}
