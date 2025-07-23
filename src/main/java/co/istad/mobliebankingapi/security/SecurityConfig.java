package co.istad.mobliebankingapi.security;


import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{noop}admin@098")
//                .roles("ADMIN")
//                .build();
//        manager.createUser(admin);
//
//        UserDetails staff = User.builder()
//                .username("staff")
//                .password("{noop}staff@123}")
//                .roles("STAFF")
//                .build();
//        manager.createUser(staff);
//
//        UserDetails customer = User.builder()
//                .username("customer")
//                .password("{noop}customer@222")
//                .roles("CUSTOMER")
//                .build();
//        manager.createUser(customer);
//
//        return manager;
//    }

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    @Bean
    public DaoAuthenticationProvider daoAuthProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthProvider.setPasswordEncoder(passwordEncoder);

        return daoAuthProvider;

    }

    @Bean
    public SecurityFilterChain apiSecurity(HttpSecurity http)throws Exception {
//        all request must be authenticated
        http.authorizeHttpRequests(requests
                ->requests
                .requestMatchers(HttpMethod.POST,"/api/v1/customers/**")
                .hasAnyRole("ADMIN","STAFF")

                .requestMatchers(HttpMethod.PUT,"/api/v1/customers/**")
                .hasAnyRole("ADMIN")

                .requestMatchers(HttpMethod.DELETE,"/api/v1/customers/**")
                .hasAnyRole("ADMIN")

                .requestMatchers(HttpMethod.GET,"/api/v1/customers/**")
                .hasAnyRole("ADMIN","STAFF","CUSTOMER")

                .requestMatchers("/api/v1/accounts/**")
                .hasAnyRole("USER")
                .anyRequest().authenticated());

//        disable form login default
        http.formLogin(form->form.disable());
        http.csrf(token
                ->token.disable());

//        set security mechanism
//        basic Authentication(user & password)
        http.httpBasic(Customizer.withDefaults());

//        set session to stateless
        http.sessionManagement(session
                ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
