package csd.uoc.gr.view;

import javax.swing.*;
import java.awt.*;

/**
 * @author Konstantinos Ntavarinos<br/>
 * This class is responsible for the background image.
 */

public class JLayeredPaneExtension extends JLayeredPane {
    private final Image image;

    /**
     * Class constructor<br/>
     * <b>Pre condition</b>: Image != NULL<br/>
     * <b>Post condition</b>: initialises image.
     * @param image background image.
     */
    public JLayeredPaneExtension(Image image) {
        this.image = image;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
