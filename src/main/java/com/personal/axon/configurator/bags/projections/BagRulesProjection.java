package com.personal.axon.configurator.bags.projections;

import com.personal.axon.configurator.bags.events.BagCreatedEvent;
import com.personal.axon.configurator.bags.events.BagDeletedEvent;
import com.personal.axon.configurator.bags.events.BagModifiedEvent;
import com.personal.axon.configurator.bags.events.BagRuleCreatedEvent;
import com.personal.axon.configurator.bags.events.BagRulesPublishedEvent;
import com.personal.axon.configurator.bags.models.BagModel;
import com.personal.axon.configurator.bags.models.BagCollectionModel;
import com.personal.axon.configurator.bags.models.Environment;
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
    private Environment bagRulesEnvironment = new Environment();
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

        if (com.personal.axon.configurator.bags.events.Environment.DEV.equals(event.getEnvironment())){
            BagCollectionModel bagRules = new BagCollectionModel();
            bagRules.setVersion(String.valueOf(lastestVersion));
            bagRules.setEnvironment(com.personal.axon.configurator.bags.events.Environment.DEV);
            bagRules.setCreateAt(LocalDateTime.now());
            bagRules.setBagRulesList(new ArrayList<>(draftRules.values()));
            bagRulesEnvironment.getMap().put(com.personal.axon.configurator.bags.events.Environment.DEV, bagRules);
        }
        if (com.personal.axon.configurator.bags.events.Environment.LIVE.equals(event.getEnvironment())){
            lastestVersion++;
            BagCollectionModel bagRules = new BagCollectionModel();
            bagRules.setVersion(String.valueOf(lastestVersion));
            bagRules.setEnvironment(com.personal.axon.configurator.bags.events.Environment.LIVE);
            bagRules.setCreateAt(LocalDateTime.now());
            bagRules.setBagRulesList(new ArrayList<>(bagRulesEnvironment.getMap().get(com.personal.axon.configurator.bags.events.Environment.DEV).getBagRulesList()));
            bagRulesEnvironment.getMap().put(com.personal.axon.configurator.bags.events.Environment.LIVE, bagRules);
            bagRulesEnvironment.getMap().get(com.personal.axon.configurator.bags.events.Environment.DEV).setVersion(String.valueOf(lastestVersion));
            snapShots.add(bagRules);
        }


    }


    @QueryHandler(queryName = "draft")
    public Collection<BagModel> draft(){
        return draftRules.values();
    }
    @QueryHandler(queryName = "findAll")
    public Environment findAll(){
        return bagRulesEnvironment;
    }

    @QueryHandler(queryName = "snapShots")
    public List<BagCollectionModel> snapShots(){
        return snapShots;
    }
}
