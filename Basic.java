/** NetId: nnnnn, nnnnn. Time spent: hh hours, mm minutes.
 An instance maintains info about the PhD of a person. */

public class PhD {
	private String name;//name of PhD holder, >1 character
	private int month;//month of graudation, 1-12, being 1: January
	private int year;//Year of graduation
	private PhD advisor1;//First advisor of the phd, null if unknown
	private PhD advisor2;//Second Avisor, null if unknown or non-existant
	private int n_advisees;//Number of students advised by the PhD holder

	/**Constructor: an instance for a person with name n, PhD month m, PhD year y. Its
	advisors are unknown, and it has no advisees.
	Precondition: n has at least 1 char and m is in 1..12. */

	public PhD(String n, int m, int y) {
		assert n.length()>1;
		assert m<13 && m>0;
		name=n;
		month=m;
		year=y;
		advisor1 = null;
		advisor2=null;
		n_advisees=0;
	}
	/** Return the name of this person */
	public String name() {
		return name;
	}
	/**Return the month this person got their PhD.*/
	public int month() {
		return month;
	}
	/**Return the year this person got their PhD.*/
	public int year() {
		return year;
	}
	/**Return the first advisor of this PhD (null if unknown).*/
	public PhD advisor1() {
		return advisor1;
	}
	/**Return the second advisor of this PhD (null if unknown or non-existent)*/
	public PhD advisor2() {
		return advisor2;
	}
	/**Return the number of PhD advisees of this person.*/
	public int numAdvisees() {
		return	n_advisees;
	}
}
