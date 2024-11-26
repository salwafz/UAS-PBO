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
![image](https://github.com/user-attachments/assets/f72ee571-b027-4379-9fec-1ac9ca596a63)
### b. Pilih template sesuai keinginan anda, kemudian next.
![image](https://github.com/user-attachments/assets/d780b96b-ea2d-43d8-9209-9ebd6b42e15c)
### c. Masukkan database yang akan dikoneksikan, dan ketik sql berikut pada kolom sql
![image](https://github.com/user-attachments/assets/9b8be955-8474-4294-ac8c-3bfa8bf37ab0)
Add all Field yang ada di tabel kiri, kemudian next sampai selesai.
![image](https://github.com/user-attachments/assets/da85b6ec-2d5b-494a-97ec-7e82f31805d8)

### d. Masukkan source code berikut pada button CETAK :
<pre>
  private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        String driver = "org.postgresql.Driver";
        String koneksi = "jdbc:postgresql://localhost:5432/entitasMahasiswa";
        String user = "postgres";
        String password = "lyra123";
        File reportFile = new File(".");
        String dirr = "";

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(koneksi, user, password);
            java.sql.Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM mahasiswaa";
            dirr = reportFile.getCanonicalPath() + "/src/UASPBO/";
            JasperDesign design = JRXmlLoader.load(dirr + "report1.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(design);
            ResultSet rs = stmt.executeQuery(sql);
            JRResultSetDataSource rsDataResource = new JRResultSetDataSource(rs);
            JasperPrint jp = JasperFillManager.fillReport(jr, new HashMap(), rsDataResource);
            JasperViewer.viewReport(jp);
        } catch (ClassNotFoundException | SQLException | IOException | JRException ex) {
            JOptionPane.showMessageDialog(null, "\nGagal Mencetak\n" + ex,
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }         
</pre>

## 5. Membuat CREATE pada LOGIN dan CREATE LOGIN
### a. Create Login
Masukkan Source Code berikut pada BUTTON SIMPAN formCreateLogin
<pre>
  private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        if (txtUsername.getText().equals("") || txtPassword.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Isi username dan password");
        } else {
            String username, password;
            username = txtUsername.getText();
            password = txtPassword.getText();

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

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("LatihanSemester3PU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            Pwloginmhs y = em.find(Pwloginmhs.class, username);
            if (y != null) {
                JOptionPane.showMessageDialog(null, "Username sudah digunakan, gunakan Username lain");
                bersih();
                txtUsername.requestFocus();
            } else {
                Pwloginmhs account = new Pwloginmhs();
                account.setUsername(username);
                account.setPassword(password);
                em.persist(account);
                em.getTransaction().commit();

                JOptionPane.showMessageDialog(null, "Akun berhasil dibuat");
                bersih();

                this.dispose();
                formLogin frame = new formLogin();
                frame.setVisible(true);
            }
            em.close();
            emf.close();
        }
    }         
</pre>

### b. Login 
Masukkan Source Code berikut pada BUTTON Login pada formLogin untuk masuk pada form mahasiswa.
<pre>
  private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        if (txtUsername.getText().equals("") | txtPassword.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Isi Terlebih Dahulu");
        } else {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("LatihanSemester3PU");
            EntityManager em = emf.createEntityManager();

            em.getTransaction().begin();

            String username = txtUsername.getText();
            String password = txtPassword.getText();
            Pwloginmhs y = em.find(Pwloginmhs.class, username);

            if (y == null) {
                JOptionPane.showMessageDialog(null, "Username tidak ditemukan");
                bersih();
            } else if (y.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(null, "SELAMAT DATANG!");
                formMahasiswa m = new formMahasiswa();
                m.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Username atau Password yang Anda Masukkan Salah!");
            }
            em.getTransaction().commit();
            em.close();
            emf.close();
        }
    }     
</pre>

### c. Membuat fungsi untuk Text create new account pada formLogin
<pre>
  private void createnewMouseClicked(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
        formCreateLogin y = new formCreateLogin();
        y.setVisible(true);
        this.dispose();
    }  
</pre>

### d. Membuat fungsi Show Password pada atribut CheckBox yang ada pada formLogin dan formCreateLogin.
CheckBox ini nantinya digunakan untuk melihat password yang diinputkan.
<pre>
  private void cbPwActionPerformed(java.awt.event.ActionEvent evt) {                                     
        // TODO add your handling code here:
        if(cbPw.isSelected()){
            txtPassword.setEchoChar((char)0);
        }
        else
        txtPassword.setEchoChar('*');
    }                    
</pre>

# DEMO PROGRAM
## 1. Login
### a. Menjalankan formLogin
Fungsi Show Password
![image](https://github.com/user-attachments/assets/9d5581ad-506b-4379-8b92-6a128a901d86)
### b. Jika username belum daftar
![image](https://github.com/user-attachments/assets/8aa1929d-cca3-465b-afba-e0f470790ac6)

## 2. Create Login
### a. Klik create new account pada formLogin
### b. Jika Password tidak sesuai dengan ketentuan, akan muncul nontifikasi
![image](https://github.com/user-attachments/assets/7757deae-d0b7-4bcf-bf63-fa471f587c3a)
### c. Jika berhasil, akan diarahkan pada halaman Login. Dan jika Login berhasil akan diarahkan pada halaman formMahasiswa
![image](https://github.com/user-attachments/assets/d0403215-905b-4509-a390-8fa3f7ae5e8d)
![image](https://github.com/user-attachments/assets/b978f88a-ee95-4bba-a9f6-4238f9657fcf)

## 3. CRUD Mahasiswa
### a. Tampil/Read
![image](https://github.com/user-attachments/assets/d3674719-c4fe-460b-bcef-4e9b0fff3c4f)
### b. Create
![image](https://github.com/user-attachments/assets/bb608786-d10c-496b-801a-cedd5ee500e7)
![image](https://github.com/user-attachments/assets/fe7710c9-a485-4663-9359-615c38a06b6e)
![image](https://github.com/user-attachments/assets/f04f5b5d-4712-4da5-9a1d-481d3986901b)

### c. Update
![image](https://github.com/user-attachments/assets/b5a66804-508d-4788-bd7d-767729925474)
![image](https://github.com/user-attachments/assets/57ea9dbe-461d-46e0-a39f-6970270404b2)

### d. Delete
![image](https://github.com/user-attachments/assets/6203ad9a-a40a-4b0a-bcea-b8cea0dd137e)
![image](https://github.com/user-attachments/assets/f2deb5dd-7db4-45ab-bbec-6a01bcc5d2e0)

### e. Upload 
Buat data pada MS.Excel kemudian simpan dengan extension .csv
Klik button Upload, pilih file .csv
![image](https://github.com/user-attachments/assets/f0c85062-6d6e-42a4-91ae-04a4b2a84dde)
![image](https://github.com/user-attachments/assets/e8904716-2791-4735-9797-a754f3b2f160)

### e. Cetak
Klik button Cetak
![image](https://github.com/user-attachments/assets/26605243-60ce-4e73-93b6-6d61f1c335c9)

# TERIMA KASIH, SELAMAT BEREKSPLOR
