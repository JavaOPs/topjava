package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final MealRepository mealRepository;

    @Autowired
    public InMemoryUserRepository(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
        addSomeUsers();
    }

    @Override
    public boolean delete(int id) {
        log.info("delete user with id={}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save user {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            mealRepository.addNewMealBasket(user.getId());
            return user;
        }
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get user with id={}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll users");
        return repository.values().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("user getByEmail {}", email);
        return repository.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    private void addSomeUsers() {
        save(new User(null, "ВасяМоков1", "vasily1@good.edu", "1234", Role.USER));
        save(new User(null, "ВасяМоков2", "vasily2@good.edu", "4321", Role.USER));
    }
}
