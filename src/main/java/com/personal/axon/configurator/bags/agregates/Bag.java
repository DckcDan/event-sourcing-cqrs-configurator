package com.personal.axon.configurator.bags.agregates;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Bag
{

    private final String bagRuleId;
    private final String bagId;
    private final Double price;
    private final LocalDateTime createdBy;
}

