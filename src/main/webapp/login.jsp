<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="headerGaleria.jsp" %>

        <div class="seccionGaleria">
            <div class="galeria2" data-anijs="if: scroll, on: window, do: animate__fadeIn animated, before: $scrollReveal repeat">
                
                 <form method="post" class="loginform" >
                    <label for="correo">Correo</label><br>
                    <input type="text" id="correo" name="correo"></imput>
                    <span class="mensaje-error"></span>
                    <br>
                    <label for="contrasena">Contraseña</label><br>
                    <input type="password" id="contrasena" name="contrasena"></imput>
                    <span class="mensaje-error"></span>
                    <br>
                    <c:if test="${not empty mensajeError}">
                        <span class="mensaje-error" style="color:red; display:block; margin-bottom:10px;">${mensajeError}</span>
                    </c:if>
                    <button type="submit">Ingresar</button>
                 </form>         
           </div>
     </div>
<%@ include file="footer.jsp" %>


