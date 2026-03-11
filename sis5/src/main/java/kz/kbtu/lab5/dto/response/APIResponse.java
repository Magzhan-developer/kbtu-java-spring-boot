package kz.kbtu.lab5.dto.response;

import kz.kbtu.lab5.constants.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse<T> {
    private Status status;
    private String message;
    private T data;
}
