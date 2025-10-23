<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="headerGaleria.jsp" %>

     <div class="seccionGaleria">
            <div class="galeria2" data-anijs="if: scroll, on: window, do: animate__fadeIn animated, before: $scrollReveal repeat">
                <h2>Galery</h2>

                <div class="imagenes">
                     <c:forEach items="${imagenes}" var="item">
                         <div class="imagen imggalery">
                              <img src="/mostrarFoto?foto=${item.foto}" alt="${item.nombre} ${item.alt}"/>
                              <c:if test="${sessionScope.logueado != null}">
                                        <div class="action">
                                        <a href="/editar?id=${item.id}">
                                        <i class="fa-solid fa-pencil"></i>
                                        </a>
                                        <i class="fa-solid fa-trash" onclick="eliminarImagen(${item.id})"></i>
                                   </div>
                              </c:if>
                              
                         </div>
                     </c:forEach>
                    
                     
                    </div>
                    <c:if test="${sessionScope.logueado != null}">
                      <a class="uploadbtn" href="/subir">Subir Imágen</a>
                   </c:if>
                       
           </div>
     </div>
     <script>
          async function eliminarImagen(id){
             await fetch("/borrar?id=" + id , {method:"DELETE"});
                 window.location.reload();

          }
     </script>
<%@ include file="footer.jsp" %>
