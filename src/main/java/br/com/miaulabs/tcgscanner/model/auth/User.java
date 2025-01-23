package br.com.miaulabs.tcgscanner.model.auth;

import br.com.miaulabs.tcgscanner.model.Collection;
import br.com.miaulabs.tcgscanner.model.base.BaseModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "user_account")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseModel {
    @Column
    @NotBlank(message = "O campo 'Nome' da entidade Usuario não pode ser vazio")
    private String name;

    @Column
    @NotBlank(message = "O campo 'Sobrenome' da entidade Usuario não pode ser vazio")
    private String lastname;

    @Column
    @Email
    private String email;

    // Explicitamente adicionar o getter para `id`
    @Override
    public Long getId() {
        return super.getId();
    }
}
