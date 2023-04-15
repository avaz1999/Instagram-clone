package uz.avaz.instagramclone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {

    @Bean
    public AuditorAwareImpl auditorProvider(){
        return new AuditorAwareImpl();
    }
}
