<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- Determinar el código de error --%>
<%
    Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
    String requestUri = (String) request.getAttribute("jakarta.servlet.error.request_uri");
    Throwable exception = (Throwable) request.getAttribute("jakarta.servlet.error.exception");

    String errorIcon, errorTitulo, errorMensaje, errorColor;

    if (statusCode != null && statusCode == 404) {
        errorIcon = "fa-solid fa-compass";
        errorTitulo = "Página no encontrada";
        errorMensaje = "La página que buscas no existe o fue movida.";
        errorColor = "#3A5A40";
    } else if (statusCode != null && statusCode == 500) {
        errorIcon = "fa-solid fa-triangle-exclamation";
        errorTitulo = "Error del servidor";
        errorMensaje = "Hubo un problema interno. Intenta nuevamente más tarde.";
        errorColor = "#a3b18a";
    } else if (exception != null) {
        errorIcon = "fa-solid fa-bug";
        errorTitulo = "Error en la aplicación";
        errorMensaje = "Ocurrió un error inesperado en la aplicación.";
        errorColor = "#e74c3c";
    } else {
        errorIcon = "fa-solid fa-triangle-exclamation";
        errorTitulo = "Error inesperado";
        errorMensaje = "Error genérico.";
        errorColor = "#a3b18a";
    }

    pageContext.setAttribute("statusCode", statusCode);
    pageContext.setAttribute("requestUri", requestUri);
    pageContext.setAttribute("errorIcon", errorIcon);
    pageContext.setAttribute("errorTitulo", errorTitulo);
    pageContext.setAttribute("errorMensaje", errorMensaje);
    pageContext.setAttribute("errorColor", errorColor);
%>

<%@ include file="headerGaleria.jsp" %>

        <div class="seccionGaleria">
            <div class="galeria2" data-anijs="if: scroll, on: window, do: animate__fadeIn animated, before: $scrollReveal repeat">
                <div class="errorCard">
                    <i class="${errorIcon} errorIcon" style="color: ${errorColor};"></i>

                    <c:if test="${statusCode != null && statusCode != 0}">
                        <span class="errorBadge">${statusCode}</span>
                    </c:if>

                    <h2 class="errorTitulo">${errorTitulo}</h2>
                    <p class="errorMensaje">${errorMensaje}</p>

                    <c:if test="${statusCode == 404 && not empty requestUri}">
                        <div class="errorUri">
                            <code>${requestUri}</code>
                        </div>
                    </c:if>

                    <div class="errorBotones">
                        <a href="/inicio" class="btnSubir">Ir al inicio</a>
                        <button class="btnSubir" onclick="history.back()">Volver atrás</button>
                    </div>

                    <p class="errorAyuda">Si el problema persiste, contacta al administrador.</p>
                </div>
           </div>
     </div>

<%@ include file="footer.jsp" %>