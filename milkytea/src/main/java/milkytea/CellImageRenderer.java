package milkytea;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class CellImageRenderer implements TableCellRenderer {
	private JLabel image;
	private JPanel panel;
	private Dimension dimension;
	
	public CellImageRenderer() {
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		image = new JLabel();
		dimension = new Dimension(75, 75);
	}
	
	public CellImageRenderer(Dimension d) {
		this();
		dimension = d;
	}
	
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			panel.setForeground(table.getSelectionForeground());
			panel.setBackground(table.getSelectionBackground());
		} else {
			panel.setForeground(table.getForeground());
			panel.setBackground(table.getBackground());
		}
		
		try {
			File imageFile = new File(value.toString());
			BufferedImage bufferedImage = ImageIO.read(imageFile);
			ImageIcon icon = new ImageIcon(bufferedImage); 
			Image scaledImage = (icon).getImage().getScaledInstance((int)dimension.getWidth(), (int)dimension.getHeight(), Image.SCALE_SMOOTH);
			icon = new ImageIcon(scaledImage);
			image.setIcon(icon);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		panel.add(image);
		
		return panel;
	}

}

