<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="headerGaleria.jsp" %>

        <div class="seccionGaleria">
            <div class="galeria2" data-anijs="if: scroll, on: window, do: animate__fadeIn animated, before: $scrollReveal repeat">
                
                 <form method="post" enctype="multipart/form-data">
                    <label for="correo">Foto</label><br>
                    <input type="file" id="foto" name="foto"/>
                    <br>
                    <label for="nombre">Nombre</label><br>
                    <input id="nombre" name="nombre"/>
                    <br>
                     <label for="alt">Alt</label><br>
                    <input type="text" id="alt" name="alt"/>
                    <br>
                    <button type="submit">Subir Imagen</button>
                 </form>         
           </div>
     </div>
<%@ include file="footer.jsp" %>


