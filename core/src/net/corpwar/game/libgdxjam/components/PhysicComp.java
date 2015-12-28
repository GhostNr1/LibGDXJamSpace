package net.corpwar.game.libgdxjam.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * LibGDXJam
 * Created by Ghost on 2015-12-20.
 */
public class PhysicComp extends Component {

    public PhysicComp(Vector2 velocity, float speed, float rotationSpeed) {
        this.velocity = velocity;
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
    }

    public PhysicComp() {
    }

    public Vector2 velocity = new Vector2();

    public float speed = 0;

    public float rotationSpeed = 0;

}
