package    Screen;

import javax.swing.*;
import java.awt.*;

public class MainScreen {
    private JPanel panel1; // Change from 'panel' to 'panel1'
    private JButton useRoomButton;
    private JButton manageCardButton;
    private JButton cardManageHistoryButton;
    private JButton historyEnterRoomButton;
    private JButton manageFloorButton;
    private JButton historyRoomButton;

    public MainScreen() {
        // สร้าง Panel หลัก
        panel1 = new JPanel(new BorderLayout()); // Change from 'panel' to 'panel1'

        // สร้าง Panel สำหรับปุ่ม
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        // สร้างปุ่มทั้งหมด
        useRoomButton = new JButton("Use Room");
        manageCardButton = new JButton("Manage Cards");
        cardManageHistoryButton = new JButton("Card Manage History");
        historyEnterRoomButton = new JButton("Enter Floor History");
        manageFloorButton = new JButton("Manage Floors");
        historyRoomButton = new JButton("Enter Room History");

        // เพิ่มปุ่มทั้งหมดลงใน Panel
        buttonPanel.add(useRoomButton);
        buttonPanel.add(cardManageHistoryButton);
        buttonPanel.add(manageCardButton);
        buttonPanel.add(historyEnterRoomButton);
        buttonPanel.add(manageFloorButton);
        buttonPanel.add(historyRoomButton);

        // เพิ่ม Panel ปุ่มไปยัง Panel หลัก
        panel1.add(buttonPanel, BorderLayout.CENTER); // Change from 'panel' to 'panel1'

        // **ActionListener สำหรับแต่ละปุ่ม**
        useRoomButton.addActionListener(e -> openNewWindow("Enter Floor", new UseFloor().getPanel()));
        cardManageHistoryButton.addActionListener(e -> openNewWindow("Card Manage History", new CardManageHistory().getPanel()));
        manageCardButton.addActionListener(e -> openNewWindow("Manage Cards", new CardManage().getPanel()));
        historyEnterRoomButton.addActionListener(e -> openNewWindow("Enter Floor History", new EnterFloorHistoryScreen().getPanel()));
        manageFloorButton.addActionListener(e -> openNewWindow("Manage Floors", new FloorManage().getPanel()));
        historyRoomButton.addActionListener(e -> openNewWindow("History Room", new FloorRoomHistoryScreen().getPanel()));
    }

    // **ฟังก์ชันสำหรับเปิดหน้าต่างใหม่**
    private void openNewWindow(String title, JPanel contentPanel) {
        if (contentPanel != null) {
            JFrame newFrame = new JFrame(title);
            newFrame.setContentPane(contentPanel);
            newFrame.setSize(1280, 720);
            newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newFrame.setVisible(true);
        } else {
            System.err.println("Error: Panel is null for " + title);
        }
    }

    public JPanel getPanel() {
        return panel1; // Change from 'panel' to 'panel1'
    }
}
