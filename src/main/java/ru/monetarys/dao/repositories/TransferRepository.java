package ru.monetarys.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.monetarys.dao.models.Transfer;

import java.util.Optional;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

    Optional<Transfer> findByTransactionId(String transactionId);

}
