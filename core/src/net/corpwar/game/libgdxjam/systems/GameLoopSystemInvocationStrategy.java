package net.corpwar.game.libgdxjam.systems;

import com.artemis.BaseSystem;
import com.artemis.SystemInvocationStrategy;
import com.artemis.utils.Bag;
import com.badlogic.gdx.utils.Array;

/**
 * LibGDXJam
 * Created by Ghost on 2015-12-20.
 */
public class GameLoopSystemInvocationStrategy extends SystemInvocationStrategy {


    private float timeToProgress;
    private float accumulator  = 0;
    private float simAccumulator = 0;

    private final Array<BaseSystem> logicMarkedSystems;
    private final Array<BaseSystem> otherSystems;

    private boolean systemsSorted = false;

    public GameLoopSystemInvocationStrategy(float logicFPS) {
        timeToProgress = logicFPS;
        logicMarkedSystems = new Array<BaseSystem>();
        otherSystems = new Array<BaseSystem>();
    }

    @Override
    protected void process(Bag<BaseSystem> systems) {
        if (!systemsSorted) {
            sortSystems(systems);
        }
        accumulator += world.delta;
        while (accumulator >= timeToProgress) {
            for (int i = 0; i < logicMarkedSystems.size; i++) {
                /**
                 * Make sure your systems keep the current state before calculating the new state
                 * else you cannot interpolate later on when rendering
                 */
                logicMarkedSystems.get(i).process();
                updateEntityStates();
            }
            accumulator -= timeToProgress;
            simAccumulator = 0;
        }
        world.setDelta(accumulator);
        for (int i = 0; i < otherSystems.size; i++) {
            /**
             * Use the kept state from the logic above and interpolate with the current state within your render systems.
             */
            otherSystems.get(i).process();
            updateEntityStates();
        }
    }

    private void sortSystems(Bag<BaseSystem> systems) {
        if (!systemsSorted) {
            Object[] systemsData = systems.getData();
            for (int i = 0, s = systems.size(); s > i; i++) {
                BaseSystem system = (BaseSystem) systemsData[i];
                if (system instanceof LogicRenderMarker) {
                    logicMarkedSystems.add(system);
                } else {
                    otherSystems.add(system);
                }
            }
            systemsSorted = true;
        }
    }
}
