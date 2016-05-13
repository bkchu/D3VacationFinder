var markers = [];
var infobox = new InfoBox();

var valueOfTd;
var indexMarker = {};


function showLocationsOnMap(places, lati, longi) {
    console.log("Zooming map");
    var map = new window.google.maps.Map(document.getElementById('map'), {
        zoom: 11,
        center: {
            lat: parseFloat(lati),
            lng: parseFloat(longi)
        }
    });
    var geocoder = new window.google.maps.Geocoder;

    var bounds = new window.google.maps.LatLngBounds();


    for (i in places) {
        displayAndAddMarker(geocoder, map, infobox, bounds, places[i]);
    }

    (function () {
        $('.targetRow').mousedown(function () {
            valueOfTd = $(this).find('td:first-child').text();
            valueOfTd = valueOfTd.substr(0, valueOfTd.indexOf('.'));
            console.log(valueOfTd);

            var markerDance = indexMarker[valueOfTd.toString()];
            setOps(infobox, places[valueOfTd - 1], valueOfTd);
            infobox.open(map, markerDance);

            var loc = new window.google.maps.LatLng(places[i].latitude, places[i].longitude);
            map.panTo(loc);
        });
    })();

    map.fitBounds(bounds);
}

function displayAndAddMarker(geocoder, map, infobox, bounds, place) {
    var marker = new window.google.maps.Marker({
        position: {
            lat: parseFloat(place.latitude),
            lng: parseFloat(place.longitude)
        },
        map: map
    });
    markers.push(marker);
    bounds.extend(marker.getPosition());


    for (var i = 0; i < markers.length; i++) {
        indexMarker[(i + 1).toString()] = markers[i];
    }

    var loc = new window.google.maps.LatLng(place.latitude, place.longitude);

    marker.addListener('click', function () {
        map.panTo(loc);
        setOps(infobox, place, markers.indexOf(marker) + 1);
        infobox.open(map, this);
    });

    infobox.addListener('domready', function () {
        $("#infobox-close").on('click', function () {
            infobox.close();
        });
    });
}


function setOps(infobox, place, rank) {
    infobox.setOptions({
        content:
            '<div class="infobox-wrapper">' +
                '<div id="infobox">' +
                    '<div class="panel panel-default">' +
                        '<div class="panel-heading">' +
                            'Rank #' + rank +
                            '<span id="infobox-close" class="glyphicon glyphicon-remove pull-right" aria-hidden="true"></span>' +
                        '</div>' +
                        '<div class="panel-body">' +
                            '<a href="' + place.website + '" target="_blank">' +
                                place.name +
                            '</a>' +
                        '</div>' +
                    '</div>' +
                '</div>' +
            '</div>',


        disableAutoPan: false,
        maxWidth: 150,
        pixelOffset: new window.google.maps.Size(-140, 0),
        zIndex: null,
        boxStyle: {
            background: "url('http://google-maps-utility-library-v3.googlecode.com/svn/trunk/infobox/examples/tipbox.gif') no-repeat"
        },
        closeBoxMargin: "12px 4px 2px 2px",
        closeBoxURL: '',
        infoBoxClearance: new window.google.maps.Size(1, 1)
    });
}
