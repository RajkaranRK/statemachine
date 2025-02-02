package com.rk.statemachine.dto;


import com.rk.statemachine.enums.OrderEvent;
import com.rk.statemachine.enums.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Serializable {

    private Long id;

    private String userId;

    private Date datetime;

    private OrderState orderState;

    private OrderEvent orderEvent;
}
