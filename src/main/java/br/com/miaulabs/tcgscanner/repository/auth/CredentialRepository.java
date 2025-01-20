package br.com.miaulabs.tcgscanner.repository.auth;

import br.com.miaulabs.tcgscanner.model.auth.Credential;
import br.com.miaulabs.tcgscanner.model.base.BaseRepository;

import java.util.Optional;

public interface CredentialRepository extends BaseRepository<Credential> {
    // O JPA vai implementar esse metodo:
    Optional<Credential> findByUsername(String username);
}
