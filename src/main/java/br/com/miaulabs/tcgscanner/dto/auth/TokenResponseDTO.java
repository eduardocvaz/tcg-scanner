package br.com.miaulabs.tcgscanner.dto.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponseDTO {
    private String token;
    private String username;
    private Long userId;

}
