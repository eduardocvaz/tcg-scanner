package br.com.miaulabs.tcgscanner.service;

import br.com.miaulabs.tcgscanner.model.Collection;
import br.com.miaulabs.tcgscanner.model.auth.User;
import br.com.miaulabs.tcgscanner.model.base.BaseService;
import br.com.miaulabs.tcgscanner.repository.CollectionRepository;
import org.springframework.stereotype.Service;

@Service
public class CollectionService extends BaseService<Collection, CollectionRepository> {
    // Qualquer operação específica da Collection pode ser adicionada aqui
    public Collection findByUser(User user) {
        return repository.findByUser(user);
    }
}
