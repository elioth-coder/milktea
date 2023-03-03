package milkytea;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class CellButtonEditor extends DefaultCellEditor {
	private static final long serialVersionUID = -8760058890742603997L;
	private JButton button;
	private String buttonLabel;
	private JPanel panel;
    private JTable parentTable;
    private int rowIndex, colIndex;
    private boolean clicked;
	private CellButtonAction action;
	
	public CellButtonEditor() {
		super(new JCheckBox());		
		
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		button = new JButton();
		button.setOpaque(true);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Hahaha it works!");
				fireEditingStopped();
			}
		});
	}
	
	public JTable getParentTable() {
		return parentTable;
	}

	public int getRowIndex() {
		return this.rowIndex;
	}

	public int getColIndex() {
		return this.colIndex;
	}
	
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    	parentTable = table;
		rowIndex = row;
		colIndex = column;
		action.setup(this);
		buttonLabel = (value == null) ? "" : value.toString();
		
		button.setForeground(Color.black);
      	button.setBackground(UIManager.getColor("Button.background"));
      	button.setText(buttonLabel);
      	clicked = true;
		
		panel.setForeground(parentTable.getSelectionForeground());
		panel.setBackground(parentTable.getSelectionBackground());
		
		button.setText(value.toString());
		panel.add(button);
		
		return panel;
	}
    
    public void setAction(CellButtonAction action) {
    	this.action = action;
    }
    
    public Object getCellEditorValue() {
    	if (clicked && action != null) {
    		action.execute();
        }
        
        clicked = false;
        return new String(buttonLabel);
    }

    public boolean stopCellEditing() {
    	clicked = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
    	super.fireEditingStopped();
    }
}

