package io.github.some_example_name.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import io.github.some_example_name.Main;

public class MovingBackround {
    Texture texture;
    int speed = 2;
    int texture1X, texture2X;
     public MovingBackround(String pathToTexture){
        texture = new Texture(pathToTexture);
        texture1X = 0;
        texture2X = Main.SCR_WIDTH;
    }

    public void onDraw(Batch batch)
    {
        batch.draw(texture, texture1X, 0, Main.SCR_WIDTH, Main.SCR_HEIGHT);
        batch.draw(texture, texture2X, 0, Main.SCR_WIDTH, Main.SCR_HEIGHT);
    }

    public void move()
    {
        texture1X -= speed;
        texture2X -= speed;
        if (texture1X <= -Main.SCR_WIDTH)
        {
            texture1X = Main.SCR_WIDTH;
        }
        if(texture2X <= -Main.SCR_WIDTH)
        {
            texture2X = Main.SCR_WIDTH;
        }
    }
}
