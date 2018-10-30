package tr.edu.uludag.webprogramlama.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import tr.edu.uludag.webprogramlama.security.ActiveDirectoryPreAuthenticationFilter;
import tr.edu.uludag.webprogramlama.security.AjaxLogoutHandler;
import tr.edu.uludag.webprogramlama.security.ApiUserDetailsService;
import tr.edu.uludag.webprogramlama.security.Http200AuthenticationSuccessHandler;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private ApiUserDetailsService apiUserDetailsService;


    /**
     * API userların şifreleri veritabanında açık halde kesinlikle bulunmamalıdır.
     * Bu şifreler oluşturulurken BCryptPasswordEncoder ile encode edilip kaydedilmelidir.
     * Bu bean ile gelen şifreyi kontrol ederken BCryptPasswordEncoder a göre kontrol yapılır.
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(apiUserDetailsService);
    }

    @Configuration
    @Order(2)
    @Lazy
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private ActiveDirectoryPreAuthenticationFilter activeDirectoryPreAuthenticationFilter;
        @Autowired
        private Http200AuthenticationSuccessHandler http200AuthenticationSuccessHandler;
        @Autowired
        private AuthenticationEntryPoint http401UnauthorizedEntryPoint;
        @Autowired
        private PasswordEncoder passwordEncoder;


        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/resources/**", "/favicon.gif");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            /* TODO /ogrenci/notgor.html ve detay sayfalarına hocalar de girebilsin diye kural konulmuştur
            Bu aslında öğrenci sayfasına hoca almaktır bunun yerine ayrı sayfalar yapıp içerik paylşmalıyız.
            */
            http.exceptionHandling()
                    .authenticationEntryPoint(http401UnauthorizedEntryPoint)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/html-parts/**").permitAll()
                    .antMatchers("/urunler/**").permitAll()
                    .anyRequest().denyAll()
                    .and()
                    .addFilter(activeDirectoryPreAuthenticationFilter);
            http.formLogin().successHandler(http200AuthenticationSuccessHandler);
            http.logout()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(new AjaxLogoutHandler())
                    .deleteCookies("JSESSIONID")
                    .permitAll();
            http.requiresChannel().antMatchers("/login**,/user**").requiresSecure();
        }

    }

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/api/mgmt/**")
                    .authorizeRequests()
                    .antMatchers("/api/mgmt/**").hasRole("MGMT");
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.httpBasic();
        }
    }


}
