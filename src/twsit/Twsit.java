/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twsit;

import twsit.Controller.TwistController;
import twsit.Model.TwistBE;
import twsit.gui.TwistGui;

import java.io.File;
import java.util.List;

/**
 *
 * @author jungs
 */
public class Twsit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TwistBE twistBE = new TwistBE();
        TwistGui gui = new TwistGui();
        TwistController controller = new TwistController(twistBE,gui);

    }



}