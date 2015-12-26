package net.corpwar.game.libgdxjam.systems;

import com.artemis.*;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import net.corpwar.game.libgdxjam.components.AIComp;
import net.corpwar.game.libgdxjam.components.PhysicComp;
import net.corpwar.game.libgdxjam.components.ShipComp;
import net.corpwar.game.libgdxjam.components.Transform2DComp;

/**
 * LibGDXJam
 * Created by Ghost on 2015-12-21.
 */
public class AISystem extends IteratingSystem implements LogicRenderMarker {

    private ComponentMapper<AIComp> ai;
    private ComponentMapper<PhysicComp> physic;
    private ComponentMapper<Transform2DComp> transform2d;

    private float closestShip = 0;

    public AISystem() {
        super(Aspect.all(PhysicComp.class, Transform2DComp.class, AIComp.class));
    }

    @Override
    protected void begin() {
        closestShip = -1f;
    }

    @Override
    protected void process(int entityId) {
        AspectSubscriptionManager asm = world.getAspectSubscriptionManager();
        EntitySubscription es = asm.get(Aspect.all(ShipComp.class, Transform2DComp.class));
        Entity ep = world.getEntity(entityId);
        Entity toShip = null;
        for (int i = es.getEntities().size() - 1; i >= 0; i--) {
            Entity e = world.getEntity(i);
            if (e.getId() != entityId) {
                float distance = e.getComponent(Transform2DComp.class).position.dst(ep.getComponent(Transform2DComp.class).position);
                if (closestShip == -1 || distance < closestShip) {
                    closestShip = distance;
                    toShip = e;
                }
            }
        }
        if (toShip != null) {
            float degree = toShip.getComponent(Transform2DComp.class).position.cpy().sub(ep.getComponent(Transform2DComp.class).position).angle();
            ep.getComponent(Transform2DComp.class).rotation = degree;
            float x = MathUtils.cosDeg(degree);
            float y = MathUtils.sinDeg(degree);
            ep.getComponent(PhysicComp.class).velocity.set(x, y);
        }

    }
}
