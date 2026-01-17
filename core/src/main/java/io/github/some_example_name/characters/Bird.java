package io.github.some_example_name.characters;

import static io.github.some_example_name.Main.SCR_HEIGHT;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
public class Bird {

        int x , y;
        int speed ;
        int frameCounter;
        Texture[] framesArray;
        int width;
        int height;
        int jumpHeight;
        final int maxHeightOfJump = 200;
        boolean jump;

        public Bird(int x, int y, int speed, int height, int width){
            this.x = x;
            this.y = y;
            this.width = width;
            this.speed = speed;
            this.height = height;
            frameCounter = 0;

            framesArray = new Texture[]{
                new Texture("birdTiles/bird0.png"),
                new Texture("birdTiles/bird1.png"),
                new Texture("birdTiles/bird2.png"),
                new Texture("birdTiles/bird1.png"),
            };
        }
    public void onClick() {
        jump = true;
        jumpHeight = maxHeightOfJump + y;
    }

public boolean isInField()
{
    if (y + height < 0)
    {
        return false;
    }
    if (y > SCR_HEIGHT)
    {
        return false;
    }

   return true;
}

    public void fly() {
        if (y >= jumpHeight) {
            jump = false;
        }

        if (jump) {
            y += speed;
        } else {
            y -= speed;
        }
    }

    public void draw(Batch batch, boolean visible) {
            if(!visible) return;
        int frameMultiplier = 10;
        batch.draw(framesArray[frameCounter / frameMultiplier], x, y, width, height);
        if (frameCounter++ == framesArray.length * frameMultiplier - 1) frameCounter = 0;
    }
    public void dispose() {
        for (Texture texture : framesArray) {
            texture.dispose();
        }
    }

    public void setY(int y) {
            this.y = y;
    }
public Rectangle getrect()
{
    return new Rectangle(x, y, width, height);
}
}
