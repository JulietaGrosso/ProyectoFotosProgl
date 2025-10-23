<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="header.jsp" %>

   <div class="espacioFrase">
            <h1 data-anijs="if: scroll, on: window, do: animate__slideInLeft animated, before: $scrollReveal repeat">«If you really love nature,<br>
               you will find beauty everywhere»<br>
               -Vincent Van Gogh</h1>

      </div>
      <div class="galeria">
           <h2>Galery</h2>
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
                       <p  data-anijs="if: scroll, on: window, do: animate__fadeInDown animated, before: $scrollReveal repeat">I enjoy capture diferent moments and places in nature, <br>
                           just for fun and to connect with my inner artist.
                       </p>
                       <div class="paraPorfolio" data-anijs="if: scroll, on: window, do: animate__fadeInUp animated, before: $scrollReveal repeat">
                           <h4><i class="fa-solid fa-graduation-cap"></i>Experience:</h4>

                           <ul>
                               <li>2016 technical school graduate. </li>
                               <li>2021 language and literature teacher graduate.</li>
                               <li>At present, I am a student of Software Development. </li>

                           </ul>


                       </div>
                   </div>
           </div>

      </div>
<%@ include file="footer.jsp" %>
