package com.phorest.fruitmachine.domain;

import java.math.BigDecimal;
import java.util.List;

public record PlayOutcome(List<String> slots,
                          PrizeType prizeType,
                          BigDecimal payout,
                          int freePlaysCredited,
                          BigDecimal currentFloat) {


}
