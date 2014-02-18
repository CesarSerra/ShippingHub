package com.java102.cesar;

public class Parcel
{
	private String time;
	private Person onePerson;
	private int ID;
	
	public Parcel()
	{
		this(0,"",Person.NOBODY);
	}
	public Parcel(int ID, String time, Person onePerson)
	{
		setID(ID);
		setTime(time);
		setPerson(onePerson);
	}
	public void setID(int ID)
	{
		this.ID = ID;
	}
	public void setTime(String time)
	{
		this.time = time;
	}
	public void setPerson(Person person) 
	{
		if (person == null) {
			onePerson = Person.NOBODY;
		}
		else 
		{
			onePerson = person;
		}
	}
	public String getTime()
	{
		return time;
	}
	public int getID()
	{
		return ID;
	}
	public Person getPerson()
	{
		return onePerson;
	}
}
