package Entity;

public class EBasket {
	
	private String number;
	private String name;
	private String professor;
	private String credit;
	private String day;
	private String Time;
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}
	
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}

	public boolean checkExcist(ELecture eLecture) {
		if (eLecture.getName().contentEquals(this.getName())) {
			return true;
		} else {
			return false;
		}
	}
}
