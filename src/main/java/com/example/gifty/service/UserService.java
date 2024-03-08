package com.example.gifty.service;

import com.example.gifty.entity.Friend;
import com.example.gifty.entity.Funding;
import com.example.gifty.entity.State;
import com.example.gifty.exception.FundingNotExistException;
import com.example.gifty.repository.FundingJPARepository;
import com.example.gifty.security.CustomUserDetails;
import com.example.gifty.security.JWTTokenProvider;
import com.example.gifty.dto.user.UserRequestDTO;
import com.example.gifty.dto.user.UserResponseDTO;
import com.example.gifty.entity.User;
import com.example.gifty.exception.ErrorCode;
import com.example.gifty.exception.UserNotExistException;
import com.example.gifty.repository.UserJPARepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.RedirectView;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
    private final UserJPARepository userJPARepository;
    private final FundingJPARepository fundingJPARepository;

    @Value("${kakao.rest-api-key}")
    private String kakaoRestApiKey;

    @Value("${kakao.admin-key}")
    private String kakaoAdminKey;

    @Value("${kakao.authorize-uri}")
    private String authorizeUri;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.authorize-scope}")
    private String authorizeScope;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoUri;

    @Value("${kakao.user-friend-list-uri}")
    private String userFriendListUri;

    private final JWTTokenProvider jwtTokenProvider;

    private HashMap<String, String> tokenInfo;

//    public RedirectView getKakaoAuthorizeCode() throws Exception {
////        URI uri = UriComponentsBuilder
////                .fromUriString("https://kauth.kakao.com")
////                .path("/oauth/authorize")
////                .queryParam("client_id", kakaoRestApiKey)
////                .queryParam("redirect_uri", kakaoRedirectUri)
////                .queryParam("response_type", "code")
////                .queryParam("scope", "profile_nickname,profile_image,account_email,friends")
////                .build()
////                .toUri();
//
////        RestTemplate restTemplate = new RestTemplate();
////        String response = restTemplate.getForObject(uri, String.class);
////        return response;
//
//        String uri = authorizeUri + "?client_id=" + kakaoRestApiKey + "&redirect_uri=" + redirectUri + "&response_type=code&scope=" + authorizeScope;
//        return new RedirectView(uri);
//    }

    public UserResponseDTO.KakaoLoginDTO kakaoLogin(String code, HttpServletRequest request) throws Exception {
        tokenInfo = getKakaoToken(code, request);
        UserRequestDTO.KakaoLoginDTO kakaoUserDTO = getKakaoUser();
        Optional<User> user = userJPARepository.findByEmail(kakaoUserDTO.getEmail());

        if (user.isEmpty()) {
            join(kakaoUserDTO);
        }

        UserResponseDTO.KakaoLoginDTO responseDTO = login(kakaoUserDTO);

        return responseDTO;
    }

    private HashMap<String, String> getKakaoToken(String code, HttpServletRequest request) {
        HashMap<String, String> tokenInfo = new HashMap<String, String>();

//        redirectUri = getClientIp(request) + "/auth";

        String requestURL = tokenUri;

        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code")
                    .append("&client_id=").append(kakaoRestApiKey)
                    .append("&redirect_uri=").append(redirectUri)
                    .append("&code=").append(code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = connection.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body: " + result);

            JsonElement element = JsonParser.parseString(result.toString());

            String token_type = element.getAsJsonObject().get("token_type").getAsString();
            String access_token = element.getAsJsonObject().get("access_token").getAsString();
            String scope = element.getAsJsonObject().get("scope").getAsString();
//            refresh_token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token: " + access_token);
//            System.out.println("refresh_token: " + refresh_token);

            tokenInfo.put("token_type", token_type);
            tokenInfo.put("access_token", access_token);
            tokenInfo.put("scope", scope);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tokenInfo;
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-RealIP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private UserRequestDTO.KakaoLoginDTO getKakaoUser() {
        UserRequestDTO.KakaoLoginDTO kakaoUser = new UserRequestDTO.KakaoLoginDTO();

        String requestURL = userInfoUri;

        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization",  "Bearer " + tokenInfo.get("access_token"));

            int responseCode = connection.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body: " + result);

            JsonElement element = JsonParser.parseString(result.toString());

            Long kakaoId = element.getAsJsonObject().get("id").getAsLong();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            String email = kakao_account.get("email").getAsString();
            String nickname = kakao_account.get("profile").getAsJsonObject().get("nickname").getAsString();
            String profileImage = kakao_account.get("profile").getAsJsonObject().get("profile_image_url").getAsString();

            kakaoUser.setKakaoId(kakaoId);
            kakaoUser.setEmail(email);
            kakaoUser.setNickname(nickname);
            kakaoUser.setProfileImage(profileImage);

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return kakaoUser;
    }

    @Transactional
    protected void join(UserRequestDTO.KakaoLoginDTO requestDTO) throws Exception {
        userJPARepository.save(requestDTO.toEntity());
    }

    private UserResponseDTO.KakaoLoginDTO login(UserRequestDTO.KakaoLoginDTO requestDTO) {
        User user = userJPARepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new UserNotExistException(ErrorCode.USER_NOT_EXIST));
        UserResponseDTO.TokenDTO tokenDTO = jwtTokenProvider.createToken(user);

        return new UserResponseDTO.KakaoLoginDTO(tokenDTO, user);
    }


    public List<Friend> getUserFriendList(User user) {
        List<Friend> friendList = new ArrayList<>();

        String requestURL = userFriendListUri;

        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Bearer " + tokenInfo.get("access_token"));

            int responseCode = connection.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body: " + result);

            JsonElement element = JsonParser.parseString(result.toString());

            JsonArray elements = (JsonArray) element.getAsJsonObject().get("elements");
            for (Object e : elements) {
                JsonObject obj = (JsonObject) e;
                Long kakaoId = obj.get("id").getAsLong();
                String kakaoUuid = obj.get("uuid").getAsString();
                String nickname = obj.get("profile_nickname").getAsString();
                String profileImage = obj.get("profile_thumbnail_image").getAsString();

                Friend newFriend = Friend.builder()
                        .user(user)
                        .kakaoId(kakaoId)
                        .kakaoUuid(kakaoUuid)
                        .nickname(nickname)
                        .profileImage(profileImage)
                        .build();
                friendList.add(newFriend);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return friendList;
    }

    public UserResponseDTO.MyPageDTO findMyPage(CustomUserDetails userDetails) {
        Funding funding = fundingJPARepository.findByUserAndState(userDetails.getUser().getId(), State.ONGOING)
                .orElseThrow(() -> new FundingNotExistException(ErrorCode.FUNDING_NOT_EXIST));
        return new UserResponseDTO.MyPageDTO(userDetails.getUser(), funding);
    }
}
