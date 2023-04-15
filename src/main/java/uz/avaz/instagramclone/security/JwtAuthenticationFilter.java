package uz.avaz.instagramclone.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.avaz.instagramclone.service.impl.UserDetailsServiceImpl;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JwtAuthenticationUtil jwtAuthenticationUtil;

    public JwtAuthenticationFilter(UserDetailsServiceImpl userDetailsServiceImpl, JwtAuthenticationUtil jwtAuthenticationUtil) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.jwtAuthenticationUtil = jwtAuthenticationUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String jwtToken = parseJwt(request);
        if (jwtToken != null && jwtAuthenticationUtil.isValidToken(jwtToken)) {
            String username = jwtAuthenticationUtil.getUsernameFromToken(jwtToken);
            try {
                UserDetails user = userDetailsServiceImpl.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (UsernameNotFoundException e) {
                e.printStackTrace();
            }
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
