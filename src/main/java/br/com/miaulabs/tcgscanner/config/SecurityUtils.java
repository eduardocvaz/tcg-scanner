package br.com.miaulabs.tcgscanner.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class SecurityUtils {

    public static Long getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaim("userId"); // Recupera o ID do usuário
        }
        return null;
    }
}
