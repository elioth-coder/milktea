package milkytea;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class CellButtonRenderer implements TableCellRenderer {
	private JButton button;
	private JPanel panel;
	
	public CellButtonRenderer() {
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		button = new JButton();
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			panel.setForeground(table.getSelectionForeground());
			panel.setBackground(table.getSelectionBackground());
		} else {
			panel.setForeground(table.getForeground());
			panel.setBackground(table.getBackground());
		}
		
		button.setText(value.toString());
		panel.add(button);
		
		return panel;
	}

}

