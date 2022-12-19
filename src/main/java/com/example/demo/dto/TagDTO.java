package com.example.demo.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data

@Schema(description = "Тэг поста")
public class TagDTO {
    @Schema(description = "Идентификатор поста")
    private Long id;
    @Schema(description = "Тэг")
    private String tag;
}
