const user = JSON.parse(localStorage.getItem("user"));

if (!user) {
  alert("Please login first");
  window.location.href = "login.html";
}

fetch(`http://localhost:8086/api/complaints/user/${user.id}`)
  .then(res => {
    if (!res.ok) {
      throw new Error("HTTP Error " + res.status);
    }
    return res.json();
  })
  .then(data => {
    const tbody = document.getElementById("complaintTableBody");
    tbody.innerHTML = "";

    if (!Array.isArray(data) || data.length === 0) {
      tbody.innerHTML = "<tr><td colspan='4'>No complaints found</td></tr>";
      return;
    }

    data.forEach(c => {
      const submittedDate = new Date(c.submittedAt).toLocaleString(); // format timestamp
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
