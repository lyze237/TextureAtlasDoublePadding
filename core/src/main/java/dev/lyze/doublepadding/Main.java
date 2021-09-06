package dev.lyze.doublepadding;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.VisUI.SkinScale;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;
import lombok.var;

public class Main extends ApplicationAdapter {
	private static final Logger<Main> logger = new Logger<>(Main.class);

	private final IDropZone dropZone;
	private Stage stage;

	public Main(IDropZone dropZone) {
		this.dropZone = dropZone;
	}

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		VisUI.setSkipGdxVersionCheck(true);
		VisUI.load(SkinScale.X1);

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		var root = new VisTable();
		root.setFillParent(true);

		var preview = new Preview();
		root.add(preview);


		stage.addActor(root);
		dropZone.initialize(preview);
	}

	@Override
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void dispose () {
		VisUI.dispose();
		stage.dispose();
	}
}