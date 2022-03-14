package csd.uoc.gr.model;

/**
 * @author Konstantinos Ntavarinos
 */

public interface Card {
    /**
     * <b>Pre condition</b>: path != null.<br/>
     * <b>Post condition</b>: set the path for the icon.
     * @param path path of the icon.
     */
    void setIconPath(String path);

    /**
     * <b>Pre condition</b>: message != null.<br/>
     * <b>Post condition</b>: set a message for the card.
     * @param message the card will show.
     */
    void setCardMessage(String message);

    /**
     *<b>Pre condition</b>: choiceMessage != null.<br/>
     * <b>Post condition</b>: sets the choice message for the card.
     * @param choiceMessage current choice.
     */
    void setChoiceMessage(String choiceMessage);
}
