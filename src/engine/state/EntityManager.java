package engine.state;

import engine.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private final List<Entity> entities = new ArrayList<>();
    private final List<Entity> toAdd = new ArrayList<>();
    private final List<Entity> toRemove = new ArrayList<>();

    public void addEntity(Entity e) {
        toAdd.add(e);
    }
    public void remove(Entity e) {
        toRemove.add(e);
    }
    public void sync() {
        entities.addAll(toAdd);
        entities.removeAll(toRemove);
        toAdd.clear();
        toRemove.clear();
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void clear() {
        toAdd.clear();
        toRemove.clear();
        entities.clear();
    }
}
