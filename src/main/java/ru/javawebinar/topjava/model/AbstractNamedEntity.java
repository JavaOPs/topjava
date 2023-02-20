package ru.javawebinar.topjava.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Alexei Valchuk, 07.02.2023, email: a.valchukav@gmail.com
 */

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
@ToString
public abstract class AbstractNamedEntity extends AbstractBaseEntity {

    @Column(nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    protected String name;

    protected AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
