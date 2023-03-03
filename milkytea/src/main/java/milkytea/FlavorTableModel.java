package milkytea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class FlavorTableModel extends CustomAbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2708803316198939746L;

	public boolean isCellEditable(int row, int column) {
		ArrayList<Integer> editableColumns = new ArrayList<>(Arrays.asList(4, 5));
		
		return editableColumns.indexOf(column) >= 0;
	}
	
	public List<Flavor> rows = new ArrayList<Flavor>();
	protected String[] columnNames = new String[] { 
		"ID", "IMAGE", "FLAVOR", "PRICE", "", ""
	};

	public void addRow(Object flavor) {
		rows.add((Flavor)flavor);
		
		fireTableRowsInserted(rows.size() - 1, rows.size() - 1);
	}
	
	public void setRow(int rowIndex, Object flavor) {
		rows.set(rowIndex, (Flavor) flavor);
		
		fireTableRowsUpdated(rowIndex, rowIndex);
	}
	
	public void removeRow(int rowIndex) {
		rows.remove(rowIndex);
		
		fireTableRowsDeleted(rows.size() - 1, rows.size() - 1);
	}
	
	public Flavor getRow(int rowIndex) {
		Flavor flavor = rows.get(rowIndex);
		
		return flavor;
	}
	
	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Flavor flavor = rows.get(rowIndex);
		
		switch(columnIndex) {
			case 0: return flavor.getID();
			case 1: return flavor.getImage();
			case 2: return flavor.getFlavor();
			case 3: return flavor.getPrice();
			case 4: return "EDIT";
			case 5: return "DELETE";
		}
		
		return null;
	}
}
