"use strict";

const byId = id => document.getElementById(id);

const parameters = new URLSearchParams(window.location.search);
const werknemerId = parameters.get("id");

byId("terug").href = `werknemer.html?id=${werknemerId}`;

byId("bestellingForm").onsubmit = async event => {
    event.preventDefault();

    byId("onvoldoendeBudget").hidden = true;
    byId("fout").hidden = true;
    byId("storing").hidden = true;

    const nieuweBestelling = {
        omschrijving: byId("omschrijving").value,
        bedrag: Number(byId("bedrag").value)
    };

    try {
        const response = await fetch(
            `werknemers/${werknemerId}/bestellingen`,
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(nieuweBestelling)
            }
        );

        if (response.ok) {
            window.location.href = `werknemer.html?id=${werknemerId}`;
        } else if (response.status === 409) {
            byId("onvoldoendeBudget").hidden = false;
        } else if (response.status === 400) {
            byId("fout").hidden = false;
        } else {
            byId("storing").hidden = false;
        }
    } catch {
        byId("storing").hidden = false;
    }
};
