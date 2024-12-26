package com.example.Control;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    String error;
    String message;
    HttpStatus status;
    MediaType contentType;
}
