package uz.avaz.instagramclone.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MailService {

    @Value("{mail.from}")
    private String from;

    private final JavaMailSender mailSender;

    public void sendSimpleMailMessage(String to, String subject, String text) {
        Runnable runnable = () -> {
//            try {
//                MimeMessageHelper helper = prepareMimeMessageHelper(from, to, subject, text);
//                mailSender.send(helper.getMimeMessage());
//            } catch (MessagingException e) {
//                e.printStackTrace();
//            }
        };
        runnable.run();
    }

    private MimeMessageHelper prepareMimeMessageHelper(String from, String to, String subject, String text) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        messageHelper.setFrom(from);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(text);
        return messageHelper;
    }
}
