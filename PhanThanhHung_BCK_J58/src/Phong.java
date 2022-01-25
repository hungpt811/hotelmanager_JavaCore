
public class Phong {
	private int maPhong;
	private String tenPhong;
	private String kieu;
	private int giaTien;
	
	public Phong(int maPhong, String tenPhong, String kieu, int giaTien) {
		this.maPhong = maPhong;
		this.tenPhong = tenPhong;
		this.kieu = kieu;
		this.giaTien = giaTien;
	}

	public int getMaPhong() {
		return maPhong;
	}

	public void setMaPhong(int maPhong) {
		this.maPhong = maPhong;
	}

	public String getTenPhong() {
		return tenPhong;
	}

	public void setTenPhong(String tenPhong) {
		this.tenPhong = tenPhong;
	}

	public String getKieu() {
		return kieu;
	}

	public void setKieu(String kieu) {
		this.kieu = kieu;
	}

	public int getGiaTien() {
		return giaTien;
	}

	public void setGiaTien(int giaTien) {
		this.giaTien = giaTien;
	}
	
	@Override
	public String toString() {
		return String.format("%-5d %10s %10s %10d", maPhong, tenPhong, kieu, giaTien);
	}
	
	
}
