package com.rk.statemachine.controller.v1;


import com.rk.statemachine.constant.APIConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIConstant.HEALTH_URL)
public class PublicHealthController {


    @GetMapping
    public ResponseEntity<String> health(){
        return new ResponseEntity<>("Ok Health", HttpStatus.OK);
    }
}
