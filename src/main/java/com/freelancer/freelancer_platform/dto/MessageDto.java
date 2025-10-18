package com.freelancer.freelancer_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDto {
    private Long receiverId;
    private String content;
}
