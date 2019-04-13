package pers.qyj.graduationpr.pojo;

public class UserInformation {
	private Long id;
	private String head_portrait;
	private String name;
	private String birthday;
	private String sex;
	private String phone;
	private String prefecture;
	private String real_name;
	private String id_card;
	public String getHead_portrait() {
		return head_portrait;
	}
	public void setHead_portrait(String head_portrait) {
		this.head_portrait = head_portrait;
	}
	public String getBirthday() {
		return birthday;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPrefecture() {
		return prefecture;
	}
	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card =id_card;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
