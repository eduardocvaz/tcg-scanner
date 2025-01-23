package br.com.miaulabs.tcgscanner.controller;

import br.com.miaulabs.tcgscanner.dto.CardDTO;
import br.com.miaulabs.tcgscanner.mapper.CardMapper;
import br.com.miaulabs.tcgscanner.model.Card;
import br.com.miaulabs.tcgscanner.model.Price;
import br.com.miaulabs.tcgscanner.service.CardIdentificationService;
import br.com.miaulabs.tcgscanner.service.CardScrapingService;
import br.com.miaulabs.tcgscanner.service.CardService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cards")
public class CardController {


    @Autowired
    private CardService cardService;

    @Autowired
    private CardMapper cardMapper;  // Injeção do Mapper para Card

    // Método que recebe uma imagem, identifica a carta e verifica se o imageName existe no banco
    @Autowired
    private CardIdentificationService cardIdentificationService;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CardScrapingService cardScrapingService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/identify")
    public ResponseEntity<CardDTO> identifyCard(@RequestParam("image") MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", imageFile.getResource());

        HttpEntity<Object> requestEntity = new HttpEntity<>(builder.build(), headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:5000/identify_card", HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                JsonNode jsonResponse = objectMapper.readTree(response.getBody());
                String cardName = jsonResponse.get("card_name").asText();
                JsonNode detailsNode = jsonResponse.get("details");

                // Verifica se a carta já existe no banco de dados
                Card card = cardService.findByName(cardName);
                if (card == null) {
                    card = new Card();
                    card.setName(cardName);
                }

                // Atualiza os detalhes da carta
                if (detailsNode != null && !detailsNode.isEmpty()) {
                    if (detailsNode.has("image_url") && !detailsNode.get("image_url").isNull()) {
                        card.setUrlImage(detailsNode.get("image_url").asText());
                    }

                    if (detailsNode.has("type") && !detailsNode.get("type").isNull()) {
                        card.setType(detailsNode.get("type").asText());
                    }

                    if (detailsNode.has("color") && !detailsNode.get("color").isNull()) {
                        card.setColor(detailsNode.get("color").asText());
                    }

                    if (detailsNode.has("mana_cost") && !detailsNode.get("mana_cost").isNull()) {
                        card.setManaCost(detailsNode.get("mana_cost").asText());
                    }

                    // Atualiza os preços
                    JsonNode pricesNode = detailsNode.get("prices");
                    if (pricesNode != null) {
                        List<Price> prices = new ArrayList<>();
                        pricesNode.fieldNames().forEachRemaining(type -> {
                            JsonNode priceDetails = pricesNode.get(type);
                            Price price = new Price();
                            price.setType(type);

                            if (priceDetails.has("min") && !priceDetails.get("min").isNull()) {
                                price.setMinPrice(priceDetails.get("min").asText());
                            }
                            if (priceDetails.has("avg") && !priceDetails.get("avg").isNull()) {
                                price.setAvgPrice(priceDetails.get("avg").asText());
                            }
                            if (priceDetails.has("max") && !priceDetails.get("max").isNull()) {
                                price.setMaxPrice(priceDetails.get("max").asText());
                            }

                            prices.add(price);
                        });
                        card.setPrices(prices);
                    }
                }

                // Salva ou atualiza a carta no banco de dados
                card = cardService.saveOrUpdate(card);

                // Converte para CardDTO
                CardDTO cardDTO = cardMapper.cardToCardDTO(card);
                return ResponseEntity.ok(cardDTO);
            } else {
                return ResponseEntity.status(response.getStatusCode()).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

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

    @GetMapping("/image/{imageName}")
    public CardDTO getCardByImageName(@PathVariable String imageName) {
        Card card = cardService.findByImageName(imageName); // Busca a carta pelo serviço
        return cardMapper.cardToCardDTO(card); // Converte para DTO
    }
}