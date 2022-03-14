package csd.uoc.gr.model;

/**
 * @author Konstantinos Ntavarinos.
 */

public class DealCard implements Card{
    private int buyCost;
    private int saleCost;
    private String iconPath;
    private String message;
    private String choice1;
    private String choice2;

    /**
     * <b>transformer</b>: changes buyCost value.<br/>
     * <b>Pre condition</b>: cost > 0.<br/>
     * <b>Post condition</b>: sets the buyCost value to cost.
     * @param cost cost to buy the card.
     */
    public void setBuyCost(String cost){
        this.buyCost = Integer.parseInt(cost);
    }

    /**
     * <b>transformer</b>: changes saleCost value.<br/>
     * <b>Pre condition</b>: saleValue > 0.<br/>
     * <b>Post condition</b>: sets the saleCost value to saleValue.
     * @param saleValue value of the card if you sell it.
     */
    public void setSaleCost(String saleValue) {
        this.saleCost = Integer.parseInt(saleValue);
    }

    /**
     * <b>transformer</b>: changes choice1 message.<br/>
     * <b>Pre condition</b>: choiceMessage != NULL.<br/>
     * <b>Post condition</b>: sets first choice.<br/>
     * @param choiceMessage message to add to choice1.
     */
    @Override
    public void setChoiceMessage(String choiceMessage) {
        this.choice1= choiceMessage;
    }

    /**
     *<b>transformer</b>: changes choice2 message.<br/>
     *<b>Pre condition</b>: message != NULL.<br/>
     *<b>Post condition</b>: sets the second choice.<br/>
     * @param message message to add to choice2.
     */
    public void setChoiceMessage2(String message) {
        this.choice2 = message;
    }

    /**
     * <b>transformer</b>: changes iconPath.<br/>
     * <b>Pre condition</b>: path != NULL.<br/>
     * <b>Post condition</b>: sets the cards image path to path.<br/>
     * @param path path of the icon.
     */
    @Override
    public void setIconPath(String path) {
        this.iconPath = path;
    }

    /**
     * <b>transformer</b>: changes card message<br/>
     * <b>Pre condition</b>: message != NULL.<br/>
     * <b>Post condition</b>: sets the cards message to message.<br/>
     * @param message message the card will show.
     */
    @Override
    public void setCardMessage(String message) {
        this.message = message;
    }

    /**
     * <b>accessor</b>: Access to the value of buyCost.<br/>
     * <b>Post condition</b>: returns the value of buyCost.<br/>
     * @return value of buyCost.
     */
    public int getBuyCost() {
        return this.buyCost;
    }

    /**
     * <b>accessor</b>: Access to the value of saleCost.<br/>
     * <b>Post condition</b>: returns the value of saleCost.<br/>
     * @return value of saleCost.
     */
    public int getSaleCost() {
        return this.saleCost;
    }

    /**
     * <b>accessor</b>: Access to the value of iconPath.<br/>
     * <b>Post condition</b>: returns a String with the icon path.<br/>
     * @return String of the cards icon path.
     */
    public String getIconPath() {
        return this.iconPath;
    }

    /**
     * <b>accessor</b>: Access to the value of message.<br/>
     * <b>Post condition</b>: returns a String  the cards with message.<br/>
     * @return card message.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * <b>accessor</b>: Access to the value of choice1.<br/>
     * <b>Post condition</b>: returns a String with the first choice.<br/>
     * @return cards first choice.
     */
    public String getChoice1() {
        return this.choice1;
    }

    /**
     * <b>accessor</b>: Access to the value of choice2.<br/>
     * <b>Post condition</b>: returns a String with the second choice<br/>
     * @return cards second choice.
     */
    public String getChoice2() {
        return this.choice2;
    }

}
