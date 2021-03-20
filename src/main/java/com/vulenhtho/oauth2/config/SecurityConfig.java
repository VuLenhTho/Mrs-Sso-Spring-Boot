package com.vulenhtho.oauth2.config;

import com.vulenhtho.oauth2.security.CustomSuccessHandler;
import com.vulenhtho.oauth2.security.Oauth2CustomSuccessHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.client.RestTemplate;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(a -> a
                        .antMatchers("/admin/**").hasAnyRole("ADMIN")
                        .antMatchers("/sale/**").hasAnyRole("SALE")
                        .anyRequest().permitAll()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .csrf(c -> c
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .logout(l -> l
                        .logoutSuccessUrl("/").permitAll()
                )
                .oauth2Login(o -> o
                        .loginPage("/login")
                        .successHandler(new Oauth2CustomSuccessHandler(new RestTemplate()))
                )
                .formLogin(f -> f
                        .loginPage("/login")
                        .usernameParameter("userName")
                        .passwordParameter("password")
                        .loginProcessingUrl("/form-login")
                        .successHandler(new CustomSuccessHandler())
                        .failureUrl("/login")
                )
                .addFilterBefore(new EncodingFilter(), ChannelProcessingFilter.class);
    }
}
