package com.personal.axon.configurator.bags.event;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

@Data
public class BagRulesPublishedEvent
{

    @TargetAggregateIdentifier
    private final String bagRuleId;
    private final Environment environment;
    private final LocalDateTime createAt;

}
