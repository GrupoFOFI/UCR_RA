package ra.inge.ucr.da;


import java.util.ArrayList;

import ra.inge.ucr.da.entity.TargetObject;
import ra.inge.ucr.da.entity.TargetType;

/**
 * <h1> Data </h1>
 * Class used to load all the target data to the app
 */
public class Data {

    /**
     * List that contains all the target objects to list
     */
    public final static ArrayList<TargetObject> targetObjects = new ArrayList<TargetObject>() {{

        add(new TargetObject(1, TargetType.BUILDING, 9.936463, -84.053772, 0, new int[]{157, 158}, "Facultad de Derecho", "derecho", "La Facultad de Derecho, es la bienvenida en la entrada de la UCR. Sus orígenes se remontan a la Escuela de Derecho de la Universidad de Santo Tomás por lo que se le considera como la facultad más antigua del país.", "VideoPlayback/derecho.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.derecho));
        add(new TargetObject(2, TargetType.BUILDING, 9.937304, -84.052909, 0, new int[]{34}, "Comedor universitario", "comedor", "El comedor universitario es un lugar amplio y abierto para que los estudiantes puedan realizar sus trabajos y también probar alimentos económicos de buena calidad.", "VideoPlayback/comedor.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.comedor));
        add(new TargetObject(3, TargetType.BUILDING, 9.935766, -84.052017, 0, new int[]{93}, "Facultad de Ingeniería", "ingenieria", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/ingenieria.html", R.drawable.logo_ucr));
        add(new TargetObject(4, TargetType.BUILDING, 9.936493, -84.051655, 0, new int[]{43, 46}, "Escuela de Física y Matemáticas", "mate", "La Escuela de Física es una subdivisión de la Facultad de Ciencias de la Universidad de Costa Rica. Esta Facultad trajo consigo la apertura del Departamento de Física y Matemática, en donde se ofreció la carrera de Licenciatura en Física y Matemática.", "VideoPlayback/mate.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.mate));
        add(new TargetObject(5, TargetType.BUILDING, 9.936014, -84.05058, 0, new int[]{52, 53}, "Escuela de Estudios Generales", "generales", "La escuela de estudios generales consiste en una cátedra universitaria que reúne gran cantidad de estudiantes con el objetivo de difundir valores y principios que permitan a los estudiantes consolidarse como profesionales íntegros y humanistas", "VideoPlayback/generales.mp4", "museo.ucr.ac.cr/rfb/generales.html", R.drawable.generales));
        add(new TargetObject(6, TargetType.BUILDING, 9.935944, -84.050965, 0, new int[]{163}, "Biblioteca Carlos Monge", "carlos_monge", "La Biblioteca Carlos Monge Alfaro es la biblioteca principal de la Universidad de Costa Rica.", "VideoPlayback/carlos-monge.mp4", "museo.ucr.ac.cr/rfb/biblioteca_carlos_monge.html", R.drawable.logo_ucr));
        add(new TargetObject(7, TargetType.BUILDING, 9.937643, -84.052352, 0, new int[]{17}, "Centro de Informática", "centro_info", "El Centro de Informática de la Universidad de Costa Rica, ha contribuido de manera decidida al quehacer universitario. ", "VideoPlayback/centro-info.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.centro_info));
        add(new TargetObject(8, TargetType.BUILDING, 9.93489, -84.052488, 0, new int[]{160}, "Escuela de Arquitectura", "arquitectura", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.logo_ucr));
        add(new TargetObject(9, TargetType.BUILDING, 9.9386838, -84.0536305, 0, new int[]{3}, "Sección de Educación Preescolar", "educacion_preescolar", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.logo_ucr));
        add(new TargetObject(10, TargetType.BUILDING, 9.935988, -84.052569, 0, new int[]{159}, "Biblioteca Luis Demetrio Tinoco", "tinoco", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.logo_ucr));
        add(new TargetObject(11, TargetType.BUILDING, 9.938376, -84.052643, 0, new int[]{35}, "Facultad de Letras", "letras", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.logo_ucr));
        add(new TargetObject(12, TargetType.BUILDING, 9.93809, -84.052524, 0, new int[]{14}, "Escuela Centroamericana de Geología", "geologia", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/geologia.html", R.drawable.logo_ucr));
        add(new TargetObject(13, TargetType.BUILDING, 9.936922, -84.05195, 0, new int[]{29, 161}, "Facultad de Ciencias Económicas", "economicas", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/economicas.html", R.drawable.logo_ucr));
        add(new TargetObject(14, TargetType.BUILDING, 9.937967, -84.052035, 0, new int[]{37}, "Escuela de Computación e Informática", "ecci", "Fofidescripcion de la ECCI", "VideoPlayback/ecci.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.ecci));
        add(new TargetObject(15, TargetType.BUILDING, 9.938461, -84.051817, 0, new int[]{72}, "Facultad de Odontología", "odonto", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.logo_ucr));
        add(new TargetObject(16, TargetType.BUILDING, 9.938783, -84.050774, 0, new int[]{135}, "Facultad de Medicina", "medicina", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/medicina.html", R.drawable.logo_ucr));
        add(new TargetObject(17, TargetType.BUILDING, 9.938934, -84.049986, 0, new int[]{136}, "Facultad de Farmacia", "farmacia", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.logo_ucr));
        add(new TargetObject(18, TargetType.BUILDING, 9.93794, -84.049238, 0, new int[]{166}, "Facultad de Microbiología", "microbiologia", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/microbiologia.html", R.drawable.logo_ucr));
        add(new TargetObject(19, TargetType.BUILDING, 9.935435, -84.053959, 0, new int[]{81, 104, 105}, "Oficina de Becas y Atención Socioeconómica", "oficina_becas", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.logo_ucr));
        add(new TargetObject(20, TargetType.BUILDING, 9.937623, -84.049312, 0, new int[]{124}, "Escuela de Biología", "biologia", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.logo_ucr));
        add(new TargetObject(21, TargetType.BUILDING, 9.937465, -84.048789, 0, new int[]{62, 165}, "Escuela de Química", "quimica", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/quimica.html", R.drawable.logo_ucr));
        add(new TargetObject(22, TargetType.BUILDING, 9.937571, -84.048044, 0, new int[]{164}, "Escuela de Artes Musicales", "artes_musicales", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.logo_ucr));
        add(new TargetObject(23, TargetType.BUILDING, 9.936529, -84.048112, 0, new int[]{115}, "Escuela de Bellas Artes", "bellas_artes", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.logo_ucr));
        add(new TargetObject(24, TargetType.BUILDING, 9.935826, -84.048699, 0, new int[]{120, 64}, "Facultad de Educación", "educacion", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/educacion.html", R.drawable.logo_ucr));
        add(new TargetObject(25, TargetType.BUILDING, 9.93758, -84.051405, 0, "Bosque Leonel Oviedo", "bosque_oviedo", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.logo_ucr));
        add(new TargetObject(26, TargetType.BUILDING, 0, 0, 0, "Mariposario", "mariposario", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.logo_ucr));
        add(new TargetObject(27, TargetType.BUILDING, 9.936614, -84.050735, 0, new int[]{54, 162}, "Edificio de Aulas", "aulas", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.logo_ucr));
        add(new TargetObject(28, TargetType.BUILDING, 9.935435, -84.053959, 0, new int[]{81, 104, 105}, "Oficina de Becas y Atención Socioeconómica", "oficina_becas", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.logo_ucr));
        add(new TargetObject(29, TargetType.MONUMENT, 9.936614, -84.050735, 0, "Escultura 'Antárticos'", "antarticos", "Escultura hecha en acero doblado y pintado.", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.antarticos));
        add(new TargetObject(30, TargetType.MONUMENT, 9.936614, -84.050735, 0, "Escultura 'Osos Amorosos'", "osos_amorosos", "Escultura  hecha en mármol blanco, en 1992, por el escultor José Sancho.", "VideoPlayback/osos.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.osos_amorosos));
        add(new TargetObject(31, TargetType.MONUMENT, 9.936614, -84.050735, 0, "Leda y el Cisne", "leda", "Monumento formado por granito de escazú, esculpido por el reconocido José Sancho en 1980", "VideoPlayback/leda.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.leda));
        add(new TargetObject(32, TargetType.MONUMENT, 9.936614, -84.050735, 0, "Busto de José Joaquín Gutiérrez", "bustos", "Decano de la Facultad de Cirugía Dental de la UCR y primer odontólogo de América Latina.", "VideoPlayback/osos.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.jose));
        add(new TargetObject(33, TargetType.MONUMENT, 9.936614, -84.050735, 0, "Licenciado Fernando Bauza", "fernando", "Decano de la Facultad de Derecho en 1939 y rector de la Universidad de Costa Rica por primera vez en 1946.", "VideoPlayback/fernando.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.fernando_baudrit));
        add(new TargetObject(34, TargetType.MONUMENT, 9.936244, -84.050692, 0, "Plaza 24 de abril", "plaza24", "En 1971, la FEUCR decidió bautizar y colocar una placa conmemorativa de  la gesta de ALCOA en   la UCR y en ella, escrita en mármol se grabó. “A la juventud de Costa Rica, Plaza 24 de Abril, violar la ley del imperio es defender los derechos del pueblo”.", "VideoPlayback/24-abril.mp4", "museo.ucr.ac.cr/rfb/plaza_24_de_abril.html", R.drawable.plaza24));
        add(new TargetObject(35, TargetType.MONUMENT, 9.935895, -84.050638, 0, "El Pretil", "pretil", "Fofidescripcion", "VideoPlayback/antarticos.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.pretil));
        add(new TargetObject(36, TargetType.MONUMENT, 9.936652, -84.049869, 0, "Quebrada Los Negritos", "negritos", "La Quebrada de los Negritos, nace en el patio de cinco viviendas, en Calle del Chorro, Sabanilla, enfrente del Colegio Metodista.", "VideoPlayback/quebrada_negritos.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.negritos));
        add(new TargetObject(37, TargetType.MONUMENT, 9.936390, -84.050679, 0, "Juan y María", "juan", "Escultura hecha en hormigón policromado por Leda Astorga. Conmemora el 50 aniversario de la Esculea de Trabajo Social", "VideoPlayback/juan_maria.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.juan_maria));
        add(new TargetObject(38, TargetType.MONUMENT, 9.936390, -84.050679, 0, "Girasol", "girasol", "El mural, obra creada en cerámica y metal, muestra un girasol, que a la vez evoca el Big Bang, el pensamiento racional, el arte y la búsqueda de la luz. Conmemora el 50 aniversario de la Escuela de Estudios Genreales", "VideoPlayback/girasol.mp4", "museo.ucr.ac.cr/rfb/", R.drawable.girasol));
    }};

    /**
     * List of distances
     */
    public static double[] distances = new double[targetObjects.size()];


    /**
     * Finds a target object by name
     *
     * @param name the target object's name
     * @return the TargetObject if found, otherwise null
     */
    public static TargetObject getByName(String name) {
        for (TargetObject to : targetObjects) {
            if (to.getName().contains(name)) {
                return to;
            }
        }
        return null;
    }
}
