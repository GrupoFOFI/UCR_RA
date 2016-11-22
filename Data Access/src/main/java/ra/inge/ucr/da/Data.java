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

        add(new TargetObject(1, TargetType.BUILDING, 9.936463, -84.053772, 0, new int[]{157,158},   "Facultad de Derecho", "derecho", "La Facultad de Derecho, es la bienvenida en la entrada de la UCR. Sus orígenes se remontan a la Escuela de Derecho de la Universidad de Santo Tomás por lo que se le considera como la facultad más antigua del país.", "Videoplayback/derecho.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(5, TargetType.BUILDING, 9.937304, -84.052909, 0, new int[]{34},        "Comedor universitario", "comedor", "El comedor universitario es un lugar amplio y abierto para que los estudiantes puedan realizar sus trabajos y también probar alimentos económicos de buena calidad. Dicha instalación fue reabierta en el 2005 para garantizar el beneficio complementario de alimentación a muchos estudiantes becados y provee un amplio espacio para la recreación y el descanso", "Videoplayback/comedor.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(6, TargetType.BUILDING, 9.935766, -84.052017, 0, new int[]{93},        "Facultad de Ingeniería", "ingenieria", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(7, TargetType.BUILDING, 9.936493, -84.051655, 0, new int[]{43,46},     "Escuela de Física y Matemáticas", "mate", "La Escuela de Física es una subdivisión de la Facultad de Ciencias de la Universidad de Costa Rica. Esta Facultad trajo consigo la apertura del Departamento de Física y Matemática, en donde se ofreció la carrera de Licenciatura en Física y Matemática; impulsando la consolidación de nuevos profesionales en el área de los números", "Videoplayback/mate.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(8, TargetType.BUILDING, 9.936014, -84.05058, 0,  new int[]{52,53},     "Escuela de Estudios Generales", "generales", "La escuela de estudios generales consiste en una cátedra universitaria que reúne gran cantidad de estudiantes con el objetivo de difundir valores y principios que permitan a los estudiantes consolidarse como profesionales íntegros y humanistas", "Videoplayback/generales.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(9, TargetType.BUILDING, 9.935944, -84.050965, 0, new int[]{163},       "Biblioteca Carlos Monge", "carlos_monge", "La Biblioteca Carlos Monge Alfaro es la biblioteca principal de la Universidad de Costa Rica. Lleva este nombre en honor de su ex-rector, el Benemérito de la Patria, Carlos Monge Alfaro y es la biblioteca central de las que integran el Sistema de Bibliotecas, Documentación e Información, conocida por las siglas SIBDI", "Videoplayback/carlos-monge.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(12, TargetType.BUILDING, 9.937643, -84.052352, 0,new int[]{17},        "Centro de Informática", "centro_info", "El Centro de Informática de la Universidad de Costa Rica, ha contribuido de manera decidida al quehacer universitario, en medio de los constantes cambios y desarrollos tecnológicos en ciclos cada vez más cortos.  De esta manera, se ha convertido en uno de los edificios más relevantes, pues toda la universidad depende del mismo tanto para gestiones de conexión con la Red universitaria como asuntos de T I.\nEl Centro de Informática de la Universidad de Costa Rica, ha contribuido de manera decidida al quehacer universitario, en medio de los constantes cambios y desarrollos tecnológicos en ciclos cada vez más cortos.  De esta manera, se ha convertido en uno de los edificios más relevantes, pues toda la universidad depende del mismo tanto para gestiones de conexión con la Red universitaria como asuntos de T I.\nEl Centro de Informática de la Universidad de Costa Rica, ha contribuido de manera decidida al quehacer universitario, en medio de los constantes cambios y desarrollos tecnológicos en ciclos cada vez más cortos.  De esta manera, se ha convertido en uno de los edificios más relevantes, pues toda la universidad depende del mismo tanto para gestiones de conexión con la Red universitaria como asuntos de T I", "Videoplayback/centro-info.mp4", "www.museodeinsectoscr.com"));

        add(new TargetObject(4, TargetType.BUILDING, 9.93489, -84.052488, 0, new int[]{160},        "Escuela de Arquitectura", "arquitectura", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(10, TargetType.BUILDING, 9.9386838, -84.0536305, 0, new int[]{3},      "Sección de Educación Preescolar", "educacion_preescolar", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(3, TargetType.BUILDING, 9.935988, -84.052569, 0, new int[]{159},       "Biblioteca Luis Demetrio Tinoco", "tinoco", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(11, TargetType.BUILDING, 9.938376, -84.052643, 0,new int[]{35}  ,      "Facultad de Letras", "letras", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(13, TargetType.BUILDING, 9.93809, -84.052524, 0, new int[]{14},        "Escuela Centroamericana de Geología", "geologia", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(14, TargetType.BUILDING, 9.936922, -84.05195, 0, new int[]{29,161},    "Facultad de Ciencias Económicas", "economicas", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(15, TargetType.BUILDING, 9.937967, -84.052035, 0, new int[]{37},       "Escuela de Computación e Informática", "ecci", "Fofidescripcion de la ECCI", "centro-info.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(16, TargetType.BUILDING, 9.938461, -84.051817, 0, new int[]{72},       "Facultad de Odontología", "odonto", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(17, TargetType.BUILDING, 9.938783, -84.050774, 0, new int[]{135},      "Facultad de Medicina", "medicina", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(18, TargetType.BUILDING, 9.938934, -84.049986, 0, new int[]{136},      "Facultad de Farmacia", "farmacia", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(19, TargetType.BUILDING, 9.93794, -84.049238, 0,  new int[]{166},      "Facultad de Microbiología", "microbiologia", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(20, TargetType.BUILDING, 9.937623, -84.049312, 0, new int[]{124},      "Escuela de Biología", "biologia", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(21, TargetType.BUILDING, 9.937465, -84.048789, 0, new int[]{62, 165},  "Escuela de Química", "quimica", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(22, TargetType.BUILDING, 9.937571, -84.048044, 0, new int[]{164},      "Escuela de Artes Musicales", "artes_musicales", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(23, TargetType.BUILDING, 9.936529, -84.048112, 0, new int[]{115},      "Escuela de Bellas Artes", "bellas_artes", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(24, TargetType.BUILDING, 9.935826, -84.048699, 0, new int[]{120, 64},  "Facultad de Educación", "educacion", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(25, TargetType.BUILDING, 9.93758, -84.051405, 0,                       "Bosque Leonel Oviedo", "bosque_oviedo", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(26, TargetType.BUILDING, 0, 0, 0,                                      "Mariposario", "mariposario", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(29, TargetType.BUILDING, 9.936614, -84.050735, 0, new int[]{54,162},    "Edificio de Aulas", "aulas", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(2, TargetType.BUILDING, 9.935435, -84.053959, 0, new int[]{81,104,105}, "Oficina de Becas y Atención Socioeconómica", "oficina_becas", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));

        add(new TargetObject(28, TargetType.MONUMENT, 9.936614, -84.050735, 0, "Escultura 'Antárticos'", "antarticos", "Escultura hecha en acero doblado y pintado.", "VideoPlayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(29, TargetType.MONUMENT, 9.936614, -84.050735, 0, "Escultura 'Osos Amorosos'", "osos_amorosos", "Escultura  hecha en mármol blanco, en 1992, por el escultor José Sancho.", "Videoplayback/osos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(30, TargetType.MONUMENT, 9.936614, -84.050735, 0, "Leda y el Cisne", "leda", "Monumento formado por granito de escazú, esculpido por el reconocido José Sancho en 1980", "Videoplayback/leda.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(31, TargetType.MONUMENT, 9.936614, -84.050735, 0, "Busto de José Joaquín Gutiérrez", "bustos", "Decano de la Facultad de Cirugía Dental de la UCR y primer odontólogo de América Latina.", "Videoplayback/joaquin.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(31, TargetType.MONUMENT, 9.936614, -84.050735, 0, "Licenciado Fernando Bauza", "fernando", "Decano de la Facultad de Derecho en 1939 y rector de la Universidad de Costa Rica por primera vez en 1946.", "Videoplayback/fernando.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(33, TargetType.MONUMENT, 9.936244, -84.050692, 0, "Plaza 24 de abril", "plaza24", "En 1971, la FEUCR decidió bautizar y colocar una placa conmemorativa de  la gesta de ALCOA en   la UCR y en ella, escrita en mármol se grabó. “A la juventud de Costa Rica, Plaza 24 de Abril, violar la ley del imperio es defender los derechos del pueblo”, frase que después de 44 años han podido leer e inspirar varias generaciones de estudiantes y profesores", "Videoplayback/24-abril.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(34, TargetType.MONUMENT, 9.935895, -84.050638, 0, "El Pretil", "pretil", "Fofidescripcion", "Videoplayback/antarticos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(35, TargetType.MONUMENT, 9.936652, -84.049869, 0, "Quebrada Los Negritos", "negritos", "La Quebrada de los Negritos, nace en el patio de cinco viviendas, en Calle del Chorro, Sabanilla, enfrente del Colegio Metodista.", "Videoplayback/quebrada_negritos.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(36, TargetType.MONUMENT, 9.936390, -84.050679, 0, "Juan y María", "juan", "Escultura hecha en hormigón policromado por Leda Astorga. Conmemora el 50 aniversario de la Esculea de Trabajo Social", "Videoplayback/juan_maria.mp4", "www.museodeinsectoscr.com"));
        add(new TargetObject(36, TargetType.MONUMENT, 9.936390, -84.050679, 0, "Girasol", "girasol", "El mural, obra creada en cerámica y metal, muestra un girasol, que a la vez evoca el Big Bang, el pensamiento racional, el arte y la búsqueda de la luz. Conmemora el 50 aniversario de la Escuela de Estudios Genreales", "Videoplayback/girasol.mp4", "www.museodeinsectoscr.com"));
    }};

    /**
     * List of distances
     */
    public static double[] distances = new double[targetObjects.size()];


    public static TargetObject getByName(String name){
        for(TargetObject to:targetObjects){
            if(to.getName().contains(name)){
                return to;
            }
        }
        return null;
    }
}
