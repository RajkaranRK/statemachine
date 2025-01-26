package com.rk.statemachine.stateactions.entry;

import com.rk.statemachine.enums.OrderEvent;
import com.rk.statemachine.enums.OrderState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderSubmittedEntryStateAction implements Action<OrderState, OrderEvent> {

    @Override
    public void execute(StateContext<OrderState, OrderEvent> context) {
        Long orderId = Long.class.cast(context.getExtendedState().getVariables().getOrDefault("orderId",-1L));
        log.info("Order Id : {}",orderId);
        log.info("Entering submitted state!!");
    }
}
