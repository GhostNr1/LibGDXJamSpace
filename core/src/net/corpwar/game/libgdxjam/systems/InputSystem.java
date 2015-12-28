package net.corpwar.game.libgdxjam.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import net.corpwar.game.libgdxjam.LibGDXJamSpace;
import net.corpwar.game.libgdxjam.components.*;

/**
 * LibGDXJam
 * Created by Ghost on 2015-12-20.
 */
public class InputSystem extends IteratingSystem {

    private LibGDXJamSpace jamSpace;
    private ComponentMapper<PhysicComp> physic;
    private ComponentMapper<Transform2DComp> transform2d;
    private ComponentMapper<ShipComp> ship;

    private float delta;

    public InputSystem(LibGDXJamSpace jamSpace, float logicFPS) {
        super(Aspect.all(InputComp.class, PhysicComp.class, Transform2DComp.class, ShipComp.class));
        this.jamSpace = jamSpace;
        delta = logicFPS;
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
            physicComp.velocity.set(x, y);
        } else {
            if (shipComp.directStop) {
                physicComp.velocity.set(0,0);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            float x = MathUtils.cosDeg(transform2DComp.rotation);
            float y = MathUtils.sinDeg(transform2DComp.rotation);
            Vector2 position = new Vector2(transform2DComp.position);
            position.x += 36 + (x * 36);
            position.y += 55 + (y * 55);
            Entity fire = world.createEntity();
            fire.edit().add(new TextureComp(new Sprite(new Texture("weapons/bomb.png"))))
                    .add(new Transform2DComp(position, 2, new Vector2(1, 1), transform2DComp.rotation))
                    .add(new PhysicComp(new Vector2(x, y), 500, 0))
                    .add(new BombComp(2));

        }
    }
}
