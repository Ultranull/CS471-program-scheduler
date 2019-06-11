import javax.swing.*;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

class LogDisplay{
    private static StyledDocument doc;

    public static void init(){
        JTextPane text = new JTextPane();
        text.setEditable(false);

        DefaultCaret caret = (DefaultCaret) text.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        doc = text.getStyledDocument();

        JFrame frame=new JFrame("LOG");

        JScrollPane scrollPane = new JScrollPane(text);
        frame.add(scrollPane);
        frame.setSize(200, 400);
        frame.setLocation(700, 0);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    @SuppressWarnings("SpellCheckingInspection")
    public static void print(String text){
        SimpleAttributeSet def = new SimpleAttributeSet();
        StyleConstants.setForeground(def, Color.BLACK);
        StyleConstants.setBackground(def, Color.white);
        StyleConstants.setBold(def, true);
        StyleConstants.setFontFamily(def,"Calibri");
        StyleConstants.setFontSize(def,16);
        try {
            doc.insertString(doc.getLength(),text,def);
        }
        catch(Exception e) { e.printStackTrace(); }
    }
    @SuppressWarnings("SpellCheckingInspection")
    public static void print(String text, Color fore, Color back){
        SimpleAttributeSet def = new SimpleAttributeSet();
        StyleConstants.setForeground(def, fore);
        StyleConstants.setBackground(def, back);
        StyleConstants.setBold(def, true);
        StyleConstants.setFontFamily(def,"Calibri");
        StyleConstants.setFontSize(def,16);
        StyleConstants.setBold(def,true);
        try {
            doc.insertString(doc.getLength(),text,def);
        }
        catch(Exception e) {e.printStackTrace(); }
    }
}
