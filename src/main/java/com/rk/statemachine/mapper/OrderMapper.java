package com.rk.statemachine.mapper;

import com.rk.statemachine.dto.OrderDTO;
import com.rk.statemachine.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDTO entityToDto(Order order);

    Order dtoToEntity(OrderDTO order);

    List<OrderDTO> entityToDto(List<Order> orders);
}
