package com.example.gifty.security;

import com.example.gifty.entity.User;
import com.example.gifty.exception.ErrorCode;
import com.example.gifty.exception.UserNotExistException;
import com.example.gifty.repository.UserJPARepository;
import com.example.gifty.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserJPARepository userJPARepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userJPARepository.findByEmail(email)
                .orElseThrow(() -> new UserNotExistException(ErrorCode.USER_NOT_EXIST));
        return new CustomUserDetails(user);
    }
}
