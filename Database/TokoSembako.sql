CREATE DATABASE TokoSembako

USE TokoSembako

					--MASTER--

--HADIAH MEMBER--
CREATE TABLE HadiahMember
(
	id_hadiah		VARCHAR		(10)	NOT NULL,
	hadiah			VARCHAR		(50)	NOT NULL,
	stok_hadiah		INT					NOT NULL,
	poin			INT					NOT NULL,

	PRIMARY KEY(id_hadiah)
)

DROP TABLE HadiahMember
SELECT * FROM HadiahMember	

--Insert HadiahMember
CREATE PROCEDURE sp_InsertHadiahMember
	@id_hadiah		VARCHAR		(10),
	@hadiah			VARCHAR		(50),
	@stok_hadiah	INT				,
	@poin			INT				
AS
BEGIN
	INSERT INTO HadiahMember
	VALUES(@id_hadiah,@hadiah,@stok_hadiah,@poin)
END

DROP PROCEDURE sp_InsertHadiahMember

--SP Update HadiahMember
CREATE PROCEDURE sp_UpdateHadiahMember
	@id_hadiah		VARCHAR		(10),
	@hadiah			VARCHAR		(50),
	@stok_hadiah	INT				,
	@poin			INT				
AS
BEGIN
	UPDATE HadiahMember SET
	hadiah			= @hadiah,
	stok_hadiah		= @stok_hadiah,
	poin			= @poin
	WHERE id_hadiah = @id_hadiah
END

--SP Delete HadiahMember
CREATE PROCEDURE sp_DeleteHadiahMember
	@id_hadiah			VARCHAR		(10)
AS
BEGIN
	DELETE FROM Hadiah WHERE id_hadiah = @id_hadiah
END

--SP Cari HadiahMember
CREATE PROCEDURE sp_SearchHadiahMember
	@id_hadiah	VARCHAR		(10)
AS
BEGIN
	SELECT * FROM HadiahMember WHERE id_hadiah = @id_hadiah
END

---

--SP Load HadiahMember
CREATE PROCEDURE sp_LoadHadiahMember
AS
BEGIN
	SELECT * FROM HadiahMember
END

--SP Load HadiahMember Base Nama
CREATE PROCEDURE sp_LoadHadiahMemberBaseNama
	@nama_hadiah		VARCHAR		(30)
AS
BEGIN
	SELECT * FROM HadiahMember WHERE nama_hadiah LIKE @nama_hadiah
END
---------------------------------------------------------

--MEMBER--
CREATE TABLE Member
(
	id_member		VARCHAR		(10)	NOT NULL,
	nama			VARCHAR		(50)	NOT NULL,
	alamat			VARCHAR		(100)	NOT NULL,
	Jeniskelamin	VARCHAR		(13)	NOT NULL,
	noTelp			VARCHAR		(13)	NOT NULL,
	poin			INT					NOT NULL,

	PRIMARY KEY(id_member)
)

   SELECT * FROM Member

   DROP TABLE Member

----SP Member---
SELECT * FROM Member

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE sp_Insertmember
	@id_member			VARCHAR		(10),
	@nama				VARCHAR		(50),
	@alamat				VARCHAR		(100),
	@Jeniskelamin		VARCHAR		(13),
	@noTelp				VARCHAR		(13),
	@poin				INT			
AS
BEGIN
	INSERT INTO Member
	VALUES(@id_member,@nama,@alamat,@Jeniskelamin,@noTelp,@poin)
END

DROP PROCEDURE sp_InsertMember


--SP Update Member
CREATE PROCEDURE sp_UpdateMember
	@id_member		VARCHAR		(10),
	@nama			VARCHAR		(50),
	@alamat			VARCHAR		(100),
	@Jeniskelamin	VARCHAR		(13),
	@noTelp			VARCHAR		(13),
	@poin				INT			
		
AS
BEGIN
	UPDATE Member SET
	nama	= @nama,
	alamat	 = @alamat,
	Jeniskelamin = @Jeniskelamin,
	noTelp = @noTelp,
	poin = @poin
	WHERE id_member = @id_member
END

DROP PROCEDURE sp_UpdateMember

--SP Delete Member
CREATE PROCEDURE sp_DeleteMember
	@id_member	VARCHAR		(10)
AS
BEGIN
	DELETE FROM Member WHERE id_member = @id_member
END

DROP PROCEDURE sp_DeleteMember

--SP Cari Member
CREATE PROCEDURE sp_SearchMember
	@id_member	VARCHAR		(10)
AS
BEGIN
	SELECT * FROM Member WHERE id_member = @id_member
END

DROP PROCEDURE sp_SearchMember
---------------------------------------------------------

--JABATAN--
CREATE TABLE Jabatan
(
	id_jabatan		VARCHAR		(10)	NOT NULL,
	nama_jabatan	VARCHAR		(50)	NOT NULL

	PRIMARY KEY(id_jabatan)

)


