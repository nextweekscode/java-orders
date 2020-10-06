package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.services.AgentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agents")
public class AgentController {

    @Autowired
    AgentServices agentServices;

    // GET /agents/agent/{id}
    // Returns the agent and their customers with the given agent id
    @GetMapping(value = "/agent/{agentID}", produces = {"application/json"})
    public ResponseEntity<?> findAgentByID(@PathVariable long agentID) {
        Agent a = agentServices.findAgentByID(agentID);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }
}