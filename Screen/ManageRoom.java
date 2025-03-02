package    Screen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import Class.ChildFloor;
import Class.Room;

public class ManageRoom {
    private JButton addRoomButton;
    private JTable table1;
    private JPanel panel1; // This is bound to the form
    private ChildFloor selectedFloor; // Add this field to store the selected floor

    public ManageRoom(ChildFloor selectedFloor) {
        this.selectedFloor = selectedFloor; // Initialize the selected floor
        try {
            if (panel1 == null) {
                createPanel();
            }
            loadRooms(); // Load rooms from selectedFloor
            addRoomButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showAddRoomDialog();
                }
            });
            table1.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        int row = table1.getSelectedRow();
                        if (row != -1) {
                            Room room = selectedFloor.getRooms().get(row);
                            showEditRoomDialog(room, row);
                        }
                    }
                }
            });
            table1.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int row = table1.rowAtPoint(evt.getPoint());
                    if (row >= 0) {
                        Room selectedRoom = selectedFloor.getRooms().get(row);
                        showAccessRolesDialog(selectedRoom);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to initialize ManageRoom panel: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateRoomTable(List<Room> rooms) {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0); // Clear existing data
        for (Room room : rooms) {
            model.addRow(new Object[] { room.getId(), room.getName(), room.getAccessLevel() });
        }
    }

    public JPanel getPanel() {
        return panel1; // Return the form panel
    }

    public void createPanel() {
        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        addRoomButton = new JButton("Add Room");
        table1 = new JTable(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Name", "Access Level" }));
        panel1.add(addRoomButton);
        panel1.add(new JScrollPane(table1));
    }

    private void showAddRoomDialog() {
        JDialog dialog = new JDialog((Frame) null, "Add Room", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new BorderLayout());

        JPanel dialogPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Room Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialogPanel.add(nameLabel, gbc);

        JTextField nameTextField = new JTextField(30); // Increased width
        gbc.gridx = 1;
        gbc.gridy = 0;
        dialogPanel.add(nameTextField, gbc);

        JLabel accessLevelLabel = new JLabel("Access Level:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialogPanel.add(accessLevelLabel, gbc);

        JPanel accessLevelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JCheckBox lowCheckBox = new JCheckBox("Low");
        JCheckBox mediumCheckBox = new JCheckBox("Medium");
        JCheckBox highCheckBox = new JCheckBox("High");

        accessLevelPanel.add(lowCheckBox);
        accessLevelPanel.add(mediumCheckBox);
        accessLevelPanel.add(highCheckBox);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        dialogPanel.add(accessLevelPanel, gbc);

        dialog.add(dialogPanel, BorderLayout.CENTER);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            //  เพิ่ม Room เข้าใน Floor
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                List<String> accessLevels = new ArrayList<>();
                if (lowCheckBox.isSelected()) {
                    accessLevels.add("Low");
                }
                if (mediumCheckBox.isSelected()) {
                    accessLevels.add("Medium");
                }
                if (highCheckBox.isSelected()) {
                    accessLevels.add("High");
                }

                if (selectedFloor != null) {
                    Room room = new Room(selectedFloor.getRooms().size() + 1, name, accessLevels);
                    selectedFloor.addRoom(room);
                    updateRoomTable(selectedFloor.getRooms()); // Update table with rooms from the selected floor
                } else {
                    JOptionPane.showMessageDialog(dialog, "Please select a floor first.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                dialog.dispose();
            }
        });
        dialog.add(addButton, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(panel1);
        dialog.setVisible(true);
    }

    private void showEditRoomDialog(Room room, int rowIndex) {
        JDialog dialog = new JDialog((Frame) null, "Edit Room", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new BorderLayout());

        JPanel dialogPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Room Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialogPanel.add(nameLabel, gbc);

        JTextField nameTextField = new JTextField(room.getName(), 20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        dialogPanel.add(nameTextField, gbc);

        JLabel accessLevelLabel = new JLabel("Access Level:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialogPanel.add(accessLevelLabel, gbc);

        JPanel accessLevelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JCheckBox lowCheckBox = new JCheckBox("Low");
        JCheckBox mediumCheckBox = new JCheckBox("Medium");
        JCheckBox highCheckBox = new JCheckBox("High");

        List<String> accessLevels = room.getAccessLevel();
        for (String level : accessLevels) {
            if (level.equals("Low")) {
                lowCheckBox.setSelected(true);
            } else if (level.equals("Medium")) {
                mediumCheckBox.setSelected(true);
            } else if (level.equals("High")) {
                highCheckBox.setSelected(true);
            }
        }

        accessLevelPanel.add(lowCheckBox);
        accessLevelPanel.add(mediumCheckBox);
        accessLevelPanel.add(highCheckBox);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        dialogPanel.add(accessLevelPanel, gbc);

        dialog.add(dialogPanel, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                List<String> accessLevels = new ArrayList<>();
                if (lowCheckBox.isSelected()) {
                    accessLevels.add("Low");
                }
                if (mediumCheckBox.isSelected()) {
                    accessLevels.add("Medium");
                }
                if (highCheckBox.isSelected()) {
                    accessLevels.add("High");
                }
                room.setName(name);
                room.setAccessLevel(accessLevels);
                selectedFloor.getRooms().set(rowIndex, room);
                updateRoomTable(selectedFloor.getRooms());
                dialog.dispose();
            }
        });
        dialog.add(saveButton, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(panel1);
        dialog.setVisible(true);
    }

    private void showAccessRolesDialog(Room room) {
        JDialog dialog = new JDialog((Frame) null, "Access Roles for " + room.getName(), true);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel label = new JLabel("Access Roles for " + room.getName() + ":");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label, gbc);

        java.util.List<String> roles = java.util.Arrays.asList("Low", "Medium", "High");
        java.util.List<JCheckBox> checkBoxes = new java.util.ArrayList<>();
        int y = 1;
        for (String role : roles) {
            JCheckBox checkBox = new JCheckBox(role);
            checkBox.setSelected(room.getAccessLevel().contains(role));
            checkBoxes.add(checkBox);
            gbc.gridx = 0;
            gbc.gridy = y++;
            panel.add(checkBox, gbc);
        }

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                java.util.List<String> selectedRoles = new java.util.ArrayList<>();
                for (JCheckBox cb : checkBoxes) {
                    if (cb.isSelected()) {
                        selectedRoles.add(cb.getText());
                    }
                }
                room.setAccessLevel(selectedRoles);
                updateRoomTable(selectedFloor.getRooms());
                dialog.dispose();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(okButton, gbc);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.setLocationRelativeTo(table1);
        dialog.setVisible(true);
    }

    private void loadRooms() {
        List<Room> rooms = selectedFloor.getRooms(); // Load rooms from the selected floor
        updateRoomTable(rooms);
    }

    // This method is automatically generated by the GUI designer
    private void $$$setupUI$$$() {
        // ...existing code...
    }
}