SELECT * FROM Jabatan

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE sp_InsertJabatan
	@id_jabatan			VARCHAR		(10),
	@nama_jabatan		VARCHAR		(50)		
AS
BEGIN
	INSERT INTO Jabatan
	VALUES(@id_jabatan,@nama_jabatan)
END

DROP PROCEDURE sp_InsertJabatan

--SP Update Jabatan
CREATE PROCEDURE sp_UpdateJabatan
	@id_jabatan		VARCHAR		(10),
	@nama_jabatan			VARCHAR		(50)
	
AS
BEGIN
	UPDATE Jabatan SET
	@nama_jabatan = @nama_jabatan
	WHERE id_jabatan = @id_jabatan
END

DROP PROCEDURE sp_UpdateJabatan

--SP Delete Jabatan
CREATE PROCEDURE sp_DeleteJabatan
	@id_jabatan	VARCHAR		(10)
AS
BEGIN
	DELETE FROM Jabatan WHERE id_jabatan = @id_jabatan
END

DROP PROCEDURE sp_DeleteJabatan

--SP Cari Jabatan
CREATE PROCEDURE sp_SearchJabatan
	@id_jabatan	VARCHAR		(10)
AS
BEGIN
	SELECT * FROM Jabatan WHERE id_jabatan = @id_jabatan
END

DROP PROCEDURE sp_SearchJabatan
--------------------------------------------------------------

--PENGGUNA JABATAN--
CREATE TABLE PenggunaJabatan
(
	id_penggunajabatan		VARCHAR		(10)		NOT NULL,
	id_pengguna				VARCHAR		(10)		NOT NULL,
	jabatan					VARCHAR		(10)		NOT NULL,

	PRIMARY KEY(id_PenggunaJabatan),
	FOREIGN KEY (id_pengguna) REFERENCES  pengguna(id_pengguna) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (jabatan) REFERENCES  jabatan (id_jabatan)

)

SELECT * FROM PenggunaJabatan

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE sp_InsertPenggunaJabatan
	@id_penggunajabatan	VARCHAR		(10),
	@id_pengguna		VARCHAR		(10),
	@jabatan			VARCHAR		(50)		
AS
BEGIN
	INSERT INTO PenggunaJabatan
	VALUES(@id_penggunajabatan,@id_pengguna,@jabatan)
END

DROP PROCEDURE sp_InsertPenggunaJabatan

--SP Update PenggunaJabatan
CREATE PROCEDURE sp_UpdatePenggunaJabatan
	@id_penggunajabatan		VARCHAR		(10),
	@id_pengguna			VARCHAR		(10),
	@jabatan				VARCHAR		(50)
	
AS
BEGIN
	UPDATE PenggunaJabatan SET
	@id_pengguna = @id_pengguna,
	@jabatan = @jabatan
	WHERE id_penggunajabatan = @id_penggunajabatan
END

DROP PROCEDURE sp_UpdatePenggunaJabatan

--SP Delete PenggunaJabatan
CREATE PROCEDURE sp_DeletePenggunaJabatan
	@id_penggunajabatan	VARCHAR		(10)
AS
BEGIN
	DELETE FROM PenggunaJabatan WHERE id_penggunajabatan = @id_penggunajabatan
END

DROP PROCEDURE sp_DeletePenggunaJabatan

--SP Cari PenggunaJabatan
CREATE PROCEDURE sp_SearchPenggunaJabatan
	@id_penggunajabatan	VARCHAR		(10)
AS
BEGIN
	SELECT * FROM PenggunaJabatan WHERE @id_penggunajabatan = @id_penggunajabatan
END

DROP PROCEDURE sp_SearchPenggunaJabatan
--------------------------------------------------------------

--PENGGUNA--
CREATE TABLE Pengguna
(
	id_pengguna		VARCHAR		(10)	PRIMARY KEY		NOT NULL,
	id_jabatan		VARCHAR		(10)					NOT NULL,
	nama			VARCHAR		(50)					NOT NULL,
	noTelp			VARCHAR		(13)					NOT NULL,
	alamat			VARCHAR		(100)					NOT NULL,
	JenisKelamin	VARCHAR		(13)					NOT NULL,
	username		VARCHAR		(50)					NOT NULL,
	pass			VARCHAR		(50)					NOT NULL

	FOREIGN KEY (id_jabatan) REFERENCES  Jabatan(id_jabatan)
	ON UPDATE CASCADE ON DELETE CASCADE,
)



SELECT * FROm  Barang
INSERT INTO Pengguna VALUES
('PGN01','JBT1','Aldo Fernando','088802344916','Jalan Paledang','Laki Laki','aldofernando17','fernando17')
--sp Pengguna
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE sp_InsertPengguna
	@id_pengguna	VARCHAR		(10),
	@id_jabatan		VARCHAR		(10),
	@nama			VARCHAR		(50),
	@noTelp			VARCHAR		(13),
	@alamat			VARCHAR		(100),
	@JenisKelamin	VARCHAR		(13),
	@username		VARCHAR		(50),
	@pass			VARCHAR		(50)
