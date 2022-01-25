import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;


public class Main {
	static ArrayList<Phong> dsPhong;
	static ArrayList<KhachHang> dsKH;
	static ArrayList<ThongTinDatPhong> dsDatPhong;
	static String filePhong = "F:/eclipse/eclipse-workspace/"
			+ "PhanThanhHung_BCK_J58/src/phong.txt";
	static String fileKH = "F:/eclipse/eclipse-workspace/"
			+ "PhanThanhHung_BCK_J58/src/khachhang.txt";
	static String fileDatPhong = "F:/eclipse/eclipse-workspace/"
			+ "PhanThanhHung_BCK_J58/src/datphong.txt";
	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	static Scanner in = new Scanner(System.in);
	public static void main(String[] args) {
		int chon;
		docDSPhong();
		docDSKH(); 
		docDSDatPhong();
		do {
			printMenu();
			chon = Integer.parseInt(in.nextLine());
			switch (chon) {
			case 1:
				inDSPhong();
				break;
			case 2:sxTheoGia();
				inDSPhong();
				break;
			case 3: nhapTTDatPhong();
				break;
			case 4: System.out.println("------Tra cu thong tin------");
				System.out.println("1. Thong tin KH theo ma khach hang");
				System.out.println("2. DS phong trong theo ngay check-in, check-out");
				System.out.print("\nNhap lua chon: ");
				int choice = Integer.parseInt(in.nextLine());
				switch(choice) {
				case 1: System.out.println("Nhap ma khach hang: ");
					int maKH = Integer.parseInt(in.nextLine());
					for (int i = 0; i < dsKH.size(); i++) {
						if(dsKH.get(i).getMaKH() == maKH) {
							dsKH.get(i).showInfo();
						}
					}	
					break;
				case 2:
					System.out.println("Nhap ngay check-in(dd/MM/yyyy): ");
					String checkin = in.nextLine();
					System.out.println("So ngay muon thue: ");
					int soNgayThue = Integer.parseInt(in.nextLine());
					inDSPhongTrong(checkin, soNgayThue);
					break;
				default:
					break;
				}
				break;
			case 5: 
				System.out.print("\nNhap sdt khach hang: ");
				String sdtCI = in.nextLine();
				checkIn(sdtCI);
				break;
			case 6:
				System.out.print("\nNhap sdt khach hang: ");
				String sdtCO = in.nextLine();
				checkOut(sdtCO);
				break;
			case 7:System.out.print("\nNhap sdt khach hang: ");
				String sdt = in.nextLine();
				System.out.println("------Hoa don------");
				if(hoaDon(sdt) == 0) {
					System.out.println("Khong co thong tin hoa don cua khach hang");
				}
				System.out.print("\nThanh tien: "+hoaDon(sdt));
				break;
			default:
				break;
			}
		}while(chon != 0);
		
		luuDSKH();
		luuDSDatPhong();
		
	}

	private static int hoaDon(String sdt) {
		int tienPhong = 0;
		for (KhachHang kh : dsKH) {
			if(kh.getSdt().equalsIgnoreCase(sdt)) {
				int ma = kh.getMaKH();
				for (ThongTinDatPhong tt : dsDatPhong) {
					if(tt.maKH == ma) {
						int[] maP= tt.maPhong;
						for (int i = 0; i < maP.length; i++) {
							int m = maP[i];
							int giaPhong = getGiaPhong(m);
							if(tt.trangThai == TrangThai.DA_CHECK_OUT) {
								tt.soNgayThue = tt.newCheckOutDate();
							}
							tienPhong += giaPhong *tt.soNgayThue;
						}
						
					} 
				}
			}
		}
	return tienPhong;
	}

	private static void checkOut(String sdtCO) {
		for (KhachHang kh : dsKH) {
			if(kh.getSdt().equalsIgnoreCase(sdtCO)) {
				int ma = kh.getMaKH();
				for (ThongTinDatPhong tt : dsDatPhong) {
					if(tt.maKH == ma) {
						hienThiTTPhong(tt.maPhong);
						tt.showInfo();
						hienThiTTKH(tt.maKH);
						int choice;
						confirmMenu();
						choice = Integer.parseInt(in.nextLine());
						switch (choice) {
						case 1:
							tt.trangThai = TrangThai.DA_CHECK_OUT;	
							System.out.println("Check-out thanh cong!");
							break;
						case 2: 
							System.out.println("Check-out that bai!");
						default:
					}
						
					}
				}
			}
		}
	}

