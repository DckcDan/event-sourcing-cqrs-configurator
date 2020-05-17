package com.personal.axon.configurator.bags.events;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

@Data
public class BagRuleCreatedEvent
{
    @TargetAggregateIdentifier
    private final String bagRuleId;
}