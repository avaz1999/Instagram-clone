//package uz.avaz.instagramclone.config;
//
//import uz.avaz.instagramclone.security.JwtAuthenticationEntryPoint;
//import uz.avaz.instagramclone.security.JwtAuthenticationFilter;
//import uz.avaz.instagramclone.service.impl.UserDetailsServiceImpl;
//import org.modelmapper.ModelMapper;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private UserDetailsServiceImpl userDetailsService;
//    private JwtAuthenticationEntryPoint authenticationEntryPoint;
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    public WebSecurityConfig(@Lazy UserDetailsServiceImpl userDetailsServiceImpl,
//                             @Lazy JwtAuthenticationFilter jwtFilter,
//                             JwtAuthenticationEntryPoint authenticationEntryPoint) {
//        this.userDetailsService = userDetailsServiceImpl;
//        this.jwtAuthenticationFilter = jwtFilter;
//        this.authenticationEntryPoint = authenticationEntryPoint;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/api/auth/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
//
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//
//    @Bean
//    public ModelMapper modelMapper() {
//        return new ModelMapper();
//    }
//
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(getDaoAuthenticationProvider());
//    }
//
//    /**
//     * AUTHENTICATION MANAGER FOR LATER USE, TO AUTHENTICATE USERS USING USERNAME PASSWORD AUTHENTICATION TOKEN
//     */
//    @Override
//    @Bean("authenticationManager")
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Bean(name = "daoAuthenticationProvider")
//    public DaoAuthenticationProvider getDaoAuthenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//        daoAuthenticationProvider.setPasswordEncoder(getBcryptPasswordEncoder());
//        return daoAuthenticationProvider;
//    }
//
//    @Bean(name = "bcryptPasswordEncoder")
//    public PasswordEncoder getBcryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean(name = "modelMapper")
//    public ModelMapper getModelMapper() {
//        return new ModelMapper();
//    }
//
//}
