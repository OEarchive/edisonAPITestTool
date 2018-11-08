
package com.oe.opticx.api.gui;

import Controller.OptiCxAPIController;
import Model.OptiCxAPIModel;
import View.MainFrame;


public class GlueCode {

    public GlueCode() {
        
         OptiCxAPIModel model = new OptiCxAPIModel();
         MainFrame view = new MainFrame();
         OptiCxAPIController controller = new OptiCxAPIController();
                     
         controller.tellControllerAboutTheModel(model);
         controller.tellTheControllerAboutTheView( view );
                
         view.setController(controller);
         view.setLocationRelativeTo(null);
         view.setVisible(true);
                
         controller.initModel();
        
    }
}
