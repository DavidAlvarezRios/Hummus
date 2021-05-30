package ub.dalvarezrios.hummus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import ub.dalvarezrios.hummus.auth.handler.LoginSuccessHandler;
import ub.dalvarezrios.hummus.models.VBoxManager;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.regex.Pattern;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfigurer extends WebSecurityConfigurerAdapter {

    protected final Log _logger = LogFactory.getLog(this.getClass());

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    protected void configurerGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(encoder)
                .usersByUsernameQuery("select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) " +
                        "where u.username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/index", "/register", "/about", "/resources/**", "/dhcp/**").permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .successHandler(loginSuccessHandler)
                    .defaultSuccessUrl("/exercises")
                    .permitAll()
                .and()
                    .logout().permitAll()
                .and()
                    .exceptionHandling().accessDeniedPage("/error_403")
                .and()
                    .authorizeRequests().antMatchers("/mv", "/tunnel?write**", "/tunnel?read**", "/tunnel?connect**").hasAnyRole("USER", "ADMIN")
                .and()
                    .authorizeRequests().antMatchers("/admin/","/admin/**").hasRole("ADMIN")
                .and()
                    .csrf()
                        .requireCsrfProtectionMatcher(new RequestMatcher() {
                            private final Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
                            private final RegexRequestMatcher guacamoleMatcher = new RegexRequestMatcher("/tunnel.*", null);

                            @Override
                            public boolean matches(HttpServletRequest request) {
                                // Disable csrf in allowed methods
                                if(allowedMethods.matcher(request.getMethod()).matches()) {
                                    return false;
                                }
                                // No csrf due to connection with guacamole
                                if(guacamoleMatcher.matches(request)){
                                    return false;
                                }
                                return true;
                            }
                        })
                .and()
                    .httpBasic();

    }

}
