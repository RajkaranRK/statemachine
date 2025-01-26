package com.rk.statemachine.listener;

import com.rk.statemachine.enums.OrderEvent;
import com.rk.statemachine.enums.OrderState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class OrderStateListenerAdapter extends StateMachineListenerAdapter<OrderState, OrderEvent> {

    @Override
    public void stateChanged(State<OrderState, OrderEvent> from, State<OrderState, OrderEvent> to) {
        log.info("State changed from {} to {}",from,to);
    }
}
