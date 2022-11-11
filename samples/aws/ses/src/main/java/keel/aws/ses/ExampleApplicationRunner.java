package keel.aws.ses;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ExampleApplicationRunner implements ApplicationRunner {

    private final SimpleMailService simpleMailService;

    private final AttachmentMailService attachmentMailService;

    public ExampleApplicationRunner(SimpleMailService simpleMailService, AttachmentMailService attachmentMailService) {
        this.simpleMailService = simpleMailService;
        this.attachmentMailService = attachmentMailService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        simpleMailService.sendMail();
        attachmentMailService.sendMail();
    }
}
