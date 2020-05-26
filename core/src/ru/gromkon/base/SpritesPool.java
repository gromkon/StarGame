package ru.gromkon.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public abstract class SpritesPool<T extends Sprite> {

    private final List<T> activeObjects = new ArrayList<>();
    private final List<T> freeObjects = new ArrayList<>();

    protected abstract T newObject();

    public List<T> getActiveObjects() {
        return activeObjects;
    }

    public List<T> getFreeObjects() {
        return freeObjects;
    }

    public T obtain() {
        T object;
        if (freeObjects.isEmpty()) {
            object = newObject();
        } else {
            object = freeObjects.remove(0);
        }
        activeObjects.add(object);
        return object;
    }

    public void updateActiveSprites(float delta) {
        for (Sprite sprite: activeObjects) {
            if (sprite.isDestroyed()) {
                continue;
            }
            sprite.update(delta);
        }
    }

    public void drawActiveSprites(SpriteBatch batch) {
        for (Sprite sprite: activeObjects) {
            if (sprite.isDestroyed()) {
                continue;
            }
            sprite.draw(batch);
        }
    }

    public void freeAllDestroyed() {
        for (int i = 0; i < activeObjects.size(); i++) {
            if (activeObjects.get(i).isDestroyed()) {
                free(activeObjects.get(i));
                i--;
            }
        }
    }

    private void free(T object) {
        object.flushDestroy();
        if (activeObjects.remove(object)) {
            freeObjects.add(object);
        }
    }

    public void dispose() {
        activeObjects.clear();
        freeObjects.clear();
    }


}
