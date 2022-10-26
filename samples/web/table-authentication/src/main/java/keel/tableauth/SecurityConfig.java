package keel.tableauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// example-start
@Configuration
public class SecurityConfig {

    // role-start
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz
                .antMatchers("/admin/**")
                .hasRole("admin")
                .anyRequest()
                .authenticated()
        ).formLogin(login -> login
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/top", true)
        ).logout(logout -> logout
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/login?logout")
                .permitAll()
        );

        return http.build();
    }

    @Bean
    public RoleHierarchyVoter roleHierarchyVoter() {
        return new RoleHierarchyVoter(roleHierarchy());
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        // 権限の階層構造の設定をします。
        // admin権限は、user権限を含む権限となります。
        final RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }
    // role-end

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
// example-end
