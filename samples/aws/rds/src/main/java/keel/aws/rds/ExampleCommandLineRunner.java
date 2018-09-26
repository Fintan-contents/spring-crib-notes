package keel.aws.rds;

import org.seasar.doma.jdbc.Result;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ExampleCommandLineRunner implements CommandLineRunner {
    
    private final UserService userService;

    public ExampleCommandLineRunner(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(final String... args) throws Exception {
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