AS
BEGIN
	INSERT INTO Pengguna
	VALUES(@id_pengguna,@id_jabatan,@nama,@noTelp,@alamat,@JenisKelamin,@username,@pass)
END

--SP Update Pengguna
CREATE PROCEDURE sp_UpdatePengguna
	@id_pengguna	VARCHAR		(10),
	@id_jabatan		VARCHAR		(10),
	@nama			VARCHAR		(50),
	@noTelp			VARCHAR		(13),
	@alamat			VARCHAR		(100),
	@JenisKelamin	VARCHAR		(13),
	@username		VARCHAR		(50),
	@pass			VARCHAR		(50)
AS
BEGIN
	UPDATE Pengguna SET
	id_jabatan = @id_jabatan,
	nama = @nama,
	noTelp = @noTelp,
	alamat = @alamat,
	JenisKelamin = @JenisKelamin,
	username = @username,
	pass = @pass
	WHERE @id_pengguna = @id_pengguna
END

--SP Delete Pengguna
CREATE PROCEDURE sp_DeletePengguna
	@id_pengguna			VARCHAR		(10)
AS
BEGIN
	DELETE FROM pengguna WHERE id_pengguna = @id_pengguna
END

--SP Cari Pengguna
CREATE PROCEDURE sp_SearchPengguna
	@id_pengguna	VARCHAR		(10)
AS
BEGIN
	SELECT * FROM pengguna WHERE id_pengguna = @id_pengguna
END
---------------------------------------------------------

--SATUAN--
CREATE TABLE Satuan
(
	id_satuan		VARCHAR		(10)		NOT NULL,
	satuan			VARCHAR		(50)		NOT NULL,

	PRIMARY KEY(id_satuan)
)

SELECT * FROM Satuan

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE sp_InsertSatuan
	@id_satuan		VARCHAR		(10),
	@satuan			VARCHAR		(50)		
AS
BEGIN
	INSERT INTO Satuan
	VALUES(@id_satuan,@satuan)
END

DROP PROCEDURE sp_InsertSatuan

--SP Update satuan
CREATE PROCEDURE sp_UpdateSatuan
	@id_satuan		VARCHAR		(10),
	@satuan			VARCHAR		(50)
	
AS
BEGIN
	UPDATE Satuan SET
	@satuan = @satuan
	WHERE @id_satuan = @id_satuan
END

DROP PROCEDURE sp_UpdateSatuan

--SP Delete satuan
CREATE PROCEDURE sp_DeleteSatuan
	@id_satuan	VARCHAR		(10)
AS
BEGIN
	DELETE FROM Satuan WHERE @id_satuan = @id_satuan
END

DROP PROCEDURE sp_DeleteSatuan

--SP Cari satuan
CREATE PROCEDURE sp_SearchSatuan
	@id_satuan	VARCHAR		(10)
AS
BEGIN
	SELECT * FROM Satuan WHERE @id_satuan = @id_satuan
END

DROP PROCEDURE sp_SearchSatuan
-----------------------------------------------------------

--PROMO--
CREATE TABLE Promo
(
	id_promo			VARCHAR		(10)PRIMARY KEY		NOT NULL,
	nama_promo			VARCHAR		(50)				NOT NULL,
	discount			INT								NOT NULL,
	tanggal_mulai		DATE							NULL,
	tanggal_berakhir	DATE							NULL
)
DROP TABLE Promo
SELECT *FROM Promo

--Insert Promo
CREATE PROCEDURE sp_InsertPromo
	@id_promo			VARCHAR		(10),
	@nama_promo			VARCHAR		(50),
	@discount			INT				,
	@tanggal_mulai		DATE			,
	@tanggal_berakhir	DATE		
AS
BEGIN
	INSERT INTO Promo
	VALUES(@id_promo,@nama_promo,@discount,@tanggal_mulai,@tanggal_berakhir)
END

--SP Update Promo 
CREATE PROCEDURE sp_UpdatePromo
	@id_promo			VARCHAR		(10),
	@nama_promo			VARCHAR		(50),
	@discount			INT				,
	@tanggal_mulai		DATE			,
	@tanggal_berakhir	DATE		
AS
BEGIN
	UPDATE Promo SET
	id_promo = @id_promo,
	nama_promo = @nama_promo,
	tanggal_mulai = @tanggal_mulai,
	tanggal_berakhir = @tanggal_berakhir
	WHERE id_promo = @id_promo
END

--SP Delete Promo
CREATE PROCEDURE sp_DeletePromo
	@id_promo			VARCHAR		(10)
AS
BEGIN
	DELETE FROM Promo WHERE id_promo = @id_promo
END

--SP Cari Promo
CREATE PROCEDURE sp_SearchPromo
	@id_promo			VARCHAR		(10)
