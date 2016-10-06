package ra.inge.ucr.da;


import java.util.ArrayList;

public class Datos {
    public final static ArrayList<Edificio> edificios = new ArrayList<Edificio>() {{
        add(new Edificio(1, "Facultad de Derecho", 9.936463, -84.053772, 0, null));
        add(new Edificio(2, "Oficina de Becas y Atención Socioeconómica", 9.935435, -84.053959, 0, null));
        add(new Edificio(3, "Biblioteca Luis Demetrio Tinoco", 9.935988, -84.052569, 0, null));
        add(new Edificio(4, "Escuela de Arquitectura", 9.93489, -84.052488, 0, null));
        add(new Edificio(5, "Comedor universitario", 9.937304, -84.052909, 0, null));
        add(new Edificio(6, "Facultad de Ingeniería", 9.935766, -84.052017, 0, null));
        add(new Edificio(7, "Escuela de Física y Matemáticas", 9.936493, -84.051655, 0, null));
        add(new Edificio(8, "Escuela de Estudios Generales", 9.936014, -84.05058, 0, null));
        add(new Edificio(9, "Biblioteca Carlos Monge", 9.935944, -84.050965, 0, null));
        add(new Edificio(10, "Sección de Educación Preescolar", 9.9386838, -84.0536305, 0, null));
        add(new Edificio(11, "Facultad de Letras", 9.938376, -84.052643, 0, null));
        add(new Edificio(12, "Centro de Informática", 9.937643, -84.052352, 0, null));
        add(new Edificio(13, "Escuela Centroamericana de Geología", 9.93809, -84.052524, 0, null));
        add(new Edificio(14, "Facultad de Ciencias Económicas", 9.936922, -84.05195, 0, null));
        add(new Edificio(15, "Escuela de Computación e Informática", 9.937967, -84.052035, 0, null));
        add(new Edificio(16, "Facultad de Odontología", 9.938461, -84.051817, 0, null));
        add(new Edificio(17, "Facultad de Medicina", 9.938783, -84.050774, 0, null));
        add(new Edificio(18, "Facultad de Farmacia", 9.938934, -84.049986, 0, null));
        add(new Edificio(19, "Facultad de Microbiología", 9.93794, -84.049238, 0, null));
        add(new Edificio(20, "Escuela de Biología", 9.937623, -84.049312, 0, null));
        add(new Edificio(21, "Escuela de Química", 9.937465, -84.048789, 0, null));
        add(new Edificio(22, "Escuela de Artes Musicales", 9.937571, -84.048044, 0, null));
        add(new Edificio(23, "Escuela de Bellas Artes", 9.936529, -84.048112, 0, null));
        add(new Edificio(24, "Facultad de Educación", 9.935826, -84.048699, 0, null));
        add(new Edificio(25, "Bosque Leonel Oviedo", 9.93758, -84.051405, 0, null));
        add(new Edificio(26, "Mariposario",0 ,0 , 0, null));
        add(new Edificio(27, "Plaza 24 de abril", 9.936244, -84.050692, 0, null));
        add(new Edificio(28, "El Pretil", 9.935895, -84.050638, 0, null));
        add(new Edificio(29, "Edificio de Aulas", 9.936614, -84.050735, 0, null));
    }};

    public static double[] distances = new double[edificios.size()];
}
