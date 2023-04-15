package uz.avaz.instagramclone.config;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean(name = "javaMailSender")
    public JavaMailSenderImpl getJavaMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();

        sender.setHost("smtp.gmail.com");
        sender.setPort(587);

        sender.setUsername("avaz.faang.dev@gmail.com");
        sender.setPassword("bwesmbhhadnouela");
        sender.setDefaultEncoding("UTF-8");

        Properties props = sender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return sender;
    }

    @Bean
    public EmailValidator getEmailValidator(){
        return EmailValidator.getInstance();
    }
}
