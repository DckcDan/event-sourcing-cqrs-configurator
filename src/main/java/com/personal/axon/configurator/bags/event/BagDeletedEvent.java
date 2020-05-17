package com.personal.axon.configurator.bags.event;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

@Data
public class BagDeletedEvent
{

    @TargetAggregateIdentifier
    private final String bagRuleId;
    private final String bagId;
    private final LocalDateTime createAt;

}
