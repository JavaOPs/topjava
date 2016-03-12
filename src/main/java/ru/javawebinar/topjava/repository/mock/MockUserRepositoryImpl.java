package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class MockUserRepositoryImpl implements UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(MockUserRepositoryImpl.class);
    private final AtomicInteger counter = new AtomicInteger(0);
    private final Map<Integer, User> repository = new ConcurrentHashMap<>();

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        Integer id = user.isNew() ? counter.incrementAndGet() : user.getId();
        user.setId(id);
        repository.put(id, user);
        if (!checkEmail(user))
            throw new IllegalArgumentException("To many email to repository");
        LOG.info("save " + user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        return new ArrayList<>(repository.values());
    }

    @Override
    public User getByEmail(final String email) {
        LOG.info("getByEmail " + email);
        return getAll().parallelStream().filter(u -> u.getEmail()
                .equals(email)).findAny().get();
    }

    private boolean checkEmail(User user) {
        return (user.getEmail() == null || user.getEmail().isEmpty())
                || repository.values().parallelStream().
                filter(u -> u.getEmail().equals(user.getEmail())).count() == 1;
    }
}
