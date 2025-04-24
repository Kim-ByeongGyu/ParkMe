package org.example.parkme.application.user;

import lombok.RequiredArgsConstructor;
import org.example.parkme.common.jwt.JwtTokenProvider;
import org.example.parkme.domain.user.OAuthProvider;
import org.example.parkme.domain.user.User;
import org.example.parkme.domain.user.UserRepository;
import org.example.parkme.interfaces.user.dto.LoginRequest;
import org.example.parkme.interfaces.user.dto.UserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void register(UserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .nickname(request.getNickname())
                .oauthProvider(OAuthProvider.NONE)
                .build();

        userRepository.save(user);
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return jwtTokenProvider.generateToken(user.getEmail());
    }
}
