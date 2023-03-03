package milkytea;


import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class CellEditor extends DefaultCellEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4335415135947578437L;
	private JPanel panel;
	private ArrayList<CellEditorComponent> components = new ArrayList<CellEditorComponent>();

	
	public CellEditor() {
		super(new JCheckBox());		
		
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
	}
	
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		panel.setForeground(table.getSelectionForeground());
		panel.setBackground(table.getSelectionBackground());
		
		for(CellEditorComponent component : components) {
			component.setForeground(Color.black);
			component.setBackground(UIManager.getColor("Button.background"));
			component.setTable(table);
			component.setRow(row);
			component.setColumn(column);
			panel.add(component);
		}
		
		return panel;
	}
    
    public  void add(CellEditorComponent component) {
    	components.add(component);
    }
}

