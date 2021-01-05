package ru.monetarys.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.monetarys.dao.models.Transfer;

import java.util.Optional;

public interface TransferRepository extends JpaRepository<Transfer, Long>, QuerydslPredicateExecutor<Transfer> {

    Optional<Transfer> findByTransactionId(String transactionId);

}
