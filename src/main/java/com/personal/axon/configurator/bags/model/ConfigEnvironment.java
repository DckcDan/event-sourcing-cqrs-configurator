package com.personal.axon.configurator.bags.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ConfigEnvironment
{

    private Map<com.personal.axon.configurator.bags.event.Environment, BagCollectionModel> map = new HashMap<>();
}
