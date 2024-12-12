package toolBarData;

import org.example.Canvas;

import javax.swing.*;
import java.awt.*;

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

	public static final int NONE = -1;
	public static final int LINE_BUTTON = 0;
	public static final int POLYGON_BUTTON = 1;
	public static final int FILL_BUTTON = 2;
	public static final int REGULAR_PENTAGON_BUTTON = 3;
	public static final int CUT_BUTTON = 4;
	public static final int CAMERA_BUTTON = 5;

	public ToolBar()
	{
		setBackground(Color.DARK_GRAY);
		setLayout(new FlowLayout(FlowLayout.CENTER));

		Dimension buttonDimension = new Dimension(40, 40);

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

		line.addActionListener(e -> canvas.addPolygon());
		polygon.addActionListener(e -> canvas.addPolygon());
		fill.addActionListener(e -> canvas.addPolygon());
		regularPentagon.addActionListener(e -> canvas.addPolygon());
		cut.addActionListener(e -> canvas.addPolygon());

		add(line);
		add(polygon);
		add(regularPentagon);
		add(Box.createRigidArea(new Dimension(50, 0)));
		add(thicknessLabel);
		add(thicknessSpinner);
		add(Box.createRigidArea(new Dimension(100, 0)));
		add(fill);
		add(cut);
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(0, 50);
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
}
