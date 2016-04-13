package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public static final Comparator<User> USER_COMPARATOR = Comparator.comparing(User::getName);

    @Override
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @PostConstruct
    public void postConstruct() {
        LOG.info("+++ PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        LOG.info("+++ PreDestroy");
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public User get(int id) {
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        return repository.values().stream().sorted(USER_COMPARATOR).collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return repository.values().stream().filter(u -> email.equals(u.getEmail())).findFirst().orElse(null);
    }
}
