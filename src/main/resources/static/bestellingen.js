"use strict";

const byId = id => document.getElementById(id);

const parameters = new URLSearchParams(window.location.search);
const werknemerId = parameters.get("id");

byId("terug").href = `werknemer.html?id=${werknemerId}`;

async function laadBestellingen() {
    const response = await fetch(`werknemers/${werknemerId}/bestellingen`);

    if (response.ok) {
        const bestellingen = await response.json();

        if (bestellingen.length === 0) {
            byId("geenBestellingen").hidden = false;
        } else {
            const tbody = byId("bestellingenBody");

            for (const bestelling of bestellingen) {
                const row = tbody.insertRow();

                row.insertCell().textContent = bestelling.id;
                row.insertCell().textContent = bestelling.omschrijving;
                row.insertCell().textContent = bestelling.bedrag;
                row.insertCell().textContent =
                    new Date(bestelling.moment).toLocaleString("nl-BE");
            }

            byId("bestellingen").hidden = false;
        }
    } else {
        byId("storing").hidden = false;
    }
}

laadBestellingen().catch(() => {
    byId("storing").hidden = false;
});
