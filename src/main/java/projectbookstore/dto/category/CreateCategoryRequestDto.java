package projectbookstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCategoryRequestDto {
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    private String description;
}
