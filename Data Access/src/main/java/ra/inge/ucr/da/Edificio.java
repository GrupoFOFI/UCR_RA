package ra.inge.ucr.da;

/**
 * <h1>Edificio</h1>
 * <p>
 *     Class that contains all the attributes needed to map a Building
 * </p>
 */
public class Edificio {

    private int id;
    private double lat;
    private double lng;
    private double alt;
    private String nmbr;
    private String dscrpt;
    private int[] entradas;

    /***
     * Complete constructor for the class
     *
     * @param id
     * @param nmbr
     * @param lat
     * @param lng
     * @param alt
     * @param dscrpt
     */
    public Edificio(int id, String nmbr, double lat, double lng, double alt, String dscrpt, int[] entradas) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
        this.nmbr = nmbr;
        this.dscrpt = dscrpt;
        this.entradas = entradas;
    }

    /**
     * Getters & Setters
     */


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

    public int[] getEntradas() {
        return entradas;
    }

    public Edificio setEntradas(int[] entradas) {
        this.entradas = entradas;
        return this;
    }
}
