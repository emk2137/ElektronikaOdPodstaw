package pl.gda.pg.elektronikaodpodstaw.levels;

import java.util.List;

/**
 * Represents a level in the game, including its name, theory content, and stages.
 * Each level defines specific learning content and a sequence of tasks (stages) for the player.
 *
 * @param name the name of the level.
 * @param theory the theoretical content associated with the level.
 * @param stages the list of stages that comprise the level.
 */
public record Level(String name, String theory, List<Stage> stages) {

}
