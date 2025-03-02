package     Screen;

import javax.swing.*;
import java.awt.*;

public class HistoryRoomScreen {
    private JPanel panel;
    private JLabel label;

    public HistoryRoomScreen() {
        panel = new JPanel(new BorderLayout());
        label = new JLabel("History Room Screen");
        panel.add(label, BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return panel;
    }
}
