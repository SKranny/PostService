package com.example.demo.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Schema(description = "Сообщение")
@Builder
public class PostDTO {
    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Время создания поста")
    private Date time;
    @Schema(description = "Идентификатор автора")
    private Long authorId;
    @Schema(description = "Заголовок")
    private String title;
    @Schema(description = "Текст поста")
    private String postText;
    @Schema(description = "Состояние блокировки поста")
    private Boolean isBlocked;
}
