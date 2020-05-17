package com.personal.axon.configurator.bags.commands;

import com.personal.axon.configurator.bags.events.Environment;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class PublishBagRulesCommand
{

    @TargetAggregateIdentifier
    private final String bagRuleId;
    private final Environment environment;



}