AS
BEGIN
	SELECT * FROM Promo WHERE id_promo = @id_promo
END 

---
--SP Load Promo
CREATE PROCEDURE sp_LoadPromo
AS
BEGIN
	SELECT * FROM Promo
END

--SP Load Promo Base Nama
CREATE PROCEDURE sp_LoadPromoBaseNama
	@nama_promo			VARCHAR		(30)
AS
BEGIN
	SELECT * FROM Promo WHERE nama_promo LIKE @nama_promo
END
------------------------------------------------------------------

--BARANG--
CREATE TABLE Barang
(
	id_barang		VARCHAR		(10)	PRIMARY KEY	NOT NULL,
	id_merk			VARCHAR		(10)				NOT NULL,
	nama			VARCHAR		(50)				NOT NULL,
	stok			INT								NOT NULL,
	harga_jual		INT								NOT NULL

	FOREIGN KEY (id_merk) REFERENCES Merk(id_merk)
	ON UPDATE CASCADE ON DELETE CASCADE
)

DROP TABLE Barang

SELECT * FROM Barang

--SP BARANG
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE sp_InsertBarang
	@id_barang			VARCHAR		(10),
	@id_merk			VARCHAR		(10),
	@nama				VARCHAR		(50),
	@stok				INT				,
	@harga_jual			MONEY			
AS
BEGIN
	INSERT INTO Barang
	VALUES(@id_barang,@id_merk,@nama,@stok,@harga_jual)
END

 DROP PROC sp_InsertBarang

--SP Update Barang
CREATE PROCEDURE sp_UpdateBarang
	@id_barang			VARCHAR		(10),
	@id_merk			VARCHAR		(10),
	@nama				VARCHAR		(50),
	@stok				INT				,
	@harga_jual			MONEY			
AS
BEGIN
	UPDATE Barang SET
	id_barang = @id_barang,
	id_merk = @id_merk,
	nama = @nama,
	stok = @stok,
	harga_jual = @harga_jual

	WHERE id_barang = @id_barang
END

DROP PROC sp_UpdateBarang 

--SP Delete barang
CREATE PROCEDURE sp_DeleteBarang
	@id_barang			VARCHAR		(10)
AS
BEGIN
	DELETE FROM Barang WHERE id_barang = @id_barang
END

DROP PROC sp_DeleteBarang 

--SP Cari Barang
CREATE PROCEDURE sp_SearchBarang
	@id_barang			VARCHAR		(10)
AS
BEGIN
	SELECT * FROM Barang WHERE id_barang = @id_barang
END

DROP PROC sp_SearchBarang
---------------------------------------------------------------

--MERK--
CREATE TABLE Merk
(
	id_merk	VARCHAR		(10)		NOT NULL,
	merk	VARCHAR		(50)		NOT NULL,

	PRIMARY KEY(id_merk)
)

SELECT * FROM Merk

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE sp_InsertMerk
	@id_merk	VARCHAR		(10),
	@merk		VARCHAR		(50)		
AS
BEGIN
	INSERT INTO Merk
	VALUES(@id_merk,@merk)
END

DROP PROCEDURE sp_InsertMerk

--SP Update Merk
CREATE PROCEDURE sp_UpdateMerk
	@id_merk	VARCHAR		(10),
	@merk		VARCHAR		(50)
	
AS
BEGIN
	UPDATE Merk SET
	@merk = @merk
	WHERE @id_merk = @id_merk
END

DROP PROCEDURE sp_UpdateMerk

--SP Delete Merk
CREATE PROCEDURE sp_DeleteMerk
	@id_Merk	VARCHAR		(10)
AS
BEGIN
	DELETE FROM Merk WHERE id_merk = @id_Merk
END

DROP PROCEDURE sp_DeleteMerk

--SP Cari Merk
CREATE PROCEDURE sp_SearchMerk
	@id_merk	VARCHAR		(10)
AS
BEGIN
	SELECT * FROM Merk WHERE @id_merk = @id_merk
END

DROP PROCEDURE sp_SearchMerk
---------------------------------------------------------

--SUPPLIER--
CREATE TABLE Supplier
(
	id_supplier		VARCHAR		(10)	NOT NULL,
	nama			VARCHAR		(50)	NOT NULL,
	alamat			VARCHAR		(100)	NOT NULL,
	noTelp			VARCHAR		(13)	NOT NULL,
	email			VARCHAR		(50)	NOT NULL,

	PRIMARY KEY(id_supplier	)
)
SELECT * FROM Supplier

--SP Insert Supplier
CREATE PROCEDURE sp_InsertSupplier
	@id_supplier	VARCHAR		(10),
	@nama			VARCHAR		(50),
	@alamat			VARCHAR		(100),
	@noTelp			VARCHAR		(13),
	@email			VARCHAR		(50)
AS
BEGIN
	INSERT INTO Supplier
	VALUES(@id_supplier,@nama,@alamat,@noTelp,@email)
