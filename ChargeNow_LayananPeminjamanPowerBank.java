import java.util.*;
import java.text.SimpleDateFormat;

/**
 * ChargeNow - Simple console application for Power Bank borrowing system.
 * Features:
 * - User can borrow up to MAX_PER_USER power banks (choose one by one)
 * - Admin can view active borrowers, return by PB code, and view full history
 * - Return shows borrow time, return time, status (on time / late); if late, admin inputs penalty
 * - Ripanrz
 */

class PowerBank {
    String kode;
    boolean dipinjam;
    int userIndex;

    PowerBank(String kode) {
        this.kode = kode;
        this.dipinjam = false;
        this.userIndex = -1;
    }
}

class User {
    String name;
    String nim;
    String prodi;
    String instansi;
    List<Integer> borrowedPB = new ArrayList<>();
    int penalti = 0;
    boolean active = false;
    User(String name, String nim, String prodi, String instansi) {
        this.name = name;
        this.nim = nim;
        this.prodi = prodi;
        this.instansi = instansi;
    }
}

class Transaction {
    String userName;
    String userNIM;
    String prodi;
    String instansi;
    String pbCode;
    Date borrowTime;
    Date returnTime; 
    boolean late;
    int penalty;

    Transaction(String userName, String userNIM, String prodi, String instansi, String pbCode, Date borrowTime) {
        this.userName = userName;
        this.userNIM = userNIM;
        this.prodi = prodi;
        this.instansi = instansi;
        this.pbCode = pbCode;
        this.borrowTime = borrowTime;
        this.returnTime = null;
        this.late = false;
        this.penalty = 0;
    }
}

public class ChargeNow {
    static final int MAX_USERS = 100;
    static final int NUM_PB = 5;
    static final int MAX_PER_USER = 5;
    static final int DUE_HOURS = 24; 
    static List<User> users = new ArrayList<>();
    static List<PowerBank> pbs = new ArrayList<>();
    static List<Transaction> history = new ArrayList<>();

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void initPowerBanks() {
        pbs.clear();
        for (int i = 1; i <= NUM_PB; i++) {
            pbs.add(new PowerBank(String.format("PB%03d", i)));
        }
    }

    // find user by NIM (active or not)
    public static User findUserByNIM(String nim) {
        for (User u : users) {
            if (u.nim.equalsIgnoreCase(nim)) return u;
        }
        return null;
    }

