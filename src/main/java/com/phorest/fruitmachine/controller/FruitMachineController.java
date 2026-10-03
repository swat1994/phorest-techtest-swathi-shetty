package com.phorest.fruitmachine.controller;


import com.phorest.fruitmachine.domain.FruitMachineConfiguration;
import com.phorest.fruitmachine.domain.PlayOutcome;
import com.phorest.fruitmachine.dto.ConfigureMachineStateRequest;
import com.phorest.fruitmachine.dto.MachineStateResponse;
import com.phorest.fruitmachine.service.FruitMachineService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/machine")
public class FruitMachineController {
    private final FruitMachineService fruitMachineService;
    public FruitMachineController(FruitMachineService fruitMachineService) {
        this.fruitMachineService = fruitMachineService;
    }

    @PostMapping("/play")
    public PlayOutcome play(){
        return fruitMachineService.play();
    }

    @GetMapping
    public MachineStateResponse getMachineState(){
        return  fruitMachineService.getToMachineStateResponse();
    }

    @PutMapping("/configuration")
    public void  configure( @Valid  @RequestBody ConfigureMachineStateRequest request){
        FruitMachineConfiguration configuration=
                new FruitMachineConfiguration(request.slots(),
                        request.colors(),
                        request.adjacentMatchCount(),
                        request.playCost(),
                        request.initialFloat());
        fruitMachineService.configure(configuration);
    }
}
