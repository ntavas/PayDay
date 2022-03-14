package csd.uoc.gr.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Konstantinos Ntavarinos.
 */
public abstract class Player {
    private final String name;
    private int money;
    private int loan;
    private int bills;
    private int dice;
    private boolean rolledDice;
    private boolean turn;
    private boolean onBuyerTile;
    private boolean reachedPayDayPosition;
    private int position;
    private final ArrayList<DealCard> dealCards;
    private int mailCardsToTake;
    private boolean takeDealCard;
    private final Random random;
    private static final int MAX_DICE_ROLL = 6;
    private static final int MIN_DICE_ROLL = 1;

    /**
     * <b>Constructor</b>: creates a player.
     * @param name Players name.
     * @param money Players money.
     * @param loan Players loan.
     * @param bills Players bills.
     */
    public Player(String name,int money, int loan, int bills){
        this.name = name;
        this.setMoney(money);
        this.setLoan(loan);
        this.setBills(bills);
        this.setDice(1);
        this.rolledDice = false;
        this.setTurn(false);
        this.setOnBuyerTile(false);
        this.setReachedPayDayPosition(false);
        this.setPosition(0);
        this.dealCards = new ArrayList<>();
        this.setMailCardsToTake(0);
        this.setTakeDealCard(false);
        this.random = new Random();
    }

    /**
     * <b>transformer</b>: changes value of money.<br/>
     * <b>Pre condition</b>: money > 0.<br/>
     * <b>Post condition</b>: assigns money to players money.<br/>
     * @param money players new money.
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * <b>transformer</b>: changes value of loan.<br/>
     * <b>Pre condition</b>: loan > 0.<br/>
     * <b>Post condition</b>: assigns loan to players loan.<br/>
     * @param loan players new loan.
     */
    public void setLoan(int loan) {
        this.loan = loan;
    }

    /**
     * <b>transformer</b>: changes value of bills.<br/>
     * <b>Pre condition</b>: bills > 0.<br/>
     * <b>Post condition</b>: assigns bills to players bills.<br/>
     * @param bills players new bills.
     */
    public void setBills(int bills) {
        this.bills = bills;
    }

    /**
     * <b>transformer</b>: changes value of money.<br/>
     * <b>Pre condition</b>: dice >= 1 AND dice <=6.<br/>
     * <b>Post condition</b>: assigns dice to players dice.<br/>
     * @param dice players dice roll.
     */
    public void setDice(int dice) {
        this.dice = dice;
    }

    /**
     * <b>transformer</b>: changes the value of turn.<br/>
     * <b>Pre condition</b>: hasTurn == true or false.<br/>
     * @param hasTurn value to set turn.
     */
    public void setTurn(boolean hasTurn) {
        this.turn = hasTurn;
    }

    /**
     * <b>transformer</b>: changes the value of position.<br/>
     * <b>Pre condition</b>: position > 0.<br/>
     * <b>Post condition</b> changes the value of position.<br/>
     * @param position value to set the players position.
     */
    public void setPosition(int position) {
        this.position = position;
    }


    /**
     *<b>transformer</b>: changes rolledDice value.<br>
     * <b>Pre condition</b>: rolled != NULL.<br/>
     * <b>Post condition</b>: changes the value of rolledDice.
     * @param rolled value to set rolledDice.
     */
    public void setRolledDice(boolean rolled){
        this.rolledDice = rolled;
    }

    /**
     * <b>transformer</b>: changes the value of takeDealCard.<br/>
     * <b>Pre condition</b>: takeDealCard != NULL<br/>
     * <b>Post condition</b>: takeDealCard sets to true/false.
     * @param takeDealCard true/false, if the player can take deal card or not
     */
    public void setTakeDealCard(boolean takeDealCard) {
        this.takeDealCard = takeDealCard;
    }

    /**
     * <b>transformer</b>: changes the value of mailCardsToTake.<br/>
     * <b>Pre condition</b>: mailCardsToTake > 0.<br/>
     * <b>Post condition</b>: changes the value of mailCardsToTake
     * @param mailCardsToTake number of mail cards the player should take.
     */
    public void setMailCardsToTake(int mailCardsToTake) {
        this.mailCardsToTake = mailCardsToTake;
    }

