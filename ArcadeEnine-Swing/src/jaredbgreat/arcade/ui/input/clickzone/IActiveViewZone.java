/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.arcade.ui.input.clickzone;

import java.awt.event.MouseEvent;

/**
 *
 * @author jared
 */
public interface IActiveViewZone extends IViewZone {
    public void activate(MouseEvent e);
}
