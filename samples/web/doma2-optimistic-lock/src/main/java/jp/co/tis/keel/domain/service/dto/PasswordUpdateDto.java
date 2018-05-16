package jp.co.tis.keel.domain.service.dto;

import lombok.Value;

@Value
public class PasswordUpdateDto {

    private Long userId;
    private String userName;
    private Long versionNo;
}
