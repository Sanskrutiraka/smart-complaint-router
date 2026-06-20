// ---------- AUTH GUARD ----------

let user = null;

(function () {

	const storedUser = localStorage.getItem("user");

	if (!storedUser) {
	    alert("Please login first");
	    window.location.replace("login.html");
	    throw new Error("AUTH_BLOCK"); // ✅ stops script execution completely
	}

	let user;

	try {
	    user = JSON.parse(storedUser);
	} catch (e) {
	    localStorage.clear();
	    alert("Session expired. Please login again.");
	    window.location.replace("login.html");
	    throw new Error("AUTH_BLOCK");
	}

	if (!user.sessionToken) {
	    localStorage.clear();
	    alert("Session expired. Please login again.");
	    window.location.replace("login.html");
	    throw new Error("AUTH_BLOCK");
	}
})();


// ---------- HARD STOP ----------
if (!user) {
    throw new Error("Authentication failed — stopping script");
}


// ---------- LOAD USER COMPLAINTS ----------

fetch(`http://localhost:8086/api/complaints/user/${user.id}`, {
    headers: {
        "X-SESSION-TOKEN": user.sessionToken
    }
})
.then(res => {

    if (res.status === 401 || res.status === 403) {
        localStorage.clear();
        alert("Session expired. Please login again.");
        window.location.replace("login.html");
        return;
    }

    if (!res.ok) {
        throw new Error("HTTP Error " + res.status);
    }

    return res.json();
})
.then(data => {

    const tbody = document.getElementById("complaintTableBody");
    tbody.innerHTML = "";

    if (!Array.isArray(data) || data.length === 0) {
        tbody.innerHTML =
            "<tr><td colspan='4'>No complaints found</td></tr>";
        return;
    }

    data.forEach(c => {

        const submittedDate =
            new Date(c.submittedAt).toLocaleString();

        const row = `
            <tr>
                <td>${c.id}</td>
                <td>${c.description}</td>
                <td>${c.status}</td>
                <td>${submittedDate}</td>
            </tr>
        `;

        tbody.innerHTML += row;
    });
})
.catch(err => {
    console.error(err);
    alert("Failed to load complaints");
});
