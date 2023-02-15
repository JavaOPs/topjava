package ru.javawebinar.topjava.model;

import lombok.*;

/**
 * @author Alexei Valchuk, 07.02.2023, email: a.valchukav@gmail.com
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode
public abstract class AbstractBaseEntity {

    public static final Integer START_SEQ = 100000;

    protected Integer id;

    public AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return this.id == null;
    }
}
