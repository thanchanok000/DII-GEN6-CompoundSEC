package    Screen;

import Class.CardEditHistory;
import Class.CardEditHistoryList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CardManageHistory {
    private JPanel panel1;
    private JTable table1;

    public CardManageHistory() {
        panel1 = new JPanel(new BorderLayout());
        table1 = new JTable(new DefaultTableModel(new Object[]{"Card ID", "Old Value", "New Value", "Timestamp"}, 0));
        panel1.add(new JScrollPane(table1), BorderLayout.CENTER);
        loadHistory();
    }

    private void loadHistory() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0); // Clear existing data
        List<CardEditHistory> historyList = CardEditHistoryList.getHistoryList();
        for (CardEditHistory history : historyList) {
            model.addRow(new Object[]{
                    history.getCard().getId(),
                    history.getOldValue(),
                    history.getNewValue(),
                    history.getTimestamp()
            });
        }
    }

    public JPanel getPanel() {
        return panel1;
    }
}
