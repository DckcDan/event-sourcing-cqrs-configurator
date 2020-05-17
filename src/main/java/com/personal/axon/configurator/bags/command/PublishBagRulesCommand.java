package com.personal.axon.configurator.bags.command;

import com.personal.axon.configurator.bags.event.Environment;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class PublishBagRulesCommand
{

    @TargetAggregateIdentifier
    private final String bagRuleId;
    private final Environment environment;



}
