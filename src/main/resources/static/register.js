async function registerUser() {
  const username = document.getElementById("regUsername").value.trim();
  const password = document.getElementById("regPassword").value.trim();
  if (!username || !password) { alert("Please fill all fields!"); return; }

  const res = await fetch("http://localhost:8080/auth/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password })
  });
  const text = await res.text();
  alert(text);
  if (text.includes("successful")) window.location.href = "login.html";
}
