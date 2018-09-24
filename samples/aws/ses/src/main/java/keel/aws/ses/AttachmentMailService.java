package keel.aws.ses;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class AttachmentMailService {

    private final JavaMailSender javaMailSender;
    private final MailProperties mailProperties;

    public AttachmentMailService(final JavaMailSender javaMailSender, final MailProperties mailProperties) {
        this.javaMailSender = javaMailSender;
        this.mailProperties = mailProperties;
    }

    public void sendMail() {
        javaMailSender.send(mimeMessage -> {
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            
            helper.setFrom(mailProperties.getFrom());
            helper.setTo(mailProperties.getTo());
            
            helper.setSubject("タイトル");
            helper.setText("本文");

            // この例では、クラスパス上にあるファイルを添付ファイルとして追加しています。
            final ClassPathResource resource = new ClassPathResource("sample.txt");
            helper.addAttachment("sample.txt", resource);
        });
    }
}
