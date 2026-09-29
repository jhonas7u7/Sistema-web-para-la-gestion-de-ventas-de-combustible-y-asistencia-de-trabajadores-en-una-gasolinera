/* ==========================================================================
   OctanoGT — script.js
   Interacciones de frontend. Los formularios se envían al servidor
   (Spring MVC); este archivo solo aporta comportamiento visual.
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
  document.querySelectorAll(".sidebar .nav-link").forEach(function (link) {
    link.addEventListener("click", function () {
      if (window.innerWidth < 992) closeSidebar();
    });
  });

  /* ------------------------------------------------------------ */
  /* Fecha actual en la barra superior                               */
  /* ------------------------------------------------------------ */
  const today = new Date();
  const options = { weekday: "long", day: "numeric", month: "long", year: "numeric" };
  const formatted = today.toLocaleDateString("es-PE", options);
  const dateEl = document.getElementById("todayDate");
  if (dateEl) dateEl.textContent = formatted;

  /* ------------------------------------------------------------ */
  /* Buscador simple en tablas (filtra filas visualmente)            */
  /* ------------------------------------------------------------ */
  document.querySelectorAll("[data-table-search]").forEach(function (input) {
    input.addEventListener("keyup", function () {
      const term = input.value.trim().toLowerCase();
      const table = document.querySelector(".table-modern");
      if (!table) return;
      table.querySelectorAll("tbody tr").forEach(function (row) {
        const text = row.textContent.toLowerCase();
        row.style.display = text.includes(term) ? "" : "none";
      });
    });
  });

  /* ------------------------------------------------------------ */
  /* Mostrar / ocultar contraseña en el login                        */
  /* ------------------------------------------------------------ */
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
  /* Auto-cierre de alertas de confirmación después de unos segundos  */
  /* ------------------------------------------------------------ */
  document.querySelectorAll(".alert[data-autoclose]").forEach(function (alertEl) {
    setTimeout(function () {
      alertEl.style.transition = "opacity .4s ease";
      alertEl.style.opacity = "0";
      setTimeout(function () { alertEl.remove(); }, 500);
    }, 4000);
  });

});
