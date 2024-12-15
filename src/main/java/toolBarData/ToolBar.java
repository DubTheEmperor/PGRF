package toolBarData;

import org.example.Canvas;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.*;

public class ToolBar extends JPanel
{
	private JToggleButton line;
	private JToggleButton polygon;
	private JToggleButton fill;
	private JToggleButton regularPentagon;
	private JToggleButton cut;
	private CustomButtonGroup buttonGroup;
	private JSpinner thicknessSpinner;
	private Canvas canvas;
	private Color backgroundColor;

	private JButton rotateX;
	private JButton rotateY;
	private JButton rotateZ;
	private JPanel sizePanel;
	private JButton sizeUp;
	private JButton sizeDown;
	private JTextField xPosition;
	private JTextField yPosition;
	private JTextField zPosition;

	public static final int NONE = -1;
	public static final int LINE_BUTTON = 0;
	public static final int POLYGON_BUTTON = 1;
	public static final int FILL_BUTTON = 2;
	public static final int REGULAR_PENTAGON_BUTTON = 3;
	public static final int CUT_BUTTON = 4;

	public ToolBar()
	{
		backgroundColor = Color.DARK_GRAY;

		setBackground(backgroundColor);
		setLayout(new FlowLayout(FlowLayout.CENTER));

		Dimension buttonDimension = new Dimension(40, 40);
		Dimension rotateButtonDimension = new Dimension(15,15);
		Dimension sizeButtonDimension = new Dimension(20, 20);

		line = new JToggleButton();
		line.setPreferredSize(buttonDimension);
		line.setSelectedIcon(new ImageIcon("src/main/icons/lineIconSelected.jpg"));
		line.setIcon(new ImageIcon("src/main/icons/lineIcon.jpg"));

		polygon = new JToggleButton();
		polygon.setPreferredSize(buttonDimension);
		polygon.setSelectedIcon(new ImageIcon("src/main/icons/polygonIconSelected.jpg"));
		polygon.setIcon(new ImageIcon("src/main/icons/polygonIcon.jpg"));

		fill = new JToggleButton();
		fill.setPreferredSize(buttonDimension);
		fill.setSelectedIcon(new ImageIcon("src/main/icons/fillIconSelected.jpg"));
		fill.setIcon(new ImageIcon("src/main/icons/fillIcon.jpg"));

		regularPentagon = new JToggleButton();
		regularPentagon.setPreferredSize(buttonDimension);
		regularPentagon.setSelectedIcon(new ImageIcon("src/main/icons/pentagonIconSelected.jpg"));
		regularPentagon.setIcon(new ImageIcon("src/main/icons/pentagonIcon.jpg"));

		cut = new JToggleButton();
		cut.setPreferredSize(buttonDimension);
		cut.setSelectedIcon(new ImageIcon("src/main/icons/cutIconSelected.jpg"));
		cut.setIcon(new ImageIcon("src/main/icons/cutIcon.jpg"));

		buttonGroup = new CustomButtonGroup();
		buttonGroup.add(line);
		buttonGroup.add(polygon);
		buttonGroup.add(fill);
		buttonGroup.add(regularPentagon);
		buttonGroup.add(cut);

		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 10, 1);
		thicknessSpinner = new JSpinner(spinnerModel);
		thicknessSpinner.setPreferredSize(new Dimension(50, 40));

		JComponent editor = thicknessSpinner.getEditor();
		JFormattedTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
		textField.setEditable(false);
		textField.setFocusable(false);
		textField.setHorizontalAlignment(JTextField.CENTER);

		Label thicknessLabel = new Label("Thickness:");
		thicknessLabel.setForeground(Color.WHITE);
		thicknessLabel.setFont(new Font("Courier", Font.BOLD, 16));

		JPanel rotateLabelPanel = new JPanel();
		rotateLabelPanel.setBackground(backgroundColor);
		rotateLabelPanel.setLayout(new BoxLayout(rotateLabelPanel, BoxLayout.Y_AXIS));

