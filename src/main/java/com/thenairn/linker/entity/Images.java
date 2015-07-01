/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenairn.linker.entity;

import com.thenairn.linker.Main;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Tom
 */
public class Images {

    public static final int DONE = 0;
    public static final int LIKE = 1;
    public static final int SAD = 2;

    public static BufferedImage getImage(int image) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("/images/");
        switch (image) {
            case DONE:
                builder.append("done");
                break;
            case LIKE:
                builder.append("like");
                break;
            case SAD:
                builder.append("sad");
                break;
        }
        builder.append(".png");
        return ImageIO.read(Main.class.getResourceAsStream(builder.toString()));
    }
    
    public static Icon getIcon(int image) {
        try {
            return new ImageIcon(getImage(image));
        } catch (IOException ex) {
            Logger.getLogger(Images.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
