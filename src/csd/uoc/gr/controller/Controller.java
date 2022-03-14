package csd.uoc.gr.controller;

import csd.uoc.gr.model.*;
import csd.uoc.gr.view.GraphicUI;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Random;


/**
 * @author Konstantinos Ntavarinos <br/>
 * csd4284 <b>at</b> csd.uoc.gr<br/>
 * Class controller is responsible for the communication between View and Model.
 */

public class Controller{
    private final Random random;
    private final Player1 player1;
    private final Player2 player2;
    private final Board board;
    private final GraphicUI view;
    private int loanRequest;
    private Tile currentTile;

    /**
     * <b>Constructor</b>: creates the view, board and the players.
     */
    public Controller(){
        view = new GraphicUI();
        board = new Board(view.enterMonthsToPlay());
        player1 = new Player1(view.readPlayersName("1"),3500,0,0);
        player2 = new Player2(view.readPlayersName("2"),3500,0,0);
        random = new Random();
    }

    /**
     * <b>transformer</b>: calls the functions to initialise the game components, and starts the game.
     */
    public void startGame(){
        playSound("Shuffle.wav");
        board.readMailCards();
        board.readDealCards();
        board.readBoardTiles();

        view.initBoardTiles(board.getBoardTiles());
        view.initDays();
        view.initInfoBox("Info Box\n" + "Months left: " + board.getMonths() + "\n==> Παίζει ο παίχτης " + giveStartingTurn());
        view.initJackpot(0);
        view.initPlayerCards(player1.getName(), player2.getName() ,3500,0,0);
        view.initButtons();
        view.initDiceImage("csd/uoc/gr/resources/gameImages/dice-1.jpg");
        view.initPawns();

        view.setVisible(true);
        view.addRollDiceListener(new RollDiceListener());
        view.addEndTurnListener(new EndTurnListener());
        view.addGetLoanListener(new LoanListener());
        view.addCardListener(new CardListener());
        view.addMyDealCardsActionListener(new MyDealCardsListener());
    }

    /**
     * <b>observer</b>: checks if the game is over.
     * @return true/false.
     */
    private boolean isGameOver(){
        return board.getMonths() == 0;
    }

    /**
     * <b>transformer</b>: Random chooses player to play first.
     * @return number of the player who has turn.
     */
    private String giveStartingTurn(){
        int turn = random.nextInt((2 - 1) + 1) + 1;
        if(turn == 1) {
            player1.setTurn(true);
            return String.valueOf(turn);
        }
        player2.setTurn(true);
        return String.valueOf(turn);
    }

    /**
     * <b>observer</b>: Checks if both players have reached pay day.
     * @return true/false.
     */
    private boolean bothPlayersReachedPayDay(){
        return player1.getPosition() == 31 && player2.getPosition() == 31;
    }

    /**
     * <b>observer</b>: Checks the status of the game.
     * If both players reached payday and its the last month,declares the winner.
     * If its not the last month it restarts the game.
     */
    private void checkGame(){
        if (bothPlayersReachedPayDay()){
            board.deductMonth();
            if (isGameOver()){
                declareWinner();
                System.exit(0);
            }
            board.shuffleTiles();
            view.updateBoardTiles(board.getBoardTiles());
            player1.setReachedPayDayPosition(false);
            player1.setPosition(0);
            player1.setTurn(false);
            player1.setRolledDice(false);
            player2.setReachedPayDayPosition(false);
            player2.setPosition(0);
            player2.setTurn(false);
            player2.setRolledDice(false);
            view.resetPawns();
            playSound("Shuffle.wav");
            view.updateInfoBox("Info Box\n" + "Months left: " + board.getMonths() + "\n==> Παίζει ο παίχτης " + giveStartingTurn());
        }
    }

    /**
     * Calculates each players total balance and declares the winner.
     */
    private void declareWinner(){
        int player1Balance = player1.getMoney() - (player1.getLoan() + player1.getBills());
        int player2Balance = player2.getMoney() - (player2.getLoan() + player2.getBills());
        if (player1Balance > player2Balance){
            view.initInfoBox("Info Box\n" + "Months left: " + board.getMonths() + "\n==> Maybe next time " + player2.getName());
            view.showWinner(player1);
        }
        else {
            view.initInfoBox("Info Box\n" + "Months left: " + board.getMonths() + "\n==> Maybe next time " + player1.getName());
            view.showWinner(player2);
        }
    }

