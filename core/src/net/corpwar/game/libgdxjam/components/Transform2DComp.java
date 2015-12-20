package net.corpwar.game.libgdxjam.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * LibGDXJam
 * Created by Ghost on 2015-12-19.
 */
public class Transform2DComp extends Component implements Comparable<Transform2DComp>{

    public Transform2DComp() {
    }

    public Transform2DComp(Vector2 position, int zValue, Vector2 scale, float rotation) {
        this.position = position;
        this.zValue = zValue;
        this.scale = scale;
        this.rotation = rotation;
    }

    /**
     * Position in the world
     */
    public Vector2 position;

    /**
     * Z-value, lowest value will be render first. High number will be above low number. Same value is random
     */
    public int zValue;

    /**
     * Scale the object
     */
    public Vector2 scale;

    /**
     * Rotate the object
     */
    public float rotation = 0;

    @Override
    public int compareTo(Transform2DComp transform2DCompTmp) {
        if (transform2DCompTmp != null) {
            return zValue - transform2DCompTmp.zValue;
        }
        return 0;
    }
}
