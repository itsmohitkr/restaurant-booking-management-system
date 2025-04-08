package online.devplanet.crud_application.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class UserPrincipal implements UserDetails {

    private int ownerId;
    private String ownerEmail;
    private String ownerPassword;
    private List<GrantedAuthority> authorities;

    public UserPrincipal(int ownerId, String ownerEmail, String ownerPassword, List<GrantedAuthority> authorities) {
        this.ownerId = ownerId;
        this.ownerEmail = ownerEmail;
        this.ownerPassword = ownerPassword;
        this.authorities = authorities;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return ownerPassword;
    }

    @Override
    public String getUsername() {
        return ownerEmail;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
