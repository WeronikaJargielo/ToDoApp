package ToDoApplication;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class MaxLengthDocument extends PlainDocument{
	private static final long serialVersionUID = 1L;
	private int maxLength;
	
	//Create a Document with specified max length
	public MaxLengthDocument(int maxLength_) {
		maxLength = maxLength_;
	}
	
	// Don't allow an insertion to exceed to max the length
	public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
		if (getLength()+ str.length()>maxLength) {
			java.awt.Toolkit.getDefaultToolkit().beep();
		} else {
			super.insertString(offset, str,a);
		}
		
		
		
	}
	
	
}