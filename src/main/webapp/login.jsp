<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="headerGaleria.jsp" %>

        <div class="seccionGaleria">
            <div class="galeria2" data-anijs="if: scroll, on: window, do: animate__fadeIn animated, before: $scrollReveal repeat">
                
                 <form method="post" class="loginform" >
                    <label for="correo">Correo</label><br>
                    <input type="text" id="correo" name="correo"></imput>
                    <br>
                    <label for="contrasena">Contraseña</label><br>
                    <input type="text" id="contrasena" name="contrasena"></imput>
                    <br>
                    <button type="submit">Ingresar</button>
                 </form>         
           </div>
     </div>
<%@ include file="footer.jsp" %>