    /**
     * <b>transformer</b>: If the user decides to invest in a cryptocurrency this method is being called
     * to let the user know if he won/lost money.<br/>
     * Else it continues the game.<br/>
     * <b>Pre condition</b>: player != NULL.<br/>
     * <b>Post condition</b>: add/deduct money to player.
     * @param player current player.
     */
    private void observeCryptoStatus(Player player){
        int choice = view.betOnCrypto();
        switch (choice){
            case 0:
                checkBalance(player,300);
                int cryptoRoll = random.nextInt((6 - 1) + 1) + 1;
                if (cryptoRoll == 1 || cryptoRoll == 2) {
                    view.displayMessageThrowWindow("Το κρυπτονόμισμα έπεσε!\nΧάνεις", 300);
                    player.deductMoney(300);
                    view.updatePlayerCard(player, player.getMoney(), player.getLoan(), player.getBills());
                }
                else if (cryptoRoll == 3 || cryptoRoll == 4 ){
                    view.displayMessageThrowWindow("Το κρυπτονόμισμα σταθεροποιήθηκε!\nΠαίρνεις πίσω", 300);
                }
                else{
                    view.displayMessageThrowWindow("Το κρυπτονόμισμα εκτοξέυθηκε!\nΠάρε ", 600);
                    player.addMoney(600);
                    view.updatePlayerCard(player, player.getMoney(), player.getLoan(), player.getBills());
                }
                break;
            case 1:
                break;
        }
    }


    /**
     * <b>observer</b>: Checks the currents player balance, if the players money is less than
     * the maxAmount, user automatically gets a loan.<br/>
     * <b>Pre condition</b>: Player != NULL, maxAmount > 0.<br/>
     * <b>Post condition</b>: adds a loan to the player if necessary.
     * @param player current player.
     * @param maxAmount money to compare.
     */
    private void checkBalance(Player player, int maxAmount){
        if (player.getMoney() < maxAmount){
            player.addLoan(1000);
            player.addMoney(1000);
        }
    }

    /**
     * <b>transformer</b>: The player can bet on a football match, this method changes the players money if
     * his guess is correct, if the player decides not to bet it continues the game.<br/>
     * <b>Pre condition</b>: player != NULL.<br/>
     * <b>Post condition</b>: player != NULL.<br/>
     * @param player current player.
     */
    private void spectateFootballMatch(Player player){
        int choice = view.betOnFootballMatch();
        int matchRoll = random.nextInt((6 - 1) + 1) + 1;
        switch (choice){
            case 0:
                checkBalance(player,500);
                if (matchRoll == 1 || matchRoll == 2) {
                    view.displayMessageThrowWindow("Σωστή πρόβλεψη! Barcelona - Real Madrid 2-1.\nΚέρδισες ", 1000);
                    player.addMoney(1000);
                }
                else{
                    view.displayMessageThrowWindow("Λάθος πρόβλεψη! Barcelona - Real Madrid 0-1.\nΈχασες ", 500);
                    player.deductMoney(500);
                }
                view.updatePlayerCard(player, player.getMoney(), player.getLoan(), player.getBills());
                break;
            case 1:
                checkBalance(player,500);
                if (matchRoll == 3 || matchRoll == 4) {
                    view.displayMessageThrowWindow("Σωστή πρόβλεψη! Barcelona - Real Madrid 2-2.\nΚέρδισες ", 1000);
                    player.addMoney(1000);
                }
                else{
                    view.displayMessageThrowWindow("Λάθος πρόβλεψη! Barcelona - Real Madrid 3-1.\nΈχασες ", 500);
                    player.deductMoney(500);
                }
                view.updatePlayerCard(player, player.getMoney(), player.getLoan(), player.getBills());
                break;
            case 2:
                checkBalance(player,500);
                if (matchRoll == 5 || matchRoll == 6) {
                    view.displayMessageThrowWindow("Σωστή πρόβλεψη! Barcelona - Real Madrid 0-2.\nΚέρδισες ", 1000);
                    player.addMoney(1000);
                }
                else{
                    view.displayMessageThrowWindow("Λάθος πρόβλεψη! Barcelona - Real Madrid 4-1.\nΈχασες ", 500);
                    player.deductMoney(500);
                }
                view.updatePlayerCard(player, player.getMoney(), player.getLoan(), player.getBills());
                break;
            case 3:
                break;
        }
    }

