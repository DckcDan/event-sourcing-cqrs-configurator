package com.personal.axon.configurator.bags.models;

import com.personal.axon.configurator.bags.events.Environment;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Environment
{

    private Map<com.personal.axon.configurator.bags.events.Environment, BagCollectionModel> map = new HashMap<>();
}
