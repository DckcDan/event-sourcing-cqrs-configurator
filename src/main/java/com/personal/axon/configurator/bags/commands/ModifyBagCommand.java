package com.personal.axon.configurator.bags.commands;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

@Data
public class ModifyBagCommand
{
    @TargetAggregateIdentifier
    private final String bagRuleId;
    private final String bagId;
    private final Double price;
    private final LocalDateTime createdAt;

}
