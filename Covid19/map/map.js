function getRandomInt(max) {
    return Math.floor(Math.random() * Math.floor(max));
}

function nombreVilleConfDansTab(tabVille, dep) {
    dep.properties.nbVilleConf = 0;
    dep.properties.villesConf = new Array();

    tabVille.forEach(codePostale => {
        if (dep.properties.code == codePostale.substring(0, 2)) {
            dep.properties.nbVilleConf += 1;
            dep.properties.villesConf.push({ nom: codePostale });
        }
    });
}

function readSingleFile(e) {
    let file = e.target.files[0];

    if (!file) {
        return;
    }

    let reader = new FileReader();

    reader.onload = function (e) {
        function getColor(nbVille) {
            if (nbVille > 10) {
                return '#7A1B18';
            } else if (nbVille > 8) {
                return '#EA2B1F';
            } else if (nbVille > 6) {
                return '#F19143';
            } else if (nbVille > 4) {
                return '#EFB550';
            } else if (nbVille > 2) {
                return '#A8C256';
            } else {
                return '#5FAD41';
            }
        }
        
        function style(feature) {
            return {
                fillColor: getColor(feature.properties.nbVilleConf),
                weight: 2,
                opacity: 0.3,
                color: 'white',
                dashArray: '3',
                fillOpacity: 0.7
            }
        }
        
        function clickHandler(e) {
            controlPanel.update(e.target.feature.properties);
            map.fitBounds(e.target.getBounds());
        }
        
        function hoverHandler(e) {
            e.target.setStyle({
                weight: 5,
                opacity: 0.5
            })
        }
        
        function resetHover(e) {
            geojson.resetStyle(e.target);
        }
        
        function onEachFeature(feature, layer) {
            layer.on({
                mouseover: hoverHandler,
                mouseout: resetHover,
                click: clickHandler
            });
        }
        
        let controlPanel = L.control();
        let legend = L.control();
        
        controlPanel.onAdd = function (map) {
            this._div = L.DomUtil.create('div');
            this.update();
            return this._div;
        }
        
        legend.onAdd = function(map) {
            let div = L.DomUtil.create('div', 'legend');
            let levels = [1, 3, 5, 7, 9, 11];
        
            div.innerHTML += "<h3>Villes confinés</h3>"
        
            for (let i = 0; i < levels.length; i++) {
                div.innerHTML += "<div><span style='background-color: " + getColor(levels[i]) + "'></span>" + (levels[i] - 1) + (levels[i + 1] ? ' à ' + (levels[i] + 1) : " +") + "</div>";
            }
        
            return div;
        }
        
        controlPanel.update = function (props) {
            let html = "";
        
            if (props) {
                html += "<div class='info-panel'>";
                html += "<h2>" + props.nom + "</h2>";
                html += "<h4>Nombres de ville confinés : <strong>" + props.nbVilleConf + "</strong></h4>";
                html += "<h4>Villes confinés :</h4><ul>";
        
                if (props.villesConf) {
                    props.villesConf.forEach(ville => {
                        html += "<li>" + ville.nom + "</li>";
                    });
                }
        
                html += "</div>";
            }
        
            this._div.innerHTML = html;
        }


        let contents = e.target.result;
        let depConfines = contents.split("\n");
        depConfines.pop();

        departements.features.forEach(dep => {
            nombreVilleConfDansTab(depConfines, dep);
        });

        let mapBoxAccessToken = 'pk.eyJ1IjoiamZtYXgiLCJhIjoiY2thNTR5eTg5MTBtdTNncG5uNHFpdXJtbiJ9.OLwvvTi-IV0A5lhq_SwSog';
        let map = L.map('map').setView([46.711, 2.5], 6);

        L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
            minZoom: 6,
            maxZoom: 9,
            id: 'mapbox/streets-v11',
            tileSize: 512,
            zoomOffset: -1,
            accessToken: mapBoxAccessToken
        }).addTo(map);

        let geojson = L.geoJson(departements, {
            style: style,
            onEachFeature: onEachFeature
        }).addTo(map);

        controlPanel.addTo(map);
        legend.addTo(map);
    }

    reader.readAsText(file);
}

document.getElementById('csvInput').addEventListener('change', readSingleFile, false);