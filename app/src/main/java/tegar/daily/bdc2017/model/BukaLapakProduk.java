package tegar.daily.bdc2017.model;

/**
 * Created by Acer on 5/20/2017.
 */

public class BukaLapakProduk {
    String idproduk;
    String gambar;
    String namaproduk;
    String namalapak;
    String hargaasli;
    String hargadiskon;
    String feedback;
    boolean diskon;

    public String getHargadiskon() {
        return hargadiskon;
    }

    public void setHargadiskon(String hargadiskon) {
        this.hargadiskon = hargadiskon;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getIdproduk() {
        return idproduk;
    }

    public void setIdproduk(String idproduk) {
        this.idproduk = idproduk;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getNamaproduk() {
        return namaproduk;
    }

    public void setNamaproduk(String namaproduk) {
        this.namaproduk = namaproduk;
    }

    public String getNamalapak() {
        return namalapak;
    }

    public void setNamalapak(String namalapak) {
        this.namalapak = namalapak;
    }

    public String getHargaasli() {
        return hargaasli;
    }

    public void setHargaasli(String harga) {
        this.hargaasli = harga;
    }

    public boolean isDiskon() {
        return diskon;
    }

    public void setDiskon(boolean diskon) {
        this.diskon = diskon;
    }
}
