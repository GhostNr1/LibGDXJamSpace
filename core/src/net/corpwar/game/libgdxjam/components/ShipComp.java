package net.corpwar.game.libgdxjam.components;

import com.artemis.Component;

/**
 * LibGDXJam
 * Created by Ghost on 2015-12-20.
 */
public class ShipComp extends Component {

    public ShipComp() {
    }

    public ShipComp(float maxSpeed, float maxRotationSpeed, boolean directStop) {
        this.maxSpeed = maxSpeed;
        this.maxRotationSpeed = maxRotationSpeed;
        this.directStop = directStop;
    }

    public float maxSpeed;

    public float maxRotationSpeed;

    public boolean directStop = false;


}
