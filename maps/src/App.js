import logo from './logo.svg';
import './App.css';

import {MapContainer, TileLayer, Marker, Popup, GeoJSON, Polygon, Rectangle} from 'react-leaflet'
import 'leaflet/dist/leaflet.css';
import * as turf from "@turf/turf";

function App() {

  const centerPoint = [-25.88536975144579, 28.277796392845673];
  // const centerPoint = [51.505, -0.09];
  const blackOptions = { color: 'green',
  opacity:0.0,
  fillOpacity:0.6}

  const rectangle = [[
    [-25.872882, 28.266343],
    [-25.881950, 28.270612],
      ],
    [
    [-25.881950, 28.270612],
    [-25.891950, 28.280612]
        ]
    // [51.49, -0.08],
    // [51.5, -0.06],
  ]

  var radius = 0.1;

  var options = {
    steps: 50,
    units: "miles",
    properties: {
      text: "test"
    }
  };

  const firstCircle = turf.circle(centerPoint, radius, options);
  var bbox = [-25.88536975144, 28.277796392843, -25.88536975144579, 28.277796392845673];

  var poly = turf.bboxPolygon(bbox);

  function getAreaColor(feature){
    console.log(feature)
    switch (feature.properties.Name){
      case 'Area One' : return 'blue' ;
      case 'Area Two' : return 'yellow' ;
        break;
    }
  };

  function areaStyle(feature){
    return {
      fillColor: getAreaColor(feature),
      color:getAreaColor(feature),
      weight: 2,
      opacity: 0.0,
      strokeOpacity: 0.5,
      // dashArray: '3',
      fillOpacity: 0.7
    }
  };

  return (

      <link
          rel="stylesheet"
          href="https://unpkg.com/leaflet@1.6.0/dist/leaflet.css"
          integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ=="
          crossOrigin=""
      />,
      <MapContainer center={centerPoint} zoom={13} scrollWheelZoom={false}>

        <TileLayer
            attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />

        {/*<Marker position={centerPoint}>*/}
        {/*  <Popup>*/}
        {/*    A pretty CSS3 popup. <br /> Easily customizable.*/}
        {/*  </Popup>*/}
        {/*</Marker>*/}
        {/*<Polygon data={poly}/>*/}
        {/*<Rectangle bounds={rectangle} pathOptions={blackOptions} />*/}
      </MapContainer>
  );
}

export default App;