    /**
     * <b>transformer</b>: changes the value of reachedPayDayPosition.<br/>
     * <b>Pre condition</b>: reachedPayDayPosition != NULL.<br/>
     * <b>Post condition</b>: set value of reachedPayDayPosition true/false.
     * @param reachedPayDayPosition value to set reachPayDayPosition, true/false.
     */
    public void setReachedPayDayPosition(boolean reachedPayDayPosition) {
        this.reachedPayDayPosition = reachedPayDayPosition;
    }

    /**
     * <b>transformer</b>: changes the value of onBuyerTile.<br/>
     * <b>Pre condition</b>: isOn != NULL<br/>
     * <b>Post condition</b>: changes the value of onBuyerTile.
     * @param isOn true/false if the player is on buyer Tile.
     */
    public void setOnBuyerTile(boolean isOn) {
        this.onBuyerTile = isOn;
    }


    /**
     * <b>transformer</b>: adds a new card to players deal cards.<br/>
     * <b>Pre condition</b>: card instanceof DealCard.<br/>
     * <b>Post condition</b>: adds card to dealCards array.<br/>
     * @param card card to add to players deal cards.
     */
    public void addDealCard(DealCard card){
        dealCards.add(card);
    }

    /**
     * <b>accessor</b>: accesses the players money.<br/>
     * <b>Post condition</b>: returns players money.<br/>
     * @return players money.
     */
    public int getMoney() {
        return this.money;
    }

    /**
     * <b>accessor</b>: accesses the players loan.<br/>
     * <b>Post condition</b>: returns players loan.<br/>
     * @return players loan.
     */
    public int getLoan(){
        return this.loan;
    }

    /**
     * <b>accessor</b>: accesses the players bills.<br/>
     * <b>Post condition</b>: returns players bills.<br/>
     * @return players bills.
     */
    public int getBills() {
        return this.bills;
    }

    /**
     * <b>accessor</b>: access to the value of name.<br/>
     * <b>Post condition</b>: returns the name of the player.<br/>
     * @return players name.
     */
    public String getName(){
        return this.name;
    }

    /**
     * <b>accessor</b>: access position of the pawn.<br/>
     * <b>Post condition</b>: returns the position of the pawn<br/>
     * @return position.
     */
    public int getPosition(){
        return this.position;
    }

    /**
     * <b>accessor</b>: access to the players deal cards.<br/>
     * <b>Post condition</b>: returns an ArrayList of the players deal cards.
     * @return players deal cards.
     */
    public ArrayList<DealCard> getDealCards(){
        return this.dealCards;
    }

    /**
     * <b>accessor</b>: access to the value of dice.<br/>
     * <b>Post condition</b>: returns the players dice.
     * @return players dice.
     */
    public int getDice() {
        return dice;
    }

    /**
     * <b>accessor</b>: access to the value of mailCardsToTake<br/>
     * <b>Post condition</b>: returns the value of mailCardsToTake
     * @return number of mail cards the player can take.
     */
    public int getAmountOfMailCardsToTake() {
        return mailCardsToTake;
    }

    /**
     * <b>accessor</b>: accesses the players dice.<br/>
     * <b>Post condition</b>: returns players dice.<br/>
     * @return players current dice roll.
     */
    public int rollDice() {
        return random.nextInt((MAX_DICE_ROLL - MIN_DICE_ROLL) + 1) + MIN_DICE_ROLL;
    }

    /**
     * <b>transformer</b>: Changes the amount of money.<br/>
     * <b>Pre condition</b>: money > 0.<br/>
     * <b>Post condition</b>: Adds money to the current amount of the players money.
     * @param money amount of money to add.
     */
    public void addMoney(int money){
        this.money += money;
    }

