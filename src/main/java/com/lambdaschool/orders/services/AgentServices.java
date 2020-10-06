package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;

public interface AgentServices {

    Agent findAgentByID(long agentID);

    Agent save(Agent agent);
}