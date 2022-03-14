package csd.uoc.gr.view;

import csd.uoc.gr.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.time.DayOfWeek;

/**
 * @author Konstantinos Ntavarinos
 * <p>The GraphicUI class is responsible for every frame/button the user sees on the screen,
 * also is responsible for updating the panel after an event.</p>
 */

public class GraphicUI extends JFrame{
    private JButton player1RollDice;
    private JButton player1MyDealCards;
    private JButton player1GetLoan;
    private JButton player1EndTurn;
    private JButton player2RollDice;
    private JButton player2MyDealCards;
    private JButton player2GetLoan;
    private JButton player2EndTurn;
    private JButton getDealCard;
    private JButton getMailCard;

    private JLabel[] days;
    private JLabel player1DiceImage;
    private JLabel player2DiceImage;
    private JLabel jackpotPrize;
    private JLabel[] imagesPosition;
    private JLabel player1Name;
    private JLabel player1Money;
    private JLabel player1Loan;
    private JLabel player1Bills;
    private JLabel player2Name;
    private JLabel player2Money;
    private JLabel player2Loan;
    private JLabel player2Bills;
    private JLabel bluePawn;
    private JLabel yellowPawn;

    private JPanel player1Card;
    private JPanel player2Card;

    private int currentCard = 0;
    private JTextArea infobox;
    private final ClassLoader cldr;
    private JLayeredPaneExtension mainPanel;


