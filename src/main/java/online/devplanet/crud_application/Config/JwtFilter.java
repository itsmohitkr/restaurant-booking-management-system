package online.devplanet.crud_application.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import online.devplanet.crud_application.Service.JwtService;
import online.devplanet.crud_application.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    ApplicationContext context;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //  Extract JWT from cookies
        String token = extractTokenFromCookies(request);
        String ownerEmail = null;
        int ownerId = 0;
        String role = null;

        if (token != null) {
            ownerEmail = jwtService.extractUsername(token);
            ownerId = jwtService.extractOwnerId(token);
        }
        // check if the ownerName is not null and the security context is not authenticated
        if (ownerEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // get the user details
            UserDetails userDetails = myUserDetailsService.loadUserByUserId(ownerId);

            // check if the token is valid
            if (jwtService.validateToken(token, userDetails)) {
                // create the authentication object
                UserAuthenticationToken authToken = new UserAuthenticationToken(userDetails, null, userDetails.getAuthorities(), ownerId);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
    // 🔹 Extract JWT token from cookies
    private String extractTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("JWT-TOKEN".equals(cookie.getName())) {  // Match cookie name
                    return cookie.getValue();  // Return token value
                }
            }
        }
        return null; // No token found in cookies
    }
}

