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
  var myGJ ={
    "type": "FeatureCollection",
    "features": [
      {
        "type": "Feature",
        "properties": {
          "Name":"Area One",
          "stroke": "#cb2a2a",
          "stroke-width": 2,
          "stroke-opacity": 0.5,
          "fill": "#b72a2a",
          "fill-opacity": 0.5
        },
        "geometry": {
          "type": "Polygon",
          "coordinates": [
            [
              [
                28.274345397949215,
                -25.893202651527982
              ],
              [
                28.28996658325195,
                -25.893202651527982
              ],
              [
                28.28996658325195,
                -25.879303293404806
              ],
              [
                28.274345397949215,
                -25.879303293404806
              ],
              [
                28.274345397949215,
                -25.893202651527982
              ]
            ]
          ]
        }
      },
      {
        "type": "Feature",
        "properties": {
          "Name":"Area Two",
          "stroke": "#555555",
          "stroke-width": 2,
          "stroke-opacity": 0.4,
          "fill": "red",
          "fill-opacity": 1
        },
        "geometry": {
          "type": "Polygon",
          "coordinates": [
            [
              [
                28.274345397949215,
                -25.903819114210975
              ],
              [
                28.289923667907715,
                -25.903819114210975
              ],
              [
                28.289923667907715,
                -25.893241258577042
              ],
              [
                28.274345397949215,
                -25.893241258577042
              ],
              [
                28.274345397949215,
                -25.903819114210975
              ]
            ]
          ]
        }
      }
    ]
  };

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
        <GeoJSON data={myGJ.features} style={areaStyle} />
      </MapContainer>
  );
}

export default App;
