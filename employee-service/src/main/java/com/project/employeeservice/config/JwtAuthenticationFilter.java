package com.project.employeeservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader =request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        //null değilse ve bearer ile başlamıyorsa filtreleme yap anlamıan geliyor.
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return; //return çünkü if'ten sonra aşağıdaki kodların da çalışması gerekiyor.
        }
        jwt=authHeader.substring(7);
        userEmail=jwtService.extractUsername(jwt);
        //yani useremail varsa ve authenticate olmadıysa yani bağlanmadıysa  anlamına gelir.
        if(userEmail!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            ////loadUserByUsername->ApplicationConfig'den gelir.Ve database'den ismi alırız.
            UserDetails userDetails=this.userDetailsService.loadUserByUsername(userEmail);
            //token geçerli mi geçersiz mi validate etmek için
            if(jwtService.isTokenValid(jwt,userDetails)){
                //authentication bilgilerini temsil etmek için
                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, //şifre için ancak jwt'de genelde şifre yok çünkü jwt ile giriş yapılır
                        //ek olarak kullanıcı şifreleri credentials'da değil jwt içerisinde bulunur.(yaygın kullanım)
                        userDetails.getAuthorities()
                );
                //req atmak için
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //securitcontextholder'i update ederiz böylece.
                SecurityContextHolder.getContext().setAuthentication(authToken);


            }
        }
        filterChain.doFilter(request,response);

    }

}
