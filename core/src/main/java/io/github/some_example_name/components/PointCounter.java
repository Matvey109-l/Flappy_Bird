package io.github.some_example_name.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PointCounter {

    int x, y;
    BitmapFont font;

    float scale = 5f;

    float normalScale = 5f;
    float speedBack = 6f;
    float pulseScale = 6.3f;

    boolean puls = false;

    public PointCounter(int x, int y) {
        this.x = x;
        this.y = y;

        font = new BitmapFont();

        font.setColor(Color.WHITE);
        font.getData().setScale(scale);
    }
    public void pulse()
    {
        puls = true;
        scale = pulseScale;
    }

    public void draw(Batch batch, int counterOfPoints, float delta) {
if (puls)
{
    scale -= speedBack * delta;

    if (scale <= normalScale)
    {
        scale = normalScale;
        puls = false;
    }
}

        font.getData().setScale(scale);

        font.draw(batch, "Count: " + counterOfPoints, x, y);
    }

    public void dispose() {
        font.dispose();
    }
}
