package akirakozov.ru.smartjumper.level;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import akirakozov.ru.smartjumper.map.LeafState;

/**
 * Created by akirakozov on 13.06.16.
 */
public class Levels {
    private final Map<LevelType, List<Level>> levels;

    public Levels() {
        this(new HashMap<LevelType, List<Level>>());
    }

    @JsonCreator
    public Levels(@JsonProperty("levels") Map<LevelType, List<Level>> levels) {
        this.levels = levels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Levels levels1 = (Levels) o;

        return levels != null ? levels.equals(levels1.levels) : levels1.levels == null;

    }

    public static class Level {
        public final Map<Integer, LeafState> states;

        @JsonCreator
        public Level(@JsonProperty("states")  Map<Integer, LeafState> states) {
            this.states = states;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Level level = (Level) o;

            return states != null ? states.equals(level.states) : level.states == null;
        }
    }

    public void addLevel(LevelType levelType, Level level) {
        if (levels.get(levelType) == null) {
            levels.put(levelType, new ArrayList<Level>());
        }
        levels.get(levelType).add(level);
    }

    public Level getLevel(LevelType levelType, int num) {
        return levels.get(levelType).get(num);
    }
}
