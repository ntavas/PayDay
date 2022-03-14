package csd.uoc.gr.model;

/**
 * @author Konstantinos Ntavarinos.
 */
public class Tile {
    private final String sType;
    private final String imagePath;

    /**
     * <b>constructor</b>: creates a new Tile.
     * @param sType String Type of Tile.
     * @param imagePath path for the image.
     */
    public Tile(String sType,String imagePath){
        this.sType = sType;
        this.imagePath = imagePath;
    }

    /**
     * <b>accessor</b>: access to the value of sType.<br/>
     * <b>Post condition</b>: returns a String with the sType.<br/>
     * @return String type of Tile.
     */
    public String getSType(){
        return this.sType;
    }


    /**
     * <b>accessor</b>: access to the value of imagePath<br/>
     * <b>Post condition</b>: returns the value of imagePath.
     * @return the path for the image.
     */
    public String getImagePath(){
        return this.imagePath;
    }

}
