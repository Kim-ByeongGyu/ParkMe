package org.example.parkme.infrastructure.user;

import lombok.RequiredArgsConstructor;
import org.example.parkme.domain.user.User;
import org.example.parkme.domain.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaRepo;

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepo.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return jpaRepo.save(user);
    }
}
