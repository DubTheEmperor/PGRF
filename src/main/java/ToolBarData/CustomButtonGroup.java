package ToolBarData;

import javax.swing.*;

public class CustomButtonGroup extends ButtonGroup
{
	private ButtonModel modifiedSelection;

	public CustomButtonGroup()
	{
		modifiedSelection = null;
	}

	public void add(AbstractButton b)
	{
		if (b == null)
		{
			return;
		}
		buttons.addElement(b);

		if (b.isSelected())
		{
			if (modifiedSelection == null)
			{
				modifiedSelection = b.getModel();
			} else
			{
				b.setSelected(false);
			}
		}

		b.getModel().setGroup(this);
	}

	public void remove(AbstractButton b)
	{
		if (b == null)
		{
			return;
		}
		buttons.removeElement(b);
		if (b.getModel() == modifiedSelection)
		{
			modifiedSelection = null;
		}
		b.getModel().setGroup(null);
	}

	public ButtonModel getSelection()
	{
		return modifiedSelection;
	}

	public void setSelected(ButtonModel m, boolean b)
	{
		if (!b && m == modifiedSelection)
		{
			modifiedSelection = null;
			return;
		}
		if (b && m != null && m != modifiedSelection)
		{
			ButtonModel oldSelection = modifiedSelection;
			modifiedSelection = m;
			if (oldSelection != null)
			{
				oldSelection.setSelected(false);
			}
			m.setSelected(true);
		}
	}

	public boolean isSelected(ButtonModel m)
	{
		return (m == modifiedSelection);
	}
}