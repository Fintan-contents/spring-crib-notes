package keel.aws.ses;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class SimpleMailService {
    
    private final MailSender mailSender;
    private final MailProperties mailProperties;

    public SimpleMailService(final MailSender mailSender, final MailProperties mailProperties) {
        this.mailSender = mailSender;
        this.mailProperties = mailProperties;
    }

    public void sendMail() {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getFrom());
        message.setTo(mailProperties.getTo());
        message.setSubject("title");
        message.setText("本文");
        mailSender.send(message);
    }

}
