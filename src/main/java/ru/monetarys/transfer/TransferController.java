package ru.monetarys.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.monetarys.models.Transfer;
import ru.monetarys.transfer.entities.TransferRequest;

@RestController(value = "/")
public class TransferController {

    @Autowired
    private TransferComponent transferComponent;

    @PostMapping("v1/execute")
    public ResponseEntity<?> execute(@RequestBody TransferRequest request) {
        Transfer transfer = transferComponent.transferMoney(request);
        return ResponseEntity.ok(TransferUtil.getTransferResponse(transfer));
    }

}
