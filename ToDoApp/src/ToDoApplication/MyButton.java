package ToDoApplication;
import javax.swing.*;

public class MyButton extends JButton {
	
	private static final long serialVersionUID = 1L;
	long id;
	String name;
	
	public MyButton(long id_, String name_) {
		id = id_;
		name = name_;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id_) {
		id = id_;
	}
	
	public void setName(String name_) {
		setText(name_);
	}
}
