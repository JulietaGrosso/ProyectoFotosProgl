<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="headerGaleria.jsp" %>

        <div class="seccionGaleria">
            <div class="galeria2" data-anijs="if: scroll, on: window, do: animate__fadeIn animated, before: $scrollReveal repeat">
                
                 <form method="post">
                   <input value="<%= request.getAttribute("id") %>" id="id"  name="id" style="height: 0; width: 0;"/>
                    <label for="nombre">Nombre</label><br>
                    <input value="<%= request.getAttribute("nombre") %>" id="nombre" name="nombre"/>
                    <br>
                     <label for="alt">Alt</label><br>
                    <input  value="<%= request.getAttribute("alt") %>" type="text" id="alt" name="alt"/>
                    <br>
                    <img width="100" height="100" src="/mostrarFoto?foto=<%= request.getAttribute("foto") %>"><br>
                    <button type="submit">Editar Imagen</button>
                 </form>         
           </div>
     </div>
<%@ include file="footer.jsp" %>


