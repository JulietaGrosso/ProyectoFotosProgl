
</html>
<footer>
    <div class="pieDePagina" id="elContacto">
        <div class="logosPiePag">
            <img src="./images/logobeige.png" alt="logo">
        </div>
         <div class="contacto">
            <p> Este proyecto fue desarrollado para <br>
                la catedra de Programación II en la<br>
                 Tecnicatura Superior en Desarrollo de Software.<br>
                Este sitio web es sobre fotografía personal.
            </p>
            <h3>Contacto:</h3>
            <a href="https://www.instagram.com/julietagrosso_/" target="_blank" aria-label="instagram">
                <i class="fa-brands fa-instagram"></i>
            </a>
            <i class="fa-solid fa-mobile-screen-button"></i>
        </div>

    </div>
   </footer>

   <button id="darkModeToggle" class="darkModeToggle" aria-label="Cambiar modo oscuro">
       <i class="fa-solid fa-moon"></i>
   </button>

   <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
   <script src="https://anijs.github.io/lib/anijs/anijs.js"></script>
   <script src="https://anijs.github.io/lib/anijs/helpers/scrollreveal/anijs-helper-scrollreveal.js"></script>
   <script>
   (function () {
       var toggle = document.getElementById('darkModeToggle');
       var icon = toggle.querySelector('i');
       var body = document.body;

       if (localStorage.getItem('darkMode') === 'true') {
           body.classList.add('dark-mode');
           icon.className = 'fa-solid fa-sun';
       }

       toggle.addEventListener('click', function () {
           var isDark = body.classList.toggle('dark-mode');
           icon.className = isDark ? 'fa-solid fa-sun' : 'fa-solid fa-moon';
           localStorage.setItem('darkMode', isDark);
       });
   })();
   </script>

</body>
</html>