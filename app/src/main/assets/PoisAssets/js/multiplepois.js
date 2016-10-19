// implementation of AR-Experience (aka "World")
var World = {
	// different POI-Marker assets
	markerDrawable_idle: null,
	markerDrawable_selected: null,
	markerDrawable_directionIndicator: null,

	// list of AR.GeoObjects that are currently shown in the scene / World
	markerList: [],

	// The last selected marker
	currentMarker: null,

	//Informacion de Edificios
	Edificios: [{"id":1, "name":"Facultad de Derecho", "latitude":9.936463, "longitude":-84.053772, "altitude":100, "description":"Facultad de Derecho"},

               {"id":2, "name":"Oficina de Becas y Atención Socioeconómica", "latitude":9.935435, "longitude":-84.053959, "altitude":100, "description":"Oficina de Becas y Atención Socioeconómica"},

               {"id":3, "name":"Biblioteca Luis Demetrio Tinoco", "latitude":9.935988, "longitude":-84.052569, "altitude":0, "description":"Biblioteca Luis Demetrio Tinoco"},

               {"id":4, "name":"Escuela de Arquitectura", "latitude":9.93489, "longitude":-84.052488, "altitude":0, "description":"Escuela de Arquitectura"},

               {"id":5, "name":"Comedor universitario", "latitude":9.937304, "longitude":-84.052909, "altitude":0, "description":"Comedor universitario"},

               {"id":6, "name":"Facultad de Ingeniería", "latitude":9.935766, "longitude":-84.052017, "altitude":0, "description":"Facultad de Ingeniería"},

               {"id":7, "name":"Escuela de Física y Matemáticas", "latitude":9.936493, "longitude":-84.051655, "altitude":0, "description":"Escuela de Física y Matemáticas"},

               {"id":8, "name":"Escuela de Estudios Generales", "latitude":9.936014, "longitude":-84.05058, "altitude":0, "description":"Escuela de Estudios Generales"},

               {"id":9, "name":"Biblioteca Carlos Monge", "latitude":9.935944, "longitude":-84.050965, "altitude":0, "description":"Biblioteca Carlos Monge"},

               {"id":10, "name":"Sección de Educación Preescolar", "latitude":9.9386838, "longitude":-84.0536305, "altitude":0, "description":"La Sección de Educación Preescolar es una división académica especializada en el estudio de la Educación Inicial, que administra la carrera de Bachillerato y Licenciatura en Educación Preescolar, pero además realiza labores investigación y acción social."},

               {"id":11, "name":"Facultad de Letras", "latitude":9.938376, "longitude":-84.052643, "altitude":0, "description":"Facultad de Letras de la Universidad de Costa Rica.\nConstituida por las Escuelas de:\nEscuela de Lenguas Modernas\nEscuela de Filología, Lingüística y Literatura\nEscuela de Filosofía"},

                {"id":12, "name":"Centro de Informática", "latitude":9.937643, "longitude":-84.052352, "altitude":0, "description":"El Centro de Informática es una oficina administrativa coadyuvante de la Rectoría y apoyo técnico del Comité Gerencial de Informática en el gobierno de las tecnologías de la información y comunicación, funciona como instancia estratégica, asesora, técnica y de servicio, dedicada a asegurar que la información y tecnología están acordes y soportan los objetivos de la Institución hacia una posición de vanguardia y excelencia."},

                {"id":13, "name":"Escuela Centroamericana de Geología", "latitude":9.93809, "longitude":-84.052524, "altitude":0, "description":"En abril de 1967 se creó la carrera de Geología y en noviembre de ese año se fundó la Escuela Centroamericana de Geología, cuyo promotor fue César Dóndoli, ayudado por varios políticos y geólogos, como el Geólogo Gabriel Dengo (CASTILLO, S., PERALDO, G.; 2000)."},

                {"id":14, "name":"Facultad de Ciencias Económicas", "latitude":9.936922, "longitude":-84.05195, "altitude":0, "description":"Facultad de Ciencias Económicas"},

                {"id":15, "name":"Escuela de Computación e Informática", "latitude":9.937967, "longitude":-84.052035,"altitude": 0, "description":"La Escuela de Ciencias de la Computación e Infomática de la Universidad de Costa Rica se fundó en 1981, como resultado de la fusión de dos programas distintos pero relacionados, el de Bachillerato en Informática y el de Bachillerato y Licenciatura en Computación."},

                {"id":16, "name":"Facultad de Odontología", "latitude":9.938425, "longitude": -84.051431, "altitude":0, "description":"El 16 de marzo de 1942 se inauguró la Facultad de Cirugía Dental de la Universidad de Costa Rica, tomando como eje de su quehacer:  la promoción y la protección de la salud y prevención de las enfermedades buco-dento-maxilofaciales."},

                {"id":17, "name":"Facultad de Medicina", "latitude":9.938783, "longitude":-84.050774, "altitude":0, "description":"Facultad de Medicina"},

                {"id":18, "name":"Facultad de Farmacia", "latitude":9.938934, "longitude":-84.049986, "altitude":0, "description":"Facultad de Farmacia"},

                {"id":19, "name":"Facultad de Microbiología", "latitude":9.93794, "longitude":-84.049238, "altitude":0, "description":"Facultad de Microbiología"},

                {"id":20, "name":"Escuela de Biología", "latitude":9.937623, "longitude":-84.049312, "altitude":0, "description":"Escuela de Biología"},

                {"id":21, "name":"Escuela de Química", "latitude":9.937465, "longitude":-84.048789, "altitude":0,"description": "Escuela de Química"},

                {"id":22, "name":"Escuela de Artes Musicales", "latitude":9.937571, "longitude":-84.048044, "altitude":0, "description":"Escuela de Artes Musicales"},

                 {"id":23,"name": "Escuela de Bellas Artes", "latitude":9.936529, "longitude":-84.048112, "altitude":0, "description":"Escuela de Bellas Artes"},

                 {"id":24, "name":"Facultad de Educación", "latitude":9.935826, "longitude":-84.048699, "altitude":0,"description": "Facultad de Educación"},

                 {"id":25, "name":"Bosque Leonel Oviedo", "latitude":9.93758, "longitude":-84.051405, "altitude":0, "description":"Bosque Leonel Oviedo"},

                 {"id":26, "name":"Mariposario","latitude":0 ,"longitude":0 , "altitude":0,"description": "Mariposario"},

                 {"id":27, "name":"Plaza 24 de abril", "latitude":9.936244, "longitude":-84.050692, "altitude":0, "description":"Plaza 24 de abril"},

                 {"id":28, "name":"El Pretil", "latitude":9.935895, "longitude":-84.050638, "altitude":0, "description":"El Pretil"},

                 {"id":29,"name": "Edificio de Aulas", "latitude":9.936614, "longitude":-84.050735, "altitude":0,"description": "Edificio de Aulas"}],


	/*
		Have a look at the native code to get a better understanding of how data can be injected to JavaScript.
		Besides loading data from assets it is also possible to load data from a database, or to create it in native code. Use the platform common way to create JSON Objects of your data and use native 'architectView.callJavaScript()' to pass them to the ARchitect World's JavaScript.
		'World.loadPoisFromJsonData' is called directly in native code to pass over the poiData JSON, which then creates the AR experience.
	*/
	// called to inject new POI data
	loadPoisFromJsonData: function loadPoisFromJsonDataFn(id1,id2,id3) {

	    id1 -= 1;
	    id2 -= 1;
	    id3 -= 1;

		// empty list of visible markers
		World.markerList = [];

		// start loading marker assets
		World.markerDrawable_idle = new AR.ImageResource("assets/marker_idle.png");
		World.markerDrawable_selected = new AR.ImageResource("assets/marker_selected.png");
		World.markerDrawable_directionIndicator = new AR.ImageResource("assets/indi.png");

		// loop through POI-information and create an AR.GeoObject (=Marker) per POI
        var edificio1 = {
            "id": World.Edificios[id1].id,
            "latitude": parseFloat(World.Edificios[id1].latitude),
            "longitude": parseFloat(World.Edificios[id1].longitude),
            "altitude": parseFloat(World.Edificios[id1].altitude),
            "title": World.Edificios[id1].name,
            "description": World.Edificios[id1].description
        };
        World.markerList.push(new Marker(edificio1));

        var edificio2 = {
                    "id": World.Edificios[id2].id,
                    "latitude": parseFloat(World.Edificios[id2].latitude),
                    "longitude": parseFloat(World.Edificios[id2].longitude),
                    "altitude": parseFloat(World.Edificios[id2].altitude),
                    "title": World.Edificios[id2].name,
                    "description": World.Edificios[id2].description
                };
                World.markerList.push(new Marker(edificio2));

        var edificio3 = {
                    "id": World.Edificios[id3].id,
                    "latitude": parseFloat(World.Edificios[id3].latitude),
                    "longitude": parseFloat(World.Edificios[id3].longitude),
                    "altitude": parseFloat(World.Edificios[id3].altitude),
                    "title": World.Edificios[id3].name,
                    "description": World.Edificios[id3].description
                };
                World.markerList.push(new Marker(edificio3));

		World.updateStatusMessage(3 + ' places loaded');
	},

	// updates status message shon in small "i"-button aligned bottom center
	updateStatusMessage: function updateStatusMessageFn(message, isWarning) {

		var themeToUse = isWarning ? "e" : "c";
		var iconToUse = isWarning ? "alert" : "info";

		$("#status-message").html(message);
		$("#popupInfoButton").buttonMarkup({
			theme: themeToUse
		});
		$("#popupInfoButton").buttonMarkup({
			icon: iconToUse
		});
	},

	// location updates, fired every time you call architectView.setLocation() in native environment
	locationChanged: function locationChangedFn(lat, lon, alt, acc) {
		/*
			No action required in JS, in this sample places are injected via native code.
			Although it is recommended to inject any geo-content >after< first location update was fired.
		*/
	},

	// fired when user pressed maker in cam
	onMarkerSelected: function onMarkerSelectedFn(marker) {
    		World.currentMarker = marker;

    		/*
    			In this sample a POI detail panel appears when pressing a cam-marker (the blue box with title & description),
    			compare index.html in the sample's directory.
    		*/
    		// update panel values
    		$("#poi-detail-title").html(marker.poiData.title);
    		$("#poi-detail-description").html(marker.poiData.description);

    		// distance and altitude are measured in meters by the SDK. You may convert them to miles / feet if required.
    		var distanceToUserValue = (marker.distanceToUser > 999) ? ((marker.distanceToUser / 1000).toFixed(2) + " km") : (Math.round(marker.distanceToUser) + " m");

    		$("#poi-detail-distance").html(distanceToUserValue);

    		// show panel
    		$("#panel-poidetail").panel("open", 123);

    		$(".ui-panel-dismiss").unbind("mousedown");

    		// deselect AR-marker when user exits detail screen div.
    		$("#panel-poidetail").on("panelbeforeclose", function(event, ui) {
    			World.currentMarker.setDeselected(World.currentMarker);
    		});
    	}

};

/* forward locationChanges to custom function */
AR.context.onLocationChanged = World.locationChanged;

/* forward clicks in empty area to World */
AR.context.onScreenClick = World.onScreenClick;