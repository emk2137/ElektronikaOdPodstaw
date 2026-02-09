package pl.gda.pg.elektronikaodpodstaw.levels;

/**
 * Represents a single stage in game.
 * A stage can either be a question-answer stage or a simulation-based stage,
 * depending on its configuration.
 */
public class Stage {

    /** The question associated with this stage. */
    private final String question;

    /** The correct answer for the question, if applicable. */
    private final String correctAnswer;

    /** Simulation parameters for the stage, if applicable. */
    private final String simulationParams;

    /** Indicates whether this stage is a question-answer stage. */
    private final boolean isAnswerStage;

    /**
     * Constructs a question-answer stage with the specified question and correct answer.
     *
     * @param question the question text for this stage.
     * @param correctAnswer the correct answer to the question.
     * @param isAnswerStage a flag indicating whether this is a question-answer stage.
     */
    public Stage(String question, String correctAnswer, boolean isAnswerStage) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.isAnswerStage = isAnswerStage;
        this.simulationParams = null;
    }

    /**
     * Constructs a simulation stage with the specified question and simulation parameters.
     *
     * @param question the question text for this stage.
     * @param simulationParams the correct parameters for the simulation.
     * The {@code isAnswerStage} field is set to {@code false}, and the {@code correctAnswer} field is set to {@code null}
     * as this constructor is specifically for simulation stages.
     */
    public Stage(String question, String simulationParams) {
        this.question = question;
        this.simulationParams = simulationParams;
        this.isAnswerStage = false;
        this.correctAnswer = null;
    }

    /**
     * Returns the question associated with this stage.
     *
     * @return the question text.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns the correct answer for the question, if applicable.
     *
     * @return the correct answer, or null if not applicable.
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Returns the correct simulation parameters for this stage, if applicable.
     *
     * @return the simulation parameters, or null if not applicable.
     */
    public String getSimulationParams() {
        return simulationParams;
    }

    /**
     * Checks whether this stage is a question-answer stage.
     *
     * @return true if this is a question-answer stage; false otherwise.
     */
    public boolean isAnswerStage() {
        return isAnswerStage;
    }

}
