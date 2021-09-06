package dev.lyze.doublepadding.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import dev.lyze.doublepadding.Main;
import lombok.var;

public class GwtLauncher extends GwtApplication {
		@Override
		public GwtApplicationConfiguration getConfig () {
			var config = new GwtApplicationConfiguration(true);
			config.padHorizontal = 0;
			config.padVertical = 0;
			return config;
		}

		@Override
		public ApplicationListener createApplicationListener () {
			return new Main(new DropZone());
		}
}