		JLabel xAxis = new JLabel("x axis");
		xAxis.setForeground(Color.WHITE);
		xAxis.setFont(new Font("Courier", Font.BOLD, 11));

		JLabel yAxis = new JLabel("y axis");
		yAxis.setForeground(Color.WHITE);
		yAxis.setFont(new Font("Courier", Font.BOLD, 11));

		JLabel zAxis = new JLabel("z axis");
		zAxis.setForeground(Color.WHITE);
		zAxis.setFont(new Font("Courier", Font.BOLD, 11));

		JPanel rotateButtonPanel = new JPanel();
		rotateButtonPanel.setBackground(backgroundColor);
		rotateButtonPanel.setLayout(new BoxLayout(rotateButtonPanel, BoxLayout.Y_AXIS));

		rotateX = new JButton();
		rotateX.setPreferredSize(rotateButtonDimension);
		rotateX.setIcon(new ImageIcon("src/main/icons/rotateIcon.jpg"));

		rotateY = new JButton();
		rotateY.setPreferredSize(rotateButtonDimension);
		rotateY.setIcon(new ImageIcon("src/main/icons/rotateIcon.jpg"));

		rotateZ = new JButton();
		rotateZ.setPreferredSize(rotateButtonDimension);
		rotateZ.setIcon(new ImageIcon("src/main/icons/rotateIcon.jpg"));

		sizePanel = new JPanel();
		sizePanel.setBackground(backgroundColor);
		sizePanel.setLayout(new BoxLayout(sizePanel, BoxLayout.Y_AXIS));

		sizeUp = new JButton();
		sizeUp.setPreferredSize(sizeButtonDimension);
		sizeUp.setIcon(new ImageIcon("src/main/icons/sizeUpIcon.jpg"));

		sizeDown = new JButton();
		sizeDown.setPreferredSize(sizeButtonDimension);
		sizeDown.setIcon(new ImageIcon("src/main/icons/sizeDownIcon.jpg"));

		JPanel positionLabelPanel = new JPanel();
		positionLabelPanel.setBackground(backgroundColor);
		positionLabelPanel.setLayout(new BoxLayout(positionLabelPanel, BoxLayout.Y_AXIS));

		JLabel xPositionLabel = new JLabel("x: ");
		xPositionLabel.setForeground(Color.WHITE);
		xPositionLabel.setFont(new Font("Courier", Font.BOLD, 11));

		JLabel yPositionLabel = new JLabel("y: ");
		yPositionLabel.setForeground(Color.WHITE);
		yPositionLabel.setFont(new Font("Courier", Font.BOLD, 11));

		JLabel zPositionLabel = new JLabel("z: ");
		zPositionLabel.setForeground(Color.WHITE);
		zPositionLabel.setFont(new Font("Courier", Font.BOLD, 11));

		JPanel positionPanel = new JPanel();
		positionPanel.setBackground(backgroundColor);
		positionPanel.setLayout(new BoxLayout(positionPanel, BoxLayout.Y_AXIS));

		xPosition = new JTextField(5);
		xPosition.setFont(new Font("Courier", Font.BOLD, 10));
		xPosition.setBorder(null);
		((AbstractDocument) xPosition.getDocument()).setDocumentFilter(new NumericDocumentFilter());

		yPosition = new JTextField(5);
		yPosition.setFont(new Font("Courier", Font.BOLD, 10));
		yPosition.setBorder(null);
		((AbstractDocument) yPosition.getDocument()).setDocumentFilter(new NumericDocumentFilter());

		zPosition = new JTextField(5);
		zPosition.setFont(new Font("Courier", Font.BOLD, 10));
		zPosition.setBorder(null);
		((AbstractDocument) zPosition.getDocument()).setDocumentFilter(new NumericDocumentFilter());

