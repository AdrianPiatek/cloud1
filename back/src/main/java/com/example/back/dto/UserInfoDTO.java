package com.example.back.dto;

import com.example.back.entity.GameResult;

import java.util.Map;

public record UserInfoDTO(String username, Map<GameResult, Long> userResults) {
}
