package com.sbnz.gleficu.config;

import com.sbnz.gleficu.service.UserService;
import com.sbnz.gleficu.util.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final JwtFilter jwtFilter;
    private final PasswordEncoder passwordEncoder;
    private final RestAuthEntryPoint restAuthenticationEntryPoint;

    @Autowired
    public SecurityConfig(UserService userService, JwtFilter jwtFilter, PasswordEncoder passwordEncoder, RestAuthEntryPoint restAuthenticationEntryPoint) {
        this.userService = userService;
        this.jwtFilter = jwtFilter;
        this.passwordEncoder = passwordEncoder;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(this.passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final String unosenjeZahtjeva = "UNOSENJE_ZAHTJEVA";
        final String popunjavanjeUpitnika = "POPUNJAVANJE_UPITNIKA";
        final String dobavljanjeZahtjevaZaKorisnika = "DOBAVLJANJE_ZAHTJEVA_ZA_KORISNIKA";

        http = http.cors().and().csrf().disable();

        http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

        http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);

        http.authorizeRequests()

                .antMatchers(HttpMethod.POST, "/api/korisnici/prijava").permitAll()
                .antMatchers(HttpMethod.POST, "/api/detekcija-nepravilnosti").permitAll()
                .antMatchers(HttpMethod.POST, "/api/zahtjev-za-dozvolu").hasAuthority(unosenjeZahtjeva)
                .antMatchers(HttpMethod.GET, "/api/korisnici/{id}/odobreni-zahtjevi").hasAuthority(dobavljanjeZahtjevaZaKorisnika)
                .antMatchers(HttpMethod.GET, "/api/korisnici/{korisnikId}/odobreni-zahtjevi/{zahtjevId}").hasAuthority(dobavljanjeZahtjevaZaKorisnika)
                .antMatchers(HttpMethod.POST, "/api/korisnicki-upitnik").hasAuthority(popunjavanjeUpitnika)
                .antMatchers(HttpMethod.GET, "/api/kalibri").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(jwtFilter,
                BasicAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
