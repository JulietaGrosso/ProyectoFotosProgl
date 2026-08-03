<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="headerGaleria.jsp" %>

        <div class="seccionGaleria">
            <div class="galeria2" data-anijs="if: scroll, on: window, do: animate__fadeIn animated, before: $scrollReveal repeat">
                
                <c:if test="${not empty mensajeError}">
                    <div style="color: red; font-weight: bold; margin-bottom: 10px;">
                        ${mensajeError}
                    </div>
                </c:if>

                 <form method="post" enctype="multipart/form-data" class="formSubir">
                    <label for="foto">Foto</label><br>
                    <input type="file" id="foto" name="foto" required/>
                    <br>
                    <label for="nombre">Nombre</label><br>
                    <input id="nombre" name="nombre" required/>
                    <br>
                     <label for="alt">Alt</label><br>
                    <input type="text" id="alt" name="alt" required/>
                    <br>
                    <div class="botonesForm">
                        <button type="submit" class="btnSubir">Subir Imagen</button>
                        <a href="galeria" class="btnCancelar">Cancelar</a>
                    </div>
                 </form>
           </div>
     </div>
<%@ include file="footer.jsp" %>


