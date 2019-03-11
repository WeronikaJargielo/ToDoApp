package ToDoApplication;

import javax.swing.*;

public class MyCheckBox extends JCheckBox{
	
	private static final long serialVersionUID = 1L;
	long id;
	
	public MyCheckBox(long id_) {
		id = id_;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id_) {
		id = id_;
	}

}
