package milkytea;

import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;

public class MainWindow {

	private JFrame frame;
	private JTextField txtFlavor;
	private JTextField txtPrice;
	private JTable table;
	private JLabel img;
	private FlavorTableModel flavorTableModel;
	private JButton btnUpdateFlavor;
	private JButton btnSaveFlavor;
	protected int editID;
	protected int editRowIndex;
	private JTabbedPane tabbedPane;
	private JPanel sizesPanel;
	private JLabel lblNewLabel_1;
	private JPanel ocrPanel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	public void clearForm() {
		txtFlavor.setText("");
		txtPrice.setText("");
		img.setIcon(null);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	
	private String generateFileName() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss_n");  
		LocalDateTime now = LocalDateTime.now();  
		String fileName = "IMG_" + String.format("%-30s", dtf.format(now)).replace(' ', 'N');
		
		return fileName;
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 796, 518);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		flavorTableModel = new FlavorTableModel();
		try {
			Connection conn = DBConnection.getInstance();
			String sql = "SELECT * FROM flavor";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Flavor flavor = new Flavor();
				flavor.setID(rs.getInt("id"))
					.setImage(rs.getString("image"))
					.setFlavor(rs.getString("flavor"))
					.setPrice(rs.getDouble("price"));
				
				flavorTableModel.addRow(flavor);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}		
		DefaultTableCellRenderer TopRightAlignedCell = new DefaultTableCellRenderer();
		TopRightAlignedCell.setHorizontalAlignment(SwingConstants.RIGHT);
		TopRightAlignedCell.setVerticalAlignment(SwingConstants.TOP);
		DefaultTableCellRenderer TopLeftAlignedCell = new DefaultTableCellRenderer();
		TopLeftAlignedCell.setHorizontalAlignment(SwingConstants.LEFT);
		TopLeftAlignedCell.setVerticalAlignment(SwingConstants.TOP);
		final CellButtonEditor editButton = new CellButtonEditor();
		editButton.setAction(new CellButtonAction() {
			@Override
			public void execute() { 
				Flavor flavor = (Flavor) getRow();
				editID = flavor.getID();
				editRowIndex = getRowIndex();
				MessageBox.show("You are editing " + flavor.getFlavor(), "info");
				txtFlavor.setText(flavor.getFlavor());
				txtPrice.setText(flavor.getPrice().toString());
				try {
					File imageFile = new File(flavor.getImage());
					BufferedImage bufferedImage = ImageIO.read(imageFile);
					ImageIcon icon = new ImageIcon(bufferedImage); 
					Image scaledImage = (icon).getImage().getScaledInstance((int)img.getWidth(), (int)img.getHeight(), Image.SCALE_SMOOTH);
					icon = new ImageIcon(scaledImage);
					img.setIcon(icon);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				btnSaveFlavor.setVisible(false);
				btnUpdateFlavor.setVisible(true);
			}
			
		});
		final CellButtonEditor deleteButton = new CellButtonEditor();
		deleteButton.setAction(new CellButtonAction() {
			@Override
			public void execute() {
				Flavor flavor = (Flavor) getRow();
				try {
					Connection conn = DBConnection.getInstance();
					String sql = "DELETE FROM flavor WHERE id=" + flavor.getID();
					Statement stmt = conn.createStatement();
					stmt.executeUpdate(sql);
					
					MessageBox.show("Successfully deleted flavor.", "info");
					File imageFile = new File(flavor.getImage());
					imageFile.delete();
					flavorTableModel.removeRow(getRowIndex());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
		tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.setBounds(0, 0, 770, 470);
		Font font = new Font("Arial", Font.CENTER_BASELINE, 20);
		tabbedPane.setFont(font);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 290, 454);
		//		frame.getContentPane().add(panel);
				panel.setLayout(null);
				
				txtFlavor = new JTextField();
				txtFlavor.setBounds(27, 236, 227, 32);
				panel.add(txtFlavor);
				txtFlavor.setColumns(10);
				
				JLabel lblNewLabel = new JLabel("FLAVOR");
				lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
				lblNewLabel.setBounds(27, 211, 227, 14);
				panel.add(lblNewLabel);
				
				JLabel lblPrice = new JLabel("PRICE");
				lblPrice.setFont(new Font("Tahoma", Font.BOLD, 12));
				lblPrice.setBounds(27, 280, 227, 14);
				panel.add(lblPrice);
				
				txtPrice = new JTextField();
				txtPrice.setColumns(10);
				txtPrice.setBounds(27, 305, 227, 32);
				panel.add(txtPrice);
				
				btnUpdateFlavor = new JButton("UPDATE FLAVOR");
				btnUpdateFlavor.setVisible(false);
				btnUpdateFlavor.setBounds(118, 366, 136, 37);
				panel.add(btnUpdateFlavor);
				
				btnUpdateFlavor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String fileName = "images/" + generateFileName() + ".png";
						
						BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
						Graphics2D g2d = image.createGraphics();
						img.printAll(g2d);
						g2d.dispose();
						File outputfile = new File(fileName);
					    try {
							ImageIO.write(image, "png", outputfile);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						Flavor flavor = new Flavor();
						flavor.setImage(fileName)
							.setFlavor(txtFlavor.getText())
							.setPrice(Double.parseDouble(txtPrice.getText()));
						
						Connection conn = DBConnection.getInstance();
						String sql = 
							"UPDATE flavor SET image='" + flavor.getImage() + "', flavor=" + "'" + flavor.getFlavor() + "', price='" + flavor.getPrice() + "' WHERE id=" + editID;
						try {
							Statement stmt = conn.createStatement();
							stmt.executeUpdate(sql);
							
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
						MessageBox.show("Successfully updated flavor.", "info");
						clearForm();
						btnUpdateFlavor.setVisible(false);
						btnSaveFlavor.setVisible(true);
						editID = 0;
						
						flavorTableModel.setRow(editRowIndex, flavor);
					}
				});
				
				
				btnSaveFlavor = new JButton("SAVE FLAVOR");
				btnSaveFlavor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String fileName = "images/" + generateFileName() + ".png";
						
						BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
						Graphics2D g2d = image.createGraphics();
						img.printAll(g2d);
						g2d.dispose();
						File outputfile = new File(fileName);
					    try {
							ImageIO.write(image, "png", outputfile);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						Flavor flavor = new Flavor();
						flavor.setImage(fileName)
							.setFlavor(txtFlavor.getText())
							.setPrice(Double.parseDouble(txtPrice.getText()));
						
						Connection conn = DBConnection.getInstance();
						String sql = 
							"INSERT INTO flavor(image, flavor, price) " + 
							"VALUES('" + flavor.getImage() + "', " + "'" + flavor.getFlavor() + "', '" + flavor.getPrice() + "')";
						try {
							Statement stmt = conn.createStatement();
							stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
							
							ResultSet generatedKeys = stmt.getGeneratedKeys();
				            if (generatedKeys.next()) {
				                flavor.setID(generatedKeys.getInt(1));
					            flavorTableModel.addRow(flavor);
				            } else {
				                throw new SQLException("Creating flavor failed, no ID obtained.");
				            }
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
						MessageBox.show("Successfully saved flavor.", "info");
						clearForm();
					}
				});
				btnSaveFlavor.setBounds(118, 366, 136, 37);
				panel.add(btnSaveFlavor);
				
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "IMAGE", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				panel_1.setBounds(27, 13, 191, 187);
				panel.add(panel_1);
				panel_1.setLayout(null);
				
				img = new JLabel("");
				img.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						final FileDialog fd = new FileDialog(frame, "Image Chooser", FileDialog.LOAD);
						fd.setFile("*.jpg;*.jpeg;*.png");
						fd.setVisible(true);
						String filename = fd.getFile();
						String directory = fd.getDirectory();
						if (filename == null) {
						  System.out.println("You cancelled the choice");
						} else {
							System.out.println("You chose " + filename);
							System.out.print(directory);
							Tesseract tesseract = new Tesseract();
					        try {
					        	
					            tesseract.setDatapath("C:\\Users\\xtian\\Downloads\\Tess4J-3.4.8-src\\Tess4J\\tessdata");
					  
					            // the path of your tess data folder
					            // inside the extracted file
					            String text
					                = tesseract.doOCR(fd.getFiles()[0]);
					  
					            // path of your image file
					            System.out.print(text);
					        }
					        catch (TesseractException ex) {
					            ex.printStackTrace();
					        }
							
							
//							try {
//								BufferedImage image = ImageIO.read(fd.getFiles()[0]);
//								ImageIcon icon = new ImageIcon(image); 
//								Image scaledImage = (icon).getImage().getScaledInstance(img.getWidth(), img.getHeight(), Image.SCALE_SMOOTH);
//								icon = new ImageIcon(scaledImage);
//								img.setIcon(icon);
//							} catch (IOException e1) {
//								e1.printStackTrace();
//							}
						}
					}
				});
				img.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/image-icon.jpg")));
				img.setBounds(10, 11, 170, 170);
				panel_1.add(img);
				img.setHorizontalAlignment(SwingConstants.CENTER);
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(310, 11, 460, 454);
				//		frame.getContentPane().add(scrollPane);
						
						
						table = new JTable();
						
						table.setModel(flavorTableModel);
						table.setRowHeight(55);
						
						table.getTableHeader().setReorderingAllowed(false);
						
						table.getColumnModel().getColumn(0).setPreferredWidth(20);
						table.getColumnModel().getColumn(1).setPreferredWidth(60);		
						table.getColumnModel().getColumn(2).setPreferredWidth(200);		
						
						table.getColumnModel().getColumn(0).setCellRenderer(TopRightAlignedCell);
						table.getColumnModel().getColumn(1).setResizable(false);
						table.getColumnModel().getColumn(1).setCellRenderer(new CellImageRenderer(new Dimension(50, 50)));
						table.getColumnModel().getColumn(2).setCellRenderer(TopLeftAlignedCell);
						table.getColumnModel().getColumn(3).setCellRenderer(TopRightAlignedCell);
						table.getColumnModel().getColumn(4).setCellRenderer(new CellButtonRenderer());
						table.getColumnModel().getColumn(4).setCellEditor(editButton);
						table.getColumnModel().getColumn(5).setCellRenderer(new CellButtonRenderer());
						table.getColumnModel().getColumn(5).setCellEditor(deleteButton);
		
		scrollPane.setViewportView(table);
		
		JPanel flavorsPanel = new JPanel();
		flavorsPanel.setFont(new Font("Tahoma", Font.PLAIN, 29));
		tabbedPane.addTab("FLAVORS", null, flavorsPanel, null);
		flavorsPanel.add(panel);
		flavorsPanel.add(scrollPane);
		flavorsPanel.setLayout(null);
		
		sizesPanel = new JPanel();
		sizesPanel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tabbedPane.addTab("SIZES", null, sizesPanel, null);
		sizesPanel.setLayout(null);
		
		lblNewLabel_1 = new JLabel("You are in sizes Panel");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(177, 79, 310, 60);
		sizesPanel.add(lblNewLabel_1);
		
		ocrPanel = new JPanel();
		tabbedPane.addTab("OCR", null, ocrPanel, null);
	}
}
