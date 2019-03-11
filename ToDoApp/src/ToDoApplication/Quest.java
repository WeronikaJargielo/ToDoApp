package ToDoApplication;


class Quest {
	
	static long idCreated = 0;
	long id;
	String name;
	String priority;
	String comment;
	
	
	public Quest(String name_, String priority_, String comment_) {
		id = idCreated++;
		name = name_;
		priority = priority_;
		comment = comment_;	
	}
	
	public Quest(String name_, String priority_) {
		id = idCreated++;
		name = name_;
		priority = priority_;
		comment = "";
	}
	
	public Quest(String name_) {
		id = idCreated++;
		name = name_;
		priority = "None";
		comment = "";
	}
	
	
	public void setName(String n) {
		name = n;
	}

	public void setComment(String c) {
		comment = c;
	}
	
	public void setPriority(String p) {
		priority = p;
	}
	
	public String getName() {
		return name;
	}
	
	public String getComment() {
		return comment;
	}
	
	public String getPriority() {
		return priority;
	}
	
	public long getId() {
		return id;
	}
	
	static long getIdCreated() { // getting the number of created quest in single running of application
		return idCreated;
	}
	
	static void setIdCreated(long idCreated_) {
		idCreated = idCreated_;
	}
}
