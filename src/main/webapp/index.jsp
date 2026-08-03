<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="header.jsp" %>

   <div class="espacioFrase">
            <h1 data-anijs="if: scroll, on: window, do: animate__slideInLeft animated, before: $scrollReveal repeat">«Si realmente amas la naturaleza,<br>
               encontrarás belleza en todas partes»<br>
               -Vincent Van Gogh</h1>

      </div>
      <div class="galeria">
           <h2>Galeria</h2>
           <div id="carouselExampleRide" class="carousel slide" data-bs-ride="true">
               <div class="carousel-inner">
               
                    <c:forEach items="${imagenes}" var="Lista">
                          <div class="carousel-item active">
                              <div class="carrete">
                                    <c:forEach items="${Lista}" var="imagen">
                                        <div class="imagenes">
                                        <img src="/mostrarFoto?foto=${imagen.foto}" alt="${imagen.alt}"/>
                                        </div>
                                   </c:forEach>       
                               </div>
                           </div>
                   </c:forEach>
               </div>
               <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleRide" data-bs-slide="prev">
                 <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                 <span class="visually-hidden">Previous</span>
               </button>
               <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleRide" data-bs-slide="next">
                 <span class="carousel-control-next-icon" aria-hidden="true"></span>
                 <span class="visually-hidden">Next</span>
               </button>
             </div>


      </div>

      <div class="sobreMi" id="AboutMe">
           <div class="informacion">
                   <img src="./images/fotomia.jpg" alt="" data-anijs="if: scroll, on: window, do: animate__slideInLeft animated, before: $scrollReveal repeat">
                   <div class="infoDescripcion">
                       <p  data-anijs="if: scroll, on: window, do: animate__fadeInDown animated, before: $scrollReveal repeat">Disfruto capturar diferentes paisajes y momentos en la naturaleza. <br>
                           Si te interesa contactarme, no dudes en escribirme. <br>
                       </p>
                       <div class="paraPorfolio" data-anijs="if: scroll, on: window, do: animate__fadeInUp animated, before: $scrollReveal repeat">
                           <div class="experienciaBloque">
                               <h4><i class="fa-solid fa-graduation-cap"></i>Experiencia:</h4>

                               <ul>
                                   <li>Egresada del Profesorado de Lengua y Literatura en 2021.</li>
                                   <li>Actualmente soy estudiante del 3er Año de <br>
                                    la tecnicatura en Desarrollo de Software.</li>
                               </ul>
                           </div>
                           <div class="contactoSobreMi">
                               <a href="/contactar">Contactar</a>
                               <a href="/contactar" class="circuloFlecha" aria-label="Ir a contactar">
                                   <i class="fa-solid fa-arrow-right-long"></i>
                               </a>
                           </div>
                       </div>
                   </div>
           </div>

      </div>
<script>
(function () {
    var carousel = document.getElementById('carouselExampleRide');
    if (!carousel) return;
    var inner = carousel.querySelector('.carousel-inner');
    var mq = window.matchMedia('(max-width: 768px)');
    var estructuraOriginal = inner.innerHTML;

    function armarSlides(grupos) {
        inner.innerHTML = '';
        grupos.forEach(function (grupo, idx) {
            var item = document.createElement('div');
            item.className = 'carousel-item' + (idx === 0 ? ' active' : '');
            var carrete = document.createElement('div');
            carrete.className = 'carrete';
            grupo.forEach(function (nodo) {
                carrete.appendChild(nodo);
            });
            item.appendChild(carrete);
            inner.appendChild(item);
        });
    }

    function agruparDeAUno() {
        var imagenes = Array.prototype.slice.call(inner.querySelectorAll('.imagenes'));
        armarSlides(imagenes.map(function (img) { return [img]; }));
    }

    function restaurar() {
        inner.innerHTML = estructuraOriginal;
    }

    function actualizar() {
        if (mq.matches) {
            agruparDeAUno();
        } else {
            restaurar();
        }
    }

    mq.addEventListener('change', actualizar);
    actualizar();
})();
</script>

<%@ include file="footer.jsp" %>
