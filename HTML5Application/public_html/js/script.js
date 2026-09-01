/* ==========================================================================
   OctanoGT — script.js
   Funcionalidades de interacción del frontend (sin backend).
   ========================================================================== */

document.addEventListener("DOMContentLoaded", function () {

  /* ------------------------------------------------------------ */
  /* Sidebar responsive (off-canvas en pantallas pequeñas)          */
  /* ------------------------------------------------------------ */
  const sidebar = document.getElementById("sidebar");
  const overlay = document.getElementById("sidebarOverlay");
  const toggleBtn = document.getElementById("sidebarToggle");

  function openSidebar() {
    if (!sidebar) return;
    sidebar.classList.add("show");
    overlay && overlay.classList.add("show");
  }
  function closeSidebar() {
    if (!sidebar) return;
    sidebar.classList.remove("show");
    overlay && overlay.classList.remove("show");
  }

  if (toggleBtn) {
    toggleBtn.addEventListener("click", function () {
      sidebar.classList.contains("show") ? closeSidebar() : openSidebar();
    });
  }
  if (overlay) {
    overlay.addEventListener("click", closeSidebar);
  }
  // Cierra el sidebar al navegar (en móvil) al hacer clic en un enlace
  document.querySelectorAll(".sidebar .nav-link").forEach(function (link) {
    link.addEventListener("click", function () {
      if (window.innerWidth < 992) closeSidebar();
    });
  });

  /* ------------------------------------------------------------ */
  /* Fecha actual en la barra superior y en el dashboard             */
  /* ------------------------------------------------------------ */
  const today = new Date();
  const options = { weekday: "long", day: "numeric", month: "long", year: "numeric" };
  const formatted = today.toLocaleDateString("es-PE", options);
  const dateEl = document.getElementById("todayDate");
  const dateEl2 = document.getElementById("todayDate2");
  if (dateEl) dateEl.textContent = formatted;
  if (dateEl2) dateEl2.textContent = formatted;

  /* ------------------------------------------------------------ */
  /* Buscador simple en tablas (filtra filas visualmente)            */
  /* ------------------------------------------------------------ */
  document.querySelectorAll("[data-table-search]").forEach(function (input) {
    input.addEventListener("keyup", function () {
      const term = input.value.trim().toLowerCase();
      const panel = input.closest(".panel, .page-intro, main") || document;
      const table = document.querySelector(".table-modern");
      if (!table) return;
      table.querySelectorAll("tbody tr").forEach(function (row) {
        const text = row.textContent.toLowerCase();
        row.style.display = text.includes(term) ? "" : "none";
      });
    });
  });

  /* ------------------------------------------------------------ */
  /* Validación visual simple del formulario de login                */
  /* ------------------------------------------------------------ */
  const loginForm = document.getElementById("loginForm");
  if (loginForm) {
    loginForm.addEventListener("submit", function (e) {
      e.preventDefault();
      const user = document.getElementById("loginUser");
      const pass = document.getElementById("loginPass");
      const userError = document.getElementById("userError");
      const passError = document.getElementById("passError");

      let valid = true;
      if (!user.value.trim()) {
        user.classList.add("is-invalid");
        userError.classList.remove("d-none");
        valid = false;
      } else {
        user.classList.remove("is-invalid");
        userError.classList.add("d-none");
      }
      if (!pass.value.trim()) {
        pass.classList.add("is-invalid");
        passError.classList.remove("d-none");
        valid = false;
      } else {
        pass.classList.remove("is-invalid");
        passError.classList.add("d-none");
      }

      if (valid) {
        // Simulación de autenticación: redirige al dashboard.
        window.location.href = "index.html";
      }
    });
  }

  /* Mostrar / ocultar contraseña en el login */
  const togglePass = document.getElementById("togglePass");
  if (togglePass) {
    togglePass.addEventListener("click", function () {
      const passInput = document.getElementById("loginPass");
      const icon = togglePass.querySelector("i");
      if (passInput.type === "password") {
        passInput.type = "text";
        icon.classList.replace("bi-eye", "bi-eye-slash");
      } else {
        passInput.type = "password";
        icon.classList.replace("bi-eye-slash", "bi-eye");
      }
    });
  }

  /* ------------------------------------------------------------ */
  /* Formularios de contacto / gestión: evitar recarga real          */
  /* ------------------------------------------------------------ */
  const contactForm = document.getElementById("contactForm");
  if (contactForm) {
    contactForm.addEventListener("submit", function (e) {
      e.preventDefault();
      alert("Mensaje enviado (simulación). En la versión final este formulario se conectará al backend.");
      contactForm.reset();
    });
  }

});
