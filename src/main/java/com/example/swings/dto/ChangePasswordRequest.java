package com.example.swings.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String currentPassword; // 현재 비밀번호
    private String newPassword; // 새로운 비밀번호
    private String userId; // 사용자 ID
}
