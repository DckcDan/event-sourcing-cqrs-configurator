package com.personal.axon.configurator.bags.agregate;

import com.personal.axon.configurator.bags.command.CreateBagCommand;
import com.personal.axon.configurator.bags.command.CreateBagCollectionCommand;
import com.personal.axon.configurator.bags.command.DeleteBagCommand;
import com.personal.axon.configurator.bags.command.ModifyBagCommand;
import com.personal.axon.configurator.bags.command.PublishBagRulesCommand;
import com.personal.axon.configurator.bags.event.BagCreatedEvent;
import com.personal.axon.configurator.bags.event.BagDeletedEvent;
import com.personal.axon.configurator.bags.event.BagModifiedEvent;
import com.personal.axon.configurator.bags.event.BagRuleCreatedEvent;
import com.personal.axon.configurator.bags.event.BagRulesPublishedEvent;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * An Aggregate is a regular object, which contains state and methods to alter that state.
 * It ensures the consistency of the data. Validate before creating events.
 */
@Aggregate
@Data
public class BagAggregate {

    @AggregateIdentifier
    private String bagRuleId;


    protected BagAggregate(){
    }

    /**
     * Constructor that initialise the aggregate.
     * @param cmd
     */
    @CommandHandler
    public BagAggregate(CreateBagCollectionCommand cmd) {
        //validation
        Assert.notNull(cmd.getBagRuleId(), "ID should not be null");

        apply(new BagRuleCreatedEvent(cmd.getBagRuleId()));
    }


    @EventSourcingHandler
    protected void handleCreatedEvent(BagRuleCreatedEvent event) {
        bagRuleId = event.getBagRuleId();
    }

    @CommandHandler
    protected void on(CreateBagCommand cmd) {
        Assert.notNull(cmd.getBagId(), "ID should not be null");
        Assert.notNull(cmd.getBagRuleId(), "BagRuleId should not be null");
        Bag bag = new Bag(cmd.getBagRuleId(), cmd.getBagId(), cmd.getPrice(), cmd.getCreatedAt());
        apply(new BagCreatedEvent(cmd.getBagRuleId(),bag,LocalDateTime.now()));
    }
    @CommandHandler
    protected void on(ModifyBagCommand cmd) {
        Assert.notNull(cmd.getBagId(), "ID should not be null");
        Assert.notNull(cmd.getBagRuleId(), "BagRuleId should not be null");
        Bag bag = new Bag(cmd.getBagRuleId(), cmd.getBagId(), cmd.getPrice(), cmd.getCreatedAt());
        apply(new BagModifiedEvent(cmd.getBagRuleId(),bag,LocalDateTime.now()));
    }


    @CommandHandler
    protected void on(DeleteBagCommand cmd) {
        Assert.notNull(cmd.getBagId(), "ID should not be null");
        Assert.notNull(cmd.getBagRuleId(), "BagRuleId should not be null");

        apply(new BagDeletedEvent(cmd.getBagRuleId(),cmd.getBagId(),LocalDateTime.now()));
    }



 /*  @EventSourcingHandler
    protected void addBagRule(BagCreatedEvent event) {

    }
*/
    @CommandHandler
    public void publishRules(PublishBagRulesCommand cmd){
        apply(new BagRulesPublishedEvent(cmd.getBagRuleId(),cmd.getEnvironment(),LocalDateTime.now()));
    }




}