    /**
     * <b>transformer</b>: This method is being called upon a player steps on radio competition Tile<br/>
     * <b>Pre condition</b>: player != NULL.<br/>
     * <b>Post condition</b>: competition between the two players, player with the highest roll wins 1000 Euros.
     * @param player player the stepped on the Tile.
     */
    private void radioCompetition(Player player){
        if (player instanceof Player1){
            int p1Roll = player1.rollDice();
            int p2Roll = player2.rollDice();
            while(p1Roll == p2Roll){
                p1Roll = player1.rollDice();
                p2Roll = player2.rollDice();
            }
            if (p1Roll > p2Roll){
                view.displayMessageThrowWindow(player1.getName() + " ζαριά = " + p1Roll + "\n" + player2.getName() + " ζαριά = " + p2Roll
                        + "\n" + player1.getName() + " κέρδισες", 1000);
                player1.addMoney(1000);
                view.updatePlayerCard(player1,player1.getMoney(),player1.getLoan(),player1.getBills());
            }
            else{
                view.displayMessageThrowWindow(player1.getName() + " ζαριά = " + p1Roll + "\n" + player2.getName() + " ζαριά = " + p2Roll
                        + "\n" + player2.getName() + " κέρδισες", 1000);
                player2.addMoney(1000);
                view.updatePlayerCard(player2,player2.getMoney(),player2.getLoan(),player2.getBills());
            }
        }
        else{
            int p2Roll = player2.rollDice();
            int p1Roll = player1.rollDice();
            while(p2Roll == p1Roll){
                p2Roll = player2.rollDice();
                p1Roll = player1.rollDice();
            }
            if (p2Roll > p1Roll){
                view.displayMessageThrowWindow(player2.getName() + " ζαριά = " + p2Roll + "\n" + player1.getName() + " ζαριά = " + p1Roll
                        + "\n" + player2.getName() + " κέρδισες", 1000);
                player2.addMoney(1000);
                view.updatePlayerCard(player2,player2.getMoney(),player2.getLoan(),player2.getBills());
            }
            else{
                view.displayMessageThrowWindow(player2.getName() + " ζαριά = " + p2Roll + "\n" + player1.getName() + " ζαριά = " + p1Roll
                        + "\n" + player1.getName() + " κέρδισες", 1000);
                player1.addMoney(1000);
                view.updatePlayerCard(player1,player1.getMoney(),player1.getLoan(),player1.getBills());
            }
        }
    }

