import java.util.*;
import java.lang.*;
import java.io.*;
import javax.swing.SwingUtilities;

import views.*;

public class Main
{
   public static void main(String s[])
   {
      UIConfig.configurarLookAndFeel();
      //UIConfig.aplicarFonteGlobal(new Font("Arial", Font.PLAIN, 26));
      UIConfig.aplicarFonteGlobal(UIConfig.carregarFonte("assets/fonts/digital-7.ttf", 20f));
      //UIConfig.aplicarFonteGlobal(UIConfig.carregarFonte("assets/fonts/robot.ttf", 18f));

      SwingUtilities.invokeLater(() -> {
            FrmPrincipal frmPrincipal = new FrmPrincipal();
            frmPrincipal.setVisible(true);
        });
   }  
}