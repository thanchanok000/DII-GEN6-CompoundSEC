package Screen;


import Class.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.List;

public class CardManage {
    private JPanel panel1;
    private JButton addButton;
    private JTable table1;
    private DefaultTableModel model;

    public CardManage() {
        // Define column headers
        String[] columnHeaders = { "ID", "Card Name", "Status" };

        // Use the existing table from the form (not reinitialize)
        model = new DefaultTableModel(columnHeaders, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Allow editing only in "Status" column
            }
        };

        table1.setModel(model); // Set model to the existing table
        loadCardData(); // Load data from CardList Singleton

        // Set the ComboBox as the editor for the "Status" column
        TableColumn statusColumn = table1.getColumnModel().getColumn(2);

        // Add MouseListener to the table for row click event
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table1.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    editCardProperties(row);
                }
            }
        });

        // Set up the Add Button
        addButton.addActionListener(e -> {
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

            JTextField cardNameField = new JTextField(10);
            inputPanel.add(new JLabel("Enter Card Owner Name:"));
            inputPanel.add(cardNameField);

            String[] accessLevels = { "low", "medium", "high" };
            JComboBox<String> accessLevelComboBox = new JComboBox<>(accessLevels);
            inputPanel.add(new JLabel("Select Access Level:"));
            inputPanel.add(accessLevelComboBox);

            int result = JOptionPane.showConfirmDialog(panel1, inputPanel, "Add New Card",
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String cardName = cardNameField.getText().trim();
                String accessLevel = (String) accessLevelComboBox.getSelectedItem();

                if (!cardName.isEmpty() && accessLevel != null) {
                    Card newCard;
                    if (accessLevel.equals("low")) {
                        newCard = new EmployeeCard(CardList.getInstance().getCards().size() + 1, cardName, accessLevel);
                    } else if (accessLevel.equals("medium")) {
                        newCard = new ManagerCard(CardList.getInstance().getCards().size() + 1, cardName);
                    } else if (accessLevel.equals("high")) {
                        newCard = new OwnerCard(CardList.getInstance().getCards().size() + 1, cardName);
                    } else {
                        newCard = new Card(CardList.getInstance().getCards().size() + 1, cardName, accessLevel) {
                            @Override
                            public String getAccessLevel() {
                                return accessLevel;
                            }
                        };
                    }
                    CardList.getInstance().addCard(newCard);
                    loadCardData();
                }
            }
        });
    }

    // Method to edit card properties
    private void editCardProperties(int row) {
        String cardName = (String) model.getValueAt(row, 1);
        String accessLevel = (String) model.getValueAt(row, 2);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        JTextField cardNameField = new JTextField(cardName, 10);
        inputPanel.add(new JLabel("Edit Card Name:"));
        inputPanel.add(cardNameField);

        String[] accessLevels = { "none", "low", "medium", "high" };
        JComboBox<String> accessLevelComboBox = new JComboBox<>(accessLevels);
        accessLevelComboBox.setSelectedItem(accessLevel);
        inputPanel.add(new JLabel("Edit Access Level:"));
        inputPanel.add(accessLevelComboBox);

        int result = JOptionPane.showConfirmDialog(panel1, inputPanel, "Edit Card Properties",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String newCardName = cardNameField.getText().trim();
            String newAccessLevel = (String) accessLevelComboBox.getSelectedItem();

            if (!newCardName.isEmpty() && newAccessLevel != null) {
                Card oldCard = CardList.getInstance().getCards().get(row);
                Card newCard;
                if (newAccessLevel.equals("low")) {
                    newCard = new EmployeeCard(oldCard.getId(), newCardName, newAccessLevel);
                } else if (newAccessLevel.equals("medium")) {
                    newCard = new ManagerCard(oldCard.getId(), newCardName);
                } else if (newAccessLevel.equals("high")) {
                    newCard = new OwnerCard(oldCard.getId(), newCardName);
                } else {
                    newCard = new Card(oldCard.getId(), newCardName, newAccessLevel) {
                        @Override
                        public String getAccessLevel() {
                            return newAccessLevel;
                        }
                    };
                }
                CardList.getInstance().updateCard(row, newCard); // Update the card in the list
                loadCardData();
            }
        }
    }

    // Method to Load Data from CardList Singleton into JTable
    private void loadCardData() {
        model.setRowCount(0); // Clear existing data

        List<Card> cards = CardList.getInstance().getCards(); // Fetch current card list
        for (Card card : cards) {
            model.addRow(new Object[] { card.getId(), card.getOwnerName(), card.getEncryptedAccessLevel() });
        }
    }

    public JPanel getPanel() {
        return panel1;
    }
}

