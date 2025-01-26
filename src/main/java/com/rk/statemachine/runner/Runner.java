package com.rk.statemachine.runner;

import com.rk.statemachine.enums.OrderEvent;
import com.rk.statemachine.enums.OrderState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class Runner implements ApplicationRunner {

    private final StateMachineFactory<OrderState, OrderEvent> orderStateMachineFactory;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Long orderId = 1234L;
        StateMachine<OrderState,OrderEvent> orderStateMachine = orderStateMachineFactory.getStateMachine(String.valueOf(orderId));
        orderStateMachine.getExtendedState().getVariables().put("orderId",orderId);
        orderStateMachine.start();
        log.info("Current state {}",orderStateMachine.getState().getId().name());
        orderStateMachine.sendEvent(OrderEvent.PAY);
        log.info("Current state {}",orderStateMachine.getState().getId().name());
        orderStateMachine.sendEvent(MessageBuilder.withPayload(OrderEvent.FULFILL).setHeader("a","b").build());
        log.info("Current state {}",orderStateMachine.getState().getId().name());
    }
}
