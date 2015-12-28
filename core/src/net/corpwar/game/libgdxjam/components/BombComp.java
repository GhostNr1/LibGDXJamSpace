package net.corpwar.game.libgdxjam.components;

import com.artemis.Component;

/**
 * LibGDXJam
 * Created by Ghost on 2015-12-27.
 */
public class BombComp extends Component {

    public BombComp() {
    }

    public BombComp(float maxLiveTime) {
        this.maxLiveTime = maxLiveTime;
    }

    public float maxLiveTime;

    public float livedTime = 0;

    public float minDmg = 50;

    public float maxDmg = 80;


}
