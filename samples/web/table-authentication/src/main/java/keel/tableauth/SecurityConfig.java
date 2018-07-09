package keel.tableauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// example-start
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    public SecurityConfig(final UserService service) {
        userService = service;
    }

    // role-start
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

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/admin/**")
            .hasRole("admin")
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            // usernameのパラメータ名を設定します。
            // この例の場合は、usernameParameterの呼び出しを省略した場合と同じ動作となります。
            // ログインフォームから送信するパラメータ名を変えたい場合は、usernameParameterにその値を設定してください。
            .usernameParameter("username")
            // passwordのパラメータ名を設定します。
            // この例の場合は、passwordParameterの呼び出しを省略した場合と同じ動作となります。
            // ログインフォームから送信するパラメータ名を変えたい場合は、、passwordParameterにその値を設定してください。
            .passwordParameter("password")
            .loginPage("/login")
            .permitAll()
            .defaultSuccessUrl("/top", true);
    }
    // role-end

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        // データベースのテーブルを使った認証を行うServiceを設定します。
        auth.userDetailsService(userService)
            .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
// example-end