    /**
     * <b>transformer</b>: Analyses the current Tile the player is on.<br/>
     * <b>Pre condition</b>: current != NULL, player != NULL.<br/>
     * <b>Post condition</b>: informs the player for the actions he has to make.
     * @param current current Tile.
     * @param player current player.
     */
    private void analyseTile(Tile current,Player player) {
        switch (current.getSType()) {
            case "Mail1":
                playSound("MailSound.wav");
                view.updateInfoBox("Info Box\n" + "Months left: " + board.getMonths() + "\n==> Πάρε 1 κάρτα mail.");
                player.setMailCardsToTake(1);
                break;

            case "Mail2":
                playSound("MailSound.wav");
                player.setMailCardsToTake(2);
                view.updateInfoBox("Info Box\n" + "Months left: " + board.getMonths() + "\n==> Πάρε 2 κάρτες mail.");
                break;

            case "Deal":
                player.setTakeDealCard(true);
                view.updateInfoBox("Info Box\n" + "Months left: " + board.getMonths() + "\n==> Πάρε μια κάρτα Deal.");
                break;

            case "Sweepstakes":
                playSound("CashRegister.wav");
                view.updateInfoBox("Info Box\n" + "Months left: " + board.getMonths() + "\n==> Sweepstakes, Κέρδισε 1000 x την ζαριά σου.");
                int sweepstakesMoney = player.rollDice() * 1000;
                view.displayMessageThrowWindow("Συγχαρητήρια, κέρδισες ",sweepstakesMoney);
                player.addMoney(sweepstakesMoney);
                view.updatePlayerCard(player,player.getMoney(),player.getLoan(),player.getBills());
                break;

            case "Lottery":
                view.updateInfoBox("Info Box\n" + "Months left: " + board.getMonths() + "\n==> Lottery, Ο κάθε παίχτης διαλέγει εναν αριθμό.\nΟ αριθμός που θα έρθει πρώτος κερδίζει 1000 Ευρώ");
                if(player instanceof Player1){
                    int p1Choice = view.lotteryChooseNumber(player1.getName(), 0);
                    int p2Choice = view.lotteryChooseNumber(player2.getName(), p1Choice);
                    int luckyNumber = random.nextInt((6 - 1) + 1) + 1;
                    while (luckyNumber != p1Choice && luckyNumber != p2Choice) {
                        luckyNumber = random.nextInt((6 - 1) + 1) + 1;
                    }
                    if (luckyNumber == p1Choice) {
                        view.displayMessageThrowWindow("Τυχερός αριθμός: " + luckyNumber + "\n" + player1.getName() + " έχεις τον τυχερό αριθμό!\nΚέρδισες ", 1000);
                        player1.addMoney(1000);
                        view.updatePlayerCard(player1, player1.getMoney(), player1.getLoan(), player1.getBills());
                    } else {
                        view.displayMessageThrowWindow("Τυχερός αριθμός: " + luckyNumber + "\n" + player2.getName() + " έχεις τον τυχερό αριθμό!\nΚέρδισες ", 1000);
                        player2.addMoney(1000);
                        view.updatePlayerCard(player2, player2.getMoney(), player2.getLoan(), player2.getBills());
                    }

                }
                else{
                    int p2Choice = view.lotteryChooseNumber(player2.getName(),0);
                    int p1Choice = view.lotteryChooseNumber(player1.getName(),p2Choice);
                    int luckyNumber = random.nextInt((6 - 1) + 1) + 1;
                    while (luckyNumber != p1Choice && luckyNumber != p2Choice) {
                        luckyNumber = random.nextInt((6 - 1) + 1) + 1;
                    }
                    if (luckyNumber == p1Choice) {
                        view.displayMessageThrowWindow("Τυχερός αριθμός: " + luckyNumber + "\n" + player1.getName() + " έχεις τον τυχερό αριθμό!\nΚέρδισες ", 1000);
                        player1.addMoney(1000);
                        view.updatePlayerCard(player1, player1.getMoney(), player1.getLoan(), player1.getBills());
                    } else {
                        view.displayMessageThrowWindow("Τυχερός αριθμός: " + luckyNumber + "\n" + player2.getName() + " έχεις τον τυχερό αριθμό!\nΚέρδισες ", 1000);
                        player2.addMoney(1000);
                        view.updatePlayerCard(player2, player2.getMoney(), player2.getLoan(), player2.getBills());
                    }
                }
                break;

            case "Radio":
                view.updateInfoBox("Info Box\n" + "Months left: " + board.getMonths() + "\n==> Radio competition, Ο παίχτης με την υψηλότερη \nζαριά κερδίζει 1000 Ευρώ.");
                radioCompetition(player);
                break;
            case "Buyer":
                if (player.getDealCards().size() > 0){
                    view.updateInfoBox("Info Box\n" + "Months left: " + board.getMonths() + "\n==> Buyer Tile.\nΆνοιξε τις κάρτες συμφωνίας σου και επέλεξε μια κάρτα\nνα πουλήσεις.");
                    player.setOnBuyerTile(true);
                }
                else{
                    view.updateInfoBox("Info Box\n" + "Months left: " + board.getMonths() + "\n==> Buyer Tile.\nΔεν έχεις κάρτες συμφωνίας, τελείωσε τον γύρο σου.");
                    player.setOnBuyerTile(false);
                }
                break;
            case "Casino":
                view.updateInfoBox("Info Box\n" + "Months left: " + board.getMonths() + "\n==> Casino, έαν η ζαριά σου ήταν 2,4,6 πάρε 500 Ευρώ.\nΑλλιώς δώσε 500 στο Jackpot.");
                int roll = player.getDice();
                if( roll % 2 == 0) {
                    view.displayMessageThrowWindow("Συχαρητήρια έφερες " + roll + ", κέρδισες" ,500);

                    player.addMoney(500);
                    view.updatePlayerCard(player, player.getMoney(), player.getLoan(), player.getBills());
                }
                else{
                    view.displayMessageThrowWindow("Έφερες " + roll + ", δίνεις στο Jackpot" ,500);
                    board.addMoneyToJackpot(500);
                    player.deductMoney(500);
                    view.updatePlayerCard(player, player.getMoney(), player.getLoan(), player.getBills());
                    view.updateJackpotPrize(board.getJackpotPrize());
                }
                break;
            case "Yard":
                if (player instanceof Player1){
                    int p1Roll = player1.rollDice();
                    int totalAmount = p1Roll * 100;
                    view.updateInfoBox("Info Box\n" + "Months left: " + board.getMonths() + "\n==> Yard, Πλήρωσε 100 * την ζαριά σου \nκαι έπειτα πάρε μια κάρτα Deal.");
                    view.displayMessageThrowWindow("Έφερες " + p1Roll + ".\nΠρέπει να πληρώσεις", totalAmount);
                    player1.deductMoney(totalAmount);
                    player1.setTakeDealCard(true);
                    view.updatePlayerCard(player1, player1.getMoney(), player1.getLoan(), player1.getBills());
                }
                else{
                    int p2Roll = player2.rollDice();
                    int totalAmount = p2Roll * 100;
                    view.updateInfoBox("Info Box\n" + "Months left: " + board.getMonths() + "\n==> Yard, Πλήρωσε 100 * την ζαριά σου \nκαι έπειτα πάρε μια κάρτα Deal.");
                    view.displayMessageThrowWindow("Έφερες " + p2Roll + ".\nΠρέπει να πληρώσεις", totalAmount);
                    player2.deductMoney(totalAmount);
                    player2.setTakeDealCard(true);
                    view.updatePlayerCard(player2, player2.getMoney(), player2.getLoan(), player2.getBills());
                }
                break;
            case "PayDay":
                view.updateInfoBox("Info Box\n" + "Months left: " + board.getMonths() + "\n==> PayDay, Έφτασες στην ημέρα πληρωμής!\nΠαίρνεις 3500 Ευρώ, πληρώνεις λογαριασμούς\nκαι μπορείς να ξεπληρώσεις το δάνειο σου.");
                player.setReachedPayDayPosition(true);
                player.setTurn(false);
                if (player instanceof Player1)
                    player2.setTurn(true);
                else
                    player1.setTurn(true);
                player.addMoney(3500);
                while (player.getMoney() < player.getBills()) {
                    player.addLoan(1000);
                    player.addMoney(1000);
                }
                player.deductMoney(player.getBills());
                player.deductBills(player.getBills());
                player.addLoan((int) (player.getLoan() * 0.10F));
                view.updatePlayerCard(player, player.getMoney(), player.getLoan(), player.getBills());
                if (player.getLoan() > 0 && (board.getMonths() != 1)) {
                    int amountPaid = view.loanRepayment(player.getLoan());
                    player.deductMoney(amountPaid);
                    player.deductLoan(amountPaid);
                }
                view.updatePlayerCard(player, player.getMoney(), player.getLoan(), player.getBills());
                checkGame();
                break;
        }
    }

