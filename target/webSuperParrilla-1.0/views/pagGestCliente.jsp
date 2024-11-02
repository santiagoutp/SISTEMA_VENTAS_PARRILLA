<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Gestión de Clientes</title>
        <meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
        <jsp:include page="../includes/css.jsp" />
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
                                <div class="card">
                                    <div class="card-header">
                                        <h5 class="card-title"><i class="flaticon-list"></i> Gestión de Clientes</h5>
                                    </div>
                                    <div class="card-body">
                                        <a href="cliente?accion=nuevo" class="btn btn-success btn-sm">
                                            <i class="fa fa-plus-circle"></i> Nuevo
                                        </a>

                                        <a href="cliente?accion=exportar_excel" 
                                           class="btn btn-sm btn-excel-green" target="_blank">
                                            <i class="fa fa-file-excel"></i> Exportar Excel
                                        </a>

                                        <div class="table-responsive mt-2">
                                            <table id="tabla" class="table table-bordered table-striped data_tabla">
                                                <thead class="bg-primary">
                                                    <tr>
                                                        <th class="text-white">ID</th>
                                                        <th class="text-white">Nombre</th>
                                                        <th class="text-white">Apellidos</th>
                                                        <th class="text-white">DNI</th>
                                                        <th class="text-white">Teléfono</th>
                                                        <th class="text-white">Dirección</th>
                                                        <th class="text-white">Estado</th>
                                                            <c:if test="${sessionScope.esAdmin == true}">
                                                            <th class="text-white">Acción</th>
                                                            </c:if>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${clientes}" var="item">
                                                        <tr>
                                                            <td>${item.idCliente}</td>
                                                            <td>${item.nombre}</td>
                                                            <td>${item.apellidos}</td>
                                                            <td>${item.dni}</td>
                                                            <td>${item.telefono}</td>
                                                            <td>${item.direccion}</td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${item.estado == 1}">
                                                                        <span class="badge badge-primary">Activo</span>
                                                                    </c:when>
                                                                    <c:when test="${item.estado == 0}">
                                                                        <span class="badge badge-danger">Inactivo</span>
                                                                    </c:when>
                                                                </c:choose>
                                                            </td>
                                                            <c:if test="${sessionScope.esAdmin == true}">
                                                                <td>
                                                                    <a href="cliente?accion=editar&id=${item.idCliente}"
                                                                       class="btn btn-info btn-sm">
                                                                        <i class="fa fa-edit"></i>
                                                                    </a>
                                                                    <a href="#"
                                                                       onclick="confirmEliminar(${item.idCliente})"
                                                                       class="btn btn-danger btn-sm">
                                                                        <i class="fa fa-trash"></i>
                                                                    </a>
                                                                </td> 
                                                            </c:if>

                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <jsp:include page="../includes/js.jsp" />

            <script>
                function confirmEliminar(id) {
                    Swal.fire({
                        title: '¿Está seguro?',
                        text: '¿Desea eliminar el cliente con id ' + id + '?',
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#28a745',
                        cancelButtonColor: '#dc3545',
                        confirmButtonText: 'Sí, eliminarlo!',
                        cancelButtonText: 'Cancelar',
                        background: '#f8f9fa'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            window.location.href = 'cliente?accion=eliminar&id=' + id;
                        }
                    });
                }
            </script>
    </body>
</html>
