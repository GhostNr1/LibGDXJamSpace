package net.corpwar.game.libgdxjam.systems;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.Entity;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

/**
 * LibGDXJam
 * Created by Ghost on 2015-12-19.
 */
public abstract class SortedIteratingSystem extends BaseEntitySystem {

    private final Array<Entity> sortedEntities = new Array<Entity>();
    private Comparator<Entity> comparator;

    /**
     * Creates a new EntityProcessingSystem.
     *
     * @param aspect
     *			the aspect to match entities
     */
    public SortedIteratingSystem(Aspect.Builder aspect) {
        super(aspect);
    }

    public void setComparator(Comparator<Entity> comparator) {
        this.comparator = comparator;
    }

    /**
     * Process a entity this system is interested in.
     *
     * @param entity
     *			the entity to process
     */
    protected abstract void process(Entity entity);

    /** @inheritDoc */
    @Override
    protected final void processSystem() {
        for (int i = sortedEntities.size - 1; i >= 0; i--) {
            process(sortedEntities.get(i));
        }
    }

    @Override
    protected void inserted(int entityId) {
        sortedEntities.add(world.getEntity(entityId));
        sortedEntities.sort(comparator);
    }

    @Override
    protected void removed(int entityId) {
        sortedEntities.removeValue(world.getEntity(entityId), false);
        sortedEntities.sort(comparator);
    }
}
