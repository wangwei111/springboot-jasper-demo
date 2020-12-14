package mosesboot.user.entity;

public class Person {

	private String name;

	private String age;

	private String sex;

	private String telephone;

	public Person() {

	}

	public Person(String name, String age, String sex, String telphone) {
		this.name = name;
		this.age = name;
		this.sex = age;
		this.telephone = telphone;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	

}
