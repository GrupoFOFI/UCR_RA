// implementation of AR-Experience (aka "World")
var World = {
	// true once data was fetched
	initiallyLoadedData: false,

	// different POI-Marker assets
	markerDrawable_idle: null,
	markerDrawable_selected: null,

	// list of AR.GeoObjects that are currently shown in the scene / World
	markerList: [],

	// The last selected marker
	currentMarker: null,

	// called to inject new POI data
	loadPoisFromJsonData: function loadPoisFromJsonDataFn(poiData) {
		// empty list of visible markers
		World.markerList = [];

		// start loading marker assets
		World.markerDrawable_idle = new AR.ImageResource("assets/marker_idle.png");
		World.markerDrawable_selected = new AR.ImageResource("assets/marker_selected.png");

		// loop through POI-information and create an AR.GeoObject (=Marker) per POI
		for (var currentPlaceNr = 0; currentPlaceNr < poiData.length; currentPlaceNr++) {
			var singlePoi = {
				"id": poiData[currentPlaceNr].id,
				"latitude": parseFloat(poiData[currentPlaceNr].latitude),
				"longitude": parseFloat(poiData[currentPlaceNr].longitude),
				"altitude": parseFloat(poiData[currentPlaceNr].altitude),
				"title": poiData[currentPlaceNr].name,
				"description": poiData[currentPlaceNr].description
			};

			/*
				To be able to deselect a marker while the user taps on the empty screen, 
				the World object holds an array that contains each marker.
			*/
			World.markerList.push(new Marker(singlePoi));
		}

		World.updateStatusMessage(currentPlaceNr + ' places loaded');
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
			The custom function World.onLocationChanged checks with the flag World.initiallyLoadedData if the function was already called. With the first call of World.onLocationChanged an object that contains geo information will be created which will be later used to create a marker using the World.loadPoisFromJsonData function.
		*/
		if (!World.initiallyLoadedData) {
			/* 
				requestDataFromLocal with the geo information as parameters (latitude, longitude) creates different poi data to a random location in the user's vicinity.
			*/
			World.requestDataFromLocal(lat, lon);
			World.initiallyLoadedData = true;
		}
	},

	// fired when user pressed maker in cam
	onMarkerSelected: function onMarkerSelectedFn(marker) {

		// deselect previous marker
		if (World.currentMarker) {
			if (World.currentMarker.poiData.id == marker.poiData.id) {
				return;
			}
			World.currentMarker.setDeselected(World.currentMarker);
		}

		// highlight current one
		marker.setSelected(marker);
		World.currentMarker = marker;
	},

	// screen was clicked but no geo-object was hit
	onScreenClick: function onScreenClickFn() {
		if (World.currentMarker) {
			World.currentMarker.setDeselected(World.currentMarker);
		}
	},

	// request POI data
	requestDataFromLocal: function requestDataFromLocalFn(centerPointLatitude, centerPointLongitude) {
		var poiData = [];

		poiData.push({  "id": 1,  "longitude": -84.0537921,    "latitude": 9.9364001,  "description": "Facultad de Derecho",   "altitude": "100.0",    "name": "Facultad de Derecho"});
		poiData.push({  "id": 2,  "longitude": -84.054207,    "latitude": 9.935489,  "description": "Oficina de Becas y Atención Socioeconómica",   "altitude": "100.0",    "name": "Oficina de Becas y Atención Socioeconómica"});
		poiData.push({  "id": 3,  "longitude": -84.052692,    "latitude": 9.935999,  "description": "Biblioteca Luis Demetrio Tinoco",   "altitude": "100.0",    "name": "Biblioteca Luis Demetrio Tinoco"});
		poiData.push({  "id": 4,  "longitude": -84.052488,    "latitude": 9.935016,  "description": "Escuela de Arquitectura",   "altitude": "100.0",    "name": "Escuela de Arquitectura"});
		poiData.push({  "id": 5,  "longitude": -84.05311,    "latitude": 9.937234,  "description": "Comedor universitario",   "altitude": "100.0",    "name": "Comedor universitario"});
		poiData.push({  "id": 6,  "longitude": -84.05192,    "latitude": 9.935885,  "description": "Facultad de Ingeniería",   "altitude": "100.0",    "name": "Facultad de Ingeniería"});
		poiData.push({  "id": 7,  "longitude": -84.051574,    "latitude": 9.936476,  "description": "Escuela de Física y Matemáticas",   "altitude": "100.0",    "name": "Escuela de Física y Matemáticas"});
		poiData.push({  "id": 8,  "longitude": -84.050466,    "latitude": 9.936159,  "description": "Escuela de Estudios Generales",   "altitude": "100.0",    "name": "Escuela de Estudios Generales"});
		poiData.push({  "id": 9,  "longitude": -84.051018,    "latitude": 9.936038,  "description": "Biblioteca Carlos Monge",   "altitude": "100.0",    "name": "Biblioteca Carlos Monge"});
		poiData.push({  "id": 10,  "longitude": -84.0536305,    "latitude": 9.9386838,  "description": "Sección de Educación Preescolar",   "altitude": "100.0",    "name": "Sección de Educación Preescolar"});
		poiData.push({  "id": 11,  "longitude": -84.052917,    "latitude": 9.938561,  "description": "Facultad de Letras",   "altitude": "100.0",    "name": "Facultad de Letras"});
		poiData.push({  "id": 12,  "longitude": -84.052172,    "latitude": 9.937683,  "description": "Centro de Informática",   "altitude": "100.0",    "name": "Centro de Informática"});
		poiData.push({  "id": 13,  "longitude": -84.052298,    "latitude": 9.938111,  "description": "Escuela Centroamericana de Geología",   "altitude": "100.0",    "name": "Escuela Centroamericana de Geología"});
		poiData.push({  "id": 14,  "longitude": -84.051732,    "latitude": 9.937144,  "description": "Facultad de Ciencias Económicas",   "altitude": "100.0",    "name": "Facultad de Ciencias Económicas"});
		poiData.push({  "id": 15,  "longitude": -84.051925,    "latitude": 9.937922,  "description": "Escuela de Computación e Informática",   "altitude": "100.0",    "name": "Escuela de Computación e Informática"});
		World.loadPoisFromJsonData(poiData);
	}

};

/* 
	Set a custom function where location changes are forwarded to. There is also a possibility to set AR.context.onLocationChanged to null. In this case the function will not be called anymore and no further location updates will be received. 
*/
AR.context.onLocationChanged = World.locationChanged;

/*
	To detect clicks where no drawable was hit set a custom function on AR.context.onScreenClick where the currently selected marker is deselected.
*/
AR.context.onScreenClick = World.onScreenClick;