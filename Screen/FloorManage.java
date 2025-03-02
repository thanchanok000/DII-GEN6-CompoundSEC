package    Screen;

import Class.ChildFloor;
import Class.Floor;
import Class.FloorList;
import Class.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FloorManage {
    private JButton addFloorButton;
    private JTable table1;
    private JPanel panel1;
    private DefaultTableModel tableModel;

    public FloorManage() {
        panel1 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel("Floor Manage");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel1.add(label, gbc);

        addFloorButton = new JButton("Add Floor");
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel1.add(addFloorButton, gbc);

        tableModel = new DefaultTableModel(new Object[] { "ID", "Name", "Accessable Role" }, 0);
        table1 = new JTable(tableModel);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel1.add(new JScrollPane(table1), gbc);

        addFloorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog((Frame) null, "Add Floor", true);
                dialog.setSize(400, 300);
                dialog.setLayout(new BorderLayout());

                JPanel dialogPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(5, 5, 5, 5);

                JLabel floorLabel = new JLabel("Floor Name:");
                gbc.gridx = 0;
                gbc.gridy = 0;
                dialogPanel.add(floorLabel, gbc);

                JTextField floorTextField = new JTextField(20);
                gbc.gridx = 1;
                gbc.gridy = 0;
                dialogPanel.add(floorTextField, gbc);

                dialog.add(dialogPanel, BorderLayout.CENTER);

                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        List<Room> rooms = new ArrayList<>(); // Initialize with an empty list of rooms
                        ChildFloor childFloor = new ChildFloor(0, floorTextField.getText(), rooms);
                        FloorList.addCard(childFloor);
                        refreshTable();
                        dialog.dispose();
                    }
                });
                dialog.add(okButton, BorderLayout.SOUTH);

                dialog.setLocationRelativeTo(panel1);
                dialog.setVisible(true);
            }
        });

        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table1.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    Floor selectedFloor = FloorList.getFloors().get(row);
                    showAccessRolesDialog(selectedFloor);
                }
            }
        });

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0); // Clear existing rows
        List<Floor> floors = FloorList.getFloors();
        for (Floor floor : floors) {
            tableModel.addRow(new Object[] { floor.getId(), floor.getName(), String.join(", ", floor.getPriority()) });
        }
    }

    private void showAccessRolesDialog(Floor floor) {
        JDialog dialog = new JDialog((Frame) null, "Access Roles", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());

        JPanel dialogPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel label = new JLabel("Access Roles for " + floor.getName() + ":");
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialogPanel.add(label, gbc);

        List<String> roles = Arrays.asList("low", "medium", "high");
        List<JCheckBox> checkBoxes = new ArrayList<>();
        int y = 1;
        for (String role : roles) {
            JCheckBox checkBox = new JCheckBox(role);
            checkBox.setSelected(floor.getPriority().contains(role));
            checkBoxes.add(checkBox);
            gbc.gridx = 0;
            gbc.gridy = y++;
            dialogPanel.add(checkBox, gbc);
        }

        JButton showRoomsButton = new JButton("Show Rooms");
        showRoomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRoomScreen(floor);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = y++;
        dialogPanel.add(showRoomsButton, gbc);

        dialog.add(dialogPanel, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> selectedRoles = new ArrayList<>();
                for (JCheckBox checkBox : checkBoxes) {
                    if (checkBox.isSelected()) {
                        selectedRoles.add(checkBox.getText());
                    }
                }
                floor.setPriority(selectedRoles);
                refreshTable();
                dialog.dispose();
            }
        });
        dialog.add(okButton, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(panel1);
        dialog.setVisible(true);
    }

    private void showRoomScreen(Floor floor) {
        ManageRoom roomManage = new ManageRoom((ChildFloor) floor); // Pass the selected floor to ManageRoom
        roomManage.updateRoomTable(floor.getRooms());
        JPanel roomPanel = roomManage.getPanel();
        
        JDialog dialog = new JDialog((Frame) null, "Room Manage", true);
        dialog.setContentPane(roomPanel);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(panel1);
        dialog.setVisible(true);
    }

    public JPanel getPanel() {
        return panel1;
    }
}
