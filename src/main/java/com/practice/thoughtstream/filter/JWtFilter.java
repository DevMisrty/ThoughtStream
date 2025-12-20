
package com.practice.thoughtstream.filter;

import com.practice.thoughtstream.utility.JwtUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JWtFilter extends OncePerRequestFilter {

    private final JwtUtility utility;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("JWT FILTER HIT: " + request.getRequestURI());

        String token = request.getHeader("Authorization");
        if(token == null || !token.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        token = token.substring(7);

        try {
            if (utility.isValidToken(token)
                    && SecurityContextHolder.getContext().getAuthentication() == null) {

                String email = utility.getEmailFromToken(token);
                String role = utility.getRoleFromToken(token);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role))
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
            System.out.println("SomeThing went wrong, jwt token error");
        }

        filterChain.doFilter(request, response);
    }
}
