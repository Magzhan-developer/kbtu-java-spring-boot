package kbtu.lab.sis1.controllers;

import jakarta.validation.Valid;
import kbtu.lab.sis1.dto.requestDto.CalculationRequestDto;
import kbtu.lab.sis1.dto.responseDto.CalculationResponseDto;
import kbtu.lab.sis1.dto.responseDto.SimpleInfoResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SimpleController {

    @GetMapping("/info")
    public ResponseEntity<SimpleInfoResponseDto> info() {
        SimpleInfoResponseDto responseDto = SimpleInfoResponseDto.builder()
                .studentFullName("Magzhan Dautbekov")
                .courseName("Spring Boot")
                .week(1)
                .build();

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/calculate")
    public ResponseEntity<CalculationResponseDto> calculate(@Valid @RequestBody CalculationRequestDto body){
        body.validate();

        long result;
        switch (body.getMethod()){
            case ADD -> result = body.getFirstNumber() + body.getSecondNumber();
            case MINUS -> result = body.getFirstNumber() - body.getSecondNumber();
            case MULTIPLY -> result = body.getFirstNumber() * body.getSecondNumber();
            case DIVIDE -> result = body.getFirstNumber() / body.getSecondNumber();
            default -> throw new IllegalStateException("Неизвестный метод");
        }

        return ResponseEntity.ok(new CalculationResponseDto(result));
    }
}
