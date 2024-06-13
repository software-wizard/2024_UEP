package pl.psi;

import lombok.Getter;

public class EngineEntity {

    private GameEngine engine = null;

    public void bindEngine(final GameEngine engine) {
        if (this.engine != null) throw new IllegalStateException("GameEngine already bound");
        this.engine = engine;
    }

    public static EngineEntity bindEngine(final EngineEntity entity, final GameEngine engine) {
        entity.bindEngine(engine);
        return entity;
    }

    public GameEngine getParentEngine() {
        return this.engine;
    }
}