	private static void checkIn(String sdt) {
		for (KhachHang kh : dsKH) {
			if(kh.getSdt().equalsIgnoreCase(sdt)) {
				int ma = kh.getMaKH();
				for (ThongTinDatPhong tt : dsDatPhong) {
					if(tt.maKH == ma) {
						if(tt.ktraCheckInDate()) {
							hienThiTTPhong(tt.maPhong);
							tt.showInfo();
							hienThiTTKH(tt.maKH);
							int choice;
							confirmMenu();
							choice = Integer.parseInt(in.nextLine());
							switch (choice) {
							case 1: 
								tt.trangThai = TrangThai.DA_CHECK_IN;
								System.out.println("Check-in thanh cong!");
								break;
							case 2: 
								System.out.println("Check-in that bai!");
							default:
							}
							
						}
					}
				}
			}
		}
	}
	private static void confirmMenu() {
		System.out.println("1. Xac nhan thong tin");
		System.out.println("2. Khong xac nhan thong tin");
		System.out.print("\nVui long chon: ");
	}

	private static void hienThiTTKH(int maKH) {
		for (KhachHang kh: dsKH) {
			if(kh.getMaKH() == maKH) {
				System.out.println("Ten KH: "+kh.getHoTen());
				System.out.println("SDT: "+kh.getSdt());
			}
		}
		
	}

	private static void hienThiTTPhong(int[] maPhong) {
		for (Phong p : dsPhong) {
			for (int i = 0; i < maPhong.length; i++) {
				if(maPhong[i] == p.getMaPhong()) {
					System.out.println("Ma phong: "+p.getMaPhong());
					System.out.println("Ten phong: "+p.getTenPhong());
				}
			}
		}
	}

