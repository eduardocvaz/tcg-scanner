package br.com.miaulabs.tcgscanner.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CardScrapingService {

    public Map<String, Object> scrapeCardDetails(String cardName) {
        Map<String, Object> cardDetails = new HashMap<>();
        String cardUrl = "https://www.ligamagic.com.br/?view=cards/card&card=" + cardName.replace("-", "%20");

        try {
            // Conecta ao site e obtém o HTML
            Document doc = Jsoup.connect(cardUrl).get();

            // Extrair imagem da carta
            Element imageElement = doc.selectFirst("img#featuredImage");
            String imageUrl = imageElement != null ? "https:" + imageElement.attr("src") : null;
            cardDetails.put("imageUrl", imageUrl);

            // Obter os preços a partir de #container-show-price
            Element priceContainer = doc.selectFirst("#container-show-price");
            if (priceContainer != null) {
                Elements priceBlocks = priceContainer.select(".bg-light-gray.container-price-mkp");

                for (Element priceBlock : priceBlocks) {
                    String type = Objects.requireNonNull(priceBlock.selectFirst(".extras")).text(); // N ou F (Normal ou Foil)
                    String minPrice = Objects.requireNonNull(priceBlock.selectFirst(".min .price")).text();
                    String avgPrice = Objects.requireNonNull(priceBlock.selectFirst(".medium .price")).text();
                    String maxPrice = Objects.requireNonNull(priceBlock.selectFirst(".max .price")).text();

                    cardDetails.put(type + "_minPrice", minPrice);
                    cardDetails.put(type + "_avgPrice", avgPrice);
                    cardDetails.put(type + "_maxPrice", maxPrice);
                }
            }

            // Extrair raridade
            Element rarityElement = doc.selectFirst("#details-screen-rarity a");
            String rarity = rarityElement != null ? rarityElement.text() : null;
            cardDetails.put("rarity", rarity);

            // Extrair tipos
            Elements typeElements = doc.select(".container-details .title:contains(Tipo) + span a");
            StringBuilder types = new StringBuilder();
            for (Element typeElement : typeElements) {
                types.append(typeElement.text()).append(", ");
            }
            cardDetails.put("type", !types.isEmpty() ? types.substring(0, types.length() - 2) : null);

            // Extrair formatos válidos
            Element formatsElement = doc.selectFirst(".container-details .title:contains(Formatos Válidos) + span");
            String formats = formatsElement != null ? formatsElement.text() : null;
            cardDetails.put("formats", formats);

            // Extrair artista
            Element artistElement = doc.selectFirst("#details-screen-artist a");
            String artist = artistElement != null ? artistElement.text() : null;
            cardDetails.put("artist", artist);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao conectar ao site da LigaMagic: " + e.getMessage(), e);
        }

        return cardDetails;
    }
}
