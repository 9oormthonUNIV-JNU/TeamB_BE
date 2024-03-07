package com.example.gifty.service;

import com.example.gifty.dto.history.HistoryResponseDTO;
import com.example.gifty.entity.History;
import com.example.gifty.repository.HistoryJPARepository;
import com.example.gifty.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class HistoryService {
    private final HistoryJPARepository historyJPARepository;

    public List<HistoryResponseDTO.HistoryDTO> findHistory(int page, CustomUserDetails userDetails) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<History> pageContent = historyJPARepository.findAllByUserOrderByModifiedDate(userDetails.getUser().getId(), pageable);

        List<HistoryResponseDTO.HistoryDTO> responseDTOs = pageContent.getContent().stream()
                .map(HistoryResponseDTO.HistoryDTO::new)
                .toList();

        return responseDTOs;
    }
}
