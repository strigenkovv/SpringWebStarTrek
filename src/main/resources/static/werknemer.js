"use strict";

const byId = id => document.getElementById(id);

const parameters = new URLSearchParams(window.location.search);
const id = parameters.get("id");

async function laadWerknemer() {
    const response = await fetch(`werknemers/${id}`);

    if (response.ok) {
        const werknemer = await response.json();

        byId("naam").textContent =
            `${werknemer.voornaam} ${werknemer.familienaam}`;

        byId("nummer").textContent = werknemer.id;
        byId("budget").textContent = werknemer.budget;

        byId("foto").src = `images/${werknemer.id}.jpg`;
        byId("foto").hidden = false;

        byId("vorigeBestellingen").href =
            `bestellingen.html?id=${werknemer.id}`;

        byId("nieuweBestelling").href =
            `nieuweBestelling.html?id=${werknemer.id}`;
    } else if (response.status === 404) {
        byId("nietGevonden").hidden = false;
    } else {
        byId("storing").hidden = false;
    }
}

laadWerknemer().catch(() => {
    byId("storing").hidden = false;
});
