package Screen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import Class.FloorList;
import Class.Floor;
import Class.CardList;
import Class.Card;
import Class.EnterFloorHistory;
import Class.EnterFloorHistoryList;

public class UseFloor {
    private JPanel panel;
    private JTable floorTable;

    public UseFloor() {
        panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton popupButton = new JButton("Enter Floor");
        topPanel.add(popupButton);
        panel.add(topPanel, BorderLayout.NORTH);

        String[] columnNames = { "ID", "Name" };
        List<Floor> floors = FloorList.getFloors();
        Object[][] rowData = new Object[floors.size()][2];
        for (int i = 0; i < floors.size(); i++) {
            Floor floor = floors.get(i);
            rowData[i][0] = floor.getId();
            rowData[i][1] = floor.getName();
        }

        DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        floorTable = new JTable(model);

        // Allow row selection.
        floorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Optional: MouseAdapter for visual feedback on double-click.
        floorTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = floorTable.getSelectedRow();
                    Object floorId = floorTable.getValueAt(selectedRow, 0);
                    System.out.println("Double clicked on floor: " + floorId);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(floorTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button event to show popup with card dropdown.
        popupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = floorTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Object floorId = floorTable.getValueAt(selectedRow, 0);
                    Object floorName = floorTable.getValueAt(selectedRow, 1);

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
                            "Enter Floor ID: " + floorId, JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        Card selectedCard = (Card) cardComboBox.getSelectedItem();

                        // Attempt to enter floor and add history.
                        Floor floor = FloorList.getFloors().get(selectedRow);
                        boolean success = floor.enterFloor(selectedCard);

                        EnterFloorHistoryList.addHistory(new EnterFloorHistory(selectedCard, success, floorName.toString()));

                        if (success) {
                            JOptionPane.showMessageDialog(panel,
                                    "Access granted to " + selectedCard.getOwnerName() + " for floor " + floorName);
                            // เพิ่มประวัติการเข้า Floor

                            JFrame newFrame = new JFrame("Use Room");
                            UseRoom useRoom = new UseRoom(floor);
                            newFrame.setContentPane(useRoom.getPanel());
                            newFrame.setSize(1280, 720);
                            newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            newFrame.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(panel,
                                    "Access denied to " + selectedCard.getOwnerName() + " for floor " + floorName);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Please select a floor first.");
                }
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}