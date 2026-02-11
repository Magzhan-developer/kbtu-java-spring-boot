package kbtu.lab.sis1.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CalculationMethods  {
    ADD("add"),
    MINUS("minus"),
    MULTIPLY("multiply"),
    DIVIDE("divide");

    private final String method;
}
