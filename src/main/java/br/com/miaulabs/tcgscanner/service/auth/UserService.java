package br.com.miaulabs.tcgscanner.service.auth;

import br.com.miaulabs.tcgscanner.model.auth.User;
import br.com.miaulabs.tcgscanner.model.base.BaseService;
import br.com.miaulabs.tcgscanner.repository.auth.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User, UserRepository> {


}
