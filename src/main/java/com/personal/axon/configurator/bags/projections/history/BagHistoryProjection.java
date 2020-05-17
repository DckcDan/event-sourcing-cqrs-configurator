package com.personal.axon.configurator.bags.projections.history;

import com.personal.axon.configurator.bags.events.BagCreatedEvent;
import com.personal.axon.configurator.bags.events.BagDeletedEvent;
import com.personal.axon.configurator.bags.events.BagModifiedEvent;
import com.personal.axon.configurator.bags.events.BagRulesPublishedEvent;
import com.personal.axon.configurator.bags.models.BagModel;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BagHistoryProjection {

    private List<BagRulesHistory> list = new ArrayList<>();


    @EventHandler
    public void on(BagCreatedEvent event)
    {
        BagModel bagRule = new BagModel();
        bagRule.setBagId(event.getBagRule().getBagId());
        bagRule.setPrice(event.getBagRule().getPrice());
        bagRule.setCreatedAt(event.getBagRule().getCreatedBy());
        list.add(BagRulesHistory.builder()
                .bagRule(bagRule)
                .timestamp(event.getBagRule().getCreatedBy())
                .description("A new bag has been added. ")
                .build());
    }

    @EventHandler
    public void on(BagDeletedEvent event){
        BagModel bagRule = new BagModel();
        bagRule.setBagId(event.getBagId());
        list.add(BagRulesHistory.builder()
                .description(event.getBagId() + " has been deleted. ")
                .build());
    }

    @EventHandler
    public void on(BagModifiedEvent event){
        BagModel bagRule = new BagModel();
        bagRule.setBagId(event.getBagRule().getBagId());
        bagRule.setPrice(event.getBagRule().getPrice());
        bagRule.setCreatedAt(event.getBagRule().getCreatedBy());
        list.add(BagRulesHistory.builder()
                .bagRule(bagRule)
                .timestamp(event.getBagRule().getCreatedBy())
                .description("A new bag has been modified. ")
                .build());
    }


    @EventHandler
    public void on(BagRulesPublishedEvent event) {
       list = new ArrayList<>();
    }

    @QueryHandler(queryName = "bagHistory")
    public List<BagRulesHistory> find(){
        return this.list;
    }

}
