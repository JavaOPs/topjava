package ru.javawebinar.topjava.model;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class NamedEntity extends BaseEntity {

    protected String name;

    public NamedEntity() {
    }

    protected NamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }
}
