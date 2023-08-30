package com.mindhub.homebankig.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter { // Maneja las reglas del servidor
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()// ordenado de lo mas accesible a lo menos o mas especifico de un cliente o admin
                .antMatchers("/web/index.html", "/web/img/", "/web/js/index.js","/web/css/style.css", "/web/accounts.html","/web/js/accounts.js", "/favicon.ico").permitAll()
                .antMatchers(HttpMethod.POST,"/api/clients","/api/login").permitAll()
                .antMatchers("/api/clients/current","/api/accounts/{id}","/api/clients","/web/cards.html", "/web/js/cards.js","/web/create-cards.html", "/web/js/create-cards.js").hasAnyAuthority("Admin", "CLIENT")
                .antMatchers("/h2-console/**", "/rest/**").hasAuthority("ADMIN");
                /*.antMatchers("/web/index.html", "/web/css/", "/web/img/", "/web/js/").permitAll()
                .antMatchers(HttpMethod.POST,"/api/login", "/api/logout","/api/clients").permitAll()
                .antMatchers("/web/admin").hasAuthority("ADMIN")
                .antMatchers("/api/clients", "/api/clients/").hasAnyAuthority("ADMIN","CLIENT")
                .antMatchers("/web/", "/web/css/").hasAnyAuthority("CLIENT", "ADMIN");*/

        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");

        // turn off checking for CSRF tokens
        http.csrf().disable();
        //disabling frameOptions so h2-console can be accessed
        http.headers().frameOptions().disable();
        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}