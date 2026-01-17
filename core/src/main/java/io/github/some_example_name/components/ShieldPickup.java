package io.github.some_example_name.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class ShieldPickup {
    Texture texture;
    float x, y;
    float size;
    boolean active = false;

    public ShieldPickup(String path, float size) {
        texture = new Texture(path);
        this.size = size;
    }

    public void spawn(float x, float y) {
        this.x = x;
        this.y = y;
        active = true;
    }

    public void update(float speed) {
        if (!active) return;
        x -= speed;
        if (x + size < 0) {
            active = false;
        }
    }

    public void draw(Batch batch)
    {
        if(!active) return;
        batch.draw(texture, x , y, size, size);
    }
    public Rectangle get_rect()

    {
        return new Rectangle(x, y, size, size);
    }
    public boolean isActive()
    {
        return active;
    }
    public void deactivate()
    {
        active = false;
    }
    public void dispose()
    {
        texture.dispose();
    }
}
