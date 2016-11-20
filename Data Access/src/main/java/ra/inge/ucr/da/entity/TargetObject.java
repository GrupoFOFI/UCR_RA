package ra.inge.ucr.da.entity;


/**
 * <h1>Target Object </h1>
 * Base class for objects to recognize later or display information
 *
 * @author konrad
 * @version 1.0
 * @since 20/11/16
 */
public class TargetObject {

    /**
     * The object's id
     */
    private int id;
    /**
     * The object type
     *
     */
    private TargetType type;
    /**
     * The latitude for the object
     */
    private double latitude;
    /**
     * The logitude for the object
     */
    private double longitude;
    /**
     * The altitude for the object
     */
    private double altitude;
    /**
     * The name for the object
     */
    private String name;
    /**
     * The object's description
     */
    private String description;

    /**
     * Empty constructor for the class
     */
    public TargetObject() {
    }

    /**
     * Complete constructor
     *
     * @param id
     * @param type
     * @param latitude
     * @param longitude
     * @param altitude
     * @param name
     * @param description
     */
    public TargetObject(int id, TargetType type, double latitude, double longitude, double altitude, String name, String description) {
        this.id = id;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.name = name;
        this.description = description;
    }

    /*  Getters and Setters */

    /**
     * Getter for the target's id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the Target's id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public TargetType getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(TargetType type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     *
     * @param altitude
     */
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
