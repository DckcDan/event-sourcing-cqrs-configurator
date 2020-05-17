package com.personal.axon.configurator.bags.commands;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class CreateBagCollectionCommand
{

    //it should match the Aggregate id name.
    @TargetAggregateIdentifier
    private final String bagRuleId;



}
