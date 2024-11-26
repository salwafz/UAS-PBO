# UAS PBO
Ujian Akhir Semester PBO, Membuat CRUD Mahasiswa disertai dengan laporan menggunakan JasperReport dan Uploud File berupa file .csv.

# Langkah-langkah :
## 1. Membuat database pada PgAdmin
### a. Buatlah database dengan atribut berikut :
![image](https://github.com/user-attachments/assets/2b612ddd-ce32-4238-bff1-3e579cff3b5b)

## 2. Membuat Projek/Package baru pada Netbenas, dan menyambungkan database menggunakan Persistance.
### a. Masuk netbeans, buat package baru
![image](https://github.com/user-attachments/assets/fa3f4981-191f-497e-a4bc-f2ee2e31bed6)
### b. Kemudian buat file Persistance untuk menyambungkan ke database, klik kanan pada Package > New > Entity Classes from Databases
![image](https://github.com/user-attachments/assets/f03c96e9-db1e-41f4-ab70-065eafdecd08)
### c. Buat koneksi baru pada New Connection
![image](https://github.com/user-attachments/assets/c150463d-3ecb-49bb-85c0-ad544d5073ec)
![image](https://github.com/user-attachments/assets/9bc2bd25-fd0f-4077-8d77-8fc5625e90ee)
Pilih Driver PostgreSQL
### d. Masukkan database anda dan klik next
![image](https://github.com/user-attachments/assets/addc6ebc-55d7-4f84-9e57-16fbfe3c0b73)
### e. Add All pada semua tabel yang ada dalam database
![image](https://github.com/user-attachments/assets/a707cead-a327-4de4-8b2b-3db6354d317b)

## 2. Buat File JFrame Form untuk membuat tampilan GUI, berikut adalah tampilan dan JFrame yang harus dibuat :
### a. Tampilan formMahasiswa
![image](https://github.com/user-attachments/assets/9e46adbf-371b-4349-8895-cec9ab9adece)
Dengan ketentuan :
TextField (Nim, Nama, Alamat, AsalSMA, NamaOrtu)
Button (Simpan, Update, Delete, Exit, Upload, Cetak, Logout)
Table Mouse Clicked (tblHasil)

### b. Tampilan formLogin
![image](https://github.com/user-attachments/assets/38efb9c3-df18-425e-b86c-b4cff1774028)
Dengan Ketentuan :
TextField (Username, Password)
Button (Login)
Text (Create new account)
CheckBox (Show Password)

### c. Tampilan formCreateLogin
![image](https://github.com/user-attachments/assets/262f7490-a32b-45a5-b269-82d71780bf93)
TextField (Username, Password)
Button (Login)
Text (Create new account)
CheckBox (Show Password)

## 3. Buat CRUD pada formMahasiswa
### a. READ/TAMPIL
Masukkan source berikut untuk membuat tampilan awal pada form Mahasiswa
<pre>
  public void tampil() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LatihanSemester3PU");
        EntityManager em = emf.createEntityManager();

        try {
            DefaultTableModel tbnmhs = new DefaultTableModel(new String[]{"NIM", "NAMA", "ALAMAT", "ASALSMA", "NAMAORTU"}, 0);

            List<Mahasiswaa> mahasiswaaList = em.createNamedQuery("Mahasiswaa.findAll", Mahasiswaa.class).getResultList();

//            List<Mahasiswa_1> mahasiswaList = em.createNamedQuery("Mahasiswa.findAll", Mahasiswa_1.class).getResultList();
            for (Mahasiswaa mahasiswaa : mahasiswaaList) {
                tbnmhs.addRow(new Object[]{
                    mahasiswaa.getNim(),
                    mahasiswaa.getNama(),
                    mahasiswaa.getAlamat(),
                    mahasiswaa.getAsalsma(),
                    mahasiswaa.getNamaortu()
                });
            }

            // Atur model tabel ke tabel GUI
            tblHasil.setModel(tbnmhs);
        } catch (Exception ex) {
            Logger.getLogger(Mahasiswaa.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close(); // Tutup EntityManager setelah selesai
        }
    }
</pre>
Sesuaikan dengan database anda dan File Persistance anda.

### b. MEMBUAT FUNGSI PADA BUTTON SIMPAN (CREATE)
Masukkan source berikut untuk memasukkan data pada form Mahasiswa
<pre>
  private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        if (txtNim.getText().equals("") || txtNama.getText().equals("")
                || txtAlamat.getText().equals("") || txtAsalSMA.getText().equals("") || txtNamaOrtu.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Isi semua data");
        } else {
            String nim, nama, alamat, asalSMA, namaOrtu;
            nim = txtNim.getText();
            nama = txtNama.getText();
            alamat = txtAlamat.getText();
            asalSMA = txtAsalSMA.getText();
            namaOrtu = txtNamaOrtu.getText();

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("LatihanSemester3PU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            Mahasiswaa mk = new Mahasiswaa();
            mk.setNim(nim);
            mk.setNama(nama);
            mk.setAlamat(alamat);
            mk.setAsalsma(asalSMA);
            mk.setNamaortu(namaOrtu);

            em.persist(mk);

            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Sukses diinput");
            bersih();
            tampil();

            em.close();
            emf.close();
        }
        tampil();
    }            
</pre>

### c. MEMBUAT FUNGSI PADA BUTTON UPDATE (UPDATE)
<pre>
  private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        if (txtNim.getText().equals("") || txtNama.getText().equals("")
                || txtAlamat.getText().equals("") || txtAsalSMA.getText().equals("") || txtNamaOrtu.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Isi semua data");
        } else {
            String nim, nama, alamat, asalSMA, namaOrtu;
            nim = txtNim.getText();
            nama = txtNama.getText();
            alamat = txtAlamat.getText();
            asalSMA = txtAsalSMA.getText();
            namaOrtu = txtNamaOrtu.getText();

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("LatihanSemester3PU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            Mahasiswaa mk = em.find(Mahasiswaa.class, nim);
            if (mk == null) {
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
            } else {
                mk.setNim(nim);
                mk.setNama(nama);
                mk.setAlamat(alamat);
                mk.setAsalsma(asalSMA);
                mk.setNamaortu(namaOrtu);

                em.getTransaction().commit();
                JOptionPane.showMessageDialog(null, "Data berhasil diupdate");

                em.close();
                emf.close();
                bersih();
                txtNim.setEditable(true);
            }
        }
        tampil();
    }           
</pre>

### d. MEMBUAT FUNGSI PADA BUTTON DELETE (DELETE)
<pre>
  private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LatihanSemester3PU");
        EntityManager em = emf.createEntityManager();

        try {
            // Validasi input
            if (txtNim.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Masukkan Kode Mata Kuliah yang akan dihapus");
            } else {
                // Mulai transaksi
                em.getTransaction().begin();

                // Cari entitas Matakuliah berdasarkan kode mata kuliah
                String nim = txtNim.getText();
                Mahasiswaa mahasiswaa = em.find(Mahasiswaa.class, nim);

                if (mahasiswaa != null) {
                    // Hapus entitas jika ditemukan
                    em.remove(mahasiswaa);

                    // Commit transaksi
                    em.getTransaction().commit();
                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus");

                    // Refresh data pada tampilan
                    tampil();
                } else {
                    JOptionPane.showMessageDialog(null, "Data tidak ditemukan untuk KodeMK: " + nim);
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
        } finally {
            em.close();
        }

        bersih();
        tampil();
    }      
</pre>

### e. MEMBUAT FUNGSI PADA BUTTON EXIT (EXIT)
<pre>
  private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
        System.exit(0);
    } 
</pre>

### f. MEMBUAT FUNGSI PADA BUTTON LOGOUT (LOGOUT)
<pre>
  private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
        formLogin z = new formLogin();
        z.setVisible(true);
        this.dispose();
    }   
</pre>

### g. MEMBUAT FUNGSI PADA BUTTON UPLOAD (UPLOAD)
<pre>
  private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:

        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LatihanSemester3PU");
        EntityManager em = emf.createEntityManager();
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File filePilihan = jfc.getSelectedFile();
            System.out.println("yang dipilih : " + filePilihan.getAbsolutePath());

            try (BufferedReader br = new BufferedReader(new FileReader(filePilihan))) {
                Class.forName(driver);
                String baris;
                String pemisah = ";";

                while ((baris = br.readLine()) != null) {
                    String[] data = baris.split(pemisah);
                    String nim = data[0];
                    String nama = data[1];
                    String alamat = data[2];
                    String asalSMA = data[3];
                    String namaOrtu = data[4];

                    if (!nim.isEmpty() && !nama.isEmpty() && !alamat.isEmpty() && !asalSMA.isEmpty() && !namaOrtu.isEmpty()) {
                        em.getTransaction().begin();

                        Mahasiswaa mk = new Mahasiswaa();
                        mk.setNim(nim);
                        mk.setNama(nama);
                        mk.setAlamat(alamat);
                        mk.setAsalsma(asalSMA);
                        mk.setNamaortu(namaOrtu);

                        em.persist(mk);

                        em.getTransaction().commit();
                        JOptionPane.showMessageDialog(null, "Sukses diinput");
                        tampil();
                    } else {
                        JOptionPane.showMessageDialog(null, "Gagal diinput");
                    }
                }
                em.close();
                emf.close();
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
