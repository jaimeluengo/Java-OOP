/** NetId: jl3752. Time spent: 6 hours, 20 minutes.
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
		assert n!=null && n.length()>1;
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

	/**Add p as the first advisor of this person.
Precondition: the first advisor is unknown and p is not null.*/
	public void setAdvisor1(PhD p) {
		assert advisor1==null && p!=null;
		advisor1=p;
		p.n_advisees+=1;
	}
	
	/**Add p as the second advisor of this person.
Precondition: The first advisor (of this person) is known, the second advisor
is unknown, p is not null, and p is different from the first advisor.*/
	public void setAdvisor2(PhD p) {
		assert advisor2==null && p!=null && p!=advisor1;
		advisor2=p;
		p.n_advisees+=1;
	}
	
	/** Constructor: a PhD with name n, PhD month m, PhD year y, first advisor
adv1, and no second advisor. Precondition: n has at least 1 char, m is in 1..12,
and adv1 is not null.*/
	public PhD(String n, int m, int y, PhD adv1) {
		assert n!= null && n.length()>1 && (m<13 && m>0) && adv1 != null;
		name = n;
		month=m;
		year=y;
		advisor1=adv1;
		advisor1.n_advisees+=1;
	}

	/**Constructor: a PhD with name n, PhD month m, PhD year y, first advisor
adv1, and second advisor adv2. Precondition: n has at least 1 char, m is in 1..12,
adv1 and adv2 are not null, and adv1 and adv2 are different*/
	public PhD(String n, int m, int y, PhD adv1, PhD adv2) {
		assert n!= null && n.length()>1 && (m<13 && m>0) && adv1 != null && adv2 != null && adv1 != adv2;
		name = n;
		month=m;
		year=y;
		advisor1=adv1;
		advisor1.n_advisees+=1;
		advisor2=adv2;
		advisor2.n_advisees+=1;
	}
	
	/**Return value of "this PhD has at least one advisee",
i.e. true if this PhD has at least one advisee and false
otherwise*/
	public boolean hasAdvisee() {
		return (numAdvisees()>0);
	}
	
	/**Return value of "p is not null and this person got the PhD
after p.”*/
	public boolean gotAfter(PhD p) {
		return(p!=null && (p.month+p.year*12<month+year*12));//scaling the years to month unit
	}
	
	/**Return value of "this person and p are intellectual siblings."
Precondition: p is not null.*/
	public boolean areSiblings(PhD p){
		assert(p!=null);
		return(this!=p &&( advisor1==p.advisor1()
				||  advisor2==p.advisor2()
				||  advisor1==p.advisor2()
				||  advisor2==p.advisor1()));
	}


	public static void main(String[] args) {
	}	
}
