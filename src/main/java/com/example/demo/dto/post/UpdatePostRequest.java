package com.example.demo.dto.post;

import com.example.demo.dto.tag.UpdateTagRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostRequest {

    @NotNull
    private Long postId;

    @NotBlank
    private String title;

    @NotBlank
    private String postText;

    private List<UpdateTagRequest> updateTagsRequests;

    @Builder.Default
    private Boolean withFriends = false;

    @Builder.Default
    private Boolean isBlocked = false;
}
