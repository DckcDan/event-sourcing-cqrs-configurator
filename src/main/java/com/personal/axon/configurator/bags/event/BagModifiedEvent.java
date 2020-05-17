package com.personal.axon.configurator.bags.event;

import com.personal.axon.configurator.bags.agregate.Bag;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

@Data
public class BagModifiedEvent
{

    @TargetAggregateIdentifier
    private final String bagRuleId;
    private final Bag bagRule;
    private final LocalDateTime createdAt;

}
