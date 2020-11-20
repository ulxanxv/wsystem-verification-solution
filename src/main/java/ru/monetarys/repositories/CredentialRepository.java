package ru.monetarys.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.monetarys.models.Credential;

public interface CredentialRepository extends CrudRepository<Credential, Long> {

    Credential findByName(String name);

}
