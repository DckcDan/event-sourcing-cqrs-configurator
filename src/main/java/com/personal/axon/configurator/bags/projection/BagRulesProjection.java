package com.personal.axon.configurator.bags.projection;

import com.personal.axon.configurator.bags.event.BagCreatedEvent;
import com.personal.axon.configurator.bags.event.BagDeletedEvent;
import com.personal.axon.configurator.bags.event.BagModifiedEvent;
import com.personal.axon.configurator.bags.event.BagRuleCreatedEvent;
import com.personal.axon.configurator.bags.event.BagRulesPublishedEvent;
import com.personal.axon.configurator.bags.model.BagModel;
import com.personal.axon.configurator.bags.model.BagCollectionModel;
import com.personal.axon.configurator.bags.model.ConfigEnvironment;
import lombok.extern.log4j.Log4j2;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class BagRulesProjection {

    public static final String DRAFT = "draft";
    private Map<String, BagModel> draftRules = new HashMap<>();
    private ConfigEnvironment bagRulesEnvironment = new ConfigEnvironment();
    private List<BagCollectionModel> snapShots = new ArrayList<>();

    private int lastestVersion;

    @EventHandler
    public void handleCreatedEvent(BagRuleCreatedEvent event) {
        lastestVersion = 0;
    }

    @EventHandler
    public void on(BagCreatedEvent event)
    {
        log.info("Bag added "+event.getBagRule().getBagId());
        BagModel build = new BagModel(event.getBagRule().getBagId(),
                event.getBagRule().getPrice(),
                event.getCreatedAt()
        );
        draftRules.put(event.getBagRule().getBagId(), build);
    }

    @EventHandler
    public void on(BagDeletedEvent event){
        log.info("Bag deleted "+event.getBagId());
        draftRules.remove(event.getBagId());
    }

    @EventHandler
    public void on(BagModifiedEvent event){
        log.info("Bag modified "+event.getBagRule().getBagId());
        BagModel build = new BagModel(event.getBagRule().getBagId(),
                event.getBagRule().getPrice(),
                event.getBagRule().getCreatedBy()
                );


        draftRules.put(event.getBagRule().getBagId(), build);
    }

    @EventHandler
    public void on(BagRulesPublishedEvent event){

        if (com.personal.axon.configurator.bags.event.Environment.DEV.equals(event.getEnvironment())){
            BagCollectionModel bagRules = new BagCollectionModel();
            bagRules.setVersion(String.valueOf(lastestVersion));
            bagRules.setEnvironment(com.personal.axon.configurator.bags.event.Environment.DEV);
            bagRules.setCreateAt(LocalDateTime.now());
            bagRules.setBagRulesList(new ArrayList<>(draftRules.values()));
            bagRulesEnvironment.getMap().put(com.personal.axon.configurator.bags.event.Environment.DEV, bagRules);
        }
        if (com.personal.axon.configurator.bags.event.Environment.LIVE.equals(event.getEnvironment())){
            lastestVersion++;
            BagCollectionModel bagRules = new BagCollectionModel();
            bagRules.setVersion(String.valueOf(lastestVersion));
            bagRules.setEnvironment(com.personal.axon.configurator.bags.event.Environment.LIVE);
            bagRules.setCreateAt(LocalDateTime.now());
            bagRules.setBagRulesList(new ArrayList<>(bagRulesEnvironment.getMap().get(com.personal.axon.configurator.bags.event.Environment.DEV).getBagRulesList()));
            bagRulesEnvironment.getMap().put(com.personal.axon.configurator.bags.event.Environment.LIVE, bagRules);
            bagRulesEnvironment.getMap().get(com.personal.axon.configurator.bags.event.Environment.DEV).setVersion(String.valueOf(lastestVersion));
            snapShots.add(bagRules);
        }


    }


    @QueryHandler(queryName = "draft")
    public Collection<BagModel> draft(){
        return draftRules.values();
    }
    @QueryHandler(queryName = "findAll")
    public ConfigEnvironment findAll(){
        return bagRulesEnvironment;
    }

    @QueryHandler(queryName = "snapShots")
    public List<BagCollectionModel> snapShots(){
        return snapShots;
    }
}
