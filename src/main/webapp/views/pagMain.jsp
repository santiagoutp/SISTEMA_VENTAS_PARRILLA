<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>Inicio</title>
    <meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
    <jsp:include page="../includes/css.jsp" />
    <style>
        .welcome-card {
            background-color: #f8f9fa;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        .welcome-card h5 {
            font-size: 24px;
            margin-bottom: 20px;
        }
        .permissions {
            margin-top: 20px;
            font-size: 18px;
            color: #343a40;
        }
        .permission-item {
            background-color: #e9ecef;
            padding: 10px;
            margin: 5px 0;
            border-radius: 5px;
        }
        .role-label {
            font-weight: bold;
            font-size: 20px;
        }
    </style>
</head>
<body>
    <div class="wrapper">
        <jsp:include page="../includes/navegacion.jsp" />
        <jsp:include page="../includes/mensaje.jsp" />

        <div class="main-panel">
            <div class="content">
                <div class="page-inner">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card welcome-card">
                                <div class="card-header">
                                    <h5 class="card-title">Bienvenido, ${usuario.nombres} ${usuario.apellidos}!</h5>
                                </div>
                                <div class="card-body">
                                    <p class="role-label">Tu rol: ${usuario.nombreRol}</p>
                                    <div class="permissions">
                                        <h6>Permisos:</h6>
                                        <c:choose>
                                            <c:when test="${usuario.idRol == 1}">
                                                <div class="permission-item">✔️ Gestión total (Clientes, Usuarios, Categorías, Productos, Ventas)</div>
                                            </c:when>
                                            <c:when test="${usuario.idRol == 2}">
                                                <div class="permission-item">✔️ Registro de Venta</div>
                                                <div class="permission-item">✔️ Consulta de Venta</div>
                                                <div class="permission-item">✔️ Registro de Cliente</div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="permission-item">🔒 Sin permisos específicos.</div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="../includes/js.jsp" />
</body>
</html>
