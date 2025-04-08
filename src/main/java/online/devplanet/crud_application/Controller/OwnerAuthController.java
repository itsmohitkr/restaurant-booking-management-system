package online.devplanet.crud_application.Controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import online.devplanet.crud_application.DTO.RestaurantOwnerDTO;
import online.devplanet.crud_application.Service.JwtService;
import online.devplanet.crud_application.Service.RestaurantOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/api/auth/owner")
public class OwnerAuthController {

    @Autowired
    private RestaurantOwnerService ownerService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    // create user
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RestaurantOwnerDTO restaurantOwnerDTO) {
        restaurantOwnerDTO.setOwnerPassword(encoder.encode(restaurantOwnerDTO.getOwnerPassword()));
        ownerService.addRestaurantOwner(restaurantOwnerDTO);
        return ResponseEntity.ok("Restaurant Owner registered successfully");
    }

    // for login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody RestaurantOwnerDTO restaurantOwnerDTO, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(restaurantOwnerDTO.getOwnerEmail(), restaurantOwnerDTO.getOwnerPassword()));
        // get userid from authentication

        if (authentication.isAuthenticated()){
            RestaurantOwnerDTO ownerFromDb = ownerService.getRestaurantOwnerByOwnerEmail(restaurantOwnerDTO.getOwnerEmail());
            String jwtToken= jwtService.generateToken(ownerFromDb.getOwnerEmail(), ownerFromDb.getOwnerId());
            ResponseCookie cookie =ResponseCookie.from("JWT-TOKEN", jwtToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(60*60*24*15)
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return ResponseEntity.ok("login Successful! Jwt stored in cookie");
        }

        return ResponseEntity.status(401).body("Login failed");
    }

}
