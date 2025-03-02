package    Screen;

import Class.Floor;
import Class.EnterRoomHistory;
import Class.FloorList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FloorRoomHistoryScreen {
    private JPanel panel;
    private JTable historyTable;

    public FloorRoomHistoryScreen() {
        panel = new JPanel(new BorderLayout());

        List<Floor> floors = FloorList.getFloors(); // Use FloorList to get floors
        if (floors.isEmpty()) {
            JLabel titleLabel = new JLabel("No floors available");
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            panel.add(titleLabel, BorderLayout.NORTH);
            return;
        }

        // Create a combo box to select floors
        JComboBox<String> floorComboBox = new JComboBox<>();
        for (Floor floor : floors) {
            floorComboBox.addItem(floor.getName());
        }
        panel.add(floorComboBox, BorderLayout.NORTH);

        // Create a table to display history
        String[] columnNames = { "Card Owner", "Access Level", "Room Name", "Success", "Timestamp" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        historyTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(historyTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add action listener to combo box
        floorComboBox.addActionListener(e -> {
            String selectedFloorName = (String) floorComboBox.getSelectedItem();
            Floor selectedFloor = floors.stream()
                    .filter(floor -> floor.getName().equals(selectedFloorName))
                    .findFirst()
                    .orElse(null);

            if (selectedFloor != null) {
                List<EnterRoomHistory> histories = selectedFloor.getEnterRoomHistory();
                model.setRowCount(0); // Clear existing rows
                for (EnterRoomHistory history : histories) {
                    Object[] rowData = {
                            history.getCard().getOwnerName(),
                            history.getCard().getAccessLevel(),
                            history.getRoomName(),
                            history.isSuccess() ? "Yes" : "No",
                            history.getTimestamp().toString()
                    };
                    model.addRow(rowData);
                }
            }
        });

        // Trigger initial load
        if (floorComboBox.getItemCount() > 0) {
            floorComboBox.setSelectedIndex(0);
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}