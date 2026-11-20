"use strict";

const byId = id => document.getElementById(id);

async function laadWerknemers() {
    const response = await fetch("werknemers");

    if (response.ok) {
        const werknemers = await response.json();

        for (const werknemer of werknemers) {
            const li = document.createElement("li");
            const link = document.createElement("a");

            link.href = `werknemer.html?id=${werknemer.id}`;
            link.textContent = `${werknemer.voornaam} ${werknemer.familienaam}`;

            li.appendChild(link);
            byId("werknemers").appendChild(li);
        }
    } else {
        byId("storing").hidden = false;
    }
}

laadWerknemers().catch(() => {
    byId("storing").hidden = false;
});
