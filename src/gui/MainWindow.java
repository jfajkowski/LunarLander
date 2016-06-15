package gui;

import game.controller.GameWindowController;
import game.model.Player;
import gui.menu.MenuWindowBuilder;
import game.model.GameWindow;
import loader.LevelLoader;
import loader.LevelLoaderException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;

public class MainWindow extends JFrame implements ActionListener
{
    private static MainWindow instance = new MainWindow();
    private MainWindow() {}
    public static MainWindow getInstance() {
        return instance;
    }


    private Player currentPlayer;

    private LevelLoader levelLoader;

    private JPanel cardHolder;

    private HallOfFame bestScores;

    private GameWindowController gameWindowController;

    private JPanel createCardHolderPanel() {
        cardHolder = new JPanel(new CardLayout());

        cardHolder.add(MenuWindowBuilder.buildMainMenu(), Language.MAIN_MENU);
        cardHolder.add(MenuWindowBuilder.buildHallOfFame(), Language.HALL_OF_FAME);
        cardHolder.add(MenuWindowBuilder.buildOptions(), Language.OPTIONS);
        cardHolder.add(MenuWindowBuilder.buildShop(), Language.SHOP);
        return cardHolder;
    }

    public void nextLevel(){
        currentPlayer.appendScore(gameWindowController.getGameWindow().getScore());

        try {
            levelLoader.loadNextLevel();
            String currentLevel = levelLoader.getCurrentLevelName();
            showCurrentLevelCard(currentLevel);
        }
        catch(LevelLoaderException e){
            String currentPlayerName = currentPlayer.getName();
            int currentPlayerScore = currentPlayer.getScore();

            bestScores.addRecord(currentPlayer);
            JOptionPane.showMessageDialog(this, "Wygrałeś " + currentPlayerName + " wynik: " + currentPlayerScore);

            CardLayout cardLayout = (CardLayout) (cardHolder.getLayout());
            cardLayout.show(cardHolder, Language.MAIN_MENU);
        }
    }

    public void retry(){
        currentPlayer.resetLives();
        currentPlayer.resetScore();
        levelLoader = new LevelLoader();
        showCurrentLevelCard(levelLoader.getCurrentLevelName());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonName = e.getActionCommand();
        CardLayout cardLayout = (CardLayout) (cardHolder.getLayout());
        switch (buttonName) {
            case Language.MAIN_MENU:
                cardLayout.show(cardHolder, Language.MAIN_MENU);
                break;
            case Language.START_GAME:
                createNewPlayer();
                if(currentPlayer.getName() != null)
                {
                    levelLoader = new LevelLoader();
                    showCurrentLevelCard(levelLoader.getCurrentLevelName());
                }
                break;
            case Language.HALL_OF_FAME:
                cardLayout.show(cardHolder, Language.HALL_OF_FAME);
                break;
            case Language.OPTIONS:
                cardLayout.show(cardHolder, Language.OPTIONS);
                break;
            case Language.SHOP:
                cardLayout.show(cardHolder, Language.SHOP);
                break;
            case Language.PLAY:
                cardLayout.show(cardHolder, Language.PLAY);
                gameWindowController.startGame();
                break;
            case Language.BACK:
                cardLayout.show(cardHolder, Language.START_GAME);
                break;
        }
    }

    private void createNewPlayer(){
        String playerName = null;

        playerName = JOptionPane.showInputDialog(this, Language.ENTER_NICK, "");


        currentPlayer = new Player(playerName);
    }

    public void showCurrentLevelCard(String currentLevel){
        CardLayout cardLayout = (CardLayout) (cardHolder.getLayout());
        cardHolder.add(MenuWindowBuilder.buildCurrentLevelWindow(currentPlayer, currentLevel), Language.START_GAME);

        gameWindowController = new GameWindowController(currentLevel, currentPlayer);
        cardHolder.add(gameWindowController, Language.PLAY);

        cardLayout.show(cardHolder, Language.START_GAME);
    }

    public void initialize() {
        this.setTitle("Lunar Lander");
        this.setMinimumSize(new Dimension(640, 480));
        this.setPreferredSize(new Dimension(1024, 768));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.bestScores = new HallOfFame();
        this.add(this.createCardHolderPanel());
        this.pack();
        this.setVisible(true);
    }

    public HallOfFame getBestScores() {
        return bestScores;
    }

}