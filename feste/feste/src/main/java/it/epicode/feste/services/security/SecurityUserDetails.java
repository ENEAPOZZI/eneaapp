package it.epicode.feste.services.security;


import it.epicode.feste.enteties.Utenti;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class SecurityUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;

    // l'elenco dei ruoli dell'utente
    private Collection<? extends GrantedAuthority> authorities;
    private String password;
    private String username;
    @Builder.Default
    private boolean accountNonExpired = true;
    @Builder.Default
    private boolean accountNonLocked = true;
    @Builder.Default
    private boolean credentialsNonExpired = true;
    @Builder.Default
    private boolean enabled = true;

    public static SecurityUserDetails build(Utenti user) {
        String ruolo = user.getRuolo();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(ruolo);
        return SecurityUserDetails.builder() //
                .withUsername(user.getUsername()) //
                .withPassword(user.getPassword()) //
                .withAuthorities(Collections.singletonList(authority)) //
                .build();
    }
}
