package br.com.miaulabs.tcgscanner.controller;

import br.com.miaulabs.tcgscanner.dto.CardDTO;
import br.com.miaulabs.tcgscanner.mapper.CardMapper;
import br.com.miaulabs.tcgscanner.model.Card;
import br.com.miaulabs.tcgscanner.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private CardMapper cardMapper;  // Injeção do Mapper para Card

    @GetMapping
    public List<CardDTO> getAllCards() {
        return cardService.findAll().stream()
                .map(cardMapper::cardToCardDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CardDTO getCardById(@PathVariable Long id) {
        Card card = cardService.findById(id);
        return cardMapper.cardToCardDTO(card);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardDTO createCard(@RequestBody CardDTO cardDTO) {
        Card card = cardMapper.cardDTOToCard(cardDTO);
        card = cardService.insert(card);
        return cardMapper.cardToCardDTO(card);
    }

    @PutMapping("/{id}")
    public CardDTO updateCard(@PathVariable Long id, @RequestBody CardDTO cardDTO) {
        cardDTO.setId(id);
        Card card = cardMapper.cardDTOToCard(cardDTO);
        card = cardService.update(card);
        return cardMapper.cardToCardDTO(card);
    }

    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable Long id) {
        cardService.delete(id);
    }

    @GetMapping("/name/{name}")
    public CardDTO getCardByName(@PathVariable String name) {
        Card card = cardService.findByName(name);
        return cardMapper.cardToCardDTO(card);
    }
}