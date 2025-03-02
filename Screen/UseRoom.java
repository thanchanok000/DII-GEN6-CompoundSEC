package Screen;

import Class.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UseRoom {
    private    JPanel panel;
    private JTable roomTable;
    private Floor floor;

    public UseRoom(Floor floor) {
        this.floor = floor;
        List<Room> rooms = floor.getRooms();
        panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton enterRoomButton = new JButton("Enter Room");
        topPanel.add(enterRoomButton);
        panel.add(topPanel, BorderLayout.NORTH);

        String[] columnNames = { "ID", "Name" };
        Object[][] rowData = new Object[rooms.size()][2];
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            rowData[i][0] = room.getId();
            rowData[i][1] = room.getName();
        }

        DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        roomTable = new JTable(model);

        // Allow row selection.
        roomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(roomTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button event to show popup with card dropdown.
        enterRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = roomTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Object roomId = roomTable.getValueAt(selectedRow, 0);
                    Object roomName = roomTable.getValueAt(selectedRow, 1);

                    // Create card dropdown from CardList.
                    List<Card> cards = CardList.getCards();
                    JComboBox<Card> cardComboBox = new JComboBox<>();
                    for (Card card : cards) {
                        cardComboBox.addItem(card);
                    }

                    // Set custom renderer to show only card ownerName.
                    cardComboBox.setRenderer(new DefaultListCellRenderer() {
                        @Override
                        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                boolean isSelected, boolean cellHasFocus) {
                            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                            if (value instanceof Card) {
                                Card card = (Card) value;
                                setText(card.getOwnerName());
                            }
                            return this;
                        }
                    });

                    JPanel inputPanel = new JPanel(new BorderLayout());
                    inputPanel.add(new JLabel("Select Card:"), BorderLayout.NORTH);
                    inputPanel.add(cardComboBox, BorderLayout.CENTER);

                    // Display popup dialog.
                    int result = JOptionPane.showConfirmDialog(panel, inputPanel,
                            "Enter Room ID: " + roomId, JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        Card selectedCard = (Card) cardComboBox.getSelectedItem();
                        
                        // Attempt to enter room and add history.
                        Room room = rooms.get(selectedRow);
                        boolean success = room.enterRoom(selectedCard);
                        floor.addEnterRoomHistory(new EnterRoomHistory(selectedCard, success, roomName.toString()));
                        
                        if (success) {
                            JOptionPane.showMessageDialog(panel,
                                    "Access granted to " + selectedCard.getOwnerName() + " for room " + roomName);
                        } else {
                            JOptionPane.showMessageDialog(panel,
                                    "Access denied to " + selectedCard.getOwnerName() + " for room " + roomName);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Please select a room first.");
                }
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