END

--SP Update Supplier
CREATE PROCEDURE sp_UpdateSupplier
	@id_supplier	VARCHAR		(10),
	@nama			VARCHAR		(50),
	@alamat			VARCHAR		(100),
	@noTelp			VARCHAR		(13),
	@email			VARCHAR		(50)
AS
BEGIN
	UPDATE Supplier SET
	nama = @nama,
	alamat = @alamat,
	noTelp = @noTelp,
	email = @email
	WHERE id_supplier = @id_supplier
END

--SP Delete Supplier
CREATE PROCEDURE sp_DeleteSupplier
	@id_supplier	VARCHAR		(10)
AS
BEGIN
	DELETE FROM Supplier WHERE id_supplier = @id_supplier
END

--SP Cari Supplier
CREATE PROCEDURE sp_SearchSupplier
	@id_supplier	VARCHAR		(10)
AS
BEGIN
	SELECT * FROM Supplier WHERE id_supplier = @id_supplier
END

---

--SP Load Supplier
CREATE PROCEDURE sp_LoadSupplier
AS
BEGIN
	SELECT * FROM Supplier
END


--SP Load Supplier
CREATE PROCEDURE sp_LoadSupplierBaseNama
	@nama			VARCHAR		(30)
AS
BEGIN
	SELECT * FROM Supplier WHERE nama LIKE @nama
END
---------------------------------------------------

				--TRANSAKSI--

--TABEL PENJUALAN--
CREATE TABLE TransaksiPenjualan
(
	trs_id			varchar	(50) NOT NULL PRIMARY KEY,
	trs_tanggal		date		 NULL,
	id_member		varchar	(10) NOT NULL,
	trs_totalbayar	money		 NULL,
	trs_statusbayar int			 NULL

	FOREIGN KEY (id_member) REFERENCES Member (id_member) ON UPDATE CASCADE ON DELETE CASCADE
)
SELECT * FROM TransaksiPenjualan

--TABEL DETAIL PENJUALAN--
CREATE TABLE DetailPenjualan
(
	trs_id		varchar(50) NOT NULL,
	id_barang	varchar(10) NOT NULL,
	trs_qty		int			NOT NULL

	
	FOREIGN KEY (trs_id) REFERENCES TransaksiPenjualan (trs_id) ON UPDATE CASCADE ON DELETE CASCADE
	
)
SELECT * FROM DetailPenjualan

--INPUT PENJUALAN--
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_InputTPenjualan]
	@trs_id			 varchar(50) ,
	@trs_tanggal	 date ,
	@id_member		 varchar(10) ,
	@trs_totalbayar  money ,
	@trs_statusbayar int 
AS
BEGIN
	INSERT INTO TransaksiPenjualan VALUES (
		@trs_id,
		@trs_tanggal,
		@id_member,
		@trs_totalbayar,
		@trs_statusbayar
)
END
GO

--INPUT DETAIL PENJUALAN--
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_InputDetailPenjualan]
	@trs_id		varchar(50) ,
	@id_barang	varchar(10) ,
	@trs_qty	int 
AS
BEGIN
	INSERT INTO DetailPenjualan VALUES (
		@trs_id,
		@id_barang,
		@trs_qty
)
END
GO

--TRANSAKSI PENJUALAN--
SELECT TransaksiPenjualan.trs_id			AS ID, 
	   TransaksiPenjualan.trs_tanggal		AS Tanggal, 
	   Barang.id_barang						AS NamaProduk, 
	   DetailPenjualan.trs_qty				AS Quantity, 
	   Barang.harga_jual					AS HargaSatuan, 
	   TransaksiPenjualan.trs_totalbayar	AS TotalBayar
FROM TransaksiPenjualan INNER JOIN DetailPenjualan 
ON TransaksiPenjualan.trs_id = DetailPenjualan.trs_id
INNER JOIN Barang ON Barang.id_barang = DetailPenjualan.id_barang

SELECT SUM(trs_totalbayar) AS Total FROM TransaksiPenjualan



--TABEL PENUKARAN HADIAH--
CREATE TABLE PenukaranHadiah
(
	trs_idPH	varchar	(50) NOT NULL PRIMARY KEY,
	trs_tanggal date		 NULL,
	id_pengguna varchar	(10) NOT NULL,
	id_member	varchar	(10) NOT NULL,
	trs_poin	int			 NOT NULL

	FOREIGN KEY (id_pengguna)	REFERENCES Pengguna (id_pengguna) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (id_member)		REFERENCES Member	(id_member) ON UPDATE CASCADE ON DELETE CASCADE
)

DROP TABLE PenukaranHadiah


--TABEL DETAIL PENUKARAN HADIAH--
CREATE TABLE DetailPenukaranHadiah
(
	trs_idPH	varchar	(10) NOT NULL,
	id_hadiah	varchar	(10) NOT NULL,
	jumlah		int			 NOT NULL,
	poin		int			 NOT NULL

	
	FOREIGN KEY (trs_idPH)	REFERENCES PenukaranHadiah  (trs_idPH),
	FOREIGN KEY (id_hadiah) REFERENCES HadiahMember		(id_hadiah) ON UPDATE CASCADE ON DELETE CASCADE
)