    public static void menuUser(Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n===== Menu User =====");
            System.out.println("1. Peminjaman Power Bank");
            System.out.println("2. Kembali");
            System.out.print("Pilihan: ");
            String input = sc.nextLine().trim();
            if (input.equals("1")) {
                pinjamFlow(sc);
            } else if (input.equals("2")) {
                back = true;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public static void pinjamFlow(Scanner sc) {
        if (users.size() >= MAX_USERS) {
            System.out.println("Data user penuh!");
            return;
        }

        System.out.println("\n===== Formulir Peminjaman Power Bank =====");
        System.out.print("Nama: ");
        String nama = sc.nextLine().trim();
        System.out.print("NIM: ");
        String nim = sc.nextLine().trim();
        System.out.print("Program Studi: ");
        String prodi = sc.nextLine().trim();
        System.out.print("Instansi: ");
        String instansi = sc.nextLine().trim();

        User user = findUserByNIM(nim);
        if (user == null) {
            user = new User(nama, nim, prodi, instansi);
            users.add(user);
        } else {
            user.name = nama;
            user.prodi = prodi;
            user.instansi = instansi;
        }

        if (user.borrowedPB.size() >= MAX_PER_USER) {
            System.out.println("Anda telah mencapai batas maksimal peminjaman (" + MAX_PER_USER + ").");
            return;
        }

        // show available PBs
        System.out.println("\n===== Daftar Power Bank =====");
        boolean ada = false;
        for (int i = 0; i < pbs.size(); i++) {
            PowerBank pb = pbs.get(i);
            String status = pb.dipinjam ? "Dipinjam" : "Tersedia";
            System.out.println((i + 1) + ". " + pb.kode + " (Status: " + status + ")");
            if (!pb.dipinjam) ada = true;
        }
        if (!ada) {
            System.out.println("Maaf, tidak ada power bank tersedia saat ini.");
            return;
        }

        System.out.print("Pilih nomor power bank (atau ketik 0 untuk batal): ");
        String sel = sc.nextLine().trim();
        int pilih;
        try {
            pilih = Integer.parseInt(sel);
        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid.");
            return;
        }
        if (pilih == 0) {
            System.out.println("Pembatalan peminjaman.");
            return;
        }
        pilih = pilih - 1;
        if (pilih < 0 || pilih >= pbs.size()) {
            System.out.println("Pilihan nomor tidak valid.");
            return;
        }
        PowerBank chosen = pbs.get(pilih);
        if (chosen.dipinjam) {
            System.out.println("Power bank sudah dipinjam. Pilih yang lain.");
            return;
        }

        chosen.dipinjam = true;
        chosen.userIndex = users.indexOf(user);
        user.borrowedPB.add(pilih);
        user.active = true;

        Date now = new Date();
        history.add(new Transaction(user.name, user.nim, user.prodi, user.instansi, chosen.kode, now));

        System.out.println("Konfirmasi: " + user.name + " meminjam " + chosen.kode);
        System.out.println("Waktu Peminjaman: " + sdf.format(now));
    }

    public static void menuAdmin(Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n===== Menu Admin =====");
            System.out.println("1. Daftar Peminjam");
            System.out.println("2. Kembalikan Power Bank");
            System.out.println("3. Histori Peminjaman & Pengembalian");
            System.out.println("4. Kembali");
            System.out.print("Pilihan: ");
            String input = sc.nextLine().trim();
            switch (input) {
                case "1":
                    adminListBorrowers();
                    break;
                case "2":
                    adminReturnPB(sc);
                    break;
                case "3":
                    adminHistory();
                    break;
                case "4":
                    back = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public static void adminListBorrowers() {
        System.out.println("\n===== Daftar Peminjam Aktif =====");
        boolean ada = false;
        for (User u : users) {
            if (!u.borrowedPB.isEmpty()) {
                ada = true;
                System.out.println("---------------------------------");
                System.out.println("Nama     : " + u.name);
                System.out.println("NIM      : " + u.nim);
                System.out.println("Prodi    : " + u.prodi);
                System.out.println("Instansi : " + u.instansi);
                System.out.println("Penalti  : " + u.penalti);
                System.out.println("Power Banks dipinjam:");
                for (int idx : u.borrowedPB) {
                    PowerBank pb = pbs.get(idx);
                    Date borrowTime = findOpenBorrowTime(pb.kode);
                    System.out.println("  - " + pb.kode + " | Waktu Peminjaman: " + (borrowTime == null ? "-" : sdf.format(borrowTime)));
                }
            }
        }
        if (!ada) {
            System.out.println("Tidak ada peminjam aktif.");
        }
    }


    public static Date findOpenBorrowTime(String pbCode) {
        for (int i = history.size() - 1; i >= 0; i--) {
            Transaction t = history.get(i);
            if (t.pbCode.equalsIgnoreCase(pbCode) && t.returnTime == null) {
                return t.borrowTime;
            }
        }
        return null;
    }

    public static Transaction findOpenTransactionByPB(String pbCode) {
        for (int i = history.size() - 1; i >= 0; i--) {
            Transaction t = history.get(i);
            if (t.pbCode.equalsIgnoreCase(pbCode) && t.returnTime == null) {
                return t;
            }
        }
        return null;
    }

    public static void adminReturnPB(Scanner sc) {
        System.out.println("\n===== Kembalikan Power Bank =====");
        System.out.print("Masukkan kode Power Bank: ");
        String kode = sc.nextLine().trim();

        PowerBank foundPB = null;
        int pbIdx = -1;
        for (int i = 0; i < pbs.size(); i++) {
            if (pbs.get(i).kode.equalsIgnoreCase(kode)) {
                foundPB = pbs.get(i);
                pbIdx = i;
                break;
            }
        }
        if (foundPB == null) {
            System.out.println("Kode Power Bank tidak ditemukan.");
            return;
        }
        if (!foundPB.dipinjam) {
            System.out.println("Power Bank " + kode + " sedang tidak dipinjam.");
            return;
        }

        // find the open transaction
        Transaction openTx = findOpenTransactionByPB(kode);
        if (openTx == null) {
            System.out.println("Transaksi peminjaman untuk " + kode + " tidak ditemukan di histori.");
            return;
        }

        Date now = new Date();
        openTx.returnTime = now;

        long diffMillis = now.getTime() - openTx.borrowTime.getTime();
        long diffHours = diffMillis / (1000 * 60 * 60);
        boolean late = diffHours > DUE_HOURS;
        openTx.late = late;

        System.out.println("Waktu Peminjaman : " + sdf.format(openTx.borrowTime));
        System.out.println("Waktu Pengembalian: " + sdf.format(openTx.returnTime));
        System.out.println("Status           : " + (late ? "Telat" : "Tepat waktu"));
        int penaltyGiven = 0;
        if (late) {
         
            while (true) {
                System.out.print("Masukkan jumlah penalti (angka) atau 0 jika tidak memberi penalti: ");
                String line = sc.nextLine().trim();
                try {
                    penaltyGiven = Integer.parseInt(line);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Input tidak valid. Masukkan angka.");
                }
            }
            openTx.penalty = penaltyGiven;

            User u = findUserByNIM(openTx.userNIM);
            if (u != null) {
                u.penalti += penaltyGiven;
            }
        } else {
            System.out.println("Tidak perlu penalti.");
        }

       
        foundPB.dipinjam = false;
        int userIndex = foundPB.userIndex;
        foundPB.userIndex = -1;
        if (userIndex >= 0 && userIndex < users.size()) {
            User borrower = users.get(userIndex);
            
            Integer boxedIdx = pbIdx;
            borrower.borrowedPB.remove(boxedIdx);
            if (borrower.borrowedPB.isEmpty()) {
                borrower.active = false;
            }
        }

        System.out.println("Pengembalian berhasil. Kembali ke menu admin.");
    }

    public static void adminHistory() {
        System.out.println("\n===== Histori Peminjaman & Pengembalian =====");
        if (history.isEmpty()) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        int i = 1;
        for (Transaction t : history) {
            System.out.println("---------------------------------");
            System.out.println("No           : " + (i++));
            System.out.println("Nama         : " + t.userName);
            System.out.println("NIM          : " + t.userNIM);
            System.out.println("Prodi        : " + t.prodi);
            System.out.println("Instansi     : " + t.instansi);
            System.out.println("Power Bank   : " + t.pbCode);
            System.out.println("Waktu Pinjam : " + sdf.format(t.borrowTime));
            System.out.println("Waktu Kembali: " + (t.returnTime == null ? "-" : sdf.format(t.returnTime)));
            System.out.println("Status       : " + (t.returnTime == null ? "Belum kembali" : (t.late ? "Telat" : "Tepat waktu")));
            System.out.println("Penalti      : " + t.penalty);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        initPowerBanks();

        System.out.println("===== Selamat datang Di ChargeNow: Layanan Peminjaman Power Bank =====");
        boolean running = true;
        while (running) {
            System.out.println("\nSilakan pilih peran Anda:");
            System.out.println("1. User");
            System.out.println("2. Admin");
            System.out.println("3. Keluar");
            System.out.print("Pilihan: ");
            String role = sc.nextLine().trim();
            switch (role) {
                case "1":
                    menuUser(sc);
                    break;
                case "2":
                    menuAdmin(sc);
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }

        System.out.println("Terima kasih. Program selesai.");
        sc.close();
    }
}
