package br.com.miaulabs.tcgscanner.controller.auth;

import br.com.miaulabs.tcgscanner.dto.auth.LoginDTO;
import br.com.miaulabs.tcgscanner.dto.auth.TokenResponseDTO;
import br.com.miaulabs.tcgscanner.service.auth.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestHeader HttpHeaders headers, HttpServletRequest request) {
        String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
        LoginDTO loginDto = extrairCredenciais(authHeader);

        assert loginDto != null;
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password())
                );
        String token = tokenService.generateToken(authentication);

        return TokenResponseDTO
                .builder()
                .username(loginDto.username())
                .token(token)
                .build();
    }

    LoginDTO extrairCredenciais(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            // Extrair e decodificar as credenciais do cabeçalho
            // Remover o prefixo "Basic " do cabeçalho Authorization
            String credentials = authHeader.substring("Basic ".length()).trim();

            // Decodificar a string Base64
            byte[] decodedBytes = Base64.getDecoder().decode(credentials);
            String decodedCredentials = new String(decodedBytes);

            // Dividir a string no formato "username:password"
            String[] splitCredentials = decodedCredentials.split(":", 2);

            // Obter nome de usuário e senha
            String username = splitCredentials[0];
            String password = splitCredentials[1];
            return new LoginDTO(username, password);
        }
        return null;
    }


}
