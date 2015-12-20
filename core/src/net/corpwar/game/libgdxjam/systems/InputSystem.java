package net.corpwar.game.libgdxjam.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import net.corpwar.game.libgdxjam.LibGDXJamSpace;
import net.corpwar.game.libgdxjam.components.InputComp;
import net.corpwar.game.libgdxjam.components.PhysicComp;
import net.corpwar.game.libgdxjam.components.ShipComp;
import net.corpwar.game.libgdxjam.components.Transform2DComp;

/**
 * LibGDXJam
 * Created by Ghost on 2015-12-20.
 */
public class InputSystem extends IteratingSystem implements LogicRenderMarker {

    private LibGDXJamSpace jamSpace;
    private ComponentMapper<PhysicComp> physic;
    private ComponentMapper<Transform2DComp> transform2d;
    private ComponentMapper<ShipComp> ship;

    private float delta;

    public InputSystem(LibGDXJamSpace jamSpace, float logicFPS) {
        super(Aspect.all(InputComp.class, PhysicComp.class, Transform2DComp.class, ShipComp.class));
        this.jamSpace = jamSpace;
        delta = 1 / logicFPS;
    }

    @Override
    protected void process(int entityId) {
        PhysicComp physicComp = physic.get(entityId);
        Transform2DComp transform2DComp = transform2d.get(entityId);
        ShipComp shipComp = ship.get(entityId);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (physicComp.rotationSpeed < shipComp.maxRotationSpeed) {
                physicComp.rotationSpeed += shipComp.maxRotationSpeed * delta;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (physicComp.rotationSpeed > -shipComp.maxRotationSpeed) {
                physicComp.rotationSpeed += -shipComp.maxRotationSpeed * delta;
            }
        } else {
            physicComp.rotationSpeed = 0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            physicComp.speed = shipComp.maxSpeed;
            float x = MathUtils.cosDeg(transform2DComp.rotation);
            float y = MathUtils.sinDeg(transform2DComp.rotation);
            physicComp.velocity.set(x * physicComp.speed, y * physicComp.speed);
        } else {
            if (shipComp.directStop) {
                physicComp.velocity.set(0,0);
            }
        }
    }
}
