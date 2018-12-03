package keel.aws.ses;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ExampleCommandLineRunner implements CommandLineRunner {

    private final SimpleMailService simpleMailService;

    private final AttachmentMailService attachmentMailService;

    public ExampleCommandLineRunner(
            final SimpleMailService simpleMailService,
            final AttachmentMailService attachmentMailService) {
        this.simpleMailService = simpleMailService;
        this.attachmentMailService = attachmentMailService;
    }

    @Override
    public void run(final String... args) throws Exception {
        simpleMailService.sendMail();
        attachmentMailService.sendMail();
    }
}
