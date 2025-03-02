package     Screen;

import Class.EnterRoomHistory;
import Class.EnterRoomHistoryList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class EnterRoomHistoryScreen {
    private JPanel panel;
    private JTable historyTable;
    private JComboBox<String> floorDropdown;
    private List<EnterRoomHistory> allHistories;

    public EnterRoomHistoryScreen() {
        panel = new JPanel(new BorderLayout());

        String[] columnNames = { "Card Owner", "Access Level", "Room Name", "Success", "Timestamp" };
        allHistories = EnterRoomHistoryList.getHistories();

        // Assuming floors are represented as strings, e.g., "Floor 1", "Floor 2", etc.
        String[] floors = { "All Floors", "Floor 1", "Floor 2", "Floor 3" }; // Add more floors as needed
        floorDropdown = new JComboBox<>(floors);
        floorDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Select Floor:"));
        topPanel.add(floorDropdown);
        panel.add(topPanel, BorderLayout.NORTH);

        historyTable = new JTable();
        panel.add(new JScrollPane(historyTable), BorderLayout.CENTER);

        updateTable(); // Initialize table with all histories
    }

    private void updateTable() {
        String selectedFloor = (String) floorDropdown.getSelectedItem();
        List<EnterRoomHistory> filteredHistories = allHistories;

        if (!"All Floors".equals(selectedFloor)) {
            filteredHistories = allHistories.stream()
                    .filter(history -> history.getRoomName().contains(selectedFloor))
                    .collect(Collectors.toList());
        }

        String[] columnNames = { "Card Owner", "Access Level", "Room Name", "Success", "Timestamp" };
        Object[][] rowData = new Object[filteredHistories.size()][5];
        for (int i = 0; i < filteredHistories.size(); i++) {
            EnterRoomHistory history = filteredHistories.get(i);
            rowData[i][0] = history.getCard().getOwnerName();
            rowData[i][1] = history.getCard().getAccessLevel();
            rowData[i][2] = history.getRoomName();
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
    }

    public JPanel getPanel() {
        return panel;
    }
}
