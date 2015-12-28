package net.corpwar.game.libgdxjam.systems;

import com.artemis.*;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import net.corpwar.game.libgdxjam.components.*;

/**
 * LibGDXJam
 * Created by Ghost on 2015-12-27.
 */
public class WeaponSystem extends IteratingSystem implements LogicRenderMarker {

    private ComponentMapper<BombComp> bomb;
    private ComponentMapper<TextureComp> texture;
    private ComponentMapper<ShipComp> ship;
    private ComponentMapper<ExplosionComp> explosionC;
    private float delta;

    public WeaponSystem(float logicFPS) {
        super(Aspect.one(BombComp.class));
        this.delta = logicFPS;
    }

    @Override
    protected void process(int entityId) {
        if (bomb.has(entityId)) {
            BombComp bombComp = bomb.get(entityId);
            if (bombComp.livedTime > bombComp.maxLiveTime) {
                world.delete(entityId);
            }
            bombComp.livedTime += delta;

            TextureComp bombTexture = texture.get(entityId);
            AspectSubscriptionManager asm = world.getAspectSubscriptionManager();
            EntitySubscription es = asm.get(Aspect.all(ShipComp.class, TextureComp.class, AIComp.class));
            for (int i = es.getEntities().size() - 1; i >= 0; i--) {
                Entity shipE = world.getEntity(es.getEntities().get(i));
                TextureComp shipTexture = texture.get(shipE);
                if (shipTexture.sprite.getBoundingRectangle().overlaps(bombTexture.sprite.getBoundingRectangle())) {
                    ShipComp shipComp = ship.get(shipE);
                    shipComp.health -= MathUtils.random(bombComp.minDmg, bombComp.maxDmg);
                    world.delete(entityId);
                    if (shipComp.health < 0) {
                        world.delete(shipE.getId());
                        shipComp.health = 500;
                    }

                    Entity explosion = world.createEntity();
                    explosion.edit().add(new Transform2DComp(new Vector2(bombTexture.sprite.getX(), bombTexture.sprite.getY()), 10, new Vector2(1,1), 0))
                            .add(new ExplosionComp());
                    explosion.getComponent(ExplosionComp.class).effect.start();
                    System.out.println("health: " + shipComp.health);
                }
            }
        }
    }
}
