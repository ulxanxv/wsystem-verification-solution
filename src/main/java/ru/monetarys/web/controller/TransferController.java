package ru.monetarys.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.monetarys.dao.models.Transfer;
import ru.monetarys.ro.web.TransferHistoryFilterRequestRo;
import ru.monetarys.ro.web.TransferRequestRo;
import ru.monetarys.ro.web.TransferResponseRo;
import ru.monetarys.web.service.TransferService;

import java.util.List;

@RestController("/v1")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/execute")
    @ResponseStatus(HttpStatus.OK)
    public TransferResponseRo execute(@RequestBody TransferRequestRo request) {
        return transferService.transferMoney(request);
    }

    @GetMapping("/history")
    @ResponseStatus(HttpStatus.OK)
    public List<Transfer> history(@RequestBody TransferHistoryFilterRequestRo filter) {
        return transferService.transferHistoryByFilter(filter);
    }

}
