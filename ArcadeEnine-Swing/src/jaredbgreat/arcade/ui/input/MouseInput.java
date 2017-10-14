package jaredbgreat.arcade.ui.input;

import jaredbgreat.arcade.game.BaseGame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Jared Blackburn
 */
public class MouseInput implements MouseListener {
    private int commands;
    private final double[] fvector;
    private IMouseTranslator clicked;
    private IMouseTranslator pressed;
    private IMouseTranslator released;
    
    
    public MouseInput(IMouseTranslator ckick, 
                      IMouseTranslator press, 
                      IMouseTranslator release) {
        fvector = new double[6];
    }
    
    
    public int getCommands() {
        return commands;
    }
    
    
    public double[] getVector() {
        return fvector;
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
        if(BaseGame.game.blockInput() || (clicked == null)) {
         return;
        }
        clicked.getCommands(e, commands);
        clicked.getVector(e, fvector);
    }
    

    @Override
    public void mousePressed(MouseEvent e) {
        if(BaseGame.game.blockInput() || (pressed == null)) {
         return;
        }
        pressed.getCommands(e, commands);
        pressed.getVector(e, fvector);
    }
    

    @Override
    public void mouseReleased(MouseEvent e) {
        if(BaseGame.game.blockInput() || (released == null)) {
         return;
        }
        released.getCommands(e, commands);
        released.getVector(e, fvector);
    }
    

    /*------------------------------------------------------------------------//
    //                           UNUSED METHODS                               //
    //------------------------------------------------------------------------*/
        
    @Override
    public void mouseEntered(MouseEvent e) {/*do nothing*/}
    @Override
    public void mouseExited(MouseEvent e) {/*do nothing*/}
    
}
