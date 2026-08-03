<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="headerGaleria.jsp" %>

        <div class="seccionGaleria">
            <div class="galeria2" data-anijs="if: scroll, on: window, do: animate__fadeIn animated, before: $scrollReveal repeat">
                <h2>Contactar</h2>

                <div class="cardContacto">
                    <p class="cardContactoIntro">Para contratar servicio o realizar consultas. Ingrese sus datos:</p>

                    <c:if test="${not empty mensajeExito}">
                        <div class="mensajeExito">${mensajeExito}</div>
                    </c:if>

                    <c:if test="${not empty mensajeError}">
                        <div class="mensajeError">${mensajeError}</div>
                        <a href="/login" class="btnSubir">Iniciar sesión</a>
                    </c:if>

                    <form method="post" action="/contactar" class="formContactar">
                        <label for="nombre">Nombre y apellido</label><br>
                        <input type="text" id="nombre" name="nombre" required><br>

                        <label for="email">Email</label><br>
                        <input type="email" id="email" name="email" required><br>

                        <label for="telefono">Número de teléfono</label><br>
                        <input type="tel" id="telefono" name="telefono" required><br>

                         <label for="motivo">Motivo de contacto</label><br>
                        <select id="motivo" name="motivo" required>
                            <option value="" disabled selected>Seleccione un motivo</option>
                            <option value="Contratar servicio">Contratar servicio</option>
                            <option value="Consulta">Consulta</option>
                            <option value="Otro">Otro</option>
                        </select><br>


                        <label for="mensaje">Mensaje</label><br>
                        <textarea id="mensaje" name="mensaje" rows="4" required></textarea><br>

                        <div class="botonesForm">
                            <button type="button" onclick="verificarSesionYEnviar()">Enviar</button>
                            <a href="/inicio" class="btnCancelar">Cancelar</a>
                        </div>
                    </form>
                </div>
           </div>
     </div>
<script>
    function goLinkWhatsap() {
        var nombre = document.getElementById('nombre').value.trim();
        var email = document.getElementById('email').value.trim();
        var telefono = document.getElementById('telefono').value.trim();
        var motivo = document.getElementById('motivo').value;
        var mensaje = document.getElementById('mensaje').value.trim();

        if (!nombre || !email || !telefono || !motivo || !mensaje) {
            alert('Por favor, complete todos los campos del formulario.');
            return;
        }

        var texto = '*Nuevo mensaje de contacto*%0A%0A'
            + '*Nombre y apellido:* ' + encodeURIComponent(nombre) + '%0A'
            + '*Email:* ' + encodeURIComponent(email) + '%0A'
            + '*Teléfono:* ' + encodeURIComponent(telefono) + '%0A'
            + '*Motivo:* ' + encodeURIComponent(motivo) + '%0A'
            + '*Mensaje:* ' + encodeURIComponent(mensaje);

        var url = 'https://wa.me/543482673265?text=' + texto;
        window.open(url, '_blank');
    }

    function verificarSesionYEnviar() {
        var logueado = '${sessionScope.logueado}';
        if (logueado === 'null' || logueado === '') {
            alert('Debes iniciar sesión para contactar al servicio.');
            window.location.href = '/login';
            return;
        }
        goLinkWhatsap();
    }
</script>
<%@ include file="footer.jsp" %>