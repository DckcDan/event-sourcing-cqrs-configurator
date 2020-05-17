package com.personal.axon.configurator.bags.controller;

import com.personal.axon.configurator.bags.model.BagModel;
import com.personal.axon.configurator.bags.model.BagCollectionModel;
import com.personal.axon.configurator.bags.model.ConfigEnvironment;
import com.personal.axon.configurator.bags.command.CreateBagCommand;
import com.personal.axon.configurator.bags.command.CreateBagCollectionCommand;
import com.personal.axon.configurator.bags.command.DeleteBagCommand;
import com.personal.axon.configurator.bags.command.ModifyBagCommand;
import com.personal.axon.configurator.bags.command.PublishBagRulesCommand;
import com.personal.axon.configurator.bags.projection.history.BagRulesHistory;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
@RestController
@RequestMapping("/configuration/bags")
public class BagRestController {

    @Autowired
    private  CommandGateway commandGateway;
    @Autowired
    private  QueryGateway queryGateway;


    @PostMapping
    public ResponseEntity<BagCollectionModel> addBagRules(@RequestBody BagCollectionModel rule) throws URISyntaxException {

        commandGateway.send(new CreateBagCollectionCommand(rule.getBagRuleId()));
        return ResponseEntity.created((new URI("/configuration/bags" +rule.getBagRuleId())))
                .body(rule);
    }

    @PostMapping("/{bagRulesId}")
    public ResponseEntity<BagModel> addBag(@PathVariable String bagRulesId, @RequestBody BagModel bag) throws URISyntaxException {
        commandGateway.send(new CreateBagCommand(bagRulesId, bag.getBagId(),bag.getPrice(), LocalDateTime.now()));
        return ResponseEntity.created((new URI("/spaces/" + bag.getBagId())))
                .body(bag);
    }

    @PutMapping("/{bagRulesId}/{bagRuleId}")
    public ResponseEntity<BagModel> addBag(@PathVariable String bagRulesId, @PathVariable String bagRuleId, @RequestBody BagModel bag) throws URISyntaxException {
        commandGateway.send(new ModifyBagCommand(bagRulesId, bag.getBagId(),bag.getPrice(), LocalDateTime.now()));
        return ResponseEntity.created((new URI("/spaces/" + bag.getBagId())))
                .body(bag);
    }

    @DeleteMapping("/{bagRulesId}/{bagRuleId}")
    public ResponseEntity<BagModel> deleteBag(@PathVariable String bagRulesId, @PathVariable String bagRuleId) throws URISyntaxException {
        commandGateway.send(new DeleteBagCommand(bagRulesId,bagRuleId));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{bagRulesId}/publish/{env}")
    public ResponseEntity<BagModel> publish(@PathVariable String bagRulesId, @PathVariable com.personal.axon.configurator.bags.event.Environment env) throws URISyntaxException {
        commandGateway.send(new PublishBagRulesCommand(bagRulesId,env));
        return ResponseEntity.ok().build();
    }



    @GetMapping
    public ResponseEntity<ConfigEnvironment> findAll() throws ExecutionException, InterruptedException
    {
        return ResponseEntity.ok(queryGateway.query("findAll", null, ResponseTypes.instanceOf(ConfigEnvironment.class)).get());
    }

    @GetMapping("/history")
    public ResponseEntity<List<BagRulesHistory>> history() throws ExecutionException, InterruptedException
    {
        return ResponseEntity.ok(queryGateway.query("bagHistory", null, ResponseTypes.multipleInstancesOf(BagRulesHistory.class)).get());
    }

    @GetMapping("/draft")
    public ResponseEntity<List<BagModel>> draft() throws ExecutionException, InterruptedException
    {
        return ResponseEntity.ok(queryGateway.query("draft", null, ResponseTypes.multipleInstancesOf(BagModel.class)).get());
    }

    @GetMapping("/snapshots")
    public ResponseEntity<List<BagCollectionModel>> snapshots() throws ExecutionException, InterruptedException
    {
        return ResponseEntity.ok(queryGateway.query("snapShots", null, ResponseTypes.multipleInstancesOf(BagCollectionModel.class)).get());
    }
}
