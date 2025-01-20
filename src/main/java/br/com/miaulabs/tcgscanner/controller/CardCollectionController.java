package br.com.miaulabs.tcgscanner.controller;

import br.com.miaulabs.tcgscanner.dto.CardCollectionDTO;
import br.com.miaulabs.tcgscanner.mapper.CardCollectionMapper;
import br.com.miaulabs.tcgscanner.model.Card;
import br.com.miaulabs.tcgscanner.model.CardCollection;
import br.com.miaulabs.tcgscanner.model.Collection;
import br.com.miaulabs.tcgscanner.service.CardCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/card-collections")
public class CardCollectionController {

    @Autowired
    private CardCollectionService cardCollectionService;

    @Autowired
    private CardCollectionMapper cardCollectionMapper;  // Injeção do Mapper para CardCollection

    @GetMapping
    public List<CardCollectionDTO> getAllCardCollections() {
        return cardCollectionService.findAll().stream()
                .map(cardCollectionMapper::cardCollectionToCardCollectionDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CardCollectionDTO getCardCollectionById(@PathVariable Long id) {
        CardCollection cardCollection = cardCollectionService.findById(id);
        return cardCollectionMapper.cardCollectionToCardCollectionDTO(cardCollection);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardCollectionDTO createCardCollection(@RequestBody CardCollectionDTO cardCollectionDTO) {
        CardCollection cardCollection = cardCollectionMapper.cardCollectionDTOToCardCollection(cardCollectionDTO);
        cardCollection = cardCollectionService.insert(cardCollection);
        return cardCollectionMapper.cardCollectionToCardCollectionDTO(cardCollection);
    }

    @PutMapping("/{id}")
    public CardCollectionDTO updateCardCollection(@PathVariable Long id, @RequestBody CardCollectionDTO cardCollectionDTO) {
        cardCollectionDTO.setId(id);
        CardCollection cardCollection = cardCollectionMapper.cardCollectionDTOToCardCollection(cardCollectionDTO);
        cardCollection = cardCollectionService.update(cardCollection);
        return cardCollectionMapper.cardCollectionToCardCollectionDTO(cardCollection);
    }

    @DeleteMapping("/{id}")
    public void deleteCardCollection(@PathVariable Long id) {
        cardCollectionService.delete(id);
    }

    @GetMapping("/collection/{collectionId}")
    public Set<CardCollectionDTO> getCardCollectionsByCollection(@PathVariable Long collectionId) {
        Collection collection = new Collection();
        collection.setId(collectionId);
        return cardCollectionService.findByCollection(collection).stream()
                .map(cardCollectionMapper::cardCollectionToCardCollectionDTO)
                .collect(Collectors.toSet());
    }

    @GetMapping("/card/{cardId}")
    public Set<CardCollectionDTO> getCardCollectionsByCard(@PathVariable Long cardId) {
        Card card = new Card();
        card.setId(cardId);
        return cardCollectionService.findByCard(card).stream()
                .map(cardCollectionMapper::cardCollectionToCardCollectionDTO)
                .collect(Collectors.toSet());
    }
}
