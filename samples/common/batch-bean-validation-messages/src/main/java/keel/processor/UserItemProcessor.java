package keel.processor;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import keel.entity.User;

@Component
public class UserItemProcessor implements ItemProcessor<User, User> {

    private final Validator validator;

    private final Logger logger = LoggerFactory.getLogger(UserItemProcessor.class);

    public UserItemProcessor(Validator validator) {
        this.validator = validator;
    }

    @Override
    public User process(final User user) throws Exception {
        final Set<ConstraintViolation<User>> results = validator.validate(user);
        if (!results.isEmpty()) {
            results.forEach(this::writeValidationErrorLog);
            return null;
        }
        return new User(
                user.getUserId(), user.getUserName() + '様');
    }

    private void writeValidationErrorLog(final ConstraintViolation<User> constraintViolation) {
        logger.warn("column name：{}, error message：{}",
                constraintViolation.getPropertyPath(),
                constraintViolation.getMessage()
        );
    }

}