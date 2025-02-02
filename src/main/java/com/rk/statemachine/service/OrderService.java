package com.rk.statemachine.service;


import com.rk.statemachine.dto.OrderDTO;
import com.rk.statemachine.entity.Order;
import com.rk.statemachine.enums.OrderEvent;
import com.rk.statemachine.enums.OrderState;
import com.rk.statemachine.mapper.OrderMapper;
import com.rk.statemachine.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final StateMachineFactory<OrderState, OrderEvent> orderStateMachineFactory;

    private final OrderMapper orderMapper;

    public OrderDTO createOrder(OrderDTO order, Map<String,String> headers){
        Order newOrder = orderMapper.dtoToEntity(order);
        newOrder.setOrderState(OrderState.SUBMITTED);
        newOrder.setDatetime(new Date());
        return orderMapper.entityToDto(orderRepository.save(newOrder));
    }


    public OrderDTO fetchOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order Not found"));
        return orderMapper.entityToDto(order);
    }


    public List<OrderDTO> fetchAll(){
        List<Order> orders = orderRepository.findAll();
        return orderMapper.entityToDto(orders);
    }


    public OrderDTO updateOrder(OrderDTO order){
        OrderDTO existingOrder =  fetchOrder(order.getId());
        StateMachine<OrderState,OrderEvent> sm =  restoreStateMachine(order.getId());
        sm.start();
        // Check if the event is valid for the current state
//        Collection<Transition<OrderState, OrderEvent>> transitions = sm.getTransitions();
//        boolean isValidEvent = transitions.stream()
//                .anyMatch(transition -> transition.getSource().getId().equals(order.getOrderState()) &&
//                        transition.getTrigger().getEvent().equals(order.getOrderEvent()));
//
//        if (!isValidEvent) {
//            throw new IllegalStateException("Invalid event: " + order.getOrderEvent() + " for state: " + existingOrder.getOrderState());
//        }
        boolean accepted = sm.sendEvent(order.getOrderEvent());
        //can check is the event accepted or not if not we can throw the exception
        //boolean result = sm.sendEvent(Mono.just(MessageBuilder.withPayload(order.getOrderEvent()).build())).blockLast();
        log.info("Event accepted flag {}",accepted);
        existingOrder.setOrderState(sm.getState().getId());
        orderRepository.save(orderMapper.dtoToEntity(existingOrder));
        return existingOrder;
    }




    /**
     *
     * @param orderId
     * @return method to return the state machine of an order
     */
    private StateMachine<OrderState,OrderEvent> restoreStateMachine(Long orderId){
        OrderDTO order = fetchOrder(orderId);
        StateMachine<OrderState,OrderEvent> sm = orderStateMachineFactory.getStateMachine(String.valueOf(order.getId()));
        sm.getExtendedState().getVariables().put("orderId",orderId);
        // Restore state for state machine
        sm.stop();
        sm.getStateMachineAccessor()
                .doWithAllRegions(accessor -> accessor.resetStateMachine(
                        new DefaultStateMachineContext<>(order.getOrderState(), null, null, null)
                ));
        return sm;
    }

}
