package ee.ituk.api.config;

import ee.ituk.api.config.security.JwtAuthenticationEntryPoint;
import ee.ituk.api.config.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] LET_THEM_THROUGH = {
            "/login/**", "/actuator/**", "/swagger-ui.html", "/swagger-resources/**",
            "/webjars/**", "/v2/**", "/user/birthdays", "/user/count", "/project",
            "/application/**"
    };
    private static final String[] ALLOW_ONLY_GET = { "/mentor/active", "/mentor/{\\d+}", "/mentor/{\\d+}/picture" };
    private static final String[] DONT_LET_THEM_IN = {"/**", "/resources/**"};

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @Resource
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(LET_THEM_THROUGH).permitAll()
                .antMatchers(HttpMethod.GET, ALLOW_ONLY_GET).permitAll()
                .antMatchers(DONT_LET_THEM_IN).authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().maximumSessions(1);
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

    /**
     * Configuring propagation of the Spring Security principal with @Async
     *
     * @return MethodInvokingFactoryBean
     */
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
        methodInvokingFactoryBean.setTargetMethod("setStrategyName");
        methodInvokingFactoryBean.setArguments(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        return methodInvokingFactoryBean;
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
