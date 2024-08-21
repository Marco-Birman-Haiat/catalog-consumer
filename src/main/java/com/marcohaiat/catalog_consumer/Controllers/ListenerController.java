package com.marcohaiat.catalog_consumer.Controllers;

import com.marcohaiat.catalog_consumer.service.ConsumerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/listener")
public class ListenerController {
    private final ConsumerService consumerService;

    public ListenerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @GetMapping
    public ResponseEntity<String> listenTest() {
        this.consumerService.receiveMessages2();
        return ResponseEntity.ok("Testeee");
    }
}
