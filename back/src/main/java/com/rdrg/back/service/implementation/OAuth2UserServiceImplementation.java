package com.rdrg.back.service.implementation;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.rdrg.back.common.object.CustomOAuth2User;
import com.rdrg.back.entity.EmailAuthNumberEntity;
import com.rdrg.back.entity.UserEntity;
import com.rdrg.back.repository.EmailAuthNumberRepository;
import com.rdrg.back.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImplementation extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final EmailAuthNumberRepository emailAuthNumberRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        String oauthClientName = userRequest.getClientRegistration().getClientName().toUpperCase();
        String id = getId(oAuth2User, oauthClientName);
        String userId = oauthClientName + "_" + id.substring(0, 10);

        boolean isExistUser = userRepository.existsByUserId(userId);
        if (!isExistUser) {
            String email = id + "@" + oauthClientName.toLowerCase() + ".com";
            String password = passwordEncoder.encode(id);

            EmailAuthNumberEntity emailAuthNumberEntity = new EmailAuthNumberEntity(email, "0000");
            emailAuthNumberRepository.save(emailAuthNumberEntity);

            UserEntity userEntity = new UserEntity(userId, password, email, "ROLE_USER", oauthClientName);
            userRepository.save(userEntity);
            }
        return new CustomOAuth2User(userId, oAuth2User.getAttributes());
    }

        private String getId(OAuth2User oAuth2User, String oauthClientName) {
            String id =null;
            
            if (oauthClientName.equals("KAKAO")) {
                Long longId =  (Long) oAuth2User.getAttributes().get("id");
                id = longId.toString();
            }
            if (oauthClientName.equals("NAVER")) {
                Map<String, String> response = (Map<String, String>) oAuth2User.getAttributes().get("response");
                id = response.get("id");
            }
        return id;
    }
}
