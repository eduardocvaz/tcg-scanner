package br.com.miaulabs.tcgscanner;

import br.com.miaulabs.tcgscanner.config.auth.RsaKeyProperties;
import br.com.miaulabs.tcgscanner.model.auth.Credential;
import br.com.miaulabs.tcgscanner.model.auth.User;
import br.com.miaulabs.tcgscanner.service.auth.CredentialService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class TcgScannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TcgScannerApplication.class, args);
    }

    //    DESCOMENTAR PARA CRIAR PRIMEIRO USUARIO PARA LOGAR NO SISTEMA
    @Autowired
    CredentialService credentialService;
    @PostConstruct
    public void started() {
        // Verificar se o usuário já existe no banco de dados
        if (credentialService.findByUsername("admin@email.com") == null) {
            // Se não existe, criar o administrador
            Credential credential = Credential.builder()
                    .username("admin@email.com")
                    .password("senha123")
                    .roles("admin")
                    .user(User.builder()
                            .name("Administrador")
                            .lastname("Admin")
                            .email("admin@email.com")
                            .build()
                    )
                    .build();
            credentialService.create(credential);
        }
    }

}
