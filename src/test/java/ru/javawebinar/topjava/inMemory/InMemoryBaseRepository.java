package ru.javawebinar.topjava.inMemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractBaseEntity;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

/**
 * @author Alexei Valchuk, 13.02.2023, email: a.valchukav@gmail.com
 */

@Repository
public class InMemoryBaseRepository<T extends AbstractBaseEntity> {

    protected static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private static final AtomicInteger counter = new AtomicInteger(START_SEQ);

    private final Map<Integer, T> repository = new ConcurrentHashMap<>();

    public T save(T entry) {
        LOG.info("save {}", entry);
        if (entry.isNew()) {
            entry.setId(counter.incrementAndGet());
            repository.put(entry.getId(), entry);
            return entry;
        }
        return repository.computeIfPresent(entry.getId(), (id, oldT) -> entry);
    }

    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    public T get(int id) {
        return repository.get(id);
    }

    Collection<T> getCollection() {
        return repository.values();
    }
}
