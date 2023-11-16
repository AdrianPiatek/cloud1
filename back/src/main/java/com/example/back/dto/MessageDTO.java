package com.example.back.dto;

import com.example.back.entity.MessageType;

public record MessageDTO<T>(MessageType messageType, T message) {
}
