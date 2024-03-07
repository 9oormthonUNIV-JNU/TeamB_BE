package com.example.gifty.controller;

import com.example.gifty.ApiResponse;
import com.example.gifty.dto.history.HistoryResponseDTO;
import com.example.gifty.security.CustomUserDetails;
import com.example.gifty.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class HistoryRestController {
    private final HistoryService historyService;

    @GetMapping("/histories")
    public ResponseEntity<?> getHistory(@RequestParam(value = "page", defaultValue = "0") Integer page, CustomUserDetails userDetails) throws Exception {
        List<HistoryResponseDTO.HistoryDTO> responseDTOs = historyService.findHistory(page, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDTOs));
    }
}
