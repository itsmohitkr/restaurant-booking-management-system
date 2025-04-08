package online.devplanet.crud_application.Config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final int ownerId;

    public UserAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, int ownerId) {
        super(principal, credentials, authorities);
        this.ownerId = ownerId;
    }

    public int getOwnerId() {
        return ownerId;
    }
}
