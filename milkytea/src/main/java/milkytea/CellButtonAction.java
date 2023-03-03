package milkytea;

public abstract class CellButtonAction {
	private CustomAbstractTableModel model;
	private Object value;
	private int rowIndex;
	
	public void setup(CellButtonEditor cbe) {
		this.model = (CustomAbstractTableModel) cbe.getParentTable().getModel();
		this.setRow(model.getRow(cbe.getRowIndex()));
		this.setRowIndex(cbe.getRowIndex());
	}
	
	private void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	
	public int getRowIndex() {
		return this.rowIndex;
	}

	public abstract void execute();

	public Object getRow() {
		return value;
	}

	public void setRow(Object value) {
		this.value = value;
	}
}
