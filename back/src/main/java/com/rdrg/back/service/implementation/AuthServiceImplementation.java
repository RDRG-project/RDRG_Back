package com.rdrg.back.service.implementation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rdrg.back.common.util.EmailAuthNumberUtil;
import com.rdrg.back.dto.request.auth.EmailAuthCheckRequestDto;
import com.rdrg.back.dto.request.auth.EmailAuthRequestDto;
import com.rdrg.back.dto.request.auth.IdCheckRequestDto;
import com.rdrg.back.dto.request.auth.SignInRequestDto;
import com.rdrg.back.dto.request.auth.SignUpRequestDto;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.auth.SignInResponseDto;
import com.rdrg.back.entity.EmailAuthNumberEntity;
import com.rdrg.back.entity.UserEntity;
import com.rdrg.back.provider.JwtProvider;
import com.rdrg.back.provider.MailProvider;
import com.rdrg.back.repository.EmailAuthNumberRepository;
import com.rdrg.back.repository.UserRepository;
import com.rdrg.back.service.AuthService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;
    private final EmailAuthNumberRepository emailAuthNumberRepository;

    private final MailProvider mailProvider;
    private final JwtProvider jwtProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {

        String accessToken = null;

        try {
            
            String userId = dto.getUserId();
            String userPassword = dto.getUserPassword();

            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return ResponseDto.signInFailed();
            
            String encodedPassword = userEntity.getUserPassword();
            boolean isMatched = passwordEncoder.matches(userPassword, encodedPassword);
            if (!isMatched) ResponseDto.signInFailed();

            accessToken = jwtProvider.create(userId);
            if (accessToken == null) return ResponseDto.tokenCreationFailed();

        } catch (Exception exception) {

            exception.printStackTrace();
            return ResponseDto.databaseError();

        }

        return SignInResponseDto.success(accessToken);

    }

    @Override
    public ResponseEntity<ResponseDto> idCheck(IdCheckRequestDto dto) {
        
        try {
            
            String userId = dto.getUserId();
            boolean existedUser = userRepository.existsByUserId(userId);
            if (existedUser) return ResponseDto.duplicatedId();

        } catch (Exception exception) {

            exception.printStackTrace();
            return ResponseDto.databaseError();

        }

        return ResponseDto.success();

    }

    @Override
    public ResponseEntity<ResponseDto> emailAuth(EmailAuthRequestDto dto) {

        try {
            
            String userEmail = dto.getUserEmail();

            boolean existedEmail = userRepository.existsByUserEmail(userEmail);
            if (existedEmail) return ResponseDto.duplicatedEmail();

            String authNumber = EmailAuthNumberUtil.createNumber();

            EmailAuthNumberEntity emailAuthNumberEntity = new EmailAuthNumberEntity();

            emailAuthNumberRepository.save(emailAuthNumberEntity);

            mailProvider.mailSend(userEmail, authNumber);

        } catch (MessagingException messagingException) {

            messagingException.printStackTrace();
            return ResponseDto.mailSendFailed();

        } catch (Exception exception) {

            exception.printStackTrace();
            return ResponseDto.databaseError();

        }

        return ResponseDto.success();

    }

    @Override
    public ResponseEntity<ResponseDto> emailAuthCheck(EmailAuthCheckRequestDto dto) {

        try {
            
            String userEmail = dto.getUserEmail();
            String authNumber = dto.getAuthNumber();

            boolean isMatch = emailAuthNumberRepository.existsByEmailAndAuthNumber(userEmail, authNumber);
            if(!isMatch) return ResponseDto.authenticationFailed();

        } catch (Exception exception) {

            exception.printStackTrace();
            return ResponseDto.databaseError();

        }

        return ResponseDto.success();

    }

    @Override
    public ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto) {

        try {
            
            String userId = dto.getUserId();
            String userPassword = dto.getUserPassword();
            String userEmail = dto.getUserEmail();
            String userNumber = dto.getAuthNumber();

            boolean existedUser = userRepository.existsById(userId);
            if (existedUser) return ResponseDto.duplicatedId();

            boolean existedEmail = userRepository.existsByUserEmail(userEmail);
            if (existedEmail) return ResponseDto.duplicatedEmail();

            boolean isMatched = emailAuthNumberRepository.existsByEmailAndAuthNumber(userEmail, userNumber);
            if (!isMatched) return ResponseDto.authenticationFailed();

            String encodedPassword = passwordEncoder.encode(userPassword);
            dto.setUserPassword(encodedPassword);

            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);

        } catch (Exception exception) {

            exception.printStackTrace();
            return ResponseDto.databaseError();

        }

        return ResponseDto.success();

    }
    
}
