package milkytea;

import javax.swing.table.AbstractTableModel;

public abstract class CustomAbstractTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8929753658292263371L;
	
	public abstract Object getRow(int rowIndex);
	public abstract void addRow(Object obj);
	public abstract void removeRow(int rowIndex);
}
