import java.awt.*;
import javax.swing.*;

public class ItemsGuideDialog extends JDialog {

    public ItemsGuideDialog(JFrame parent) {
        super(parent, "Items Guide", true);

        setDialogProperties(parent);
        
        JPanel itemsPanel = createItemsPanel();
        
        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        this.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.setFocusable(false);
        closeButton.addActionListener(e -> this.dispose()); 
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private void setDialogProperties(JFrame parent) {
        this.setResizable(false);
        this.setSize(600, 550); 
        this.setLocationRelativeTo(parent); 
        this.setLayout(new BorderLayout());
    }
    
    private JLabel createIconLabel(String imagePath) {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        return new JLabel(icon);
    }

    private JPanel createItemsPanel() {
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new GridBagLayout()); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        Object[][] items = {
            {"Bomb Block", "เมื่อเก็บจะทำการระเบิดแถวล่างสุด", "/item/atomic-bomb.png"},
            {"Bonus", "ได้รับคะแนนพิเศษ", "/item/bonus.png"},
            {"Score Decrese", "ลดคะแนน", "/item/ScoreD.png"},
            {"Speed Up", "เพิ่มความเร็วการตกของตัวต่อ", "/item/speedup.png"},
            {"White Block", "เปลี่ยนสีตัวต่อ เป็นสีขาว", "/item/white.png"},
            {"Color", "เปลี่ยนสีตัวต่อ แบบสุ่ม", "/item/Color.png"}
        };

        int row = 0;
        for (Object[] item : items) {
            addItemToPanel(itemsPanel, item, row, gbc);
            row++;
        }

        return itemsPanel;
    }

    private void addItemToPanel(JPanel panel, Object[] item, int row, GridBagConstraints gbc) {
        String name = (String) item[0];
        String description = (String) item[1];
        String imagePath = (String) item[2];

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel iconLabel = createIconLabel(imagePath);
        panel.add(iconLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.4;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        panel.add(nameLabel, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel.add(descriptionLabel, gbc);
    }
}
