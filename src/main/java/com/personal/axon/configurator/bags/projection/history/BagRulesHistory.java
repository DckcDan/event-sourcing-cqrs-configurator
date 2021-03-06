package com.personal.axon.configurator.bags.projection.history;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.personal.axon.configurator.bags.model.BagModel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BagRulesHistory {

    private BagModel bagRule;
    private String description;
    private LocalDateTime timestamp;
}
