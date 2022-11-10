package keel.aws.rds;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ExampleCommandLineRunner implements ApplicationRunner {
    
    private final UserService userService;

    public ExampleCommandLineRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("--------------------------------------------------");
        System.out.println("検索結果件数: " + userService.findAll().size());
        System.out.println("--------------------------------------------------");

        final UsersEntity entity = new UsersEntity("keel");
        final UsersEntity insertResult = userService.insert(entity);
        System.out.println("--------------------------------------------------");
        System.out.println("ID: [" + insertResult.id + "] を登録しました。");
        System.out.println("--------------------------------------------------");

        System.out.println("--------------------------------------------------");
        System.out.println("検索結果件数: " + userService.findAll().size());
        System.out.println("--------------------------------------------------");
    }
}
