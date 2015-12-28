package net.corpwar.game.libgdxjam.components;

import com.artemis.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

/**
 * LibGDXJam
 * Created by Ghost on 2015-12-27.
 */
public class ExplosionComp extends Component {

    public ExplosionComp() {
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("explosions/explosion.p"), Gdx.files.internal("explosions/"));
    }

    public ParticleEffect effect;

}