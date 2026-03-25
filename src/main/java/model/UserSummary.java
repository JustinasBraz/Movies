package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSummary {
    private long totalUsers;
    private long adminCount;
    private long managerCount;
    private long userCount;
}
