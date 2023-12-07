package pg.provingground.security;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import pg.provingground.domain.User;
import pg.provingground.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    // filterChan에서 변경???아니잔아
    @Bean
    public SecurityFilterChain filterChan(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests()
                // 해당 페이지의 모든 요청(get, post..)에 대해 해당 권한을 갖고 있어야 접근 가능하게 함.
                // 즉, 인증이 필요한 페이지 나열
                .requestMatchers("/css/**","/js/**","/icons/**","/images/**").permitAll()
                .requestMatchers("/", "/car-rental/new**", "/ground-rental/new**").permitAll()
                .requestMatchers("/login", "/join", "/logout").permitAll()
                // 특정 URI를 제외한 나머지 URI 인가
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login")
                .and()
                /*
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                 */
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")) //and()
                .build();

    }

    /*
    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
     */

}