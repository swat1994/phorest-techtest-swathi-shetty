package com.phorest.fruitmachine.controller;

import com.phorest.fruitmachine.domain.FruitMachineConfiguration;
import com.phorest.fruitmachine.domain.PlayOutcome;
import com.phorest.fruitmachine.domain.PrizeType;
import com.phorest.fruitmachine.dto.MachineStateResponse;
import com.phorest.fruitmachine.service.FruitMachineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FruitMachineController.class)
public class FruitMachineControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FruitMachineService fruitMachineService;

    @Test
    void shouldReturnPLayOutcome() throws Exception {
        PlayOutcome outcome = new PlayOutcome(
                List.of("BLACK", "RED", "BLACK", "GREEN"),
                PrizeType.NO_PRIZE,
                BigDecimal.ZERO,
                0,
                new BigDecimal("102.00")
        );

        when(fruitMachineService.play())
                .thenReturn(outcome);
        mockMvc.perform(post("/api/machine/play"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prizeType").value("NO_PRIZE"))
                .andExpect(jsonPath("$.currentFloat").value(102.00))
                .andExpect(jsonPath("$.freePlaysCredited").value(0));

    }

    @Test
    void shouldReturnMachineState() throws Exception {

        FruitMachineConfiguration configuration =
                new FruitMachineConfiguration(
                        4,
                        List.of("BLACK", "WHITE", "GREEN", "YELLOW"),
                        2,
                        new BigDecimal("2.00"),
                        new BigDecimal("100.00")
                );

        MachineStateResponse response =
                new MachineStateResponse(
                        configuration,
                        new BigDecimal("100.00"),
                        0
                );

        when(fruitMachineService.getToMachineStateResponse())
                .thenReturn(response);

        mockMvc.perform(get("/api/machine"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentFloat").value(100.00))
                .andExpect(jsonPath("$.freePlays").value(0))
                .andExpect(jsonPath(
                        "$.fruitMachineConfiguration.slots"
                ).value(4));
    }

    @Test
    void shouldConfigureMachine() throws Exception {

        String requestBody = """
            {
              "slots": 6,
              "colors": ["RED", "GREEN", "BLUE"],
              "adjacentMatchCount": 3,
              "playCost": 3.00,
              "initialFloat": 200.00
            }
            """;

        mockMvc.perform(put("/api/machine/configuration")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());

        verify(fruitMachineService)
                .configure(any(FruitMachineConfiguration.class));
    }

    @Test
    void shouldReturnBadRequestForInvalidConfiguration() throws Exception {

        String requestBody = """
            {
              "slots": 0,
              "colors": [],
              "adjacentMatchCount": 1,
              "playCost": 0,
              "initialFloat": -10
            }
            """;

        mockMvc.perform(put("/api/machine/configuration")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.slots").exists())
                .andExpect(jsonPath("$.errors.colors").exists())
                .andExpect(jsonPath("$.errors.adjacentMatchCount").exists())
                .andExpect(jsonPath("$.errors.playCost").exists())
                .andExpect(jsonPath("$.errors.initialFloat").exists());
    }

    @Test
    void shouldReturnBadRequestWhenAdjacentMatchCountExceedsSlots() throws Exception {

        String requestBody = """
            {
              "slots": 4,
              "colors": ["RED", "GREEN", "BLUE"],
              "adjacentMatchCount": 5,
              "playCost": 2.00,
              "initialFloat": 100.00
            }
            """;

        mockMvc.perform(put("/api/machine/configuration")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error")
                        .value("Adajacent match count must be between 2 and slot counts"));
    }
}
