import java.util.Calendar;
import java.util.Date;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;

public class ThongTinDatPhong implements Serializable{
	int maKH;
	int[] maPhong;
	int soNgayThue;
	Date ngayCheckin;
	TrangThai trangThai = TrangThai.CHUA_NHAN_PHONG;
	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	static Calendar cal = Calendar.getInstance(); 
	
	public ThongTinDatPhong(int maKH, int[] maPhong, int soNgayThue, 
			String ngayCheckin) throws ParseException {
		this.maKH = maKH;
		this.maPhong = maPhong;
		this.soNgayThue = soNgayThue;
		this.ngayCheckin = sdf.parse(ngayCheckin);
	}

	public boolean check(int m) {
		for (int i = 0; i < maPhong.length; i++) {
			if(maPhong[i] == m) return true;
		}
		return false;
	}

	public int getSoNgayThue() {
		return soNgayThue;
	}

	public void setSoNgayThue(int soNgayThue) {
		this.soNgayThue = soNgayThue;
	}

	public boolean checkDate(String checkin, int ngayThue) {
		try {
//			String ckin = sdf.format(ngayCheckin);
//			if(ckin.equals(checkin)) {
//				return true;
//			}
			Calendar cal1 = Calendar.getInstance(); 
			Date ckinBefore = sdf.parse(checkin);
			cal1.setTime(ckinBefore);
			cal1.add(Calendar.DAY_OF_MONTH, ngayThue);
			Date ckoutBefore = cal1.getTime();
			if(ckoutBefore.after(ngayCheckin) && 
					ckinBefore.compareTo(ngayCheckin) == 0) {
				return true;
			}
			Calendar cal2 = Calendar.getInstance(); 
			cal2.setTime(ngayCheckin);
			cal2.add(Calendar.DAY_OF_MONTH, ngayThue);
			Date checkout = cal2.getTime();
			Date newCkin = sdf.parse(checkin);
			if(newCkin.before(checkout)) {
				return true;
			}
		} catch (DateTimeException | ParseException e) {
			e.printStackTrace();
		}
		return false;	
	}

	public boolean ktraCheckInDate() {
		cal = Calendar.getInstance();
		String hienTai = sdf.format(cal.getTime());
		String ngayCI = sdf.format(ngayCheckin);
		if(hienTai.equalsIgnoreCase(ngayCI)) {
			return true;
		}
		return false;
	}
	
//	public boolean ktraCheckOutDate() {
//		Calendar cal1 = Calendar.getInstance(); 
//		cal = Calendar.getInstance();
//		String hienTai = sdf.format(cal.getTime());
//		cal1.setTime(ngayCheckin);
//		cal1.add(Calendar.DAY_OF_MONTH, soNgayThue);
//		String ngayCO = sdf.format(cal1.getTime());
//		if(hienTai.equalsIgnoreCase(ngayCO)) {
//			return true;
//		}
//		return false;
//	}
	
	public void showInfo() {
		String checkIn = sdf.format(ngayCheckin);
		cal.setTime(ngayCheckin);
		cal.add(Calendar.DAY_OF_MONTH, soNgayThue);
		String checkOut = sdf.format(cal.getTime());
		System.out.println("Ngay check-in: "+checkIn);
		System.out.println("Ngay check-out: "+checkOut);
		System.out.println("Trang thai: "+trangThai);
	}

	public int newCheckOutDate() {
		Calendar cal1 = Calendar.getInstance(); 
		Calendar cal2 = Calendar.getInstance();
		cal1 = Calendar.getInstance();
		Date hienTai = cal1.getTime();
		cal2.setTime(ngayCheckin);
		cal2.add(Calendar.DAY_OF_MONTH, soNgayThue);
		Date ngayCO = cal2.getTime();
		if(hienTai.before(ngayCO)) {
			Calendar cal3 = Calendar.getInstance(); 
			Calendar cal4 = Calendar.getInstance();
			cal3.setTime(hienTai);
			cal4.setTime(ngayCO);
			int d1 = cal3.get(Calendar.DAY_OF_YEAR);
			int d2 = cal4.get(Calendar.DAY_OF_YEAR);
			int soNgayThueNew = soNgayThue - (d2 - d1);
			return soNgayThueNew;
		}
		
		return soNgayThue;
	}
	
}
