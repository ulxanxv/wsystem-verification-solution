package ru.monetarys.security.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.monetarys.dao.models.Credential;
import ru.monetarys.dao.repositories.CredentialRepository;

@Service("userDetails")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CredentialRepository credentialRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Credential credential = credentialRepository
                .findByName(s)
                .orElseThrow(() -> new UsernameNotFoundException("User " + s + " not found!"));

        return User.withUsername(s)
                .password(credential.getPassword())
                .authorities(credential.getRole())
                .build();
    }

}