	private static void luuDSDatPhong() {
		FileOutputStream output = null;
		ObjectOutputStream objOut = null;
		try {
			output = new FileOutputStream(new File(fileDatPhong));
			objOut = new ObjectOutputStream(output);
			objOut.writeObject(dsDatPhong);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(objOut != null) {
					objOut.close();
				}
			}catch(Exception e) {}
		}
	}

	private static void luuDSKH() {
		FileOutputStream output = null;
		ObjectOutputStream objOut = null;
		try {
			output = new FileOutputStream(new File(fileKH));
			objOut = new ObjectOutputStream(output);
			objOut.writeObject(dsKH);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(objOut != null) {
					objOut.close();
				}
			}catch(Exception e) {}
		}
	}

	private static void nhapTTDatPhong() {
		Scanner in = new Scanner(System.in);
		System.out.println("Nhap thong tin khach hang");
		System.out.print("\nNhap ho ten: ");
		String ten = in.nextLine();
		System.out.print("\nNhap so cmnd: ");
		String cmnd = in.nextLine();
		System.out.print("\nNhap so dien thoai: ");
		String sdt = in.nextLine();
		KhachHang kh = new KhachHang(ten, cmnd, sdt);
		dsKH.add(kh);
		int maKH = kh.getMaKH();
		System.out.print("\nNhap loai phong: ");
		String kieu = in.nextLine();
		System.out.print("\nNgay check-in(dd/MM/yyyy):");
		String checkin = in.nextLine();
		System.out.print("\nNhap so ngay muon thue: ");
		int soNgayThue = Integer.parseInt(in.nextLine());
		inDSPhongTrong(kieu, checkin,soNgayThue);
		System.out.print("\nSo phong muon thue: ");
		int n = Integer.parseInt(in.nextLine());
		int[] maPhong = new int[n];
		for (int i = 0; i < maPhong.length; i++) {
			System.out.print("\nNhap ma phong "+(i+1)+": ");
			maPhong[i] = Integer.parseInt(in.nextLine());
		}
		try {
			ThongTinDatPhong tt = new 
					ThongTinDatPhong(maKH, maPhong, soNgayThue, checkin);
			dsDatPhong.add(tt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
	}

	private static void inDSPhongTrong(String kieu, String checkin, int ngayThue) {
		ArrayList<Phong> ds1 = (ArrayList<Phong>) dsPhong.clone();
		ds1.removeIf(p -> !(p.getKieu().equalsIgnoreCase(kieu)));
		ArrayList<ThongTinDatPhong> ds2 = new ArrayList<>();
		for (ThongTinDatPhong p : dsDatPhong) {
			int[] ma= p.maPhong;
			for (int i = 0; i < ma.length; i++) {
				int m = ma[i];
				Phong phong = getPhong(m, kieu);
				if(phong != null) {
					ds2.add(p);
				}
			}
		}
		ArrayList<Phong> dsPhongKin = new ArrayList<>();
		for (Phong p : ds1) {
			int m = p.getMaPhong();
			for (ThongTinDatPhong tt : ds2) {
				if(tt.check(m)) {
					if(tt.trangThai == TrangThai.DA_CHECK_OUT) {
						ngayThue = tt.newCheckOutDate();
					}
					if(tt.checkDate(checkin, ngayThue)) {
						dsPhongKin.add(p);
					}
				}
			}
		}
		ds1.removeAll(dsPhongKin);
		inDSPhong(ds1, "Danh sach phong trong");
	}
	private static void inDSPhongTrong(String checkin, int ngayThue) {
		ArrayList<Phong> ds1 = (ArrayList<Phong>) dsPhong.clone();
		ArrayList<ThongTinDatPhong> ds2 = new ArrayList<>();
		for (ThongTinDatPhong p : dsDatPhong) {
			int[] ma= p.maPhong;
			for (int i = 0; i < ma.length; i++) {
				int m = ma[i];
				Phong phong = getPhong(m);
				if(phong != null) {
					ds2.add(p);
				}
			}
		}
		ArrayList<Phong> dsPhongKin = new ArrayList<>();
		for (Phong p : ds1) {
			int m = p.getMaPhong();
			for (ThongTinDatPhong tt : ds2) {
				if(tt.check(m)) {
					if(tt.checkDate(checkin, ngayThue)) {
						dsPhongKin.add(p);
					}
				}
			}
		}
		ds1.removeAll(dsPhongKin);
		inDSPhong(ds1, "Danh sach phong trong");
	}
	

	private static Phong getPhong(int m, String kieu) {
		for (Phong p : dsPhong) {
			if(p.getMaPhong() == m && p.getKieu().equalsIgnoreCase(kieu)) {
				return p;
			}
		}
		return null;
	}
	private static Phong getPhong(int m) {
		for (Phong p : dsPhong) {
			if(p.getMaPhong() == m ) {
				return p;
			}
		}
		return null;
	}
	
	private static int getGiaPhong(int m) {
		for (Phong p : dsPhong) {
			if(p.getMaPhong() == m ) {
				return p.getGiaTien();
			}
		}
		return 0;
	}

	private static void docDSDatPhong() {
		FileInputStream input = null;
		ObjectInputStream obInput = null;	
		try {
		input = new FileInputStream(new File(fileDatPhong));
		obInput = new ObjectInputStream(input);
		Object object = obInput.readObject();
		dsDatPhong = (ArrayList<ThongTinDatPhong>) object;
		}catch (Exception e) {
			System.out.println("Chua co File Dat Phong(Tao ds rong)");
			dsDatPhong = new ArrayList<>();
		}finally {
			try {
				if(obInput != null) {
					obInput.close();
				}
			}catch(Exception e) {}
		}
		
	}

	private static void docDSKH() {
		FileInputStream input = null;
		ObjectInputStream obInput = null;
		try {
		input = new FileInputStream(new File(fileKH));
		obInput = new ObjectInputStream(input);
		Object object = obInput.readObject();
		dsKH = (ArrayList<KhachHang>) object;
		}catch (Exception e) {
			System.out.println("Chua co File KH(Tao ds rong)");
			dsKH = new ArrayList<>();
		}finally {
			try {
				if(obInput != null) {
					obInput.close();
				}
			}catch(Exception e) {}
		}
	}

	private static void sxTheoGia() {
		Comparator<Phong> com = new Comparator<Phong>() {

			@Override
			public int compare(Phong o1, Phong o2) {
				return o1.getGiaTien() < o2.getGiaTien()? -1 : 1;		
			}
		};
		dsPhong.sort(com);
	}

	private static void printMenu() {
		System.out.println("\n--------------------------------------");
		System.out.println("-------MENU-------");
		System.out.println("1. In danh sach phong");
		System.out.println("2. In danh sach phong theo gia tang dan");
		System.out.println("3. Dat phong");
		System.out.println("4. Tra cuu thong tin");
		System.out.println("5. Check In");
		System.out.println("6. Check Out");
		System.out.println("7. In hoa don");
		System.out.println("0. Luu va thoat");
		System.out.print("\nNhap lua chon: ");
	}

	private static void docDSPhong() {
		dsPhong = new ArrayList<>();
		FileReader fr = null;
		BufferedReader bfr = null;
		try {
			fr = new FileReader(new File(filePhong));
			bfr = new BufferedReader(fr);
			String line;
			while((line = bfr.readLine()) != null) {
				if(line.startsWith("#")) continue;
				String[] part = line.split(";");
				int maPhong = Integer.valueOf(part[0]);
				String tenPhong = part[1];
				String kieu = part[2];
				int giaTien = Integer.valueOf(part[3]);
				Phong p = new Phong(maPhong, tenPhong, kieu, giaTien);
				dsPhong.add(p);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(bfr != null) {
					bfr.close();
				}
			}catch(Exception e) {}
		}
		
	}

	private static void inDSPhong() {
		System.out.format("%-5s %10s %10s %10s", "Ma", "Ten", "Loai", "Gia");
		System.out.println();
		for (Phong p : dsPhong) {
			System.out.println(p);
		}
	}
	
	private static void inDSPhong(ArrayList<Phong> dsP, String tieude) {
		System.out.println("          "+tieude+"             ");
		System.out.format("%-5s %10s %10s %10s", "Ma", "Ten", "Loai", "Gia");
		System.out.println();
		for (Phong p : dsP) {
			System.out.println(p);
		}
	}

}
