# UAS PBO
Ujian Akhir Semester PBO, Membuat CRUD Mahasiswa disertai dengan laporan menggunakan JasperReport dan Uploud File berupa file .csv.

# Langkah-langkah :
## 1. Membuat database pada PgAdmin
### a. Buatlah database dengan atribut berikut :
![image](https://github.com/user-attachments/assets/91e0867a-68de-4f6b-a8ce-e7481ff8f38e)

## 2. Membuat Projek/Package baru pada Netbenas, dan menyambungkan database menggunakan Persistance.
### a. Masuk netbeans, buat package baru
![image](https://github.com/user-attachments/assets/90e684d6-4c18-47e2-bc8b-924e8482488a)
### b. Kemudian buat file Persistance untuk menyambungkan ke database, klik kanan pada Package > New > Entity Classes from Databases
![image](https://github.com/user-attachments/assets/f03c96e9-db1e-41f4-ab70-065eafdecd08)
### c. Buat koneksi baru pada New Connection
![image](https://github.com/user-attachments/assets/c150463d-3ecb-49bb-85c0-ad544d5073ec)

![image](https://github.com/user-attachments/assets/9bc2bd25-fd0f-4077-8d77-8fc5625e90ee)

Pilih Driver PostgreSQL
### d. Masukkan database anda dan klik next
![image](https://github.com/user-attachments/assets/bde6b8e2-1ea8-4daf-bdda-d90613fb58da)
### e. Add All pada semua tabel yang ada dalam database
![image](https://github.com/user-attachments/assets/c482acb3-4086-4259-a3c6-c8c4d5621029)

## 2. Buat File JFrame Form untuk membuat tampilan GUI, berikut adalah tampilan dan JFrame yang harus dibuat :
### a. Tampilan formMahasiswa
![image](https://github.com/user-attachments/assets/f26a646a-ba1a-4094-8116-d72a203368d3)

Dengan ketentuan :
TextField (Nim, Nama, Alamat, AsalSMA, NamaOrtu)
Button (Insert, Update, Delete, Clear, Upload, Print, Logout)
Table Mouse Clicked

### b. Tampilan form LoginMahasiswa
![image](https://github.com/user-attachments/assets/76de145f-c5fa-4834-b364-1a14db8011e7)

Dengan Ketentuan :
TextField (Username, Password)
Button (Login)
Text (Create an account)
CheckBox (Tampilkan Password)

### c. Tampilan form CreateAccount
![image](https://github.com/user-attachments/assets/b5147f9f-2809-4820-a16a-5ab66f376d7a)

TextField (Username, Password)
Text (Login)
Button (Create new account)
CheckBox (Show Password)

## 3. Buat CRUD pada formMahasiswa
### a. READ/TAMPIL
Masukkan source berikut untuk membuat tampilan awal pada form Mahasiswa
<pre>
  public void tampil() {
        em = emf.createEntityManager();

        try {
            // Buat model tabel dengan kolom yang sesuai
            DefaultTableModel tbnmhs = new DefaultTableModel(new String[]{"NIM", "Nama", "Alamat", "AsalSMA", "NamaOrangTua"}, 0);

            // Menggunakan NamedQuery "Matakuliah.findAll" untuk mengambil semua data
            List<Mahasiswa> mahasiswaList = em.createNamedQuery("Mahasiswa.findAll", Mahasiswa.class).getResultList();

            // Iterasi hasil query dan tambahkan baris ke model tabel
            for (Mahasiswa mahasiswa : mahasiswaList) {
                tbnmhs.addRow(new Object[]{
                    mahasiswa.getNim(),
                    mahasiswa.getNama(),
                    mahasiswa.getAlamat(),
                    mahasiswa.getAsalsma(),
                    mahasiswa.getNamaorangtua()
                });
            }

            // Atur model tabel ke tabel GUI
            tabel.setModel(tbnmhs);
        } catch (Exception ex) {
        }
    }
</pre>
Sesuaikan dengan database anda dan File Persistance anda.

### b. MEMBUAT FUNGSI PADA BUTTON INSERT
Masukkan source berikut untuk memasukkan data pada form Mahasiswa
<pre>
  private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        if (tfNIM.getText().equals("") || tfNama.getText().equals("") || tfAlamat.getText().equals("") || tfAsal.getText().equals("") || tfOrtu.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Isi semua data");
        } else {
            String NIM, Nama, Alamat, AsalSMA, NamaOrangTua;
            NIM = tfNIM.getText();
            Nama = tfNama.getText();
            Alamat = tfAlamat.getText();
            AsalSMA = tfAsal.getText();
            NamaOrangTua = tfOrtu.getText();
            int nim;
            //int sks;

            try {
                nim = Integer.parseInt(tfNIM.getText());
                // smt = Integer.parseInt(tfSKS.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "NIM harus berupa angka", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            em.getTransaction().begin();

            mahasiswa.setNim(String.valueOf(NIM));
            mahasiswa.setNama(Nama);
            mahasiswa.setAlamat(Alamat);
            mahasiswa.setAsalsma(AsalSMA);
            mahasiswa.setNamaorangtua(NamaOrangTua);

            em.persist(mahasiswa);

            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Sukses diinput");

            bersih();
            tampil();

        }
        tampil();
    }
</pre>

### c. MEMBUAT FUNGSI PADA BUTTON UPDATE
<pre>
  private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        if (tfNIM.getText().equals("") || tfNama.getText().equals("")
                || tfAlamat.getText().equals("") || tfAsal.getText().equals("") || tfOrtu.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Isi semua data");
        } else {
            String NIM, Nama, Alamat, AsalSMA, NamaOrangTua;
            NIM = tfNIM.getText();
            Nama = tfNama.getText();
            Alamat = tfAlamat.getText();
            AsalSMA = tfAsal.getText();
            NamaOrangTua = tfOrtu.getText();

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("UAS_PBO");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            Mahasiswa mk = em.find(Mahasiswa.class, NIM);
            if (mk == null) {
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
            } else {
                mk.setNim(NIM);
                mk.setNama(Nama);
                mk.setAlamat(Alamat);
                mk.setAsalsma(AsalSMA);
                mk.setNamaorangtua(NamaOrangTua);

                em.getTransaction().commit();
                JOptionPane.showMessageDialog(null, "Data berhasil diupdate");

                em.close();
                emf.close();
                bersih();
                tfNIM.setEditable(true);
            }
        }
        tampil();
    }            
</pre>

### d. MEMBUAT FUNGSI PADA BUTTON DELETE
<pre>
  private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        em = emf.createEntityManager();

        try {
            // Validasi input
            if (tfNIM.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Masukkan NIM yang akan dihapus");
            } else {
                // Mulai transaksi
                em.getTransaction().begin();

                // Cari entitas Matakuliah berdasarkan kode mata kuliah
                String NIM = tfNIM.getText();
                mahasiswa = em.find(Mahasiswa.class, NIM);

                if (mahasiswa != null) {
                    // Hapus entitas jika ditemukan
                    em.remove(mahasiswa);

                    // Commit transaksi
                    em.getTransaction().commit();
                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus");

                    // Refresh data pada tampilan
                    tampil();
                } else {
                    JOptionPane.showMessageDialog(null, "Data tidak ditemukan untuk NIM: " + NIM);
                    em.getTransaction().rollback();
                }
            }
        } catch (Exception e) {
            // Rollback transaksi jika terjadi kesalahan
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            JOptionPane.showMessageDialog(null, "Gagal menghapus data");
            System.out.println(e.getMessage());
        }

        // Kosongkan text field setelah penghapusan
        tfNIM.setText("");
        tfNama.setText("");
        tfAlamat.setText("");
        tfAsal.setText("");
        tfOrtu.setText("");
    }
</pre>

### e. MEMBUAT FUNGSI PADA BUTTON CLEAR
<pre>
  private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        tfNIM.setText("");
        tfNama.setText("");
        tfAlamat.setText("");
        tfAsal.setText("");
        tfOrtu.setText("");
    }    
</pre>

### f. MEMBUAT FUNGSI PADA BUTTON LOGOUT
<pre>
  private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        LoginMhs y = new LoginMhs();
        y.setVisible(true);
        this.dispose();
    }   
</pre>

### g. MEMBUAT FUNGSI PADA BUTTON UPLOAD
<pre>
  
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Gagal diinput");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Gagal diinput");
            } catch (ClassNotFoundException ex) {
            }
        }
    }     
</pre>

## 4. Membuat Laporan Menggunakan Jasper Report pada form mahasiswa
### a. Klik kanan pada package > New > Report Wizard, Pastikan anda sudah menambahkan library JasperReport pada Library Projek anda.
![image](https://github.com/user-attachments/assets/6e6bdb6e-9929-45e2-ba3a-2c32dc8b3335)
### b. Pilih template sesuai keinginan anda, kemudian next.
![image](https://github.com/user-attachments/assets/30938339-66f0-4d79-920c-71a5b00a1fc9)
### c. Masukkan database yang akan dikoneksikan, dan ketik sql berikut pada kolom sql
![image](https://github.com/user-attachments/assets/c48bb748-0d11-44f6-bb88-0e41ff9518dd)

Add all Field yang ada di tabel kiri, kemudian next sampai selesai.
![image](https://github.com/user-attachments/assets/da85b6ec-2d5b-494a-97ec-7e82f31805d8)

### d. Masukkan source code berikut pada button CETAK :
<pre>
  try {
            JasperReport reports;
            String path = "src\\UAS\\report.jasper";
            reports = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint print = JasperFillManager.fillReport(path, null, conn);
            JasperViewer view = new JasperViewer(print, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);

        } catch (JRException e) {
            System.out.println(e.getMessage());
        }  
</pre>

## 5. Membuat CREATE pada LOGIN dan CREATE LOGIN
### a. Button Create
Masukkan Source Code berikut pada BUTTON INSERT form CreateAcc
<pre>
  private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        if (tfUser.getText().equals("") || tfPass.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Isi username dan password");
        } else {
            String username, password;
            username = tfUser.getText();
            password = tfPass.getText();

            if (password.length() < 8) {
                JOptionPane.showMessageDialog(null, "Password harus memiliki setidaknya 8 karakter.");
                return;
            }

            if (!password.matches("[A-Za-z0-9]+")) {
                JOptionPane.showMessageDialog(null, "Password hanya boleh mengandung Huruf dan Angka.");
                return;
            }

            int angka = 0;
            for (char c : password.toCharArray()) {
                if (Character.isDigit(c)) {
                    angka++;
                }
            }

            if (angka < 2) {
                JOptionPane.showMessageDialog(null, "Password Harus Mengandung Setidaknya 2 Angka.");
                return;
            }

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("UAS_PBO");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            Loginmahasiswa y = em.find(Loginmahasiswa.class, username);
            if (y != null) {
                JOptionPane.showMessageDialog(null, "Username sudah digunakan, gunakan Username lain");
                bersih();
                tfUser.requestFocus();
            } else {
                Loginmahasiswa account = new Loginmahasiswa();
                account.setUsername(username);
                account.setPassword(password);
                em.persist(account);
                em.getTransaction().commit();

                JOptionPane.showMessageDialog(null, "Akun berhasil dibuat");
                bersih();

                this.dispose();
                LoginMhs frame = new LoginMhs();
                frame.setVisible(true);
            }
            em.close();
            emf.close();
        }
    }          
</pre>

### b. Button Login 
Masukkan Source Code berikut pada Text Login pada formLogin untuk masuk pada form mahasiswa.
<pre>
  private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        if (tfUser.getText().equals("") | tfPass.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Isi Terlebih Dahulu");
        } else {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("UAS_PBO");
            EntityManager em = emf.createEntityManager();

            em.getTransaction().begin();

            String user = tfUser.getText();
            String pw = tfPass.getText();
            Loginmahasiswa y = em.find(Loginmahasiswa.class, user);

            if (y == null) {
                JOptionPane.showMessageDialog(null, "Username tidak ditemukan");
            } else if (y.getPassword().equals(pw)) {
                JOptionPane.showMessageDialog(null, "SELAMAT DATANG !!!!!!");
                FormMahasiswa p = new FormMahasiswa();
                p.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Username atau Password salah!");
            }
            em.getTransaction().commit();
            em.close();
            emf.close();
        }
    }  
</pre>

### c. Membuat fungsi untuk Text create an account pada form LoginMahasiswa
<pre>
  private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
        CreateAcc y = new CreateAcc();
        y.setVisible(true);
        this.dispose();
    }     
</pre>

### d. Membuat fungsi Tampilkan Password pada atribut CheckBox yang ada pada form LoginMahasiswa dan form CreateAcc
CheckBox ini nantinya digunakan untuk melihat password yang diinputkan.
<pre>
  private void cbTampilkanActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        if (cbTampilkan.isSelected()){
            tfPass.setEchoChar((char)0);
        } else {
            tfPass.setEchoChar('*');
        }
    }                      
</pre>

# DEMO PROGRAM
## 1. Login
### a. Menjalankan form Login
Fungsi Show Password (checkbox ini berfungsi untuk melihat password apabila dicentang)
![image](https://github.com/user-attachments/assets/70b09c1e-7879-435e-bbb4-214331ea8b32)
### b. Jika username belum daftar
![image](https://github.com/user-attachments/assets/583a5be0-f38c-4e23-915c-e148731c7415)

## 2. Create Login
### a. Klik create an account pada form Login
### b. Jika Password tidak sesuai dengan ketentuan, akan muncul nontifikasi
![image](https://github.com/user-attachments/assets/be696b54-4088-4562-a7e2-aec9d90b3e88)
### c. Jika berhasil, akan diarahkan pada halaman Login. Dan jika Login berhasil akan diarahkan pada halaman form LoginMahasiswa
![image](https://github.com/user-attachments/assets/cfeeff64-df34-409f-bddd-5f7d1546eff4)

## 3. CRUD Mahasiswa
### a. Tampil
![image](https://github.com/user-attachments/assets/ee21b943-2fb2-4f96-8e68-5232267ea6aa)
### b. Insert
![image](https://github.com/user-attachments/assets/c3001193-56e1-4d0f-9d6e-b238f23a3bc4)

### c. Update
![image](https://github.com/user-attachments/assets/5bc5436d-f315-451a-a772-d958a7bab218)

### d. Delete
![image](https://github.com/user-attachments/assets/672b2122-4c3f-40b3-9ee0-41ab8c8cf240)
![image](https://github.com/user-attachments/assets/4f692a8f-0002-4793-86f0-036b58b2a4e0)

### e. Upload 
Buat data pada MS.Excel kemudian simpan dengan extension .csv
Klik button Upload, pilih file .csv 

Buat data di Excel

![image](https://github.com/user-attachments/assets/3b9fc271-c3d9-4b9d-a72b-4b2d865e5738)

Klik Upload > pilih file > next

![image](https://github.com/user-attachments/assets/d5ba2bb9-a057-4f78-a02f-a16afc9b3d65)

Maka data yang ada di excel akan otomatis masuk pada aplikasi GUI 

![image](https://github.com/user-attachments/assets/d1aef9f2-5277-4766-8a3c-6b6ee9e102fe)

### e. Print
Klik button Print
![image](https://github.com/user-attachments/assets/97a25896-b752-46d1-8d3c-58547defcd25)

# SEKIAN DARI SAYA, SEMOGA PENJELASAN INI DAPAT MEMBANTU DALAM MEMAHAMI PENUGASAN KALI INI. TERIMA KASIH!!!🙌😊
