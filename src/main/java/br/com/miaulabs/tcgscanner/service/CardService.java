package br.com.miaulabs.tcgscanner.service;

import br.com.miaulabs.tcgscanner.model.Card;
import br.com.miaulabs.tcgscanner.model.base.BaseService;
import br.com.miaulabs.tcgscanner.repository.CardRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class CardService extends BaseService<Card, CardRepository> {
    // Qualquer operação específica do Card pode ser adicionada aqui
    public Card findByName(String name) {
        return repository.findByName(name);
    }

    public Card findByImageName(String imageName) {
        return repository.findByImageName(imageName); // Delegando ao repositório
    }

    // Método para identificar a carta pela imagem
    public String identifyCard(MultipartFile imageFile) {
        // Lógica para identificar a carta pela imagem
        // Por exemplo, usar uma biblioteca de OCR ou comparação de imagem

        // Aqui você pode implementar um mecanismo de OCR ou comparação
        // De forma simplificada, vou supor que o nome da imagem seja o nome da carta
        // Isso pode ser substituído pela lógica real de identificação da imagem
        String imageName = processImageToGetImageName(imageFile);

        return imageName;
    }

    // Método para processar a imagem (simplificação, você pode adicionar lógica de OCR ou comparação)
    private String processImageToGetImageName(MultipartFile imageFile) {
        // Aqui você processaria a imagem. Pode ser um algoritmo de OCR ou qualquer outra lógica
        // Vamos supor que o nome da imagem seja o que precisamos para procurar no banco
        return imageFile.getOriginalFilename();  // Simplesmente retornando o nome do arquivo
    }

    @Transactional
    public Card saveOrUpdate(Card card) {
        Optional<Card> existingCard = Optional.ofNullable(repository.findByName(card.getName()));

        if (existingCard.isPresent()) {
            Card existing = existingCard.get();
            existing.setUrlImage(card.getUrlImage());
            existing.setType(card.getType());
            existing.setColor(card.getColor());
            existing.setManaCost(card.getManaCost());

            // Atualizar preços
            if (card.getPrices() != null) {
                existing.getPrices().clear();
                existing.getPrices().addAll(card.getPrices());
            }

            return repository.save(existing);
        } else {
            return repository.save(card);
        }
    }
}
