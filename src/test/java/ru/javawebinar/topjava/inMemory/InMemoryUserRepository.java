package ru.javawebinar.topjava.inMemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alexei Valchuk, 13.02.2023, email: a.valchukav@gmail.com
 */

@Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository{

    public static final int USER_ID = 100000;
    public static final int ADMIN_ID = 100001;

    {
        save(new User(1, "User", "user@mail.ru", "password", Role.ROLE_USER));
        save(new User(2, "Admin", "user@mail.ru", "password", Role.ROLE_USER));
    }

    @Override
    public User getByEmail(String email) {
        return getCollection().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getAll() {
        return getCollection().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }
}
