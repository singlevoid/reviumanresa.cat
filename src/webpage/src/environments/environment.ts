// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  firebaseConfig:{
    apiKey: "AIzaSyDsKLygEC5Hvxpa87wuNpqwq71oPnGjeKw",
    authDomain: "caterina-1e602.firebaseapp.com",
    databaseURL: "https://caterina-1e602.firebaseio.com",
    projectId: "caterina-1e602",
    storageBucket: "caterina-1e602.appspot.com",
    messagingSenderId: "478976111293",
    appId: "1:478976111293:web:292df00354434f57d5bdd4",
    measurementId: "G-MT175XR8T8"
  },
  mapStyle: [{"stylers":[{"saturation":-80},{"invert_lightness":true},{"lightness":5}]},{"featureType":"landscape.man_made","stylers":[{"weight":"1.00"}]},{"featureType":"poi","elementType":"labels","stylers":[{"visibility":"off"}]},{"featureType":"poi.park","elementType":"geometry.fill","stylers":[{"color":"#061907"}]},{"featureType":"road.highway","stylers":[{"weight":"0.49"}]},{"featureType":"road.highway","elementType":"labels","stylers":[{"saturation":"-35"},{"lightness":"-7"},{"visibility":"on"},{"weight":"0.01"}]},{"featureType":"road.highway","elementType":"labels.icon","stylers":[{"visibility":"on"}]},{"featureType":"road.highway","elementType":"labels.text","stylers":[{"visibility":"on"}]},{"featureType":"road.highway","elementType":"labels.text.stroke","stylers":[{"visibility":"off"}]},{"featureType":"road.local","elementType":"geometry.fill","stylers":[{"color":"#050505"}]},{"featureType":"road.local","elementType":"labels.text.fill","stylers":[{"color":"#bababa"}]}],

};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
import 'zone.js/dist/zone-error';  // Included with Angular CLI.
