package net.corpwar.game.libgdxjam.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * LibGDXJam
 * Created by Ghost on 2015-12-19.
 */
public class TextureComp extends Component {

    public TextureComp() {
    }

    public TextureComp(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite sprite;

}
