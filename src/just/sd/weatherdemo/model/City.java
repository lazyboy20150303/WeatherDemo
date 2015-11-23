package just.sd.weatherdemo.model;

/**
 * 城市
 * @author sunqing
 *日期：2015.11.21
 */
public class City {

	private int weaid;
	private String citynm;
	private String cityno;
	private String cityid;

	public City() {
		super();
	}

	public City(int weaid, String citynm, String cityno, String cityid) {
		super();
		this.weaid = weaid;
		this.citynm = citynm;
		this.cityno = cityno;
		this.cityid = cityid;
	}

	public int getWeaid() {
		return weaid;
	}
	public void setWeaid(int weaid) {
		this.weaid = weaid;
	}
	public String getCitynm() {
		return citynm;
	}
	public void setCitynm(String citynm) {
		this.citynm = citynm;
	}
	public String getCityno() {
		return cityno;
	}
	public void setCityno(String cityno) {
		this.cityno = cityno;
	}
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

}
