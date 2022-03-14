package csd.uoc.gr.model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *  @author Konstantinos Ntavarinos.
 */

public class Board {
    private ArrayList<Tile> boardTiles;
    private ArrayList<DealCard> dealCards;
    private final ArrayList<DealCard> disposedDealCards;
    private ArrayList<MailCard> mailCards;
    private final ArrayList<MailCard> disposedMailCards;
    private int jackpotPrize;
    private int months;
    private final Path path;
    private final Random random;

    /**
     * <b>constructor</b>: Creates the board.<br/>
     * <b>Pre condition</b>: months > 0 and months <= 3<br/>
     * <b>Post condition</b>: Creates and initialises the board.
     * @param months how many months the players will be playing.
     */
    public Board(int months){
        path = Paths.get("").toAbsolutePath();
        this.months = months;
        this.jackpotPrize = 0;
        this.disposedDealCards = new ArrayList<>();
        this.disposedMailCards = new ArrayList<>();
        this.random = new Random();
    }


    /**
     * Reads the Tiles and adds them to the boardTiles array.<br/>
     * <b>Post condition</b>: we have an array of Tiles.
     */
    public void readBoardTiles(){
        boardTiles = new ArrayList<>();

        Tile[] mailCardsTiles = new Tile[8];
        Tile[] dealCardsTiles = new Tile[5];
        Tile[] sweepstakesTiles = new Tile[2];
        Tile[] lotteryTiles = new Tile[3];
        Tile[] radioCompetitionTiles = new Tile[2];
        Tile[] buyerTiles = new Tile[6];
        Tile[] familyCasinoTiles = new Tile[2];
        Tile[] yardSaleTiles = new Tile[2];

        Tile startTile = new Tile("Start","csd/uoc/gr/resources/gameImages/start.png");


        new Thread(() -> {
            // Read Mail cards.
            for(int counter = 0; counter < 8; counter++){
                Tile mailCard;
                if(counter % 2 == 0){
                    mailCard = new Tile("Mail1", "csd/uoc/gr/resources/gameImages/mc1.png");
                }
                else {
                    mailCard = new Tile("Mail2", "csd/uoc/gr/resources/gameImages/mc2.png");
                }
                mailCardsTiles[counter] = mailCard;
            }
            //  End mail card reading.

            // Read Deal Cards.
            for(int cnt1 = 0; cnt1 < 5; cnt1++){
                Tile newDealCard = new Tile("Deal","csd/uoc/gr/resources/gameImages/deal.png");
                dealCardsTiles[cnt1] = newDealCard;
            }

            // End of deal cards reading.

            // Read Sweepstakes
            for(int counter = 0; counter < 2; counter++){
                Tile sweepstake = new Tile("Sweepstakes","csd/uoc/gr/resources/gameImages/sweep.png");
                sweepstakesTiles[counter] = sweepstake;
            }
            // end of sweepstakes reading

            // read lottery Tiles
            for(int counter = 0; counter < 3; counter++){
                Tile lottery = new Tile("Lottery",  "csd/uoc/gr/resources/gameImages/lottery.png");
                lotteryTiles[counter] = lottery;
            }
            // end of lottery reading

            // Read Radio competition
            for(int counter = 0; counter < 2; counter++){
                Tile radioCompetition = new Tile("Radio",  "csd/uoc/gr/resources/gameImages/radio.png");
                radioCompetitionTiles[counter] = radioCompetition;
            }
            // end radio reading

            // Read buyer Tiles
            for(int counter = 0; counter < 6; counter++){
                Tile buyer = new Tile("Buyer","csd/uoc/gr/resources/gameImages/buyer.png");
                buyerTiles[counter] = buyer;
            }
            // end of buyer tiles reading

            // read casino Tiles //
            for(int counter = 0; counter < 2; counter++){
                Tile casinoNight = new Tile("Casino",  "csd/uoc/gr/resources/gameImages/casino.png");
                familyCasinoTiles[counter] = casinoNight;
            }
            // end casino reading

            // read Yard Sale Tiles
            for(int counter = 0; counter < 2; counter++){
                Tile yardSale = new Tile("Yard",  "csd/uoc/gr/resources/gameImages/yard.png");
                yardSaleTiles[counter] = yardSale;
            }
            // end yard reading.
        }).start();

        Tile payDayTile = new Tile("PayDay","csd/uoc/gr/resources/gameImages/pay.png");
        new Thread( ()-> {
            // Adding Tiles to the boardTiles List.
            this.boardTiles.add(startTile);
            Collections.addAll(this.boardTiles, mailCardsTiles);
            Collections.addAll(this.boardTiles, dealCardsTiles);
            Collections.addAll(this.boardTiles, sweepstakesTiles);
            Collections.addAll(this.boardTiles, lotteryTiles);
            Collections.addAll(this.boardTiles, radioCompetitionTiles);
            Collections.addAll(this.boardTiles, buyerTiles);
            Collections.addAll(this.boardTiles, familyCasinoTiles);
            Collections.addAll(this.boardTiles, yardSaleTiles);
            this.boardTiles.add(payDayTile);
            Collections.shuffle(boardTiles);
            initialisePayDayAndStartTiles();
        }).start();
    }


