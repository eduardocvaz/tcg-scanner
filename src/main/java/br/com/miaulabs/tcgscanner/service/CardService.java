package br.com.miaulabs.tcgscanner.service;

import br.com.miaulabs.tcgscanner.model.Card;
import br.com.miaulabs.tcgscanner.model.base.BaseService;
import br.com.miaulabs.tcgscanner.repository.CardRepository;
import org.springframework.stereotype.Service;

@Service
public class CardService extends BaseService<Card, CardRepository> {
}
