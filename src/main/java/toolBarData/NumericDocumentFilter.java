package toolBarData;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

class NumericDocumentFilter extends DocumentFilter {
	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
		StringBuilder currentText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
		currentText.insert(offset, string);
		if (isValidInput(currentText.toString())) {
			super.insertString(fb, offset, string, attr);
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException {
		StringBuilder currentText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
		currentText.replace(offset, offset + length, string);
		if (isValidInput(currentText.toString())) {
			super.replace(fb, offset, length, string, attr);
		}
	}

	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
		StringBuilder currentText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
		currentText.delete(offset, offset + length);
		if (isValidInput(currentText.toString())) {
			super.remove(fb, offset, length);
		}
	}

	private boolean isValidInput(String text) {
		// Allow empty text (so the user can delete everything)
		if (text.isEmpty()) {
			return true;
		}
		// Regex for valid double values
		return text.matches("-?\\d*(\\.\\d*)?");
	}
}


