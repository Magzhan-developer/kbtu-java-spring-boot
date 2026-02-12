package kbtu.lab.sis3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class CreateProductDto {
    @JsonProperty("name")
    private String productName;

    @Nullable
    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("price")
    private double productPrice;
}
