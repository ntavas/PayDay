package csd.uoc.gr.model;

/**
 * @author Konstantinos Ntavarinos.
 */
public class MailCard implements Card{
    private String type;
    private String iconPath;
    private String message;
    private String choiceMessage;
    private int cost;

    /**
     * <b>transformer</b>: changes type.<br/>
     * <b>Pre condition</b>: type != NULL.<br/>
     * <b>Post condition</b>: sets the type of card to type.<br/>
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * <b>transformer</b>: changes iconPath.<br/>
     * <b>Pre condition</b>: path != NULL.<br/>
     * <b>Post condition</b>: sets the cards icon path to path.<br/>
     */
    @Override
    public void setIconPath(String path) {
        this.iconPath = path;
    }

    /**
     * <b>transformer</b>: changes card message.<br/>
     * <b>Pre condition</b>: message != NULL.<br/>
     * <b>Post condition</b>: sets the message of the card to message.<br/>
     */
    @Override
    public void setCardMessage(String message) {
        this.message = message;
    }

    /**
     * <b>transformer</b>: changes choice message.<br/>
     * <b>Pre condition</b>: choiceMessage != NULL.<br/>
     * <b>Post condition</b>: sets choice of the card to choiceMessage.<br/>
     */
    @Override
    public void setChoiceMessage(String choiceMessage) {
        this.choiceMessage = choiceMessage;
    }

    /**
     * <b>transformer</b>: changes cost value.<br/>
     * <b>Pre condition</b>: cost != null.<br/>
     * <b>Post condition</b>: sets the cost of the card to cost.<br/>
     */
    public void setCost(String cost){
        this.cost = Integer.parseInt(cost);
    }

    /**
     * <b>accessor</b>: Access to the value of type.<br/>
     * <b>Post condition</b>: returns a String with type of the card.<br/>
     * @return type of the card.
     */
    public String getType(){
        return this.type;
    }

    /**
     * <b>accessor</b>: Access to the value of iconPath.<br/>
     * <b>Post condition</b>: returns a String with the path of the icon for the current card.<br/>
     * @return path for the icon.
     */
    public String iconPath(){
        return this.iconPath;
    }

    /**
     * <b>accessor</b>: Access to the value of message.<br/>
     * <b>Post condition</b>: returns a String the message of the card.<br/>
     * @return message of the card.
     */
    public String getMessage(){
        return this.message;
    }

    /**
     * <b>accessor</b>: Access to the value of choiceMessage.<br/>
     * <b>Post condition</b>: returns a String with the cards choice.<br/>
     * @return cards choice.
     */
    public String getChoiceMessage(){
        return this.choiceMessage;
    }

    /**
     * <b>accessor</b>: Access to the value of cost.<br/>
     * <b>Post condition</b>: returns the cost of the card.<br/>
     * @return cards cost.
     */
    public int getCost(){
        return this.cost;
    }

}
