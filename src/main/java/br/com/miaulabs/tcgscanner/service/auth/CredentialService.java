package br.com.miaulabs.tcgscanner.service.auth;

import br.com.miaulabs.tcgscanner.model.auth.Credential;
import br.com.miaulabs.tcgscanner.repository.auth.CredentialRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CredentialService implements UserDetailsService {
    CredentialRepository repository;

    BCryptPasswordEncoder encoder;

    public CredentialService(CredentialRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("O usuário " + username + " não existe!"));
    }

    public Credential findByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    public Credential create(Credential c){
        c.setPassword(encoder.encode(c.getPassword()));
        return repository.save(c);
    }
}
