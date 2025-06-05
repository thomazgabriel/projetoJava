package views;

import java.util.*;
import java.lang.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

public class UIConfig {
    private static double averageCharWidthDefault;
    private static double averageCharHeightDefault;
    private static double scaleX=1;
    private static double scaleY=1;
    private static Font defaultFont=null;

    private static double getAverageCharWidth(Font font) {
        FontRenderContext frc = new FontRenderContext(null, true, true);
        TextLayout layout = new TextLayout("ABCDEFGHIJKLMNOPQRSTUVWXYZ", font, frc);
        double width = layout.getBounds().getWidth();
        return width / 26.0; 
    }

    // Método para calcular a altura média de um caractere na fonte
    private static double getAverageCharHeight(Font font) {
        FontRenderContext frc = new FontRenderContext(null, true, true);
        TextLayout layout = new TextLayout("ABCDEFGHIJKLMNOPQRSTUVWXYZ", font, frc);
        return layout.getBounds().getHeight(); 
    }

    static {
        defaultFont = UIManager.getFont("Label.font");
        averageCharWidthDefault = getAverageCharWidth(defaultFont);
        averageCharHeightDefault = getAverageCharHeight(defaultFont);
        scaleX = scaleY = 1;
    }
    public static void aplicarFonteGlobal(Font fonte) {
        UIManager.put("Label.font", fonte);
        UIManager.put("Button.font", fonte);
        UIManager.put("TextField.font", fonte);
        UIManager.put("PasswordField.font", fonte);
        UIManager.put("ComboBox.font", fonte);
        UIManager.put("TextArea.font", fonte);
        UIManager.put("Table.font", fonte);
        UIManager.put("TableHeader.font", fonte);
        UIManager.put("Spinner.font", fonte);
        UIManager.put("CheckBox.font", fonte);
        UIManager.put("RadioButton.font", fonte);
        UIManager.put("TitledBorder.font", fonte);
        UIManager.put("ToolTip.font", fonte);
        UIManager.put("Menu.font", fonte);
        UIManager.put("MenuItem.font", fonte);
        UIManager.put("OptionPane.messageFont", fonte);
        UIManager.put("OptionPane.buttonFont", fonte);

        double averageCharWidth = getAverageCharWidth(fonte);
        double averageCharHeight = getAverageCharHeight(fonte);

        scaleX = averageCharWidth / averageCharWidthDefault; 
        scaleY = averageCharHeight / averageCharHeightDefault;

        defaultFont = fonte;
    }

    public static void aplicarLookAndFeelNimbus() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("Nimbus não disponível. Usando Look and Feel padrão.");
        }
    }

    public static void aplicarLookAndFeelMetal() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e) {
            System.out.println("Metal não disponível. Usando Look and Feel padrão.");
        }
    }

    public static void listarLookAndFeelsDisponiveis() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            System.out.println(info.getName() + " -> " + info.getClassName());
        }
    }
    public static void configurarLookAndFeel() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            System.out.println(info.getName() + " -> " + info.getClassName());
            if (info.getName().equals("Nimbus")) //"Nimbus", "Metal", "CDE/Motif", "Windows" "Windows Classic"
            try {
               UIManager.setLookAndFeel(info.getClassName());
            }catch (Exception e) {
               System.out.println(info.getName()+" não disponivel. Usando Look and Fell padrão");
            }
        }
    }

    public static Font carregarFonte(String caminhoInterno, float tamanho) {
        try {
            InputStream is = UIConfig.class.getClassLoader().getResourceAsStream(caminhoInterno);
            if (is == null) throw new Exception("Fonte não encontrada: " + caminhoInterno);
            Font font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.BOLD, tamanho);
            return font;
        } catch (Exception e) {
            System.out.println("Erro ao carregar fonte personalizada: " + e.getMessage());
            return new Font("SansSerif", Font.PLAIN, (int) tamanho); // fallback
        }
    }
    public static void updateUI() {
        // Método reflexivo para atualizar todos os componentes
        try {
            Method updateComponentTreeUI = UIManager.class.getMethod("updateComponentTreeUI", JComponent.class);

            // Aplique para todos os componentes da interface
            for (Window window : Window.getWindows()) {
                updateComponentTreeUI.invoke(window, window);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateUI(Component component) {
        if (component instanceof JComponent) {
            JComponent jComponent = (JComponent) component;
            jComponent.setFont(defaultFont);
        }

        // Se o componente tiver filhos (subcomponentes), aplica recursivamente
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                updateUI(child);
            }
        }
    }
    
    public static void updateUIWithAutoScale(Component component) {
        if (component instanceof Window) {
            Window janela = (Window) component;
            int larg = janela.getWidth();
            int alt = janela.getHeight();
            janela.setSize((int) (larg * scaleX), (int) (alt * scaleY));
        } 
        else if (component instanceof JComponent) {
            JComponent jComponent = (JComponent) component;
            Dimension preferredSize = jComponent.getPreferredSize();
            if (preferredSize != null && preferredSize.width > 0 && preferredSize.height > 0) {
                int larg = preferredSize.width;
                int alt = preferredSize.height;
                jComponent.setPreferredSize(new Dimension((int) (larg * scaleX), (int) (alt * scaleY) ));
                jComponent.setFont(defaultFont);
            }
        }
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                updateUIWithAutoScale(child);
            }
        }
    }
}