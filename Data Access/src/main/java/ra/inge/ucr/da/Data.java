package ra.inge.ucr.da;


import java.util.ArrayList;

import ra.inge.ucr.da.entity.TargetObject;
import ra.inge.ucr.da.entity.TargetType;

/**
 * <h1> Data </h1>
 * Class used to load all the target data to the app
 *
 */
public class Data {

    /**
     * List that contains all the target objects to list
     */
    public final static ArrayList<TargetObject> targetObjects = new ArrayList<TargetObject>() {{

        add(new TargetObject(1, TargetType.BUILDING, 9.936463, -84.053772, 0, "Facultad de Derecho", null));
        add(new TargetObject(2, TargetType.BUILDING, 9.935435, -84.053959, 0, "Oficina de Becas y Atención Socioeconómica", null));
        add(new TargetObject(3, TargetType.BUILDING, 9.935988, -84.052569, 0, "Biblioteca Luis Demetrio Tinoco", null));
        add(new TargetObject(4, TargetType.BUILDING, 9.93489, -84.052488, 0, "Escuela de Arquitectura", null));
        add(new TargetObject(5, TargetType.BUILDING, 9.937304, -84.052909, 0, "Comedor universitario", null));
        add(new TargetObject(6, TargetType.BUILDING, 9.935766, -84.052017, 0, "Facultad de Ingeniería", null));
        add(new TargetObject(7, TargetType.BUILDING, 9.936493, -84.051655, 0, "Escuela de Física y Matemáticas", null));
        add(new TargetObject(8, TargetType.BUILDING, 9.936014, -84.05058, 0, "Escuela de Estudios Generales", null));
        add(new TargetObject(9, TargetType.BUILDING, 9.935944, -84.050965, 0, "Biblioteca Carlos Monge", null));
        add(new TargetObject(10, TargetType.BUILDING, 9.9386838, -84.0536305, 0, "Sección de Educación Preescolar", null));
        add(new TargetObject(11, TargetType.BUILDING, 9.938376, -84.052643, 0, "Facultad de Letras", null));
        add(new TargetObject(12, TargetType.BUILDING, 9.937643, -84.052352, 0, "Centro de Informática", null));
        add(new TargetObject(13, TargetType.BUILDING, 9.93809, -84.052524, 0, "Escuela Centroamericana de Geología", null));
        add(new TargetObject(14, TargetType.BUILDING, 9.936922, -84.05195, 0, "Facultad de Ciencias Económicas", null));
        add(new TargetObject(15, TargetType.BUILDING, 9.937967, -84.052035, 0, "Escuela de Computación e Informática", null));
        add(new TargetObject(16, TargetType.BUILDING, 9.938461, -84.051817, 0, "Facultad de Odontología", null));
        add(new TargetObject(17, TargetType.BUILDING, 9.938783, -84.050774, 0, "Facultad de Medicina", null));
        add(new TargetObject(18, TargetType.BUILDING, 9.938934, -84.049986, 0, "Facultad de Farmacia", null));
        add(new TargetObject(19, TargetType.BUILDING, 9.93794, -84.049238, 0, "Facultad de Microbiología", null));
        add(new TargetObject(20, TargetType.BUILDING, 9.937623, -84.049312, 0, "Escuela de Biología", null));
        add(new TargetObject(21, TargetType.BUILDING, 9.937465, -84.048789, 0, "Escuela de Química", null));
        add(new TargetObject(22, TargetType.BUILDING, 9.937571, -84.048044, 0, "Escuela de Artes Musicales", null));
        add(new TargetObject(23, TargetType.BUILDING, 9.936529, -84.048112, 0, "Escuela de Bellas Artes", null));
        add(new TargetObject(24, TargetType.BUILDING, 9.935826, -84.048699, 0, "Facultad de Educación", null));
        add(new TargetObject(25, TargetType.BUILDING, 9.93758, -84.051405, 0, "Bosque Leonel Oviedo", null));
        add(new TargetObject(26, TargetType.BUILDING, 0, 0, 0, "Mariposario", null));
        add(new TargetObject(27, TargetType.BUILDING, 9.936244, -84.050692, 0, "Plaza 24 de abril", null));
        add(new TargetObject(28, TargetType.BUILDING, 9.935895, -84.050638, 0, "El Pretil", null));
        add(new TargetObject(29, TargetType.BUILDING, 9.936614, -84.050735, 0, "Edificio de Aulas", null));
    }};

    /**
     * List of distances
     */
    public static double[] distances = new double[targetObjects.size()];
}
