package     Screen;

import Class.EnterFloorHistory;
import Class.EnterFloorHistoryList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterFloorHistoryScreen {
    private JPanel panel;
    private JTable historyTable;
    private JButton enterRoomHistoryButton;

    public EnterFloorHistoryScreen() {
        panel = new JPanel(new BorderLayout());

        String[] columnNames = { "Card Owner", "Access Level", "Floor Name", "Success", "Timestamp" };
        List<EnterFloorHistory> histories = EnterFloorHistoryList.getHistories();
        Object[][] rowData = new Object[histories.size()][5];
        for (int i = 0; i < histories.size(); i++) {
            EnterFloorHistory history = histories.get(i);
            rowData[i][0] = history.getCard().getOwnerName();
            rowData[i][1] = history.getCard().getAccessLevel();
            rowData[i][2] = history.getFloorName();
            rowData[i][3] = history.isSuccess() ? "Yes" : "No";
            rowData[i][4] = history.getTimestamp().toString();
        }

        DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        historyTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(historyTable);
        panel.add(scrollPane, BorderLayout.CENTER);



        JPanel topPanel = new JPanel(new BorderLayout());
        panel.add(topPanel, BorderLayout.NORTH);
    }

    public JPanel getPanel() {
        return panel;
    }
}
