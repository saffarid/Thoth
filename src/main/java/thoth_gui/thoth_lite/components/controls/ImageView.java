package thoth_gui.thoth_lite.components.controls;

import javafx.scene.image.Image;

/**
 * Класс для централизованного создания объекта изображения
 * */
public class ImageView
        extends javafx.scene.image.ImageView {

    private ImageView(String url,
                      double width, double height) {
        super();
        setImage(
                new Image(
                        getClass().getResource(url).toExternalForm(),
                        width, height,
                        true, true
                )
        );
    }

    public static ImageView getInstance(
            String url,
            double width, double height
    ){
        return new ImageView(url, width, height);
    }

}
