package br.com.miaulabs.tcgscanner.model.auth;

import br.com.miaulabs.tcgscanner.model.Collection;
import br.com.miaulabs.tcgscanner.model.base.BaseModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "user_account")
@Getter
@Setter
@Builder
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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection collection;  // Relacionamento de 1 para 1 com Collection
}
