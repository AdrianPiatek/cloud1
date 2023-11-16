package com.example.back.utility;

import com.example.back.dto.ErrorDTO;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class BidingResultError {
    public static List<ErrorDTO> getListErrorDTO(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .map(ErrorDTO::new).toList();
    }
}
