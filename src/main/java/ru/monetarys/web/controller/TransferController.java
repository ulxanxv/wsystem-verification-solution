package ru.monetarys.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.monetarys.web.ro.TransferRequestRo;
import ru.monetarys.web.ro.TransferResponseRo;
import ru.monetarys.web.service.TransferServiceImpl;

@RestController("/v1")
public class TransferController {

    @Autowired
    private TransferServiceImpl transferServiceImpl;

    @PostMapping(value = "/execute")
    public ResponseEntity<TransferResponseRo> execute(@RequestBody TransferRequestRo request) {
        return ResponseEntity.status(HttpStatus.OK).body(transferServiceImpl.transferMoney(request));
    }

}
