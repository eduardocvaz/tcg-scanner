package br.com.miaulabs.tcgscanner.repository.auth;

import br.com.miaulabs.tcgscanner.model.auth.User;
import br.com.miaulabs.tcgscanner.model.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User> {
    // Aqui você pode adicionar métodos customizados, por exemplo:
    User findByEmail(String email);
}
