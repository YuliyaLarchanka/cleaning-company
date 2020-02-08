package by.larchanka.tiptopcleaning.entity;

import java.io.Serializable;

public abstract class Entity implements Cloneable, Serializable {
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
