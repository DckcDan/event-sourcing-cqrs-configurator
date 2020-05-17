package com.personal.axon.configurator.bags.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.personal.axon.configurator.bags.events.Environment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BagCollectionModel
{

    private String bagRuleId;
    private LocalDateTime createAt;
    private List<BagModel> bagRulesList;
    private String version;
    private Environment environment;
}
