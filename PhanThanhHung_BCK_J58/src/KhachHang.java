import java.io.Serializable;

public class KhachHang implements Serializable {
	private int maKH;
	private String hoTen;
	private String cmnd, sdt;
	
	static int sma = 100;
	
	public KhachHang(String hoTen, String cmnd, String sdt) {
		sma++;
		this.maKH = sma;
		this.hoTen = hoTen;
		this.cmnd = cmnd;
		this.sdt = sdt;
	}

	public int getMaKH() {
		return maKH;
	}

	public void setMaKH(int maKH) {
		this.maKH = maKH;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public String getCmnd() {
		return cmnd;
	}

	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	
	public void showInfo() {
		System.out.println("Ho ten: "+hoTen);
		System.out.println("SDT: "+sdt);
		System.out.println("CMND: "+cmnd);
	}
	
	
}
