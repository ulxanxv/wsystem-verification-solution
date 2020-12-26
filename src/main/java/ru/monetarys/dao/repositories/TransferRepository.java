package ru.monetarys.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.monetarys.dao.models.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
