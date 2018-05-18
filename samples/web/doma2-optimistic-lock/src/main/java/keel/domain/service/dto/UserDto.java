package keel.domain.service.dto;

import lombok.Value;

@Value
public class UserDto {

    private Long userId;
    private String userName;
    private Long versionNo;
}
