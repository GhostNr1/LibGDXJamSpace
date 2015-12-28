package net.corpwar.game.libgdxjam.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import net.corpwar.game.libgdxjam.components.PhysicComp;
import net.corpwar.game.libgdxjam.components.Transform2DComp;

/**
 * LibGDXJam
 * Created by Ghost on 2015-12-20.
 */
public class PhysicsSystem extends IteratingSystem implements LogicRenderMarker{

    private ComponentMapper<PhysicComp> physics;
    private ComponentMapper<Transform2DComp> transform2d;

    private float delta;

    public PhysicsSystem(float logicFPS) {
        super(Aspect.all(Transform2DComp.class, PhysicComp.class));
        delta = logicFPS;
    }

    @Override
    protected void process(int entityId) {
        Transform2DComp transform2DComp = transform2d.get(entityId);
        PhysicComp physicComp = physics.get(entityId);
        transform2DComp.rotation = transform2DComp.rotation + (physicComp.rotationSpeed * delta);
        transform2DComp.position.add(physicComp.velocity.x * physicComp.speed * delta, physicComp.velocity.y * physicComp.speed * delta);
    }
}