    /**
     * <b>constructor</b>: Creates a new window, initializes Buttons/Panels/Labels/Images.<br />
     * <b>Post conditions</b>: A new window is created with button, panels, labels and images.
     */
    public GraphicUI() {
        cldr = this.getClass().getClassLoader();
        this.setResizable(false);
        this.setTitle("PayDay");
        this.setPreferredSize(new Dimension(1333, 1000));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public void initButtons(){
        getDealCard = new JButton();
        URL dealCardURL = cldr.getResource("csd/uoc/gr/resources/gameImages/dealCard.png");
        assert dealCardURL != null;
        Image dealCardImage = new ImageIcon(dealCardURL).getImage();
        getDealCard.setIcon(new ImageIcon(dealCardImage));
        getDealCard.setBounds(1095, 505,185,95);

        getMailCard = new JButton();
        URL mailCardURL = cldr.getResource("csd/uoc/gr/resources/gameImages/mailCard.png");
        assert mailCardURL != null;
        Image mailCardImage = new ImageIcon(mailCardURL).getImage();
        getMailCard.setIcon(new ImageIcon(mailCardImage));
        getMailCard.setBounds(885, 505, 185, 95);



        player1RollDice = new JButton("Roll Dice");
        player1RollDice.setBounds(5,210, 180, 30);
        player1RollDice.setFont(new Font("Plain",Font.PLAIN,16));

        player1MyDealCards = new JButton("My Deal Cards");
        player1MyDealCards.setBounds(5,245,180,30);
        player1MyDealCards.setFont(new Font("Plain", Font.PLAIN, 16));

        player1GetLoan = new JButton("Get Loan");
        player1GetLoan.setBounds(5,280,180,30);
        player1GetLoan.setFont(new Font("Plain", Font.PLAIN, 16));

        player1EndTurn = new JButton("End Turn");
        player1EndTurn.setBounds(190,280,180,30);
        player1EndTurn.setFont(new Font("Plain", Font.PLAIN, 16));

        player1RollDice.setName("RollDice1");
        player1MyDealCards.setName("MyDealCards1");
        player1GetLoan.setName("GetLoan1");
        player1EndTurn.setName("EndTurn1");

        player1Card.add(player1Name);
        player1Card.add(player1Money);
        player1Card.add(player1Loan);
        player1Card.add(player1Bills);
        player1Card.add(player1RollDice);
        player1Card.add(player1MyDealCards);
        player1Card.add(player1GetLoan);
        player1Card.add(player1EndTurn);

        player2RollDice = new JButton("Roll Dice");
        player2RollDice.setBounds(5,210, 180, 30);
        player2RollDice.setFont(new Font("Plain",Font.PLAIN,16));

        player2MyDealCards = new JButton("My Deal Cards");
        player2MyDealCards.setBounds(5,245,180,30);
        player2MyDealCards.setFont(new Font("Plain", Font.PLAIN, 16));

        player2GetLoan = new JButton("Get Loan");
        player2GetLoan.setBounds(5,280,180,30);
        player2GetLoan.setFont(new Font("Plain", Font.PLAIN, 16));

        player2EndTurn = new JButton("End Turn");
        player2EndTurn.setBounds(190,280,180,30);
        player2EndTurn.setFont(new Font("Plain", Font.PLAIN, 16));

        player2RollDice.setName("RollDice2");
        player2MyDealCards.setName("MyDealCards2");
        player2GetLoan.setName("GetLoan2");
        player2EndTurn.setName("EndTurn2");

        player2Card.add(player2Name);
        player2Card.add(player2Money);
        player2Card.add(player2Loan);
        player2Card.add(player2Bills);
        player2Card.add(player2RollDice);
        player2Card.add(player2MyDealCards);
        player2Card.add(player2GetLoan);
        player2Card.add(player2EndTurn);


        getDealCard.setName("DealCard");
        getMailCard.setName("MailCard");

        mainPanel.add(getDealCard);
        mainPanel.add(getMailCard);
        mainPanel.repaint();
    }
    /**
     * <b>transformer</b>: Initialises the board tiles.<br/>
     * <b>Pre condition</b>: boardImages not null.<br/>
     * <b>Post condition</b>: Initialises the board tiles with the boardImages.
     * @param boardImages Array of Tiles which contains images and more.
     */
    public void initBoardTiles(ArrayList<Tile> boardImages){
        URL imageURL = cldr.getResource("csd/uoc/gr/resources/gameImages/bg_greenFix.png");
        assert imageURL != null;
        Image background = new ImageIcon(imageURL).getImage();
        mainPanel = new JLayeredPaneExtension(background);
        this.setLocation(300,0 );
        URL logoURL = cldr.getResource("csd/uoc/gr/resources/gameImages/logo.png");
        assert logoURL != null;
        Image logoImage = new ImageIcon(logoURL).getImage();
        JLabel payDayLogo = new JLabel();
        payDayLogo.setIcon(new ImageIcon(logoImage));
        payDayLogo.setBounds(0,0,840,240);
        mainPanel.add(payDayLogo,0);


        imagesPosition = new JLabel[32];
        int element = 0;
        for(int counter = 0; counter < 7; counter++,element++){
            imagesPosition[element] = new JLabel();
            URL tileImageURL = cldr.getResource(boardImages.get(element).getImagePath());
            assert tileImageURL != null;
            Image image = new ImageIcon(tileImageURL).getImage();
            imagesPosition[element].setIcon(new ImageIcon(image));
            imagesPosition[element].setBounds((counter * 120), 255, 120,140);
            mainPanel.add(imagesPosition[element]);
        }

        for(int counter = 0; counter < 7; counter++,element++){
            imagesPosition[element] = new JLabel();
            URL tileImageURL = cldr.getResource(boardImages.get(element).getImagePath());
            assert tileImageURL != null;
            Image image = new ImageIcon(tileImageURL).getImage();
            imagesPosition[element].setIcon(new ImageIcon(image));
            imagesPosition[element].setBounds((counter * 120), 400, 120,140);
            mainPanel.add(imagesPosition[element]);
        }

        for(int counter = 0; counter < 7; counter++,element++){
            imagesPosition[element] = new JLabel();
            URL tileImageURL = cldr.getResource(boardImages.get(element).getImagePath());
            assert tileImageURL != null;
            Image image = new ImageIcon(tileImageURL).getImage();
            imagesPosition[element].setIcon(new ImageIcon(image));
            imagesPosition[element].setBounds((counter * 120), 545, 120,140);
            mainPanel.add(imagesPosition[element]);
        }

        for(int counter = 0; counter < 7; counter++,element++){
            imagesPosition[element] = new JLabel();
            URL tileImageURL = cldr.getResource(boardImages.get(element).getImagePath());
            assert tileImageURL != null;
            Image image = new ImageIcon(tileImageURL).getImage();
            imagesPosition[element].setIcon(new ImageIcon(image));
            imagesPosition[element].setBounds((counter * 120), 690, 120,140);
            mainPanel.add(imagesPosition[element]);
        }

        for(int counter = 0; counter < 4; counter++,element++){
            imagesPosition[element] = new JLabel();
            URL tileImageURL = cldr.getResource(boardImages.get(element).getImagePath());
            assert tileImageURL != null;
            Image image = new ImageIcon(tileImageURL).getImage();
            imagesPosition[element].setIcon(new ImageIcon(image));
            imagesPosition[element].setBounds((counter * 120), 835, 120,140);
            mainPanel.add(imagesPosition[element]);
        }

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(mainPanel, GroupLayout.PREFERRED_SIZE,1333, GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(mainPanel, GroupLayout.PREFERRED_SIZE,1000, GroupLayout.PREFERRED_SIZE));
        pack();
        mainPanel.repaint();
    }

    /**
     * <b>transformer</b>: Initialises the days on the board.<br/>
     * <b>Post Condition</b>: Adds the days the the board.<br/>
     */
    public void initDays(){
        int thisDay = 0;
        String dayName;
        days = new JLabel[32];
        days[thisDay] = new JLabel("Start",JLabel.CENTER);
        days[thisDay].setBackground(Color.YELLOW);
        days[thisDay].setOpaque(true);
        days[thisDay].setBounds(0,240,120, 25);
        days[thisDay].setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.LIGHT_GRAY));
        days[thisDay].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        mainPanel.add(days[thisDay]);
        thisDay++;
        for(int counter = 1; counter < 7; counter++,thisDay++){
            dayName = DayOfWeek.of(counter).toString().toLowerCase();
            days[thisDay] = new JLabel(dayName.substring(0,1).toUpperCase()
                                            + dayName.substring(1) + " " + thisDay, JLabel.CENTER);
            days[thisDay].setBackground(Color.YELLOW);
            days[thisDay].setOpaque(true);
            days[thisDay].setBounds((counter * 120), 240, 120, 25);
            days[thisDay].setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.LIGHT_GRAY));
            days[thisDay].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
            mainPanel.add(days[thisDay]);
        }

        dayName = DayOfWeek.of(7).toString().toLowerCase();
        days[thisDay] = new JLabel(dayName.substring(0,1).toUpperCase()
                + dayName.substring(1) + " " + thisDay, JLabel.CENTER);
        days[thisDay].setBackground(Color.YELLOW);
        days[thisDay].setOpaque(true);
        days[thisDay].setBounds(0, 385, 120,25);
        days[thisDay].setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.LIGHT_GRAY));
        days[thisDay].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        mainPanel.add(days[thisDay]);
        thisDay++;
        for(int counter = 1; counter < 7; counter++,thisDay++){
            dayName = DayOfWeek.of(counter).toString().toLowerCase();
            days[thisDay] = new JLabel(dayName.substring(0,1).toUpperCase()
                    + dayName.substring(1) + " " + thisDay, JLabel.CENTER);
            days[thisDay].setBackground(Color.YELLOW);
            days[thisDay].setOpaque(true);
            days[thisDay].setBounds((counter * 120), 385, 120, 25);
            days[thisDay].setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.LIGHT_GRAY));
            days[thisDay].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
            mainPanel.add(days[thisDay]);
        }

        dayName = DayOfWeek.of(7).toString().toLowerCase();
        days[thisDay] = new JLabel(dayName.substring(0,1).toUpperCase()
                + dayName.substring(1) + " " + thisDay, JLabel.CENTER);
        days[thisDay].setBackground(Color.YELLOW);
        days[thisDay].setOpaque(true);
        days[thisDay].setBounds(0,530,120,25);
        days[thisDay].setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.LIGHT_GRAY));
        days[thisDay].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        mainPanel.add(days[14]);
        thisDay++;
        for(int counter = 1; counter < 7; counter++,thisDay++){
            dayName = DayOfWeek.of(counter).toString().toLowerCase();
            days[thisDay] = new JLabel(dayName.substring(0,1).toUpperCase()
                    + dayName.substring(1) + " " + thisDay, JLabel.CENTER);
            days[thisDay].setBackground(Color.YELLOW);
            days[thisDay].setOpaque(true);
            days[thisDay].setBounds((counter * 120), 530, 120, 25);
            days[thisDay].setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.LIGHT_GRAY));
            days[thisDay].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
            mainPanel.add(days[thisDay]);
        }

        dayName = DayOfWeek.of(7).toString().toLowerCase();
        days[thisDay] = new JLabel(dayName.substring(0,1).toUpperCase()
                + dayName.substring(1) + " " + thisDay, JLabel.CENTER);
        days[thisDay].setBackground(Color.YELLOW);
        days[thisDay].setOpaque(true);
        days[thisDay].setBounds(0,675,120,25);
        days[thisDay].setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.LIGHT_GRAY));
        days[thisDay].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        mainPanel.add(days[thisDay]);
        thisDay++;
        for(int counter = 1; counter < 7; counter++,thisDay++){
            dayName = DayOfWeek.of(counter).toString().toLowerCase();
            days[thisDay] = new JLabel(dayName.substring(0,1).toUpperCase()
                    + dayName.substring(1) + " " + thisDay, JLabel.CENTER);
            days[thisDay].setBackground(Color.YELLOW);
            days[thisDay].setOpaque(true);
            days[thisDay].setBounds((counter * 120), 675, 120, 25);
            days[thisDay].setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.LIGHT_GRAY));
            days[thisDay].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
            mainPanel.add(days[thisDay]);
        }

        dayName = DayOfWeek.of(7).toString().toLowerCase();
        days[thisDay] = new JLabel(dayName.substring(0,1).toUpperCase()
                + dayName.substring(1) + " " + thisDay, JLabel.CENTER);
        days[thisDay].setBackground(Color.YELLOW);
        days[thisDay].setOpaque(true);
        days[thisDay].setBounds(0,820,120,25);
        days[thisDay].setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.LIGHT_GRAY));
        days[thisDay].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        mainPanel.add(days[thisDay]);
        thisDay++;
        for(int counter = 1; counter < 4; counter++,thisDay++){
            dayName = DayOfWeek.of(counter).toString().toLowerCase();
            days[thisDay] = new JLabel(dayName.substring(0,1).toUpperCase()
                    + dayName.substring(1) + " " + thisDay, JLabel.CENTER);
            days[thisDay].setBackground(Color.YELLOW);
            days[thisDay].setOpaque(true);
            days[thisDay].setBounds((counter * 120), 820, 120, 25);
            days[thisDay].setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.LIGHT_GRAY));
            days[thisDay].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
            mainPanel.add(days[thisDay]);
        }
        mainPanel.repaint();
    }


    /**
     * <b>transformer</b>: Initialises the player card info panel with the current parameters.<br>
     * <b>Pre condition</b>: player, money, loan, bills not 0.<br>
     * <b>Post condition</b>: Initialises player card info.
     * @param player_One_Name first Players Name.
     * @param player_Two_Name second Players Name.
     * @param money players money.
     * @param loan players loan.
     * @param bills players bills.
     */
    public void initPlayerCards(String player_One_Name, String player_Two_Name, int money, int loan, int bills) {
        player1Card = new JPanel();
        player1Card.setLayout(null);
        player1Card.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.BLUE));
        player1Card.setBackground(Color.decode("0xEEEEEE"));
        player1Card.setBounds(885,30,395,320);

        player1Name = new JLabel(player_One_Name);
        player1Name.setBounds(155,0,300,30);
        player1Name.setForeground(Color.BLACK);
        player1Name.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));

        player1Money = new JLabel("Money : " + money + " Ευρώ");
        player1Money.setForeground(Color.BLACK);
        player1Money.setBounds(5,50,300,30);
        player1Money.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));

        player1Loan = new JLabel("Loan  : " + loan + " Ευρώ");
        player1Loan.setForeground(Color.BLACK);
        player1Loan.setBounds(5,80,300,30);
        player1Loan.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));

        player1Bills = new JLabel("Bills : " + bills + " Ευρώ");
        player1Bills.setForeground(Color.BLACK);
        player1Bills.setBounds(5,110,300,30);
        player1Bills.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));




        player2Card = new JPanel();
        player2Card.setLayout(null);
        player2Card.setBackground(Color.decode("0xEEEEEE"));
        player2Card.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.RED));
        player2Card.setBounds(885,620,395,320);

        player2Name = new JLabel(player_Two_Name);
        player2Name.setBounds(155,0,300,30);
        player2Name.setForeground(Color.BLACK);
        player2Name.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));

        player2Money = new JLabel("Money : " + money + " Ευρώ");
        player2Money.setForeground(Color.BLACK);
        player2Money.setBounds(5,50,300,30);
        player2Money.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));

        player2Loan = new JLabel("Loan  : " + loan + " Ευρώ");
        player2Loan.setForeground(Color.BLACK);
        player2Loan.setBounds(5,80,300,30);
        player2Loan.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));

        player2Bills = new JLabel("Bills : " + bills + " Ευρώ");
        player2Bills.setForeground(Color.BLACK);
        player2Bills.setBounds(5,110,300,30);
        player2Bills.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));


        mainPanel.add(player1Card);
        mainPanel.add(player2Card);
        mainPanel.repaint();
    }

    /**
     * <b>transformer</b>: Initialises the images of the pawns and the position.<br/>
     * <b>Pre condition</b>: pathPawn1, pathPawn2, not null and position1, position2 == 0.<br/>
     * <b>Post condition</b>: Creates the pawns.
     */
    public void initPawns(){
        bluePawn = new JLabel();
        URL bluePawnURL = cldr.getResource("csd/uoc/gr/resources/gameImages/pawn_blue.png");
        assert bluePawnURL != null;
        Image bluePawnImage = new ImageIcon(bluePawnURL).getImage();
        bluePawn.setIcon(new ImageIcon(bluePawnImage));
        bluePawn.setBounds(imagesPosition[0].getX() + 55, imagesPosition[0].getY(), imagesPosition[0].getWidth(), imagesPosition[0].getHeight());
        bluePawn.setOpaque(false);
        mainPanel.add(bluePawn, 1);

        yellowPawn = new JLabel();
        URL yellowPawnURL = cldr.getResource("csd/uoc/gr/resources/gameImages/pawn_yellow.png");
        assert yellowPawnURL != null;
        Image yellowPawnImage = new ImageIcon(yellowPawnURL).getImage();
        yellowPawn.setIcon(new ImageIcon(yellowPawnImage));
        yellowPawn.setBounds(imagesPosition[0].getBounds());
        yellowPawn.setOpaque(false);
        mainPanel.add(yellowPawn, 1);
        mainPanel.repaint();
    }

    /**
     * <b>transformer</b>: if player == 1, initialises player1 dice image,
     * if player == 2, initialises player 2 dice image.<br/>
     * <b>Pre condition</b>:
     * <b>Post condition</b>
     * @param diceImagePath path of the dice image.
     */
    public void initDiceImage(String diceImagePath) {
        player1DiceImage = new JLabel();
        URL player1DiceImageURL = cldr.getResource(diceImagePath);
        assert player1DiceImageURL != null;
        Image player1DiceIcon = new ImageIcon(player1DiceImageURL).getImage();
        player1DiceImage.setIcon(new ImageIcon(player1DiceIcon));
        player1DiceImage.setBounds(215,150,130,110);


        player2DiceImage = new JLabel();
        URL player2DiceImageURL = cldr.getResource(diceImagePath);
        assert player2DiceImageURL != null;
        Image player2DiceIcon = new ImageIcon(player2DiceImageURL).getImage();
        player2DiceImage.setIcon(new ImageIcon(player2DiceIcon));
        player2DiceImage.setBounds(215,150,130,110);


        player1Card.add(player1DiceImage);
        player2Card.add(player2DiceImage);
        mainPanel.repaint();
    }

    /**
     * <b>transformer</b>: initialises the info box.<br />
     * <b>Post condition</b>: displays message in the info box.
     * @param message message to display in the info box.
     */
    public void initInfoBox(String message){
        infobox = new JTextArea();
        infobox.setEditable(false);
        infobox.setBounds(885,370,395,115);
        infobox.setFont(new Font("Plain", Font.PLAIN,16));
        infobox.setForeground(Color.BLACK);
        infobox.setBackground(Color.WHITE);
        infobox.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.BLACK));
        infobox.setOpaque(true);
        infobox.setText(message);
        mainPanel.add(infobox);
        mainPanel.repaint();
    }

    /**
     * <b>transformer</b>: initialises jackpot prize.<br/>
     * <b>Pre condition</b>: prize >= 0.<br/>
     * <b>Post condition</b> sets jackpot prize to prize.
     * @param prize jackpot starting prize
     */
    public void initJackpot(int prize) {
        JLabel jackpot = new JLabel();
        jackpotPrize = new JLabel("Jackpot: " + prize + " Ευρώ");
        URL jackpotURL = cldr.getResource("csd/uoc/gr/resources/gameImages/jackpot.png");
        assert jackpotURL != null;
        Image jackpotImage = new ImageIcon(jackpotURL).getImage();
        jackpot.setIcon(new ImageIcon(jackpotImage));
        jackpot.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));
        jackpot.setBounds(530,825,213,110);
        jackpotPrize.setBounds(530,935,270,30);
        jackpotPrize.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 18));
        jackpotPrize.setForeground(Color.WHITE);
        mainPanel.add(jackpot);
        mainPanel.add(jackpotPrize);
        mainPanel.repaint();
    }


    /**
     * <b>transformer</b>: Updates the board tiles with new tiles<br/>
     * <b>Pre condition</b>: newMonthBoardTiles != NULL<br/>
     * <b>Post condition</b>: Updates current board tiles, with new shuffled tiles.
     * @param newMonthBoardTiles the Tiles.
     */
    public void updateBoardTiles(ArrayList<Tile> newMonthBoardTiles){
        for(JLabel label : imagesPosition){
            label.setVisible(false);
        }
        imagesPosition = new JLabel[32];
        int element = 0;
        for(int counter = 0; counter < 7; counter++,element++){
            imagesPosition[element] = new JLabel();
            URL tileImageURL = cldr.getResource(newMonthBoardTiles.get(element).getImagePath());
            assert tileImageURL != null;
            Image image = new ImageIcon(tileImageURL).getImage();
            imagesPosition[element].setIcon(new ImageIcon(image));
            imagesPosition[element].setBounds((counter * 120), 255, 120,140);
            mainPanel.add(imagesPosition[element]);
        }

        for(int counter = 0; counter < 7; counter++,element++){
            imagesPosition[element] = new JLabel();
            URL tileImageURL = cldr.getResource(newMonthBoardTiles.get(element).getImagePath());
            assert tileImageURL != null;
            Image image = new ImageIcon(tileImageURL).getImage();
            imagesPosition[element].setIcon(new ImageIcon(image));
            imagesPosition[element].setBounds((counter * 120), 400, 120,140);
            mainPanel.add(imagesPosition[element]);
        }

        for(int counter = 0; counter < 7; counter++,element++){
            imagesPosition[element] = new JLabel();
            URL tileImageURL = cldr.getResource(newMonthBoardTiles.get(element).getImagePath());
            assert tileImageURL != null;
            Image image = new ImageIcon(tileImageURL).getImage();
            imagesPosition[element].setIcon(new ImageIcon(image));
            imagesPosition[element].setBounds((counter * 120), 545, 120,140);
            mainPanel.add(imagesPosition[element]);
        }

        for(int counter = 0; counter < 7; counter++,element++){
            imagesPosition[element] = new JLabel();
            URL tileImageURL = cldr.getResource(newMonthBoardTiles.get(element).getImagePath());
            assert tileImageURL != null;
            Image image = new ImageIcon(tileImageURL).getImage();
            imagesPosition[element].setIcon(new ImageIcon(image));
            imagesPosition[element].setBounds((counter * 120), 690, 120,140);
            mainPanel.add(imagesPosition[element]);
        }

        for(int counter = 0; counter < 4; counter++,element++){
            imagesPosition[element] = new JLabel();
            URL tileImageURL = cldr.getResource(newMonthBoardTiles.get(element).getImagePath());
            assert tileImageURL != null;
            Image image = new ImageIcon(tileImageURL).getImage();
            imagesPosition[element].setIcon(new ImageIcon(image));
            imagesPosition[element].setBounds((counter * 120), 835, 120,140);
            mainPanel.add(imagesPosition[element]);
        }

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(mainPanel, GroupLayout.PREFERRED_SIZE,1333, GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(mainPanel, GroupLayout.PREFERRED_SIZE,1000, GroupLayout.PREFERRED_SIZE));
        pack();
        for(JLabel label : imagesPosition){
            label.setVisible(true);
        }
        mainPanel.repaint();

    }

    /**
     * <b>transformer</b>: Updates player card panel.<br>
     * <b>Pre condition</b>: player ==1 or player == 2 and money, loan, bills >= 0.<br/>
     * <b>Post condition</b>:Updates player card info panel with the new parameters.
     * @param player current player(if == 1 player1, if == 2 player2).
     * @param money new amount of money.
     * @param loan new loan amount.
     * @param bills new bills amount .
     */
    public void updatePlayerCard(Player player, int money, int loan, int bills){
        if (player instanceof Player1) {
            player1Money.setText("Money : " + money + " Ευρώ");
            player1Loan.setText("Loan  : " + loan + " Ευρώ");
            player1Bills.setText("Bills : " + bills + " Ευρώ");
        }
        else{
            player2Money.setText("Money : " + money + " Ευρώ");
            player2Loan.setText("Loan  : " + loan + " Ευρώ");
            player2Bills.setText("Bills : " + bills + " Ευρώ");
        }
    }

    /**
     * <b>transformer</b>: Updates dice image.<br/>
     * <b>Pre condition</b>: player != NULL.<br/>
     * <b>Post condition</b>: Changes the image of the dice.<br/>
     * @param player player to update the image
     * @param dice dice number.
     */
    public void updateDiceImage(Player player, int dice){
        URL diceImageURL = cldr.getResource("csd/uoc/gr/resources/gameImages/dice-" + dice + ".jpg");
        assert diceImageURL != null;
        Image diceImage = new ImageIcon(diceImageURL).getImage();
        if (player instanceof Player1)
            player1DiceImage.setIcon(new ImageIcon(diceImage));
        else
            player2DiceImage.setIcon(new ImageIcon(diceImage));
    }

    /**
     * <b>transformer</b>: Updates the info box.<br />
     * <b>Post condition</b>: Updates the info box, with a new message.
     * @param message message to show in the info box.
     */
    public void updateInfoBox(String message) {
        this.infobox.setText(message);
        mainPanel.repaint();
    }

    /**
     * <b>transformer</b>: Updates jackpot prize.<br/>
     * <b>Pre condition</b>: prize > 0.<br/>
     * <b>Post condition</b>: Change the prize of jackpot.
     * @param prize new prize for jackpot.
     */
    public void updateJackpotPrize(int prize){
        this.jackpotPrize.setText("Jackpot: " + prize + " Ευρώ");
        mainPanel.repaint();
    }

    /**
     * Open a new dialog with a text field so the user can enter the loan he wants.<br/>
     * <b>Post condition</b>: Open new Dialog.
     * @return int value of the user input.
     */
    public int openLoanDialog(){
        String input = JOptionPane.showInputDialog(mainPanel,"Πληκτρολόγησε το ποσό που θέλεις.", "Get Loan", JOptionPane.QUESTION_MESSAGE);
        if (input == null || input.isEmpty())
            return 0;
        while(Integer.parseInt(input) % 1000 != 0 || Integer.parseInt(input) == 0){
            input = JOptionPane.showInputDialog(mainPanel,"Λάθος τιμή!Αποδεκτές τιμές: Χ % 1000 == 0.\nΠληκτρολόγησε το ποσό που θέλεις.", "Get Loan",JOptionPane.ERROR_MESSAGE);
        }
        return Integer.parseInt(input);
    }

    /**
     * <b>observer</b>: This method check if the player is on Sunday label.<br/>
     * <b>Pre condition</b>: position > 0;<br/>
     * <b>Post condition</b>: Returns true if the player is on Sunday label, else false.
     * @return true/false.
     */
    public boolean sundayFootball(int position){
        return days[position].getText().equals("Sunday " + position);
    }

    /**
     * Upon calling this method the user can choose to place a bet on a football match or not.<br/>
     * <b>Post condition</b>: Opens a dialog so the user can choose if he wants to bet or not.
     * @return choice of the user.
     */
    public int betOnFootballMatch(){
        Object[] options = {"Νίκη Μπαρτσελόνα" , "Ισοπαλία", "Νίκη Ρεαλ Μαδρίτης" , "Δεν θέλω να κάνω πρόβλεψη"};
        URL imageURL = cldr.getResource("csd/uoc/gr/resources/gameImages/Barcelona_Real.jpg");
        assert imageURL != null;
        Image image = new ImageIcon(imageURL).getImage();
        image = image.getScaledInstance(175,75,Image.SCALE_SMOOTH);
        String tab = "                                                                                                            ";
        return JOptionPane.showOptionDialog(mainPanel,
                "Στοιχιμάτησε 500 Ευρώ στο El Classico",
                tab + "La Liga match",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon(image),
                options,
                options[0]);

    }
    /**
     * <b>observer</b>: This method checks if the player is on Thursday label.<br/>
     * <b>Pre condition</b>: position > 0.<br/>
     * <b>Post condition</b>: Returns true if the player is on Thursday label, else false.
     * @return true/false.
     */
    public boolean cryptoThursday(int position) {
        return days[position].getText().equals("Thursday " + position);
    }

    /**
     * Upon calling this method the user can invest on a cryptocurrency.<br/>
     * <b>Post condition</b>: Opens a dialog and the user can choose to invest or not on a cryptocurrency.
     * @return choice of the user.
     */
    public int betOnCrypto(){
        Object[] options = {"Πόνταρε 300 Ευρώ στο κρυπτονόμισμα" , "Παράβλεψε το ποντάρισμα"};
        URL imageURL = cldr.getResource("csd/uoc/gr/resources/gameImages/crypto.jpeg");
        assert imageURL != null;
        Image image = new ImageIcon(imageURL).getImage();
        image = image.getScaledInstance(120,115, Image.SCALE_SMOOTH);
        return JOptionPane.showOptionDialog(mainPanel,
                "Ποντάρισμα στο κρυπτονόμισα SHIBA INU",
                "Crypto Thursday",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon(image),
                options,
                options[0]);
    }

    /**
     * <b>transformer</b>: Updates pawn images, "moves" pawn to new position.<br/>
     * <b>Pre condition</b>: positionToMovePawn > 0, positionOfSecondPawn> 0, position player != NULL. Tiles != NULL.<br/>
     * <b>Post condition</b>: Moves pawn to new Tile on the board.<br/>
     * @param positionToMovePawn position of pawn.
     * @param player current player.
     * @param positionOfSecondPawn the position of the second Pawn.
     * @param Tiles the tiles of the board.
     * @return Tile to which the pawn is after moving.
     */
    public Tile updatePawn(Player player, int positionToMovePawn, int positionOfSecondPawn, ArrayList<Tile> Tiles){
        if(positionToMovePawn == positionOfSecondPawn){
            if(player instanceof Player1) {
                bluePawn.setBounds(imagesPosition[positionToMovePawn].getX() + 55
                        , imagesPosition[positionToMovePawn].getY()
                        , imagesPosition[positionToMovePawn].getWidth()
                        , imagesPosition[positionToMovePawn].getHeight());
            }
            else {
                yellowPawn.setBounds(imagesPosition[positionToMovePawn].getX() + 55
                        , imagesPosition[positionToMovePawn].getY()
                        , imagesPosition[positionToMovePawn].getWidth()
                        , imagesPosition[positionToMovePawn].getHeight());
            }
        }
        else {
            if (player instanceof Player1)
                bluePawn.setBounds(imagesPosition[positionToMovePawn].getBounds());
            else
                yellowPawn.setBounds(imagesPosition[positionToMovePawn].getBounds());
        }
        return Tiles.get(positionToMovePawn);
    }

    /**
     * <b>transformer</b>: Reset the pawns at the first day of the month, to the Start Tile.<br/>
     * <b>Post condition</b>: changes the position of the pawns.
     */
    public void resetPawns(){
        bluePawn.setBounds(imagesPosition[0].getX() + 55, imagesPosition[0].getY(), imagesPosition[0].getWidth(), imagesPosition[0].getHeight());
        yellowPawn.setBounds(imagesPosition[0].getBounds());
        mainPanel.repaint();
    }


    /**
     * <b>transformer</b>: When starting the game this method is used to read from a dialog the players names.<br/>
     * <b>Pre condition</b>: player != NULL.<br/>
     * <b>Post condition</b>: returns the String the user entered.
     * @param player current player.
     * @return a String with the name of the player.
     */
    public String readPlayersName(String player){
        return JOptionPane.showInputDialog("Πληκτρολόγησε το ονομα του παίχτη " + player);
    }

    /**
     * <b>transformer</b>: Opens a dialog so the user can choose how many months to play.<br/>
     * <b>Post condition</b>: returns the users choice.
     * @return users choice.
     */
    public int enterMonthsToPlay(){
        Integer[] options = {1,2,3};
        return (Integer)JOptionPane.showInputDialog(mainPanel,"Επιλέξτε πόσους μήνες θέλετε να παίξετε:"
                ,"Μήνες",JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
    }


    /**
     * <b>observer</b>: Displaying a message through a dialog.<br/>
     * <b>Pre condition</b>: message != NULL, amount >= 0.<br/>
     * <b>Post condition</b>: Opens a dialog with a message and an amount of money.
     * @param message message to display.
     * @param amount amount of money to display.
     */
    public void displayMessageThrowWindow(String message, int amount){
        JOptionPane.showMessageDialog(mainPanel,message + " " + amount + " Ευρώ.");
    }

    /**
     * Opens dialog and the players can choose a number from 1 to 6.
     * the number the first player choose is being deleted from the options so
     * the other player cannot choose the same number.<br/>
     * <b>Pre condition</b>: player != NULL, number between 1-6.<br/>
     * <b>Post condition</b>: returns the number the player choose.
     * @param player current player.
     * @param number number the last player choose.
     * @return number the current player choose.
     */
    public int lotteryChooseNumber(String player, int number) {
        Integer[] options = {1, 2, 3, 4, 5, 6};

        if (number != 0) {
            Integer[] newOptions = new Integer[options.length - 1];
            int cnt2 = 0;
            for (Integer option : options) {
                if (option != number) {
                    newOptions[cnt2] = option;
                    cnt2++;
                }
            }
            return (Integer) JOptionPane.showInputDialog(mainPanel, player + " επέλεξε αριθμό."
                    , "Lottery", JOptionPane.INFORMATION_MESSAGE, null, newOptions, newOptions[0]);
        }
            return (Integer) JOptionPane.showInputDialog(mainPanel, player + " επέλεξε αριθμό."
                    , "Lottery", JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

    }

    /**
     * This method is being called upon the player reaching PayDay Tile, opens a dialog and
     * asks the user if he wants to repay his loan.<br/>
     * <b>Pre condition</b>: amount > 0 <br/>
     * <b>Post condition</b>: Opens a dialog.
     * @param amount amount of the current loan.
     * @return choice of the user.
     */
    public int loanRepayment(int amount){
        int result = JOptionPane.showConfirmDialog(mainPanel,"Θες να ξεπληρώσεις το δάνειο σου;"
                ,"Ξεπλήρωμα δανείου"
                ,JOptionPane.YES_NO_OPTION
                ,JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_NO_OPTION)
            return openLoanDialog(amount);
        return 0;
    }

    /**
     * Opens a dialog so the user can enter amount of loan he wants to repay.<br/>
     * <b>Pre condition</b>: amount > 0<br/>
     * <b>Post condition</b>: opens a dialog for the user to repay loan.
     * @param amount current loan.
     * @return users input.
     */
    public int openLoanDialog(int amount){
        String input = JOptionPane.showInputDialog(mainPanel,"Πληκτρολόγησε το ποσό που θες να ξεπληρώσεις", "Ξεπλήρωμα δανείου", JOptionPane.QUESTION_MESSAGE);
        if (input == null || input.isEmpty())
            return 0;
        while(Integer.parseInt(input) % 1000 != 0 || Integer.parseInt(input) == 0 || Integer.parseInt(input) > amount){
            input = JOptionPane.showInputDialog(mainPanel,"Λάθος τιμή!\nΠληκτρολόγησε το ποσό που θες να ξεπληρώσεις", "Ξεπλήρωμα δανείου",JOptionPane.ERROR_MESSAGE);
        }
        return Integer.parseInt(input);
    }

    /**
     * Opens a dialog so the user can take loan he must pay, or wishes to buy a deal Card
     * and does not have enough money.<br/>
     * <b>Pre condition</b>: amount > 0.<br/>
     * <b>Post condition</b> Opens a dialog so the user can take loan.
     * @param amount current amount of the card.
     * @return users input.
     */
    public int openLoanDialogForCard(int amount){
        String input = JOptionPane.showInputDialog(mainPanel,"Πληκτρολόγησε το δάνειο που θέλεις", "Get Loan", JOptionPane.QUESTION_MESSAGE);
        if (input == null || input.isEmpty())
            return 0;
        while(Integer.parseInt(input) % 1000 != 0 || Integer.parseInt(input) == 0 || Integer.parseInt(input) < amount){
            input = JOptionPane.showInputDialog(mainPanel,"Λάθος τιμή\nΤο δάνειο πρεπει να είναι μεγαλύτερο απο  " + amount + ".", "Get Loan",JOptionPane.ERROR_MESSAGE);
        }
        return Integer.parseInt(input);
    }

    /**
     * <b>observer</b>: Displays a Deal card through a dialog. User can either buy or ignore the card.<br/>
     * <b>Pre condition</b>: card != NULL<br/>
     * <b>Post condition</b>: Displays the Deal Card.
     * @param card card to display.
     * @return users choice.
     */
    public int showDealCard(DealCard card){
        Object[] options = {card.getChoice1(), card.getChoice2()};
        URL imageURL = cldr.getResource(card.getIconPath());
        assert imageURL != null;
        Image image = new ImageIcon(imageURL).getImage();
        image  = image.getScaledInstance(200,200, Image.SCALE_SMOOTH);
        return  JOptionPane.showOptionDialog(mainPanel,
                card.getMessage() + "\nΤιμή αγοράς: " + card.getBuyCost() + " Ευρώ \n Τιμή πώλησης: " + card.getSaleCost() + " Ευρώ \n",
                "Κάρτα Συμφωνίας",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon(image),
                options,
                options[0]);

    }

    /**
     * <b>observer</b>: Displays a Mail card through a dialog.<br/>
     * <b>Pre condition</b>: card != NULL<br/>
     * <b>Post condition</b>: Displays the Mail Card.
     * @param card card to display.
     */
    public void showMailCard(MailCard card){
        Object[] options = {card.getChoiceMessage()};
        URL imageURL = cldr.getResource(card.iconPath());
        assert imageURL != null;
        Image image = new ImageIcon(imageURL).getImage();
        image = image.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
        JOptionPane.showOptionDialog(mainPanel,
                card.getMessage(),
                card.getType(),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon(image),
                options,
                options[0]);
    }

    /**
     * <b>accessor</b>: Displays through dialog the players Deal Cards.<br/>
     * <b>Pre condition</b>: dealCards != NULL<br/>
     * <b>Post condition</b>: Shows the player his deal Cards.
     * @param dealCards players Deal cards.
     */
    public void showMyDealCards(ArrayList<DealCard> dealCards){
        JFrame dealFrame = new JFrame("My deal cards.");
        dealFrame.setSize(350,250);
        dealFrame.setLocation(860,440);
        dealFrame.setVisible(true);
        JPanel cardPanel = new JPanel();

        JPanel buttonsPanel = new JPanel();

        CardLayout cl = new CardLayout();

        JPanel[] dealPanel = new JPanel[dealCards.size()];
        JLabel[] dealLabels = new JLabel[dealCards.size()];

        JLabel[] saleLabel = new JLabel[dealCards.size()];
        JButton next = new JButton("Επόμενη");
        JButton previous = new JButton("Προηγούμενη");
        cardPanel.setLayout(cl);
        for(int counter = 0; counter < dealCards.size(); counter++){
            dealLabels[counter] = new JLabel();
            URL imageURL = cldr.getResource(dealCards.get(counter).getIconPath());
            assert imageURL != null;
            Image image = new ImageIcon(imageURL).getImage();
            image = image.getScaledInstance(150,150, Image.SCALE_SMOOTH);
            dealLabels[counter].setIcon(new ImageIcon(image));
            dealLabels[counter].setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.BLACK));
            saleLabel[counter] = new JLabel(dealCards.get(counter).getSaleCost() + " Ευρώ");


            dealPanel[counter] = new JPanel();
            dealPanel[counter].add(dealLabels[counter]);
            dealPanel[counter].add(saleLabel[counter]);
            cardPanel.add(dealPanel[counter], String.valueOf(counter));
        }

        buttonsPanel.add(previous);
        buttonsPanel.add(next);
        next.addActionListener(event -> {
            if(currentCard < dealCards.size()){
                currentCard += 1;
                cl.show(cardPanel, "" + (currentCard));
            }
        });

        previous.addActionListener(event -> {
            if (currentCard > 0){
                currentCard -= 1;
                cl.show(cardPanel, "" + (currentCard));
            }
        });

        dealFrame.add((cardPanel), BorderLayout.NORTH);
        dealFrame.add((buttonsPanel), BorderLayout.SOUTH);
    }

    /**
     * <b>accessor</b>: Displays the players Deal cards so he can choose one of them to sell.<br/>
     * <b>Pre condition</b>: dealCards != NULL<br/>
     * <b>Post condition</b>: Players chooses a deal card and returns a String of the card he choose.
     * @param dealCards Deal cards of the player.
     * @return String of the card the player choose
     */
    public String chooseDealCardToSell(ArrayList<DealCard> dealCards){
        Object[] options = new Object[dealCards.size()];
        for (int cnt1 = 0; cnt1 < dealCards.size(); cnt1++){
            options[cnt1] = dealCards.get(cnt1).getMessage() +
                    ", πούλησε την κάρτα για " +
                    dealCards.get(cnt1).getSaleCost() + " Ευρώ";
        }
        return (String)JOptionPane.showInputDialog(mainPanel, "Επέλεξε την κάρτα συμφωνίας που θες να πουλήσεις:"
                ,"My deal cards", JOptionPane.QUESTION_MESSAGE, null, options,options[0]);

    }

    /**
     * Shows the winner throw dialog.<br/>
     * <b>Pre condition</b>: player != NULL<br/>
     * <b>Post condition</b>: shows the winner.
     * @param player player who won.
     */
    public void showWinner(Player player){
        JOptionPane.showMessageDialog(mainPanel,
                "Συγχαρητήρια " + player.getName() + " Νίκησες!\n",
                "Winner winner chicken dinner",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Adds Listener to the Roll Dice buttons.<br/>
     * <b>Pre condition</b>: roll != NULL<br/>
     * <b>Post condition</b>: adds Listener to Roll Dice buttons.
     * @param roll Listener
     */
    public void addRollDiceListener(ActionListener roll){
        this.player1RollDice.addActionListener(roll);
        this.player2RollDice.addActionListener(roll);
    }

    /**
     * Adds Listener to the My Deal Cards buttons.<br/>
     * <b>Pre condition</b>: getDealCards != NULL<br/>
     * <b>Post condition</b>: adds Listener to players My Deal Card buttons.
     * @param getDealCards Listener.
     */
    public void addMyDealCardsActionListener(ActionListener getDealCards){
        this.player1MyDealCards.addActionListener(getDealCards);
        this.player2MyDealCards.addActionListener(getDealCards);
    }

    /**
     * Adds Listener to the Get Loan buttons.<br/>
     * <b>Pre condition</b>: loan != NULL<br/>
     * <b>Post condition</b>: adds Listener to Get Loan buttons.
     * @param loan Listener
     */
    public void addGetLoanListener(ActionListener loan){
        this.player1GetLoan.addActionListener(loan);
        this.player2GetLoan.addActionListener(loan);
    }

    /**
     * Adds Listener to the End Turn buttons.<br/>
     * <b>Pre condition</b>: endTurn != NULL<br/>
     * <b>Post condition</b>: adds Listener to End Turn buttons.
     * @param endTurn Listener
     */
    public void addEndTurnListener(ActionListener endTurn){
        this.player1EndTurn.addActionListener(endTurn);
        this.player2EndTurn.addActionListener(endTurn);
    }

    /**
     * Adds Listener to the Mail/Deal card buttons.<br/>
     * <b>Pre condition</b>: cardListener != NULL<br/>
     * <b>Post condition</b>: adds Listener Mail/Deal card buttons.
     * @param cardListener Listener
     */
    public void addCardListener(ActionListener cardListener){
        this.getMailCard.addActionListener(cardListener);
        this.getDealCard.addActionListener(cardListener);
    }
}