DROP TABLE DetailPenukaranHadiah

--sp detail--
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_InputDetailPenukaranHadiah]
	@trs_idPH	varchar	(50) ,
	@id_hadiah	varchar	(10) ,
	@jumlah		int,
	@poin		int
AS
BEGIN
	INSERT INTO DetailPenukaranHadiah VALUES (
		@trs_idPH,
		@id_hadiah,
		@jumlah,
		@poin)
END
GO

--TABEL SUPPLIER--
CREATE TABLE TransaksiSupplier(
   trs_idSP			varchar	(10) NOT NULL,
   id_pengguna		varchar	(10) NOT NULL,
   id_supplier		varchar	(10) NOT NULL,
   total			int			 NOT NULL,
   tgl_transaksi	date		 NOT NULL,

   Primary key	(trs_idSP),
   FOREIGN KEY	(id_pengguna) REFERENCES  Pengguna	(id_pengguna) ON UPDATE CASCADE ON DELETE CASCADE,
   FOREIGN KEY	(id_supplier) REFERENCES  Supplier	(id_supplier) ON UPDATE CASCADE ON DELETE CASCADE
)
CREATE TABLE DetailTransSupplier(
   id_detail	varchar	(10) NOT NULL,
   trs_idSP		varchar	(10) NOT NULL,
   id_barang	varchar	(10) NOT NULL,
   nama_barang	varchar	(50) NOT NULL,
   jumlah		int			 NOT NULL,

   PRIMARY KEY	(id_detail),
   FOREIGN KEY	(trs_idSP) REFERENCES TransaksiSupplier(trs_idSP) ON UPDATE CASCADE ON DELETE CASCADE,
   FOREIGN KEY	(id_barang) REFERENCES Barang(id_barang) ON UPDATE CASCADE ON DELETE CASCADE
)
Select *From TransaksiSupplier
SELECT TransaksiSupplier.trs_idSP		AS ID, 
	   TransaksiSupplier.tgl_transaksi	AS Tanggal, 
	   TransaksiSupplier.id_supplier	AS ID_Supplier, 
	   DetailTransSupplier.id_barang	AS ID_Barang, 
	   DetailTransSupplier.jumlah		AS Quantity,  
	   TransaksiSupplier.total			AS Total
FROM TransaksiSupplier INNER JOIN DetailTransSupplier 
ON TransaksiSupplier.trs_idSP = DetailTransSupplier.trs_idSP
INNER JOIN Barang ON Barang.id_barang = DetailTransSupplier.id_barang
WHERE YEAR(tgl_transaksi) IN (2022) AND MONTH(tgl_transaksi) IN (7)

Select * from DetailTransSupplier
CREATE PROCEDURE sp_InputDetailTransaksiSupplier
   @id_detail	varchar	(10) ,
   @trs_idSP	varchar	(10) ,
   @id_barang	varchar	(10) ,
   @nama_barang varchar	(50) ,
   @jumlah		int  
AS
BEGIN
    INSERT INTO DetailTransSupplier VALUES(@id_detail,@trs_idSP,@id_barang,@nama_barang,@jumlah)
END


----------------------------------------------------------------------------------------------------------------

/*VIEW & INDEX*/

--TABEL HADIAHMEMBER--
CREATE VIEW viewHadiahMember
WITH SCHEMABINDING
AS
SELECT id_hadiah, hadiah, stok_hadiah, poin FROM dbo.HadiahMember
GO
CREATE UNIQUE CLUSTERED INDEX UCID ON viewHadiahMember (id_hadiah)
CREATE NONCLUSTERED INDEX NCDESC ON viewHadiahMember (hadiah)

SET STATISTICS IO ON;
GO
SELECT * FROM viewHadiahMember WHERE hadiah LIKE 'payung%' /*menemukan nilai yang dimulai dari kata ad*/
SET STATISTICS IO OFF;
GO

--TABEL MEMBER--
CREATE VIEW viewMember
WITH SCHEMABINDING
AS
SELECT id_member, nama, alamat, Jeniskelamin, noTelp, poin FROM dbo.Member
GO
CREATE UNIQUE CLUSTERED INDEX UCID ON viewMember (id_member)
CREATE NONCLUSTERED INDEX NCDESC ON viewMember (nama)

SET STATISTICS IO ON;
GO
SELECT * FROM viewMember WHERE Jeniskelamin LIKE 'Perempuan%'
SET STATISTICS IO OFF;
GO

--TABEL JABATAN--
CREATE VIEW viewJabatan
WITH SCHEMABINDING
AS
SELECT id_jabatan, nama_jabatan FROM dbo.Jabatan
GO
CREATE UNIQUE CLUSTERED INDEX UCID ON viewJabatan (idjabatan)
CREATE NONCLUSTERED INDEX NCDESC ON viewJabatan (jabatan)

