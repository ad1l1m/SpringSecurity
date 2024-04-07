package adil.spring.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReminderUser {
    private String nameTask;
    private int hour;
    private int minute;
    private int second;

}