    /**
     * <b>transformer</b>: Analyses a mail card.<br/>
     * <b>Pre condition</b>: card != NULL, player != NULL.<br/>
     * <b>Post condition</b>: Gives the player the information of the mail card.<br/>
     * @param card current card to analyse.
     * @param player current player.
     */
    private void analyseMailCard(MailCard card, Player player){
        if (player instanceof Player1){
            switch (card.getType()) {
                case "Advertisement":
                    player1.addMoney(card.getCost());
                    view.updatePlayerCard(player1, player1.getMoney(), player1.getLoan(), player1.getBills());
                    break;
                case "Bill" :
                    player1.addBills(card.getCost());
                    view.updatePlayerCard(player1, player1.getMoney(), player1.getLoan(), player1.getBills());
                    break;
                case "Charity" :
                    if (player1.getMoney() < card.getCost()) {
                        player1.addLoan(1000);
                        player1.addMoney(1000);
                    }
                    player1.deductMoney(card.getCost());
                    board.addMoneyToJackpot(card.getCost());
                    view.updateJackpotPrize(board.getJackpotPrize());
                    view.updatePlayerCard(player1, player1.getMoney(), player1.getLoan(), player1.getBills());
                    break;
                case "PayTheNeighbor" :
                    while (player1.getMoney() < card.getCost()) {
                        player1.addLoan(1000);
                        player1.addMoney(1000);
                    }
                    player1.deductMoney(card.getCost());
                    player2.addMoney(card.getCost());
                    view.updatePlayerCard(player1, player1.getMoney(), player1.getLoan(), player1.getBills());
                    view.updatePlayerCard(player2, player2.getMoney(), player2.getLoan(), player2.getBills());
                    break;
                case "MadMoney" :
                    playSound("EasyMoney.wav");
                    if (player2.getMoney() < card.getCost()) {
                        player2.addLoan(1000);
                        player2.addMoney(1000);
                    }
                    player2.deductMoney(card.getCost());
                    player1.addMoney(card.getCost());
                    view.updatePlayerCard(player1, player1.getMoney(), player1.getLoan(), player1.getBills());
                    view.updatePlayerCard(player2, player2.getMoney(), player2.getLoan(), player2.getBills());
                    break;
                case "MoveToDealBuyer" :
                    currentTile = view.updatePawn(player1, board.getClosestBuyerDealPosition(player1.getPosition()), player2.getPosition(), board.getBoardTiles());
                    if (board.getClosestBuyerDealPosition(player.getPosition()) != player.getPosition())
                        playSound("MarioJump.wav");
                    analyseTile(currentTile, player1);
                    player1.goToTile(board.getClosestBuyerDealPosition(player1.getPosition()));
                    break;
            }
        }
        else{
            switch (card.getType()) {
                case "Advertisement" :
                    player2.addMoney(card.getCost());
                    view.updatePlayerCard(player2, player2.getMoney(), player2.getLoan(), player2.getBills());
                    break;
                case "Bill" :
                    player2.addBills(card.getCost());
                    view.updatePlayerCard(player2, player2.getMoney(), player2.getLoan(), player2.getBills());
                    break;
                case "Charity" :
                    if (player2.getMoney() < card.getCost()) {
                        player2.addLoan(1000);
                        player2.addMoney(1000);
                    }
                    player2.deductMoney(card.getCost());
                    board.addMoneyToJackpot(card.getCost());
                    view.updateJackpotPrize(board.getJackpotPrize());
                    view.updatePlayerCard(player2, player2.getMoney(), player2.getLoan(), player2.getBills());
                    break;
                case "PayTheNeighbor" :
                    while (player2.getMoney() < card.getCost()) {
                        player2.addLoan(1000);
                        player2.addMoney(1000);
                    }
                    player2.deductMoney(card.getCost());
                    player1.addMoney(card.getCost());
                    view.updatePlayerCard(player2, player2.getMoney(), player2.getLoan(), player2.getBills());
                    view.updatePlayerCard(player1, player1.getMoney(), player1.getLoan(), player1.getBills());
                break;
                case "MadMoney" :
                    playSound("EasyMoney.wav");
                    if (player1.getMoney() < card.getCost()) {
                        player1.addLoan(1000);
                        player1.addMoney(1000);
                    }
                    player1.deductMoney(card.getCost());
                    player2.addMoney(card.getCost());
                    view.updatePlayerCard(player2, player2.getMoney(), player2.getLoan(), player2.getBills());
                    view.updatePlayerCard(player1, player1.getMoney(), player1.getLoan(), player1.getBills());
                break;
                case "MoveToDealBuyer" :
                    currentTile = view.updatePawn(player2, board.getClosestBuyerDealPosition(player2.getPosition()), player1.getPosition(), board.getBoardTiles());
                    if (board.getClosestBuyerDealPosition(player.getPosition()) != player.getPosition())
                        playSound("MarioJump.wav");
                    player2.goToTile(board.getClosestBuyerDealPosition(player2.getPosition()));
                    analyseTile(currentTile, player2);
                    break;
            }
        }
    }

