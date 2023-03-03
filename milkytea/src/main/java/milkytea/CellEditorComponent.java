package milkytea;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTable;

public class CellEditorComponent extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6843989557187707784L;
	private int row, column;
	private JTable table;
	private JButton component;
	
	
	public CellEditorComponent(String text) {
		super(text);
	}
	
	public void setTable(JTable table) {
		this.table = table;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public JTable getTable() {
		return table;
	}
	public int getColumn() {
		return column;
	}
	public int getRow() {
		return row;
	}

}
