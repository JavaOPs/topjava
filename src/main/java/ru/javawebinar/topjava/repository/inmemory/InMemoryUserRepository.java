package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private AtomicInteger counter = new AtomicInteger(0);
    private Map<Integer, User> userRepositoryMap = new ConcurrentHashMap<>();
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 1;

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return userRepositoryMap.remove(id)!=null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()){
            user.setId(counter.incrementAndGet());
            userRepositoryMap.put(user.getId(), user);
            return user;
        }
        return userRepositoryMap.computeIfPresent(user.getId(), (id,oldUser)->user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return userRepositoryMap.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return userRepositoryMap.values().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return userRepositoryMap.values().stream()
                .filter(user -> email.equals(user.getEmail()))
                .findFirst()
                .orElse(null);
    }
}
