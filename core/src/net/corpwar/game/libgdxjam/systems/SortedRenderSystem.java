package net.corpwar.game.libgdxjam.systems;

import com.artemis.*;
import com.badlogic.gdx.Gdx;
import net.corpwar.game.libgdxjam.LibGDXJamSpace;
import net.corpwar.game.libgdxjam.components.*;

import java.util.Comparator;

/**
 * LibGDXJam
 * Created by Ghost on 2015-12-19.
 */
public class SortedRenderSystem extends SortedIteratingSystem {

    private LibGDXJamSpace jamSpace;
    private ComponentMapper<Transform2DComp> transform2d;
    private ComponentMapper<PhysicComp> physic;
    private ComponentMapper<TextureComp> texture;
    private ComponentMapper<ExplosionComp> explosion;

    public SortedRenderSystem(LibGDXJamSpace jamSpace) {
        super(Aspect.all(Transform2DComp.class).one(TextureComp.class, ExplosionComp.class));
        setComparator(new ZComparator());
        this.jamSpace = jamSpace;
    }

    @Override
    protected void begin() {
        if (jamSpace.camera != null) {
            AspectSubscriptionManager asm = world.getAspectSubscriptionManager();
            EntitySubscription es = asm.get(Aspect.all(InputComp.class, Transform2DComp.class));
            if (es.getEntities().size() == 1) {
                Transform2DComp t2d = world.getEntity(es.getEntities().get(0)).getComponent(Transform2DComp.class);
                if (physic.has(es.getEntities().get(0))) {
                    PhysicComp physicComp = physic.get(es.getEntities().get(0));
                    jamSpace.camera.position.set(t2d.position.x + (physicComp.velocity.x * physicComp.speed * world.getDelta()), t2d.position.y + (physicComp.velocity.y * physicComp.speed * world.getDelta()), 0);
                } else {
                    jamSpace.camera.position.set(t2d.position.x, t2d.position.y, 0);
                }
                jamSpace.camera.update();
            }
            if (jamSpace.batch != null) {
                jamSpace.camera.update();
                jamSpace.batch.setProjectionMatrix(jamSpace.camera.combined);
                jamSpace.batch.begin();
            }
        }
    }

    @Override
    protected void process(Entity entity) {
        if (jamSpace.batch != null) {
            Transform2DComp transformComp = transform2d.get(entity);
            if (texture.has(entity)) {
                TextureComp textureComp = texture.get(entity);
                if (textureComp.sprite != null) {
                    if (physic.has(entity)) {
                        PhysicComp physicComp = physic.get(entity);
                        textureComp.sprite.setPosition(transformComp.position.x + (physicComp.velocity.x * physicComp.speed * world.getDelta()), transformComp.position.y + (physicComp.velocity.y * physicComp.speed * world.getDelta()));
                    } else {
                        textureComp.sprite.setPosition(transformComp.position.x, transformComp.position.y);
                    }
                    textureComp.sprite.setScale(transformComp.scale.x, transformComp.scale.y);
                    textureComp.sprite.setRotation(transformComp.rotation);
                    textureComp.sprite.draw(jamSpace.batch);
                }
            }
            if (explosion.has(entity)) {
                ExplosionComp explosionComp = explosion.get(entity);
                explosionComp.effect.setPosition(transformComp.position.x, transformComp.position.y);
                explosionComp.effect.draw(jamSpace.batch, world.getDelta());
                if (explosionComp.effect.isComplete()) {
                    world.delete(entity.getId());
                }
            }
        }
    }

    @Override
    protected void end() {
        if (jamSpace.batch != null) {
            jamSpace.batch.end();
        }
    }

    private class ZComparator implements Comparator<Entity> {
        @Override
        public int compare(Entity e1, Entity e2) {
            return (int)Math.signum(transform2d.get(e2).zValue - transform2d.get(e1).zValue);
        }
    }
}
