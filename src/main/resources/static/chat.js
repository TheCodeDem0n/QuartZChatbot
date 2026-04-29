let currentUser = null;
let currentSessionId = null;

let allMessages = [];
let userMessageIndex = 0;   


/* INIT NEW SESSION */
async function initNewSessionAndInput() {
  const username = localStorage.getItem("username");
  if (!username) return logoutUser();

  const res = await fetch(`http://localhost:8080/chat/newSession/${username}`, {
    method: "POST",
  });

  const session = await res.json();
  currentSessionId = session.id;

  await renderSidebarSessions();
  await loadChat(session.id);
  focusMessageInput();
}


/*RENDER SIDEBAR*/
async function renderSidebarSessions() {
  const username = localStorage.getItem("username");
  if (!username) return;

  try {
    const res = await fetch(`http://localhost:8080/chat/sessions/${username}`);
    const sessions = await res.json();

    const currentContainer = document.getElementById("currentSessionContainer");
    const historyContainer = document.getElementById("historyContainer");

    currentContainer.innerHTML = "";
    historyContainer.innerHTML = "";

    const currentSession = sessions.find(s => s.id === currentSessionId);

    // Load messages for CURRENT SESSION
    if (currentSession) {
      const resMsgs = await fetch(
        `http://localhost:8080/chat/messages/${currentSessionId}`
      );
      const msgs = await resMsgs.json();

      msgs.forEach((msg, index) => {
        const div = document.createElement("div");
        div.className = "session-message";
        div.textContent =
          msg.message.length > 30 ? msg.message.slice(0, 30) + "..." : msg.message;

        div.onclick = () => scrollToMessage(index); // 🔥 correct index
        currentContainer.appendChild(div);
      });
    }

    // Load other SESSIONS
    sessions
      .filter(s => s.id !== currentSessionId)
      .forEach(s => {
        const div = document.createElement("div");
        div.className = "history-item";
        div.textContent = s.title || "Untitled";

        div.onclick = async () => {
          currentSessionId = s.id;
          await renderSidebarSessions();
          await loadChat(s.id);
          focusMessageInput();
        };

        historyContainer.appendChild(div);
      });
  } catch (e) {
    console.error("Sidebar load failed", e);
  }
}


/*SEND MESSAGE*/
async function sendMessage() {
  const msgInput = document.getElementById("userMessage");
  const message = msgInput.value.trim();
  if (!message) return;

  displayMessage("You", message);

  try {
    const res = await fetch(
      `http://localhost:8080/chat/send/${currentSessionId}`,
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          message,
          username: localStorage.getItem("username"),
        }),
      }
    );

    const data = await res.json();
    displayMessage("Bot", data.response);

    msgInput.value = "";
    await renderSidebarSessions();
  } catch (e) {
    console.error("Send failed", e);
  }
}


/* LOAD CHAT HISTORY*/
async function loadChat(sessionId) {
  try {
    const res = await fetch(`http://localhost:8080/chat/messages/${sessionId}`);
    const messages = await res.json();

    const box = document.getElementById("chatBox");
    box.innerHTML = "";

    allMessages = [];
    userMessageIndex = 0; 

    messages.forEach((msg) => {
      appendUserMessage(msg.message);   
      appendBotMessage(msg.response);
    });

    box.scrollTop = box.scrollHeight;
  } catch (e) {
    console.error("Chat load failed", e);
  }
}


/*USER MESSAGE BUBBLE*/
function appendUserMessage(text, index = userMessageIndex) {
  const box = document.getElementById("chatBox");

  const wrapper = document.createElement("div");
  wrapper.className = "userMsg";
  wrapper.id = `msg-user-${index}`; 

  const span = document.createElement("span");
  span.textContent = text;

  wrapper.appendChild(span);
  box.appendChild(wrapper);

  allMessages.push(wrapper);
  userMessageIndex++;   
}


/*BOT MESSAGE BUBBLE*/
function appendBotMessage(text) {
  const box = document.getElementById("chatBox");

  const wrapper = document.createElement("div");
  wrapper.className = "botMsg";

  const span = document.createElement("span");
  span.textContent = text;               
  span.style.whiteSpace = "pre-wrap";    

  wrapper.appendChild(span);
  box.appendChild(wrapper);

  allMessages.push(wrapper);
}



/*LIVE MESSAGE DISPLAY*/
function displayMessage(sender, text) {
  if (sender === "You") {
    appendUserMessage(text);
  } else {
    appendBotMessage(text);
  }

  const box = document.getElementById("chatBox");
  box.scrollTop = box.scrollHeight;
}


/*SCROLL TO MESSAGE + GLOW*/
function scrollToMessage(idx) {
  const el = document.getElementById(`msg-user-${idx}`);
  if (!el) return;

  const chatBox = document.getElementById("chatBox");

  const offset = el.offsetTop - chatBox.clientHeight / 2;

  chatBox.scrollTo({
    top: offset,
    behavior: "smooth",
  });

  const bubble = el.querySelector("span");
  bubble.classList.add("highlight");

  setTimeout(() => bubble.classList.remove("highlight"), 1500);
}


/*INPUT FOCUS*/
function focusMessageInput() {
  setTimeout(() => {
    const input = document.getElementById("userMessage");
    if (input) input.focus();
  }, 100);
}


/*ENTER TO SEND*/
function attachSend() {
  const msgInput = document.getElementById("userMessage");

  msgInput.addEventListener("keydown", (e) => {
    if (e.key === "Enter") {
      e.preventDefault();
      sendMessage();
    }
  });
}


/*PAGE LOAD*/
document.addEventListener("DOMContentLoaded", () => {
  if (!window.location.pathname.endsWith("chat.html")) return;

  currentUser = localStorage.getItem("username");
  if (!currentUser) return logoutUser();

  initNewSessionAndInput();
  attachSend();

  document.getElementById("toggleSidebar").onclick = () =>
    document.getElementById("sidebar").classList.toggle("hidden");
});


/*LOGOUT*/
function logoutUser() {
  localStorage.removeItem("username");
  window.location.href = "login.html";
}

