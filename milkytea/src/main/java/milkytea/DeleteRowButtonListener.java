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

public class DeleteRowButtonListener extends DefaultCellEditor {

	private static final long serialVersionUID = 2623767968436301845L;
	private JButton button;
    private String label;
    private boolean clicked;
    private int row, col;
    private JTable table;

    public DeleteRowButtonListener() {
		super(new JCheckBox());
		
		button = new JButton();
		button.setOpaque(true);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
			}
		});
	}
    
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		this.table = table;
		this.row = row;
		this.col = column;

		button.setForeground(Color.black);
      	button.setBackground(UIManager.getColor("Button.background"));
      	label = (value == null) ? "" : value.toString();
      	button.setText(label);
      	clicked = true;
      	JPanel panel = new JPanel();
      	panel.setBorder(new EmptyBorder(0, 5, 0, 5));
      	panel.add(button);
		panel.setForeground(table.getSelectionForeground());
		panel.setBackground(table.getSelectionBackground());
		
		return panel;
    }
    public Object getCellEditorValue() {
      if (clicked) {
        MessageBox.show("Column with Value: "+table.getValueAt(row, 1) + " -  Clicked!", "info");
      }
      
      clicked = false;
      return new String(label);
    }

    public boolean stopCellEditing()
    {
      clicked = false;
      return super.stopCellEditing();
    }

    protected void fireEditingStopped()
    {
      super.fireEditingStopped();
    }
  }