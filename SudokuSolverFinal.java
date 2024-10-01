import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class SudokuV1 extends JFrame {
    public SudokuV1() {
    }

    public static void main(String[] args) {
        SudokuV1 ss = new SudokuV1();
        ss.run();
    }

    public void run() {
        JFrame frame = new JFrame("Sudoku");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(330, 100);
        frame.setResizable(true);
        GameHolderPanel ghp = new GameHolderPanel();
        frame.getContentPane().add(ghp);
        frame.setVisible(true);
    }
}

class GameHolderPanel extends JPanel {
    CardLayout cards;
    int difficultyNum;

    public GameHolderPanel() {
        cards = new CardLayout();
        setLayout(cards);
        GreetingsPanel gp = new GreetingsPanel(cards, this);
        WinnerPanel wp = new WinnerPanel(cards, this);
        add(gp, "Greetings Panel");
        add(wp, "Winner Panel");
    }

    public void takeInt(int takeIt) {
        difficultyNum = takeIt;
        PlayGamePanel pgp = new PlayGamePanel(cards, this, difficultyNum);
        add(pgp, "Play Panel");
    }
}

class GreetingsPanel extends JPanel implements ActionListener {
    public CardLayout cards;
    public GameHolderPanel pCards;
    public final int lengthWidth = 9;
    public JTextField[][] myGrid;
    JButton startB;
    Font myFont;
    Color color;
    int r, g, b, count, colorCount;

    public GreetingsPanel(CardLayout cardsIn, GameHolderPanel pCardsIn) {
        colorCount = 0;
        cards = cardsIn;
        pCards = pCardsIn;
        startB = new JButton("Begin");
        GreetingsPanelButtons gpb = new GreetingsPanelButtons(cards, pCards);
        setLayout(new GridLayout(lengthWidth + 1, lengthWidth));
        setBackground(Color.BLACK);
        myGrid = new JTextField[lengthWidth][lengthWidth];
        r = 250;
        g = 0;
        b = 0;
        color = new Color(r, g, b);
        Timer timer = new Timer(0, this);
        timer.setDelay(5);
        timer.start();
        try {
            myFont = Font.createFont(Font.TRUETYPE_FONT, new File("/path/to/font.ttf")).deriveFont(40f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("/path/to/font.ttf")));
        } catch (FontFormatException | IOException e) {
        }
        for (int i = 0; i < lengthWidth; i++) {
            for (int j = 0; j < lengthWidth; j++) {
                myGrid[i][j] = new JTextField(1);
                myGrid[i][j].setHorizontalAlignment(JTextField.CENTER);
                myGrid[i][j].setFont(myFont);
                myGrid[i][j].setEditable(false);
                add(myGrid[i][j]);
            }
        }
    }

    public void actionPerformed(ActionEvent ae) {
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    class GreetingsPanelButtons implements ActionListener {
        public CardLayout cards;
        public GameHolderPanel pCards;

        public GreetingsPanelButtons(CardLayout cardsIn, GameHolderPanel pCardsIn) {
            cards = cardsIn;
            pCards = pCardsIn;
        }

        public void actionPerformed(ActionEvent evt) {
        }
    }
}

class PlayGamePanel extends JPanel {
    CardLayout cards;
    GameHolderPanel pCards;
    public final int lengthWidth = 9;
    public JTextField[][] myGrid;
    public Font myFont;
    int difficultyNum;

    public PlayGamePanel(CardLayout cardsIn, GameHolderPanel pCardsIn, int difficultyNumIn) {
        cards = cardsIn;
        pCards = pCardsIn;
        difficultyNum = difficultyNumIn;
        setLayout(new GridLayout(lengthWidth + 1, lengthWidth, 3, 3));
        setBackground(Color.BLACK);
        myGrid = new JTextField[lengthWidth][lengthWidth];
        try {
            myFont = Font.createFont(Font.TRUETYPE_FONT, new File("/path/to/font.ttf")).deriveFont(40f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("/path/to/font.ttf")));
        } catch (FontFormatException | IOException e) {
        }
        PlayGamePanelButtons pgpb = new PlayGamePanelButtons(cards, pCards, false);
        for (int i = 0; i < lengthWidth; i++) {
            for (int j = 0; j < lengthWidth; j++) {
                myGrid[i][j] = new JTextField(1);
                myGrid[i][j].setHorizontalAlignment(JTextField.CENTER);
                myGrid[i][j].setOpaque(false);
                myGrid[i][j].setFont(myFont);
                add(myGrid[i][j]);
            }
        }
        JButton revealButton = new JButton("Reveal");
        revealButton.addActionListener(e -> solve());
        add(revealButton);
        JButton checkButton = new JButton("Check");
        checkButton.addActionListener(e -> check());
        add(checkButton);
        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(pgpb);
        add(goBackButton);
    }

    public void solve() {
    }

    public void check() {
    }

    public boolean solver(int[][] userGrid) {
        return true;
    }

    public boolean solvable(int[][] userGrid, int row, int col, int solution) {
        return true;
    }

    class PlayGamePanelButtons implements ActionListener {
        public CardLayout cards;
        public GameHolderPanel pCards;
        public boolean winner;

        public PlayGamePanelButtons(CardLayout cardsIn, GameHolderPanel pCardsIn, boolean winnerIn) {
            cards = cardsIn;
            pCards = pCardsIn;
            winner = winnerIn;
        }

        public void actionPerformed(ActionEvent evt) {
        }
    }
}

class WinnerPanel extends JPanel {
    CardLayout cards;
    GameHolderPanel pCards;

    public WinnerPanel(CardLayout cardsIn, GameHolderPanel pCardsIn) {
        cards = cardsIn;
        pCards = pCardsIn;
        setLayout(new GridLayout(1, 3));
        setBackground(Color.BLACK);
        WinnerPanelButtons wpb = new WinnerPanelButtons(cards, pCards);
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        quitButton.addActionListener(wpb);
        add(quitButton);
        JButton displayWinButton = new JButton();
        JTextField winText = new JTextField("       You Win");
        winText.setEditable(false);
        winText.setFont(new Font("Monospaced", Font.BOLD, 20));
        winText.setBackground(Color.GREEN);
        displayWinButton.add(winText);
        displayWinButton.addActionListener(null);
        add(displayWinButton);
        JButton goBackButton = new JButton("Play Again");
        goBackButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        goBackButton.addActionListener(wpb);
        add(goBackButton);
    }

    class WinnerPanelButtons implements ActionListener {
        CardLayout cards;
        GameHolderPanel pCards;

        public WinnerPanelButtons(CardLayout cardsIn, GameHolderPanel pCardsIn) {
            cards = cardsIn;
            pCards = pCardsIn;
        }

        public void actionPerformed(ActionEvent evt) {
        }
    }
}

