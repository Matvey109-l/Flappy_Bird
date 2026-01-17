package io.github.some_example_name.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;


import io.github.some_example_name.Main;
import io.github.some_example_name.components.MovingBackround;
import io.github.some_example_name.components.PointCounter;
import io.github.some_example_name.components.TextButton;

public class ScreenRestart implements Screen {
    Main main;
    MovingBackround background;

    PointCounter pointCounter;

    int gamePoints;
    TextButton buttonRestart;
    public ScreenRestart(Main main)
    {
        this.main = main;
        background = new MovingBackround("backgrounds/restart_bg.png");
        buttonRestart = new TextButton(100, 400, "Restart");
        pointCounter = new PointCounter(750, 530);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);
        main.camera.update();
        main.batch.setProjectionMatrix(main.camera.combined);
        main.batch.begin();

        if (Gdx.input.justTouched())
        {
            Vector3 touch = main.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (buttonRestart.isHint((int) touch.x, (int) touch.y))
            {
                main.setScreen(main.screenGame);
            }
        }

        background.onDraw(main.batch);
        buttonRestart.draw(main.batch);
        pointCounter.draw(main.batch, gamePoints,delta);
        main.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
buttonRestart.dispose();
    }
}
