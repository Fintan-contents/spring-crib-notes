package keel.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {

    @NotEmpty
    @Length(min = 8, max = 20)
    private String userId;
    @NotEmpty
    private String userName;
}
