package ra.inge.ucr.da;

/**
 * Created by Ricardo on 10/6/2016.
 */

public class Edificio {
    private int id;
    private double lat;
    private double lng;
    private double alt;
    private String nmbr;
    private String dscrpt;
    public Edificio(int id, String nmbr, double lat, double lng, double alt, String dscrpt) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
        this.nmbr = nmbr;
        this.dscrpt = dscrpt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getAlt() {
        return alt;
    }

    public void setAlt(double alt) {
        this.alt = alt;
    }

    public String getNmbr() {
        return nmbr;
    }

    public void setNmbr(String nmbr) {
        this.nmbr = nmbr;
    }

    public String getDscrpt() {
        return dscrpt;
    }

    public void setDscrpt(String dscrpt) {
        this.dscrpt = dscrpt;
    }
}
