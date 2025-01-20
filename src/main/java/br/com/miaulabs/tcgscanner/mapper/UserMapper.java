package br.com.miaulabs.tcgscanner.mapper;

import br.com.miaulabs.tcgscanner.dto.UserDTO;
import br.com.miaulabs.tcgscanner.model.auth.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // Instância do Mapper
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Métodos de conversão
    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);
}
