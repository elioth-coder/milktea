package milkytea;


import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class CellRenderer implements TableCellRenderer {
	private JPanel panel;
	private ArrayList<CellEditorComponent> components = new ArrayList<CellEditorComponent>();

	
	public CellRenderer() {	
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
	}
    
    public  void add(CellEditorComponent component) {
    	components.add(component);
    }

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			panel.setForeground(table.getSelectionForeground());
			panel.setBackground(table.getSelectionBackground());
		} else {
			panel.setForeground(table.getForeground());
			panel.setBackground(table.getBackground());
		}
		
		for(CellEditorComponent component : components) {
			component.setTable(table);
			component.setRow(row);
			component.setColumn(column);
			panel.add(component);
		}
		
		return panel;
	}
}