		line.addActionListener(e -> canvas.addPolygon());
		polygon.addActionListener(e -> canvas.addPolygon());
		fill.addActionListener(e -> canvas.addPolygon());
		regularPentagon.addActionListener(e -> canvas.addPolygon());
		cut.addActionListener(e -> canvas.addPolygon());

		rotateX.addActionListener(e -> canvas.rotateX());
		rotateY.addActionListener(e -> canvas.rotateY());
		rotateZ.addActionListener(e -> canvas.rotateZ());

		sizeUp.addActionListener(e -> canvas.scale(1.1));
		sizeDown.addActionListener(e -> canvas.scale(0.9));

		FocusListener focusListener = new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(
						xPosition.getText().isEmpty()
						|| yPosition.getText().isEmpty()
						|| zPosition.getText().isEmpty()
				)
					return;
				
				canvas.moveTo(
						Double.parseDouble(xPosition.getText()),
						Double.parseDouble(yPosition.getText()),
						Double.parseDouble(zPosition.getText())
				);
			}
		};

		xPosition.addFocusListener(focusListener);
		yPosition.addFocusListener(focusListener);
		zPosition.addFocusListener(focusListener);

		positionPanel.add(xPosition);
		positionPanel.add(Box.createVerticalStrut(3));
		positionPanel.add(yPosition);
		positionPanel.add(Box.createVerticalStrut(3));
		positionPanel.add(zPosition);

		positionLabelPanel.add(xPositionLabel);
		rotateLabelPanel.add(Box.createVerticalStrut(2));
		positionLabelPanel.add(yPositionLabel);
		rotateLabelPanel.add(Box.createVerticalStrut(2));
		positionLabelPanel.add(zPositionLabel);

		rotateLabelPanel.add(xAxis);
		rotateLabelPanel.add(Box.createVerticalStrut(2));
		rotateLabelPanel.add(yAxis);
		rotateLabelPanel.add(Box.createVerticalStrut(2));
		rotateLabelPanel.add(zAxis);

		rotateButtonPanel.add(rotateX);
		rotateButtonPanel.add(Box.createVerticalStrut(2));
		rotateButtonPanel.add(rotateY);
		rotateButtonPanel.add(Box.createVerticalStrut(2));
		rotateButtonPanel.add(rotateZ);

		sizePanel.add(sizeUp);
		sizePanel.add(Box.createVerticalStrut(9));
		sizePanel.add(sizeDown);

		add(line);
		add(polygon);
		add(regularPentagon);
		add(fill);
		add(cut);
		add(thicknessLabel);
		add(thicknessSpinner);
		add(Box.createRigidArea(new Dimension(30, 0)));
		add(rotateLabelPanel);
		add(rotateButtonPanel);
		add(sizePanel);
		add(Box.createRigidArea(new Dimension(30, 0)));
		add(positionLabelPanel);
		add(positionPanel);
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(0, 60);
	}

	public int getSelectedButton()
	{
		if (line.isSelected())
		{
			return LINE_BUTTON;
		}
		else if (polygon.isSelected())
		{
			return POLYGON_BUTTON;
		}
		else if (fill.isSelected())
		{
			return FILL_BUTTON;
		}
		else if (regularPentagon.isSelected())
		{
			return REGULAR_PENTAGON_BUTTON;
		}
		else if(cut.isSelected())
		{
			return CUT_BUTTON;
		}
		else
			return NONE;
	}

	public int getThickness()
	{
		return (int) thicknessSpinner.getValue();
	}

	public void setCanvas(Canvas canvas)
	{
		this.canvas = canvas;
	}

	public void setXPosition(double position)
	{
		xPosition.setText(Double.toString(position));
	}

	public void setYPosition(double position)
	{
		yPosition.setText(Double.toString(position));
	}

	public void setZPosition(double position)
	{
		zPosition.setText(Double.toString(position));
	}
}

