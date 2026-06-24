package com.generation.models;

public abstract class Entity {
    public Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
 @Override
    public String toString() {
        return "Entity{" + "id=" + id +'}';
                
    }
}