    /**
     * Method to play sound.<br/>
     * <b>Pre condition</b>: String != NULL.<br/>
     * <b>Post condition</b>: Plays a sound.
     * @param soundName file to play.
     */
    private void playSound(String soundName) {
        try {
            URL soundURL= this.getClass().getClassLoader().getResource("csd/uoc/gr/sounds/"+soundName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip( );
            clip.open(audioInputStream);
            clip.start();
        }
        catch(Exception ex) {
            System.out.println("Error with sound.");
            ex.printStackTrace( );
        }
    }

    /**
     * <b>transformer</b>: Private class that implements ActionListener, responsible for the behavior of the "Roll Dice"
     * button.
     */
    private class RollDiceListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton buttonPressed = (JButton) e.getSource();
            if((buttonPressed.getName().equals("RollDice1") && player1.hasTurn() && !player1.hasRolledDice())
                    || (buttonPressed.getName().equals("RollDice1") && player2.hasReachedPayDayPosition() && !player1.isStillActive())){
                playSound("RollingDice.wav");
                int roll = player1.rollDice();
                player1.setDice(roll);
                if (board.getJackpotPrize() > 0 && roll == 6){
                    view.displayMessageThrowWindow("Έφερες 6 κερδίζεις το Jackpot!\nΠαίρνεις" , board.getJackpotPrize());
                    player1.addMoney(board.getJackpotPrize());
                    board.setJackpotPrize(0);
                    view.updatePlayerCard(player1,player1.getMoney(), player1.getLoan(), player1.getBills());
                    view.updateJackpotPrize(board.getJackpotPrize());
                }
                view.updateDiceImage(player1,player1.getDice());
                player1.movePawn(player1.getDice());
                if(player1.getPosition() > 31)
                    player1.setPosition(31);
                currentTile = view.updatePawn(player1,player1.getPosition(),player2.getPosition(),board.getBoardTiles());
                player1.setRolledDice(true);

                if (view.cryptoThursday(player1.getPosition()))
                    observeCryptoStatus(player1);

                if (view.sundayFootball(player1.getPosition())){
                    spectateFootballMatch(player1);
                }

                analyseTile(currentTile,player1);
            }
            else if((buttonPressed.getName().equals("RollDice2") && player2.hasTurn() && !player2.hasRolledDice())
                    || (buttonPressed.getName().equals("RollDice2") && player1.hasReachedPayDayPosition() && !player2.isStillActive())){
                playSound("RollingDice.wav");
                int roll = player2.rollDice();
                player2.setDice(roll);
                if (board.getJackpotPrize() > 0 && roll == 6){
                    view.displayMessageThrowWindow("Έφερες 6 κερδίζεις το Jackpot!\nΠαίρνεις" , board.getJackpotPrize());
                    player2.addMoney(board.getJackpotPrize());
                    board.setJackpotPrize(0);
                    view.updatePlayerCard(player2,player2.getMoney(), player2.getLoan(), player2.getBills());
                    view.updateJackpotPrize(board.getJackpotPrize());
                }
                view.updateDiceImage(player2,player2.getDice());
                player2.movePawn(player2.getDice());
                if(player2.getPosition() > 31)
                    player2.setPosition(31);
                currentTile = view.updatePawn(player2,player2.getPosition(),player1.getPosition(),board.getBoardTiles());
                player2.setRolledDice(true);

                if (view.cryptoThursday(player2.getPosition()))
                    observeCryptoStatus(player2);

                if (view.sundayFootball(player2.getPosition()))
                    spectateFootballMatch(player2);

                analyseTile(currentTile,player2);
            }
            else
                playSound("buzz.wav");
        }
    }

    /**
     * <b>transformer</b>: Private class that implements ActionListener, responsible for the behavior of the "Get Loan"
     * button.
     */
    private class LoanListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton buttonPressed = (JButton) e.getSource();
            if (buttonPressed.getName().equals("GetLoan1") && player1.hasTurn() || player2.hasReachedPayDayPosition()) {
                loanRequest = view.openLoanDialog();
                player1.addLoan(loanRequest);
                player1.addMoney(loanRequest);
                view.updatePlayerCard(player1,player1.getMoney(),player1.getLoan(),player1.getBills());
            }
            if (buttonPressed.getName().equals("GetLoan2") && player2.hasTurn() || player1.hasReachedPayDayPosition()){
                loanRequest = view.openLoanDialog();
                player2.addLoan(loanRequest);
                player2.addMoney(loanRequest);
                view.updatePlayerCard(player2,player2.getMoney(),player2.getLoan(),player2.getBills());
            }
        }
    }

    /**
     * <b>transformer</b>: Private class that implements ActionListener, responsible for the behavior of the "End Turn"
     * button.
     */
    private class EndTurnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton buttonPressed = (JButton) e.getSource();
            if (buttonPressed.getName().equals("EndTurn1") && player1.hasTurn() && (!player1.isStillActive() && player1.hasRolledDice()) && !player2.hasReachedPayDayPosition()) {
                player1.setTurn(false);
                player2.setTurn(true);
                player2.setRolledDice(false);
            }
            else if (buttonPressed.getName().equals("EndTurn2") && player2.hasTurn() && (!player2.isStillActive() && player2.hasRolledDice()) && !player1.hasReachedPayDayPosition()) {
                player2.setTurn(false);
                player1.setTurn(true);
                player1.setRolledDice(false);
            }
            else
                playSound("buzz.wav");
        }
    }

    /**
     * <b>transformer</b>: Private class that implements ActionListener, responsible for the behavior of
     * the Mail card and Deal card buttons.
     */
    private class CardListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton buttonPressed = (JButton) e.getSource();
            if(buttonPressed.getName().equals("DealCard") && player1.canTakeDealCard()){
                DealCard newDealCard = board.getNewDealCard();
                int choice = view.showDealCard(newDealCard);
                switch (choice) {
                    case 0 :
                        if (player1.getMoney() - newDealCard.getBuyCost() < 0) {
                            loanRequest = view.openLoanDialogForCard(newDealCard.getBuyCost() - player1.getMoney());
                            player1.addLoan(loanRequest);
                            player1.addMoney(loanRequest);
                            player1.deductMoney(newDealCard.getBuyCost());
                            player1.addDealCard(newDealCard);
                        } else {
                            player1.addDealCard(newDealCard);
                            player1.deductMoney(newDealCard.getBuyCost());
                        }
                        board.disposeCard(newDealCard);
                        view.updatePlayerCard(player1, player1.getMoney(), player1.getLoan(), player1.getBills());
                        break;
                    case 1 :
                        board.disposeCard(newDealCard);
                        break;
                    default :
                        board.disposeCard(newDealCard);
                        player1.setOnBuyerTile(false);
                        break;
                }
                player1.setTakeDealCard(false);
            }

            else if (buttonPressed.getName().equals("DealCard") && player2.canTakeDealCard()){
                DealCard newDealCard = board.getNewDealCard();
                int choice = view.showDealCard(newDealCard);
                switch (choice) {
                    case 0 :
                        if (player2.getMoney() - newDealCard.getBuyCost() < 0) {
                            loanRequest = view.openLoanDialogForCard(newDealCard.getBuyCost() - player2.getMoney());
                            player2.addLoan(loanRequest);
                            player2.addMoney(loanRequest);
                            player2.deductMoney(newDealCard.getBuyCost());
                            player2.addDealCard(newDealCard);
                        } else {
                            player2.addDealCard(newDealCard);
                            player2.deductMoney(newDealCard.getBuyCost());
                        }
                        board.disposeCard(newDealCard);
                        view.updatePlayerCard(player2, player2.getMoney(), player2.getLoan(), player2.getBills());
                        break;
                    case 1 :
                        board.disposeCard(newDealCard);
                        break;
                    default :
                        board.disposeCard(newDealCard);
                        player2.setOnBuyerTile(false);
                    break;
                }
                player2.setTakeDealCard(false);
            }

            if (buttonPressed.getName().equals("MailCard") && player1.getAmountOfMailCardsToTake() > 0){
                MailCard mailCard = board.getNewMailCard();
                view.showMailCard(mailCard);
                analyseMailCard(mailCard,player1);
                board.disposeCard(mailCard);
                player1.deductMailCard();
            }

            else if (buttonPressed.getName().equals("MailCard") && player2.getAmountOfMailCardsToTake() > 0){
                MailCard mailCard = board.getNewMailCard();
                view.showMailCard(mailCard);
                analyseMailCard(mailCard,player2);
                board.disposeCard(mailCard);
                player2.deductMailCard();
            }
        }
    }

    /**
     * <b>transformer</b>: Private class that implements ActionListener, responsible for the behavior of the
     * "My Deal Cards" button.
     */
    private class MyDealCardsListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton buttonPressed = (JButton) e.getSource();
            if (buttonPressed.getName().equals("MyDealCards1") && player1.isOnBuyerTile() && player1.getDealCards().size() > 0 && player1.hasTurn() && player1.getDealCards().size() > 0){
                String cardChosen = view.chooseDealCardToSell(player1.getDealCards());
                DealCard dealToSell = new DealCard();
                for(DealCard dealCard : player1.getDealCards()){
                    String message = dealCard.getMessage() + ", πούλησε την κάρτα για " + dealCard.getSaleCost() + " Ευρώ";
                    if(message.equals(cardChosen)){
                        dealToSell = dealCard;
                        player1.addMoney(dealCard.getSaleCost());
                        view.updatePlayerCard(player1, player1.getMoney(), player1.getLoan(), player1.getBills());
                        player1.setOnBuyerTile(false);
                    }
                }
                player1.disposeDealCard(dealToSell);
            }
            else if (buttonPressed.getName().equals("MyDealCards1") && !player1.isOnBuyerTile() && player1.hasTurn()  && player1.getDealCards().size() > 0){
                view.showMyDealCards(player1.getDealCards());
            }

            if (buttonPressed.getName().equals("MyDealCards2") && player2.isOnBuyerTile() && player2.getDealCards().size() > 0 && player2.hasTurn() && player2.getDealCards().size() > 0){
                String cardChosen = view.chooseDealCardToSell(player2.getDealCards());
                DealCard dealToSell = new DealCard();
                for (DealCard dealCard : player2.getDealCards()){
                    String message = dealCard.getMessage() + ", πούλησε την κάρτα για " + dealCard.getSaleCost() + " Ευρώ";
                    if (message.equals(cardChosen)){
                        dealToSell = dealCard;
                        player2.addMoney(dealCard.getSaleCost());
                        view.updatePlayerCard(player2, player2.getMoney(), player2.getLoan(), player2.getBills());
                        player2.setOnBuyerTile(false);
                        break;
                    }
                }
                player2.disposeDealCard(dealToSell);
            }
            else if (buttonPressed.getName().equals("MyDealCards2") && !player2.isOnBuyerTile() && player2.hasTurn() && player2.getDealCards().size() > 0){
                view.showMyDealCards(player2.getDealCards());
            }
        }
    }
}

