import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class HowToPlayDialog extends JDialog {

    public HowToPlayDialog(JFrame parent) {
        super(parent, "How to Play", true);
        
        configureDialog(parent);

        JTable table = createTable();
        JScrollPane tableScrollPane = new JScrollPane(table);
        this.add(tableScrollPane, BorderLayout.CENTER);

        JLabel additionalText = createAdditionalTextLabel();
        JPanel textPanel = createTextPanel(additionalText);

        JButton closeButton = createCloseButton();
        JPanel buttonPanel = createButtonPanel(closeButton);
        JPanel bottomPanel = createBottomPanel(textPanel, buttonPanel);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private void configureDialog(JFrame parent) {
        this.setResizable(false);
        this.setSize(600, 350); 
        this.setLocationRelativeTo(parent); 
        this.setLayout(new BorderLayout());
    }

    private JTable createTable() {
        String[] columnNames = {"P1", "P2", "Description"};
        Object[][] data = {
            {"A, D", "Left, Right", "เพื่อขยับตัวต่อไปซ้าย หรือ ขวา"},
            {"W", "UP", "เพื่อหมุนตัวต่อ"},
            {"S", "DOWN", "เพื่อให้ตัวต่อ ลงมาเร็วขึ้น"}
        };

        JTable table = new JTable(data, columnNames);

        table.setFont(new Font("Tahoma", Font.PLAIN, 16));
        table.setRowHeight(40);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100); 
        columnModel.getColumn(1).setPreferredWidth(100); 
        columnModel.getColumn(2).setPreferredWidth(400); 

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 18));

        return table;
    }

    private JLabel createAdditionalTextLabel() {
        String text = "<html><div style='text-align: center;'>พยายามขยับตัวต่อให้ตกลงมาแล้วเรียงกันให้ได้<br>" +
                      "พอเรียงกันแล้ว แถวที่ถูกเรียงครบจะถูกนำออก และจงระวังอย่าให้มีช่องว่างละ</div></html>";
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", Font.PLAIN, 18));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }

    private JPanel createTextPanel(JLabel textLabel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(textLabel, BorderLayout.CENTER);
        return panel;
    }

    private JButton createCloseButton() {
        JButton button = new JButton("Close");
        button.addActionListener(e -> this.dispose()); 
        button.setFocusable(false);
        return button;
    }

    private JPanel createButtonPanel(JButton closeButton) {
        JPanel panel = new JPanel();
        panel.add(closeButton);
        return panel;
    }

    private JPanel createBottomPanel(JPanel textPanel, JPanel buttonPanel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(textPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }
}
