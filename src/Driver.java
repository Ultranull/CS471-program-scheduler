
import javax.swing.*;

class Driver {
    public static void main(String... args){
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Motif".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        LogDisplay.init();
        JFrame frame=new JFrame("scheduler");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(700,600);
        frame.setContentPane(new MainPanel(frame));
        frame.setVisible(true);

    }
}
