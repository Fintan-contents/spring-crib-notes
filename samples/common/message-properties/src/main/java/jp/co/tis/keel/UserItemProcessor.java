package jp.co.tis.keel;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class UserItemProcessor implements ItemProcessor<User, User> {

    @Autowired
    Validator validator;

    @Override
    public User process(final User user) throws Exception {

        final Set<ConstraintViolation<User>> results = validator.validate(user);
        if (!results.isEmpty()) {
            return null;
        }

        final User transformedUser = new User(
                user.getUserId(),
                user.getUserName() + "様");

        return transformedUser;
    }

}