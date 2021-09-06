package dev.lyze.doublepadding;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Preview extends Table {
    private final Image image;

    public Preview() {
        image = new Image();
        add(image);
    }

    public void setImage(Pixmap pixmap) {
        image.setDrawable(new TextureRegionDrawable(new Texture(pixmap)));
    }
}