    /**
     * Reads the mail cards and adds them to mailCards array.<br/>
     * <b>Post condition</b>: we have an array of Mail cards.
     */
    public void readMailCards() {
        String[][] mailCardsArray = new String[48][6];
        this.mailCards = new ArrayList<>();
        BufferedReader br = null;
        String sCurrentLine;
        try{
            String fullPath =  "../resources/mailCards.csv";
            InputStream in = new FileInputStream(fullPath);
            br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }

        int count = 0;
        try {
            assert br != null;
            br.readLine();
            while ((sCurrentLine = br.readLine()) != null){
                mailCardsArray[count++] = sCurrentLine.split(",");
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        for (String[] mail : mailCardsArray) {
            MailCard mailCard = new MailCard();
            mailCard.setType(mail[1]);
            mailCard.setCardMessage(mail[2]);
            mailCard.setChoiceMessage(mail[3]);
            mailCard.setCost(mail[4]);
            mailCard.setIconPath("csd/uoc/gr/resources/images/" + mail[5]);
            this.mailCards.add(mailCard);
        }
    }

    /**
     * Reads the deal cards and adds them to dealCards array.<br/>
     * <b>Post condition</b>: we have an array of deal cards.
     */
    public void readDealCards(){
        String[][] dealCardsArray = new String[20][8];
        this.dealCards = new ArrayList<>();
        BufferedReader br = null;
        String sCurrentLine;
        try{
            String fullPath = "../resources/dealCards.csv";
            InputStream in = new FileInputStream(fullPath);
            br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }

        try {
            int count = 0;
            assert br != null;
            br.readLine();
            while ((sCurrentLine = br.readLine()) != null){
                dealCardsArray[count++] = sCurrentLine.split(",");
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        for (String[] deal : dealCardsArray) {
            DealCard dealCard = new DealCard();
            dealCard.setCardMessage(deal[2]);
            dealCard.setBuyCost(deal[3]);
            dealCard.setSaleCost(deal[4]);
            dealCard.setIconPath("csd/uoc/gr/resources/images/" + deal[5]);
            dealCard.setChoiceMessage(deal[6]);
            dealCard.setChoiceMessage2(deal[7]);
            dealCards.add(dealCard);
        }
    }

    /**
     * <b>transformer</b>: changes the value of the jackpotPrize<br/>
     * <b>Pre condition</b>: amount > 0.<br/>
     * <b>Post condition</b>: changes the value of jackpotPrize.
     * @param amount value to set jackpotPrize.
     */
    public void setJackpotPrize(int amount){
        this.jackpotPrize = amount;
    }

    /**
     * <b>accessor</b>: access to the boardTiles array.<br/>
     * <b>Post condition</b>: Returns an array list with the tiles to show on board.
     * @return ArrayList of type "Tile".
     */
    public ArrayList<Tile> getBoardTiles(){
        return  this.boardTiles;
    }


    /**
     * <b>accessor</b>: access to the value of months.<br/>
     * <b>Post condition</b>: returns the value of months.
     * @return months left.
     */
    public int getMonths(){
        return  this.months;
    }

    /**
     * <b>accessor</b>: finds the index of the Start Tile.<br/>
     * <b>Pre condition</b>: boardTiles != null.<br/>
     * <b>Post condition</b>: returns the index of the Start Tile.
     * @return Index of start Tile.
     */
    private int getIndexOfStart(){
        for(int position = 0; position < this.boardTiles.size(); position++){
            if(this.boardTiles.get(position).getSType().equals("Start"))
                return position;
        }
        return -1;
    }

    /**
     * <b>accessor</b>: finds the index of the PayDay Tile.<br/>
     * <b>Pre condition</b>: boardTiles != null.<br/>
     * <b>Post condition</b>: returns the index of the PayDay Tile.
     * @return Index of PayDay Tile.
     */
    private int getIndexOfPayDay(){
        for(int position = 0; position < this.boardTiles.size(); position++){
            if(this.boardTiles.get(position).getSType().equals("PayDay"))
                return position;
        }
        return -1;
    }

    /**
     * <b>transformer</b>: swaps the Tile so the Start Tile is the first one
     * and the last Tile is the PayDay Tile<br/>
     * <b>Pre condition</b>: boardTiles != null.<br/>
     * <b>Post condition</b>: Changes the position of the Tiles.
     */
    private void initialisePayDayAndStartTiles(){
        int startIndex = getIndexOfStart();
        int payDayIndex = getIndexOfPayDay();
        Collections.swap(this.boardTiles,startIndex,0);
        Collections.swap(this.boardTiles,payDayIndex,31);
    }


    /**
     * <b>accessor</b>: Access to the value of jackpotPrize.<br/>
     * <b>Post condition</b>: returns the value of jackpotPrize.
     * @return the current money of the jackpot.
     */
    public int getJackpotPrize() {
        return jackpotPrize;
    }

    /**
     * <b>transformer</b>: changes the value of jackpotPrize.<br/>
     * <b>Pre condition</b>: amount > 0.<br/>
     * <b>Post condition</b> adds the amount the money of jackpot.
     * @param amount money to add to the jackpot.
     */
    public void addMoneyToJackpot(int amount){
        jackpotPrize += amount;
    }

    /**
     * <b>transformer</b>: picks a random card from the dealCards array.<br/>
     * @return a Deal card.
     */
    public DealCard getNewDealCard(){
        if (this.dealCards.isEmpty()){
            Collections.shuffle(this.disposedDealCards);
            this.dealCards.addAll(disposedDealCards);
            this.disposedDealCards.clear();

            int card = random.nextInt(this.dealCards.size());
            return this.dealCards.get(card);
        }
        else {
            int card = random.nextInt(this.dealCards.size());
            return this.dealCards.get(card);
        }
    }

    /**
     * <b>transformer</b>: picks a random card from the mailCards array.<br/>
     * @return a mail card.
     */
    public MailCard getNewMailCard(){
        if (this.mailCards.isEmpty()){
            Collections.shuffle(this.disposedMailCards);
            this.mailCards.addAll(disposedMailCards);
            this.disposedMailCards.clear();

            int card = random.nextInt(this.mailCards.size());
            return this.mailCards.get(card);
        }
        else {
            int card = random.nextInt(this.mailCards.size());
            return this.mailCards.get(card);
        }
    }

    /**
     * <b>Pre condition</b>: currentPosition > 0.<br/>
     * <b>Post condition</b>: from the current position of the player, finds the closest buyer Tile.
     * @param currentPosition current position of the player.
     * @return position of the closest Buyer tile.
     */
    public int getClosestBuyerDealPosition(int currentPosition){
        int position = currentPosition;
        if (this.boardTiles.get(currentPosition).getSType().equals("Buyer"))
            position++;
        while (position < this.boardTiles.size()){
            if(this.boardTiles.get(position).getSType().equals("Buyer")
                    || this.boardTiles.get(position).getSType().equals("Deal"))
                return position;
            position++;
        }
        return currentPosition;
    }

    /**
     * <b>transformer</b>: removes a Deal/Mail card from the array, if the array is empty,
     * shuffles the disposed card and adds them to the array.
     * <b>Pre condition</b>: card != NULL.<br/>
     * <b>Post condition</b>: removes a card from the array.
     * @param card card to remove.
     */
    public void disposeCard(Card card){
        if(card instanceof DealCard){
            this.disposedDealCards.add((DealCard) card);
            this.dealCards.remove(card);
        }
        else{
            this.disposedMailCards.add((MailCard)card);
            this.mailCards.remove(card);
        }
    }

    /**
     * <b>transformer</b>: shuffles the current board Tiles, and initialises PayDay and
     * Start Tiles.<br/>
     */
    public void shuffleTiles(){
        Collections.shuffle(this.boardTiles);
        initialisePayDayAndStartTiles();
    }

    /**
     * <b>transformer</b>: deducts 1 from months.<br/>
     */
    public void deductMonth(){
        this.months -= 1;
    }
}
