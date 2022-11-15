package keel.aws.ses;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class AttachmentMailService {

    private final JavaMailSender javaMailSender;
    private final MailProperties mailProperties;

    public AttachmentMailService(JavaMailSender javaMailSender, MailProperties mailProperties) {
        this.javaMailSender = javaMailSender;
        this.mailProperties = mailProperties;
    }

    public void sendMail() {
        javaMailSender.send(mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            
            helper.setFrom(mailProperties.getFrom());
            helper.setTo(mailProperties.getTo());
            
            helper.setSubject("タイトル");
            helper.setText("本文");

            // この例では、クラスパス上にあるファイルを添付ファイルとして追加しています。
            ClassPathResource resource = new ClassPathResource("sample.txt");
            helper.addAttachment("sample.txt", resource);
        });
    }
}
