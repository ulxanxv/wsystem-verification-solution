package ru.monetarys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.monetarys.models.Credential;

public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Credential findByName(String name);

}
