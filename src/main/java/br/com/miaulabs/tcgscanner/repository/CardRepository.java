package br.com.miaulabs.tcgscanner.repository;

import br.com.miaulabs.tcgscanner.model.Card;
import br.com.miaulabs.tcgscanner.model.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends BaseRepository<Card> {

    Card findByName(String name);

    Card findByImageName(String imageName); // Método para buscar pelo imageName
}
