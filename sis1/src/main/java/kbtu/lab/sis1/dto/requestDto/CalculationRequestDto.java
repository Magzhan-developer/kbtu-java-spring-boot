package kbtu.lab.sis1.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.NotNull;
import kbtu.lab.sis1.constants.CalculationMethods;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CalculationRequestDto {

    @JsonProperty("a")
    @NotNull(message = "Первое число не может быть null")
    private Long firstNumber;

    @JsonProperty("b")
    @NotNull(message = "Второе число не может быть null")
    private Long secondNumber;

    private CalculationMethods method = CalculationMethods.ADD;

    @JsonSetter(nulls = Nulls.SKIP)
    public void setMethod(CalculationMethods method){
        if(method != null){
            this.method = method;
        }
    }

    public void validate(){
        if(method == CalculationMethods.DIVIDE && secondNumber != null && secondNumber == 0){
            throw new IllegalArgumentException("You can not divide first number to 0");
        }
    }
}