    /**
     * <b>transformer</b>: Changes the amount of money.<br/>
     * <b>Pre condition<b>: money > 0.<br/>
     * <b>Post condition</b>: Deducts money from the current amount of the players money.<br/>
     * @param money amount of money to deduct.
     */
    public void deductMoney(int money){
        this.money -= money;
    }

    /**
     * <b>accessor</b>: access to the value of turn.<br/>
     * <b>Post condition</b>: returns the value of turn.<br/>
     * @return returns true/false.
     */
    public boolean hasTurn() {
        return turn;
    }

    /**
     * <b>transformer</b>: moves the pawn.<br/>
     * <b>Pre condition</b>: position > 0.<br/>
     * <b>Post condition</b>: Moves the pawn.<br/>
     * @param diceRoll new position of the pawn.
     */
    public void movePawn(int diceRoll){
        this.position += diceRoll;
    }

    /**
     * <b>accessor</b>: access to the value of rolledDice<br/>
     * @return value of rolledDice.
     */
    public boolean hasRolledDice(){
        return this.rolledDice;
    }

    /**
     * <b>accessor</b>: access to the value of takeDealCard<br/>
     * @return value of takeDealCard, true/false.
     */
    public boolean canTakeDealCard() {
        return takeDealCard;
    }

    /**
     * <b>accessor</b>: access to the value of onBuyerTile<br/>
     * @return value of onBuyerTile, true/false.
     */
    public boolean isOnBuyerTile() {
        return this.onBuyerTile;
    }

    /**
     * <b>transformer</b>: adds loan to the current loan of the player.<br/>
     * <b>Pre condition</b>: loan > 0.<br/>
     * <b>Post condition</b>: adds loan.
     * @param loan loan to add.
     */
    public void addLoan(int loan){
        this.loan += loan;
    }

    /**
     * <b>transformer</b>: deducts loan from the current loan of the player.<br/>
     * <b>Pre condition</b>: loan > 0.<br/>
     * <b>Post condition</b>: deducts loan.
     * @param loan loan to deduct.
     */
    public void deductLoan(int loan){
        this.loan -= loan;
    }

    /**
     * <b>transformer</b>: adds bills to the current bills of the player.<br/>
     * <b>Pre condition</b>: bills > 0.<br/>
     * <b>Post condition</b>: adds bills.
     * @param bills loan to add.
     */
    public void addBills(int bills){
        this.bills += bills;
    }

    /**
     * <b>transformer</b>: deducts bills from the current bills of the player.<br/>
     * <b>Pre condition</b>: bills > 0.<br/>
     * <b>Post condition</b>: deducts bills.
     * @param bills bills to deduct.
     */
    public void deductBills(int bills){
        this.bills -= bills;
    }

    /**
     * <b>accessor</b>: access to the value of reachedPayDayPosition<br/>
     * @return value of reachedPayDayPosition, true/false.
     */
    public boolean hasReachedPayDayPosition() {
        return reachedPayDayPosition;
    }

    /**
     * <b>accessor</b>: access to the value of mailCardsToTake, takeDealCard, onBuyerTile<br/>
     * <b>Post condition</b>: returns true if the on of the cases below is true.
     * @return true/false if the player is still active or not.
     */
    public boolean isStillActive() {
        return this.mailCardsToTake > 0 || this.takeDealCard || this.onBuyerTile;
    }

    /**
     * <b>transformer</b>: deducts 1 from the value of mailCardsToTake.
     */
    public void deductMailCard(){
        this.mailCardsToTake -= 1;
    }

    /**
     * <b>transformer</b>: changes the value of position.<br/>
     * <b>Pre condition</b>: position > 0.<br/>
     * <b>Post condition</b>: set this.position to position.
     * @param position to go to, set the value position.
     */
    public void goToTile(int position){
        this.position = position;
    }

    /**
     * <b>transformer</b>: removes a Deal card from the players deal cards.<br/>
     * <b>Pre condition</b>: card != NULL.<br/>
     * <b>Post condition</b>: removes the card from the players deal cards.
     * @param card card to remove
     */
    public void disposeDealCard(DealCard card){
        this.dealCards.remove(card);
    }
}
