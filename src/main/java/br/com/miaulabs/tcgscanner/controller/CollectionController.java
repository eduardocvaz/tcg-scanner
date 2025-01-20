package br.com.miaulabs.tcgscanner.controller;

import br.com.miaulabs.tcgscanner.dto.CollectionDTO;
import br.com.miaulabs.tcgscanner.mapper.CollectionMapper;
import br.com.miaulabs.tcgscanner.mapper.UserMapper;
import br.com.miaulabs.tcgscanner.model.Collection;
import br.com.miaulabs.tcgscanner.model.auth.User;
import br.com.miaulabs.tcgscanner.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private CollectionMapper collectionMapper;  // Injeção do Mapper para Collection

    @Autowired
    private UserMapper userMapper;  // Injeção do Mapper para User

    @GetMapping
    public List<CollectionDTO> getAllCollections() {
        return collectionService.findAll().stream()
                .map(collectionMapper::collectionToCollectionDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CollectionDTO getCollectionById(@PathVariable Long id) {
        Collection collection = collectionService.findById(id);
        return collectionMapper.collectionToCollectionDTO(collection);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CollectionDTO createCollection(@RequestBody CollectionDTO collectionDTO) {
        Collection collection = collectionMapper.collectionDTOToCollection(collectionDTO);
        collection = collectionService.insert(collection);
        return collectionMapper.collectionToCollectionDTO(collection);
    }

    @PutMapping("/{id}")
    public CollectionDTO updateCollection(@PathVariable Long id, @RequestBody CollectionDTO collectionDTO) {
        collectionDTO.setId(id);
        Collection collection = collectionMapper.collectionDTOToCollection(collectionDTO);
        collection = collectionService.update(collection);
        return collectionMapper.collectionToCollectionDTO(collection);
    }

    @DeleteMapping("/{id}")
    public void deleteCollection(@PathVariable Long id) {
        collectionService.delete(id);
    }

    @GetMapping("/user/{userId}")
    public CollectionDTO getCollectionByUser(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        Collection collection = collectionService.findByUser(user);
        return collectionMapper.collectionToCollectionDTO(collection);
    }
}