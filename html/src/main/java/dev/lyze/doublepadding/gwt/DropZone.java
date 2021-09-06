package dev.lyze.doublepadding.gwt;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.google.gwt.dom.client.ImageElement;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import dev.lyze.doublepadding.IDropZone;
import dev.lyze.doublepadding.Logger;
import dev.lyze.doublepadding.Preview;
import lombok.var;
import org.eclipse.jdt.internal.eval.EvaluationResult;

public class DropZone implements IDropZone {
    private Logger<DropZone> logger;
    private Preview preview;

    @Override
    public void initialize(Preview preview) {
        logger = new Logger<>(DropZone.class);
        logger.logInfo("Initializing drop zone");

        this.preview = preview;
        setupDropZone(this);
    }

    public void onImageDropped(String name, ImageElement image) {
        logger.logInfo("Dropped file " + name);

        try {
            var pixmap = new Pixmap(image);
            if (pixmap.getWidth() > 0 && pixmap.getHeight() > 0) {
                preview.setImage(pixmap);
            } else {
                Dialogs.showErrorDialog(preview.getStage(), "Couldn't process file.", "File is either corrupted or not an image.");
            }

            destroyImage(image);
        } catch (Exception e) {
            Dialogs.showErrorDialog(preview.getStage(), "Couldn't process file.",  e.getMessage());
        }
    }

    public void onError(String error) {
        logger.logError("Couldn't drop file: " + error);

        Dialogs.showErrorDialog(preview.getStage(), "Couldn't drop file.", error);
    }

    public static native void destroyImage(ImageElement image) /*-{
        image.remove();
    }-*/;

    public static native void setupDropZone(DropZone zone) /*-{
        var embed = $doc.getElementById("embed-html");

        embed.addEventListener('dragover', function(evt) {
            evt.stopPropagation();
            evt.preventDefault();

            evt.dataTransfer.dropEffect = "copy";
        }, false);

        embed.addEventListener('drop', function(evt) {
            evt.stopPropagation();
            evt.preventDefault();

            var files = evt.dataTransfer.files;

            if (files.length !== 1) {
                console.log("multiple images dragged (or none).");
                    zone.@dev.lyze.doublepadding.gwt.DropZone::onError(*)("Multiple files specified");
                return;
            }

            var file = files[0];

            if (file.type.split('/')[0] !== "image") {
                console.log("wrong file type");
                zone.@dev.lyze.doublepadding.gwt.DropZone::onError(*)("Wrong file type: " + file.type);
                return;
            }

            var reader = new FileReader();
            reader.onload = function() {
                var img = $doc.createElement("img");
                img.crossOrigin = "Anonymous";
                img.src = this.result;

                img.onerror = function() {
                    zone.@dev.lyze.doublepadding.gwt.DropZone::onError(*)("Couldn't load image");
                }
                img.onload = function() {
                    zone.@dev.lyze.doublepadding.gwt.DropZone::onImageDropped(*)(file.name, img);
                }
            }
            reader.onerror = function() {
                zone.@dev.lyze.doublepadding.gwt.DropZone::onError(*)("Couldn't read file");
            }
            reader.readAsDataURL(file);
        }, false);
    }-*/;
}
