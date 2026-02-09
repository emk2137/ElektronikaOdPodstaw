package pl.gda.pg.elektronikaodpodstaw.levels;

import pl.gda.pg.elektronikaodpodstaw.main.MainFrame;
import pl.gda.pg.elektronikaodpodstaw.ui.AnswerStagePanel;
import pl.gda.pg.elektronikaodpodstaw.ui.SimulationStagePanel;
import javax.swing.*;
import java.awt.*;

/**
 * Handles the management of levels and stages in game.
 * It provides methods to load levels, navigate between stages, and update the game state.
 */
public class LevelManager {

    /** The index of the current level, adjusted from the global level setting. */
    private static int currentLevelIndex = MainFrame.currentLevel - 1;

    /** The index of the current stage within the current level. */
    public static int currentStageIndex = 0;

    /** Indicates whether the current stage is a question-answer stage. */
    private static boolean isAnswerStage;

    /**
     * Constructs a new instance of the class.
     * Used to initialize the object without any specific parameters or setup.
     */
    public LevelManager() {

    }

    /**
     * Retrieves the maximum number of stages in the current level.
     *
     * @return the number of stages in the current level.
     */
    public static int getCurrentMaxStageIndex() {
        Level currentLevel = Levels.levelsList.get(currentLevelIndex);
        return currentLevel.stages().size();
    }

    /**
     * Retrieves the index of the current stage.
     *
     * @return the index of the current stage.
     */
    public static int getCurrentStageIndex() {
        return currentStageIndex;
    }

    /**
     * Loads the current stage of the current level and updates the game view.
     *
     * @param frame the main application frame to update with the current stage panel.
     */
    public static void loadLevelStage(MainFrame frame) {
        Levels.initializeLevels();
        currentLevelIndex = MainFrame.currentLevel - 1;
        Level currentLevel = Levels.levelsList.get(currentLevelIndex);
        Stage currentStage = currentLevel.stages().get(currentStageIndex);

        JPanel currentStagePanel;
        if (currentStage.isAnswerStage()) {
            currentStagePanel = new AnswerStagePanel(frame, currentStage.getQuestion(), currentStage.getCorrectAnswer(), currentLevel.theory());
            isAnswerStage = true;
        } else {
            isAnswerStage = false;
            currentStagePanel = new SimulationStagePanel(frame, currentStage.getQuestion(), currentStage.getSimulationParams(), currentLevel.theory());
        }

        frame.getContentPane().add(currentStagePanel, "CurrentLevel");
        ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "CurrentLevel");
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Advances to the next stage in the current level or ends the game if it is the final stage.
     *
     * @param frame the main application frame to update with the next stage or end screen.
     */
    public void nextStage(MainFrame frame) {
        String StageInfo = LevelManager.getCurrentStageIndex() + 1 + " / " + LevelManager.getCurrentMaxStageIndex();
        if (currentStageIndex + 1 == getCurrentMaxStageIndex() && MainFrame.currentLevel == Levels.levelsList.size()) {
            Levels.endMessage(frame);
            MainFrame.configManager.setProperty("isCompleted","yes");
            MainFrame.isCompleted = "yes";
            currentLevelIndex = 0;
            currentStageIndex = 0;
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            cl.show(frame.getContentPane(), "MainMenu");
            return;
        }
        else if (currentStageIndex + 1 < getCurrentMaxStageIndex() && isAnswerStage) {
            JOptionPane.showMessageDialog(frame, "Prawidłowa odpowiedź!", StageInfo, JOptionPane.INFORMATION_MESSAGE);
            currentStageIndex++;
        } else if (isAnswerStage) {
            JOptionPane.showMessageDialog(frame, "Prawidłowa odpowiedź! Poziom. " + MainFrame.currentLevel + " ukończony", StageInfo, JOptionPane.INFORMATION_MESSAGE);
            upgradeLevel();
        } else if (currentStageIndex + 1 < getCurrentMaxStageIndex()){
            currentStageIndex++;
        } else if (currentStageIndex + 1 == getCurrentMaxStageIndex()){
            JOptionPane.showMessageDialog(frame, "Poziom. " + MainFrame.currentLevel + " ukończony", StageInfo, JOptionPane.INFORMATION_MESSAGE);
            upgradeLevel();
        }
        loadLevelStage(frame);
    }

    /**
     * Upgrades the current level to the next available level if conditions are met.
     * Updates the configuration and resets the stage index.
     */
    private void upgradeLevel() {
        if (MainFrame.currentLevel < Levels.levelsList.size() && MainFrame.currentLevel == MainFrame.availableLevel) {
            currentStageIndex = 0;
            currentLevelIndex++;
            MainFrame.currentLevel++;
            MainFrame.availableLevel++;
            MainFrame.configManager.setProperty("level", String.valueOf(MainFrame.availableLevel));
            MainFrame.setLevel.updateLevelButtons();
        }
        else if (MainFrame.currentLevel < Levels.levelsList.size()) {
            currentStageIndex = 0;
            currentLevelIndex++;
            MainFrame.currentLevel++;
        }
    }

    /**
     * Starts a new game by resetting all progress and reinitializing the levels.
     *
     * @param frame the main application frame to reset and load the initial stage.
     */
    public static void NewGame(MainFrame frame) {
        currentLevelIndex = 0;
        currentStageIndex = 0;
        MainFrame.configManager.setProperty("isCompleted","no");
        MainFrame.isCompleted = "no";
        MainFrame.currentLevel = 1;
        MainFrame.availableLevel = 1;
        MainFrame.configManager.setProperty("level", String.valueOf(MainFrame.availableLevel));
        LevelManager.loadLevelStage(frame);
    }

}
