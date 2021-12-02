package ru.vogulev.voting.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.vogulev.voting.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}