package com.personal.axon.configurator.bags.event;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class BagRuleCreatedEvent
{
    private final String bagRuleId;
}
