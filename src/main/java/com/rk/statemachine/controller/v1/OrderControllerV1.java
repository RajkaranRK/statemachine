package com.rk.statemachine.controller.v1;


import com.rk.statemachine.constant.APIConstant;
import com.rk.statemachine.dto.OrderDTO;
import com.rk.statemachine.service.OrderService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(APIConstant.BASE_URL+APIConstant.Version.V1+APIConstant.ORDER_URL)
@AllArgsConstructor
public class OrderControllerV1 {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO request, @RequestHeader Map<String,String> headers){
        return new ResponseEntity<>(orderService.createOrder(request,headers), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> fetchOrder(@PathVariable("id") Long id,Map<String,String> headers){
        return new ResponseEntity<>(orderService.fetchOrder(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> fetchAllOrder(){
        return new ResponseEntity<>(orderService.fetchAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable("id") Long id, @RequestBody OrderDTO order, @RequestHeader Map<String,String> headers){
        order.setId(id);
        return new ResponseEntity<>(orderService.updateOrder(order),HttpStatus.OK);
    }
}