SET STATISTICS IO ON;
GO
SELECT * FROM viewJabatan WHERE nama LIKE 'Kasir%'
SET STATISTICS IO OFF;
GO

--TABEL PENGGUNA JABATAN--
CREATE VIEW viewPenggunaJabatan
WITH SCHEMABINDING
AS
SELECT id_penggunajabatan, id_pengguna, jabatan FROM dbo.PenggunaJabatan
GO
CREATE UNIQUE CLUSTERED INDEX UCID ON viewPenggunaJabatan (id_PenggunaJabatan)
CREATE NONCLUSTERED INDEX NCDESC ON viewPenggunaJabatan (id_pengguna)

SET STATISTICS IO ON;
GO
SELECT * FROM viewPenggunaJabatan WHERE jabatan LIKE 'Kasir%'
SET STATISTICS IO OFF;
GO

--TABEL PENGGUNA--
CREATE VIEW viewPengguna
WITH SCHEMABINDING
AS
SELECT id_pengguna, id_jabatan, nama, noTelp, alamat, JenisKelamin, username, pass FROM dbo.Pengguna
GO
CREATE UNIQUE CLUSTERED INDEX UCID ON viewPengguna (id_pengguna)
CREATE NONCLUSTERED INDEX NCDESC ON viewPengguna (id_jabatan)

SET STATISTICS IO ON;
GO
SELECT * FROM viewPengguna WHERE id_pengguna LIKE 'Ad%'
SET STATISTICS IO OFF;
GO

--TABEL SATUAN--
CREATE VIEW viewSatuan
WITH SCHEMABINDING
AS
SELECT id_satuan, satuan FROM dbo.Satuan
GO
CREATE UNIQUE CLUSTERED INDEX UCID ON viewSatuan (id_satuan)
CREATE NONCLUSTERED INDEX NCDESC ON viewSatuan (satuan)

SET STATISTICS IO ON;
GO
SELECT * FROM viewSatuan WHERE satuan LIKE 'pcs%'
SET STATISTICS IO OFF;
GO

--TABEL PROMO--
CREATE VIEW viewPromo
WITH SCHEMABINDING
AS
SELECT id_promo, nama_promo, discount, tanggal_mulai, tanggal_berakhir FROM dbo.Promo
GO
CREATE UNIQUE CLUSTERED INDEX UCID ON viewPromo (id_promo)
CREATE NONCLUSTERED INDEX NCDESC ON viewPromo (nama_promo)

SET STATISTICS IO ON;
GO
SELECT * FROM viewPromo WHERE id_promo LIKE 'Adm%'
SET STATISTICS IO OFF;
GO


--TABEL BARANG--
CREATE VIEW viewBarang
WITH SCHEMABINDING
AS
SELECT id_barang, id_merk, nama, stok, harga_jual FROM dbo.Barang
GO
CREATE UNIQUE CLUSTERED INDEX UCID ON viewBarang (id_barang)
CREATE NONCLUSTERED INDEX NCDESC ON viewBarang (id_merk)

SET STATISTICS IO ON;
GO
SELECT * FROM viewBarang WHERE stok LIKE '10%'
SET STATISTICS IO OFF;
GO

--TABEL MERK--
CREATE VIEW viewMerk
WITH SCHEMABINDING
AS
SELECT id_merk, merk FROM dbo.Merk
GO
CREATE UNIQUE CLUSTERED INDEX UCID ON viewMerk (id_merk)
CREATE NONCLUSTERED INDEX NCDESC ON viewMerk (merk)

SET STATISTICS IO ON;
GO
SELECT * FROM viewMerk WHERE merk LIKE 'Li%'
SET STATISTICS IO OFF;
GO

--TABEL SUPPLIER--
CREATE VIEW viewSupplier
WITH SCHEMABINDING
AS
SELECT id_supplier, nama, alamat, noTelp, email FROM dbo.Supplier
GO
CREATE UNIQUE CLUSTERED INDEX UCID ON viewSupplier (id_supplier)
CREATE NONCLUSTERED INDEX NCDESC ON viewSupplier (nama)

SET STATISTICS IO ON;
GO
SELECT * FROM viewSupplier WHERE id_supplier LIKE 'SPL%'
SET STATISTICS IO OFF;
GO

--TRANSAKSI PENUKARAN HADIAH--
CREATE VIEW viewPenukaranHadiah
AS
SELECT tbTransaksiPenjualan.trs_id AS ID, 
	   tbTransaksiPenjualan.trs_tanggal AS Tanggal, 
	   tbProduct.prdct_nama AS NamaProduk, 
	   tbDetailPenjualan.trs_qty AS Quantity, 
	   tbProduct.prdct_hargajual AS HargaSatuan, 
	   tbTransaksiPenjualan.trs_totalbayar AS TotalBayar
