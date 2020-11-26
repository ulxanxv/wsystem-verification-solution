package ru.monetarys.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.monetarys.models.Credential;
import ru.monetarys.repositories.CredentialRepository;

import java.util.Optional;

@Service("userDetails")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CredentialRepository credentialRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<Credential> optionalCredential = credentialRepository.findByName(s);

        if (optionalCredential.isEmpty()) {
            throw new UsernameNotFoundException("User " + s + " not found!");
        }

        Credential credential = optionalCredential.get();

        UserBuilder userBuilder = User.withUsername(s);
        userBuilder.password(credential.getPassword());
        userBuilder.authorities(credential.getRole());

        return userBuilder.build();
    }

}
