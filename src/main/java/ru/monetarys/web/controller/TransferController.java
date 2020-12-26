package ru.monetarys.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.monetarys.ro.web.TransferRequestRo;
import ru.monetarys.ro.web.TransferResponseRo;
import ru.monetarys.web.service.impl.TransferServiceImpl;

@RestController("/v1")
@RequiredArgsConstructor
public class TransferController {

    private final TransferServiceImpl transferServiceImpl;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/execute")
    public TransferResponseRo execute(@RequestBody TransferRequestRo request) {
        return transferServiceImpl.transferMoney(request);
    }

}
