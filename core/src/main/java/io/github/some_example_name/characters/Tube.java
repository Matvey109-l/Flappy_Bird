package io.github.some_example_name.characters;

import static io.github.some_example_name.Main.SCR_HEIGHT;
import static io.github.some_example_name.Main.SCR_WIDTH;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.Random;

public class Tube {
    Random random;
    Texture textureUpperTube;
    Texture textureDownTube;

    int speed;
    int safeDistance = 40;

    final int width = 200;
    final int height = 700;
    int gapHeight = 400;
    int padding = 100;
    int x, gapY;
    int distanceBetweenTubes;

    boolean isPointReceived;
    public Tube(int tubeCount, int tubeIdx, int speed) {
        random = new Random();
        this.speed = speed;
            gapY = safeDistance + gapHeight / 2 + random.nextInt(SCR_HEIGHT - 2 * safeDistance - gapHeight);
        distanceBetweenTubes = (SCR_WIDTH + width) / (tubeCount - 1);
        x = distanceBetweenTubes * tubeIdx + SCR_WIDTH;
            textureUpperTube = new Texture("tubes/tube_flipped.png");
            textureDownTube = new Texture("tubes/tube.png");
            isPointReceived = false;
        }
    public void draw(Batch batch) {
        batch.draw(textureUpperTube, x, gapY + gapHeight / 2, width, height);
        batch.draw(textureDownTube, x, gapY - gapHeight / 2 - height, width, height);
    }

    public void move(int speed) {
        x -= speed;

        if (x < -width) {
            isPointReceived = false;
            x = SCR_WIDTH + distanceBetweenTubes;
            gapY = safeDistance + gapHeight / 2 + random.nextInt(SCR_HEIGHT - 2 * safeDistance - gapHeight);
        }
    }

    public boolean isHint(Bird bird)
    {
        if (bird.y <= gapY - gapHeight / 2 && bird.x + bird.width >= x && bird.x <= x + width)
        {
           return true;
        }
        if (bird.y + bird.height >= gapY + gapHeight / 2 && bird.x + bird.width >= x && bird.x <= x + width)
        {
            return true;
        }
        return false;
    }

    public boolean needAddPoint(Bird bird)
    {
    int tubeCenterX = x + width / 2;
    int birdCenterX = bird.x + bird.width / 2;

    return !isPointReceived && birdCenterX > tubeCenterX;
    }
    public  void setPointReceived()
    {
        isPointReceived=true;
    }
    public void setSpeed(int speed)
    {
        this.speed = speed;
    }
    public void dispose() {
        textureDownTube.dispose();
        textureUpperTube.dispose();
        }
public int getX()
{
    return x;
}
    public int getGapY()
    {
        return gapY;
    }
    public int getWidth()
    {
        return width;
    }

    }
