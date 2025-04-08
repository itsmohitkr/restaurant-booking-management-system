package online.devplanet.crud_application.Service;

import online.devplanet.crud_application.Repository.RestaurantOwnerRepository;
import online.devplanet.crud_application.model.RestaurantOwner;
import online.devplanet.crud_application.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private RestaurantOwnerRepository ownerRepository;

    @Override
    public UserDetails loadUserByUsername(String ownerName) throws UsernameNotFoundException {
        RestaurantOwner owner= (RestaurantOwner) ownerRepository.findByOwnerEmail(ownerName);
        if(owner==null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_"+owner.getOwnerRole()));
        return new UserPrincipal(owner.getOwnerId(),owner.getOwnerEmail(),owner.getOwnerPassword(),authorities);

    }

    public UserDetails loadUserByUserId(int ownerId) {
        RestaurantOwner owner= ownerRepository.findByOwnerId(ownerId);
        if(owner==null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_"+owner.getOwnerRole()));
        return new UserPrincipal(owner.getOwnerId(),owner.getOwnerEmail(),owner.getOwnerPassword(),authorities);
    }
}