FROM tbTransaksiPenjualan INNER JOIN tbDetailPenjualan 
ON tbTransaksiPenjualan.trs_id = tbDetailPenjualan.trs_id
INNER JOIN tbProduct ON tbProduct.prdct_id = tbDetailPenjualan.prdct_id
GO
SELECT * FROM viewPenukaranHadiah WHERE MONTH(Tanggal) LIKE '7%' AND YEAR(Tanggal) LIKE '2022%'

--TRANSAKSI SUPPLIER--
CREATE VIEW viewSupplier
AS
SELECT tbTransaksiSupplier.pembelian_id AS ID, 
	   tbTransaksiPembelian.pembelian_tanggal AS Tanggal, 
	   tbTransaksiPembelian.sp_nama AS NamaSupplier, 
	   tbDetailPembelian.prdct_nama AS NamaProduk, 
	   tbDetailPembelian.pembelian_qty AS Quantity,
	   tbDetailPembelian.pembelian_hargasatuan AS HargaSatuan,  
	   tbTransaksiPembelian.pembelian_totalbayar AS TotalBayar
FROM tbTransaksiPembelian INNER JOIN tbDetailPembelian 
ON tbTransaksiPembelian.pembelian_id = tbDetailPembelian.pembelian_id
GO

SELECT * FROM viewTPembelian WHERE MONTH(Tanggal) LIKE '7%' AND YEAR(Tanggal) LIKE '2022%'

SELECT * FROM TransaksiSupplier

--TRANSAKSI PENJUALAN--
CREATE VIEW viewTPenjualan
AS
SELECT tbTransaksiPenjualan.trs_id AS ID, 
	   tbTransaksiPenjualan.trs_tanggal AS Tanggal, 
	   tbProduct.prdct_nama AS NamaProduk, 
	   tbDetailPenjualan.trs_qty AS Quantity, 
	   tbProduct.prdct_hargajual AS HargaSatuan, 
	   tbTransaksiPenjualan.trs_totalbayar AS TotalBayar
FROM tbTransaksiPenjualan INNER JOIN tbDetailPenjualan 
ON tbTransaksiPenjualan.trs_id = tbDetailPenjualan.trs_id
INNER JOIN tbProduct ON tbProduct.prdct_id = tbDetailPenjualan.prdct_id
GO

SELECT * FROM viewTPenjualan WHERE MONTH(Tanggal) LIKE '7%' AND YEAR(Tanggal) LIKE '2022%'



--------------------------------------------------------------------------------------------------------


/*TRIGGER*/

CREATE TRIGGER trg_TambahStokBarang
ON Barang
FOR INSERT
AS
	UPDATE P SET
	stok =(P.stok + T.jumlah)
	FROM Barang AS P INNER JOIN DetailTransSupplier AS T
	ON P.id_barang=T.id_barang
GO

DROP TRIGGER trg_TambahStokBarang

/*Select * From DetailTransSupplier*/


CREATE TRIGGER trg_KurangStokBarang
ON Barang
FOR INSERT
AS
	UPDATE P SET
	stok =(P.stok - T.trs_qty)
	FROM Barang AS P INNER JOIN DetailPenjualan AS T
	ON P.id_barang=T.id_barang
GO





-----------------------------------------------------------------------------------------------------------------------------------------------------------

/*PIVOT TABLE*/

--PIVOT PENGGUNA--

CREATE VIEW viewPivotPengguna
AS
SELECT * FROM (
	SELECT usr_id, lvl_id FROM tbUser
)t
PIVOT(
	COUNT (usr_id)
	FOR lvl_id IN(
	[ROLE001] ,
	[ROLE002] ,
	[ROLE003] ,
	[ROLE004] 
	)
)AS pivot_tbUser
GO

SELECT * FROM viewPivotPengguna

SELECT * FROM tbUser
SELECT * FROM tbProduct

SET STATISTICS IO ON

--STATUS PRODUCT--

CREATE VIEW viewPivotSupplier
AS
SELECT * FROM (
	SELECT prdct_id, prdct_status FROM tbProduct
)t
PIVOT(
	COUNT (prdct_id)
	FOR prdct_status IN(
	[0] ,
	[1] 
	)
)AS pivot_tbStatusProduct
GO

SELECT * FROM viewPivotStatusProduct
SELECT * FROM tbTransaksiPenjualan
SELECT * FROM tbTransaksiPembelian

SELECT SUM(trs_totalbayar) FROM tbTransaksiPenjualan WHERE trs_statusbayar = '1'


USE AGEN

SET STATISTICS IO ON;
GO  
SELECT usr_id, usr_namalengkap, lvl_id FROM tbUser
WHERE lvl_id = 'ROLE003'
GO
SET STATISTICS IO OFF;  
GO

SELECT * FROM tbUser

CREATE CLUSTERED INDEX UCID ON tbUser (usr_id)