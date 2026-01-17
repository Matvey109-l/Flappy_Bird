package io.github.some_example_name.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class TextButton {
    BitmapFont font;
    String text;
    Texture texture;

    int x, y;
    int textX, textY;
    int buttonWidth, buttonHeight;
    int textWidth, textHeight;

    public TextButton(int x, int y, String text)
    {
        this.x = x;
        this.y = y;
        this.text = text;
        font = new BitmapFont();
        font.getData().setScale(5f);
        font.setColor(Color.WHITE);

        GlyphLayout g1 = new GlyphLayout(font, text);
        textWidth = (int) g1.width;
        textHeight = (int) g1.height;

        texture = new Texture("button_bg.png");
        buttonWidth = texture.getWidth();
        buttonHeight = texture.getHeight();

        textX = x + (buttonWidth - textWidth) / 2;
        textY = y + (buttonHeight - textHeight) / 2;
    }
    public void draw(Batch batch)
    {
        batch.draw(texture, x, y, buttonWidth, buttonHeight);
        font.draw(batch, text, textX, textY);
    }
    public boolean isHint(int tx, int ty)
    {
        System.out.println(tx + " - " + ty);
        System.out.println(x + " - " + y);
        return (tx >= x && tx <= x + buttonWidth) && (ty >= y && ty <= y + buttonHeight);
    }
    public void dispose()
    {
        texture.dispose();
        font.dispose();
    }

}
