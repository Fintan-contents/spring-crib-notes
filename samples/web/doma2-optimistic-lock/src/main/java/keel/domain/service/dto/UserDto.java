package keel.domain.service.dto;

public class UserDto {

    private final Long userId;
    private final String userName;
    private final Long versionNo;

    public UserDto(Long userId, String userName, Long versionNo) {
        this.userId = userId;
        this.userName = userName;
        this.versionNo = versionNo;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getVersionNo() {
        return versionNo;
    }
}
