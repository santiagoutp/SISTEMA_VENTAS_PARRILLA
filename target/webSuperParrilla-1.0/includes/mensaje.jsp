<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test = "${sessionScope.error !=null}">
    <script>
        Swal.fire(
                '¡Advertencia!',
                '${sessionScope.error}',
                'error'
                );
    </script>
</c:if> 

<c:if test = "${sessionScope.success !=null}">
    <script>
        Swal.fire(
                '¡Exito!',
                '${sessionScope.success }',
                'success'
                );
    </script>
</c:if>

<c:if test = "${sessionScope.info !=null}">
    <script>
        Swal.fire(
                '¡Información!',
                '${sessionScope.info }',
                'info'
                );
    </script>
</c:if>

<c:remove var="success" scope="session"/> 
<c:remove var="error" scope="session"/> 
<c:remove var="info" scope="session"/> 