package com.example.gifty.service;

import com.example.gifty.dto.funding.FundingRequestDTO;
import com.example.gifty.dto.funding.FundingResponseDTO;
import com.example.gifty.entity.*;
import com.example.gifty.exception.ErrorCode;
import com.example.gifty.exception.FundingNotExistException;
import com.example.gifty.exception.ProductNotExistException;
import com.example.gifty.exception.UserNotExistException;
import com.example.gifty.repository.FriendJPARepository;
import com.example.gifty.repository.FundingJPARepository;
import com.example.gifty.repository.ProductJPARepository;
import com.example.gifty.repository.UserJPARepository;
import com.example.gifty.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class FundingService {
    private final UserService userService;

    private final UserJPARepository userJPARepository;
    private final FriendJPARepository friendJPARepository;
    private final ProductJPARepository productJPARepository;
    private final FundingJPARepository fundingJPARepository;

    public void createFunding(FundingRequestDTO.FundingDTO requestDTO, CustomUserDetails userDetails) throws Exception {
        User user = userJPARepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new UserNotExistException(ErrorCode.USER_NOT_EXIST));

        Product product = productJPARepository.findById(requestDTO.getProductId())
                .orElseThrow(() -> new ProductNotExistException(ErrorCode.PRODUCT_NOT_EXIST));

        Funding newFunding = Funding.builder()
                .user(user)
                .product(product)
                .event(requestDTO.getEvent())
                .startDate(requestDTO.getStartDate())
                .endDate(requestDTO.getEndDate())
                .state(State.Ongoing)
                .message(requestDTO.getMessage())
                .totalAmount(0)
                .build();

        fundingJPARepository.save(newFunding);
    }

    public List<FundingResponseDTO.FundingDTO> findFriendFundingList(CustomUserDetails userDetails) {
        User currentUser = userJPARepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new UserNotExistException(ErrorCode.USER_NOT_EXIST));

        List<Friend> currentFriendList = userService.getUserFriendList(currentUser);
        List<Friend> savedFriendList = friendJPARepository.findAllByUser(currentUser.getId());
        List<Friend> notSavedFriendList = currentFriendList.stream()
                .filter(currentFriend -> savedFriendList.stream().noneMatch(savedFrient -> Objects.equals(savedFrient.getKakaoId(), currentFriend.getKakaoId())))
                .toList();

        friendJPARepository.saveAll(notSavedFriendList);

        List<FundingResponseDTO.FundingDTO> responseDTOs = new ArrayList<>();

//        List<User> friendAndUserList = userJPARepository.findAll().stream()
//                .filter(user -> currentFriendList.stream().anyMatch(friend -> user.getKakaoId() == friend.getKakaoId()))
//                .toList();

        for (Friend friend: currentFriendList) {
            FundingResponseDTO.FundingDTO responseDTO;
            Optional<User> friendAndUser = userJPARepository.findByKakaoId(friend.getKakaoId());
            if (friendAndUser.isEmpty()) {
                responseDTO = new FundingResponseDTO.FundingDTO(friend);
            } else {
                Optional<Funding> funding = fundingJPARepository.findByUserAndState(friendAndUser.get().getId(), State.Ongoing);
                responseDTO = funding
                        .map(f -> new FundingResponseDTO.FundingDTO(friend, f))
                        .orElseGet(() -> new FundingResponseDTO.FundingDTO(friend));
            }
            responseDTOs.add(responseDTO);
        }

        return responseDTOs;
    }

    public FundingResponseDTO.FundingDetailDTO findFriendFunding(int id) {
        Funding funding = fundingJPARepository.findById(id)
                .orElseThrow(() -> new FundingNotExistException(ErrorCode.FUNDING_NOT_EXIST));

        return new FundingResponseDTO.FundingDetailDTO(funding);
    }
}
