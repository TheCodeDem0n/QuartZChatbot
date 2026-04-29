async function loginUser() {
  const username = document.getElementById("loginUsername").value.trim();
  const password = document.getElementById("loginPassword").value.trim();
  if (!username || !password) { alert("Both fields are required."); return; }

  const res = await fetch("http://localhost:8080/auth/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password })
  });
  const text = await res.text();
  alert(text);
  if (text.includes("successful")) {
    localStorage.setItem("username", username);
    window.location.href = "chat.html";
  }
}

function logoutUser() {
  localStorage.removeItem("username");
  window.location.href = "login.html";
}
