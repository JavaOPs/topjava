package ru.javawebinar.topjava.to;

import io.swagger.annotations.ApiModelProperty;
import ru.javawebinar.topjava.HasId;

public abstract class BaseTo implements HasId {
    @ApiModelProperty(hidden = true)
    protected Integer id;

    public BaseTo() {
    }

    public BaseTo(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
