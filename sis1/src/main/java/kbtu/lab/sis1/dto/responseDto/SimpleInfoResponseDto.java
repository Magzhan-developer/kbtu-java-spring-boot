package kbtu.lab.sis1.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class SimpleInfoResponseDto {
    @JsonProperty("student")
    private String studentFullName;

    @JsonProperty("course")
    private String courseName;

    private int week;
}
