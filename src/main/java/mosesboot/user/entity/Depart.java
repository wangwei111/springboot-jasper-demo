package mosesboot.user.entity;

import java.util.List;

public class Depart {
	private String subTitle;

	private String depart_name;

	private List<Person> personList = null;

	public List<Person> getPersonList() {
		return personList;
	}

	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}

	public Depart(String title, String partName) {
		this.subTitle = title;
		this.depart_name = partName;
	}

	public String getDepart_name() {
		return depart_name;
	}

	public void setDepart_name(String depart_name) {
		this.depart_name = depart_name;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
}
