package ru.monetarys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.monetarys.models.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
