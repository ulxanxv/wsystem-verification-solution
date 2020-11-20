package ru.monetarys.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.monetarys.models.Credential;
import ru.monetarys.repositories.CredentialRepository;

@Service("userDetails")
public class UserDetailsServiceImpl implements UserDetailsService {

    private CredentialRepository credentialRepository;

    @Autowired
    public UserDetailsServiceImpl(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Credential credential = credentialRepository.findByName(s);

        if (credential == null) {
            throw new UsernameNotFoundException("User " + s + " not found!");
        }

        UserBuilder userBuilder = User.withUsername(s);
        userBuilder.password(credential.getPassword());
        userBuilder.authorities(credential.getRole());

        return userBuilder.build();
    }

}
