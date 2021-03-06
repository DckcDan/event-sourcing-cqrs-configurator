package com.personal.axon.configurator.bags.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class DeleteBagCommand
{
    @TargetAggregateIdentifier
    private final String bagRuleId;
    private final String bagId;
}
