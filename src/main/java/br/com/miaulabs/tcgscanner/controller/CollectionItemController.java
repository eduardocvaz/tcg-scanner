package br.com.miaulabs.tcgscanner.controller;

import br.com.miaulabs.tcgscanner.dto.CollectionItemDTO;
import br.com.miaulabs.tcgscanner.dto.CollectionItemRequestDTO;
import br.com.miaulabs.tcgscanner.mapper.CollectionItemMapper;
import br.com.miaulabs.tcgscanner.model.Card;
import br.com.miaulabs.tcgscanner.model.CollectionItem;
import br.com.miaulabs.tcgscanner.repository.CardRepository;
import br.com.miaulabs.tcgscanner.service.CardCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/card-collections")
public class CollectionItemController {

    @Autowired
    private CardCollectionService cardCollectionService;

    @Autowired
    private CollectionItemMapper collectionItemMapper;  // Injeção do Mapper para CardCollection



    @GetMapping
    public List<CollectionItemDTO> getAllCardCollections() {
        return cardCollectionService.findAll().stream()
                .map(collectionItemMapper::cardCollectionToCardCollectionDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CollectionItemDTO getCardCollectionById(@PathVariable Long id) {
        CollectionItem collectionItem = cardCollectionService.findById(id);
        return collectionItemMapper.cardCollectionToCardCollectionDTO(collectionItem);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CollectionItemDTO createCardCollection(@RequestBody CollectionItemRequestDTO collectionItemDTO) {
        return collectionItemMapper.cardCollectionToCardCollectionDTO(cardCollectionService.save(collectionItemDTO));
    }

    @PutMapping("/{id}")
    public CollectionItemDTO updateCardCollection(@PathVariable Long id, @RequestBody CollectionItemDTO collectionItemDTO) {
        collectionItemDTO.setId(id);
        CollectionItem collectionItem = collectionItemMapper.cardCollectionDTOToCardCollection(collectionItemDTO);
        collectionItem = cardCollectionService.update(collectionItem);
        return collectionItemMapper.cardCollectionToCardCollectionDTO(collectionItem);
    }

    @DeleteMapping("/{id}")
    public void deleteCardCollection(@PathVariable Long id) {
        cardCollectionService.delete(id);
    }

    @GetMapping("/card/{cardId}")
    public Set<CollectionItemDTO> getCardCollectionsByCard(@PathVariable Long cardId) {
        Card card = new Card();
        card.setId(cardId);
        return cardCollectionService.findByCard(card).stream()
                .map(collectionItemMapper::cardCollectionToCardCollectionDTO)
                .collect(Collectors.toSet());
    }
}
