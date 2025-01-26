package com.rk.statemachine.config;

import com.rk.statemachine.enums.OrderEvent;
import com.rk.statemachine.enums.OrderState;
import com.rk.statemachine.listener.OrderStateListenerAdapter;
import com.rk.statemachine.stateactions.entry.OrderSubmittedEntryStateAction;
import com.rk.statemachine.stateactions.exit.OrderSubmittedExitStateAction;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachineFactory
@AllArgsConstructor
public class OrderStateMachineConfiguration extends StateMachineConfigurerAdapter<OrderState, OrderEvent> {

    private final OrderStateListenerAdapter stateListenerAdapter;

    private final OrderSubmittedEntryStateAction submittedEntryStateAction;

    private final OrderSubmittedExitStateAction submittedExitStateAction;

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderState, OrderEvent> config) throws Exception {
        config.withConfiguration()
                .autoStartup(false)
                .listener(stateListenerAdapter);
    }

    @Override
    public void configure(StateMachineStateConfigurer<OrderState, OrderEvent> states) throws Exception {
        states.withStates()
                .initial(OrderState.SUBMITTED)
                //.stateEntry(OrderState.SUBMITTED,submittedEntryStateAction)
                //.stateExit(OrderState.SUBMITTED,submittedExitStateAction)
                .state(OrderState.PAID)
                .end(OrderState.FULFILLED)
                .end(OrderState.CANCELLED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderState, OrderEvent> transitions) throws Exception {
        transitions.withExternal().source(OrderState.SUBMITTED).target(OrderState.PAID).event(OrderEvent.PAY)
                .and()
                .withExternal().source(OrderState.PAID).target(OrderState.FULFILLED).event(OrderEvent.FULFILL)
                .and()
                .withExternal().source(OrderState.SUBMITTED).target(OrderState.CANCELLED).event(OrderEvent.CANCEL)
                .and()
                .withExternal().source(OrderState.PAID).target(OrderState.CANCELLED).event(OrderEvent.CANCEL)
                .and()
                .withExternal().source(OrderState.FULFILLED).target(OrderState.CANCELLED).event(OrderEvent.CANCEL);
    }
}
