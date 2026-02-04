// ---------- AUTH GUARD ----------
const userRaw = localStorage.getItem("user");

if (!userRaw) {
  alert("Please login first");
  location.href = "login.html";
  throw new Error("Not logged in");
}

let user;
try {
  user = JSON.parse(userRaw);
} catch (e) {
  localStorage.removeItem("user");
  location.href = "login.html";
  throw new Error("Invalid user data");
}

if (!user.role || user.role.toUpperCase() !== "ADMIN") {
  alert("Access denied");
  location.href = "login.html";
  throw new Error("Not admin");
}
// ---------- END AUTH GUARD ----------


// ---------- CONSTANTS ----------
const API = "http://localhost:8086/api/complaints";
const tbody = document.getElementById("complaintTableBody");
const title = document.getElementById("pageTitle");

let allComplaints = [];
let currentFilter = "pending";


// ---------- NORMALIZER (IMPORTANT FIX) ----------
function normalizeStatus(status) {
  return (status || "").toString().toLowerCase();
}


// ---------- INITIAL LOAD ----------
loadAllComplaints();


// ---------- LOAD ALL ----------
function loadAllComplaints() {
  fetch(API)
    .then(res => {
      if (!res.ok) throw new Error("Failed to load complaints");
      return res.json();
    })
    .then(data => {
      allComplaints = Array.isArray(data) ? data : [];
      updateCounts();
      renderByFilter("pending");
    })
    .catch(() => alert("Failed to load complaints"));
}


// ---------- FILTER ----------
function renderByFilter(status) {
  currentFilter = status.toLowerCase();

  title.innerText =
    currentFilter === "all"
      ? "All Complaints"
      : currentFilter.charAt(0).toUpperCase() +
        currentFilter.slice(1) +
        " Complaints";

  const filtered =
    currentFilter === "all"
      ? allComplaints
      : allComplaints.filter(
          c => normalizeStatus(c.status) === currentFilter
        );

  renderTable(filtered);
}


// ---------- RENDER ----------
function renderTable(data) {
  tbody.innerHTML = "";

  if (!Array.isArray(data) || data.length === 0) {
    tbody.innerHTML =
      "<tr><td colspan='6'>No complaints found</td></tr>";
    return;
  }

  data.forEach(c => {
    tbody.innerHTML += `
      <tr>
        <td>${c.id}</td>
        <td>${c.description}</td>
        <td>${c.user?.email ?? "-"}</td>
        <td>${c.status}</td>
        <td>${c.submittedAt}</td>
        <td>${getActionButtons(c)}</td>
      </tr>
    `;
  });
}


// ---------- ACTION BUTTON LOGIC ----------
function getActionButtons(c) {
  const status = normalizeStatus(c.status);
  let buttons = "";

  if (status === "pending") {
    buttons += actionBtn(c.id, "APPROVED");
    buttons += actionBtn(c.id, "REJECTED");
    buttons += actionBtn(c.id, "RESOLVED");
  } 
  else if (status === "approved") {
    buttons += actionBtn(c.id, "RESOLVED");
    buttons += actionBtn(c.id, "REJECTED");
  } 
  else if (status === "rejected") {
    buttons += actionBtn(c.id, "APPROVED");
  } 
  else if (status === "resolved") {
    buttons += actionBtn(c.id, "APPROVED");
  }

  return buttons || "-";
}

function actionBtn(id, status) {
  return `<button onclick="updateStatus(${id}, '${status}')">${status}</button>`;
}


// ---------- UPDATE STATUS ----------
function updateStatus(id, status) {
  fetch(`${API}/${id}/status?status=${status}`, { method: "PUT" })
    .then(res => {
      if (!res.ok) throw new Error("Update failed");
      return res.json();
    })
    .then(updated => {
      allComplaints = allComplaints.map(c =>
        c.id === updated.id ? updated : c
      );

      updateCounts();
      renderByFilter(currentFilter);
    })
    .catch(() => alert("Failed to update status"));
}


// ---------- COUNTS ----------
function updateCounts() {
  document.getElementById("totalCount").innerText =
    allComplaints.length;

  document.getElementById("pendingCount").innerText =
    allComplaints.filter(c => normalizeStatus(c.status) === "pending").length;

  document.getElementById("approvedCount").innerText =
    allComplaints.filter(c => normalizeStatus(c.status) === "approved").length;

  document.getElementById("resolvedCount").innerText =
    allComplaints.filter(c => normalizeStatus(c.status) === "resolved").length;

  document.getElementById("rejectedCount").innerText =
    allComplaints.filter(c => normalizeStatus(c.status) === "rejected").length;
}


// ---------- FILTER BUTTON HOOKS ----------
function showAll() { renderByFilter("all"); }
function showPending() { renderByFilter("pending"); }
function showApproved() { renderByFilter("approved"); }
function showResolved() { renderByFilter("resolved"); }
function showRejected() { renderByFilter("rejected"); }
