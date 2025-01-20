package br.com.miaulabs.tcgscanner.repository;


import br.com.miaulabs.tcgscanner.model.Collection;
import br.com.miaulabs.tcgscanner.model.auth.User;
import br.com.miaulabs.tcgscanner.model.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends BaseRepository<Collection> {
    // Você pode adicionar métodos customizados se necessário
    Collection findByUser(User user);
}
