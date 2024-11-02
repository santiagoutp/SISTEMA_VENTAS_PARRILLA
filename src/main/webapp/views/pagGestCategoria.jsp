<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Gest. Categorías</title>
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
                                        <h5 class="card-title"><i class="flaticon-list"></i> Gestión de Categorías</h5>
                                    </div>
                                    <div class="card-body">
                                        <a href="categoria?accion=nuevo" class="btn btn-success btn-sm">
                                            <i class="fa fa-plus-circle"></i> Nuevo
                                        </a>

                                        <div class="table-responsive mt-2">
                                            <table id="tabla" class="table table-bordered table-striped data_tabla">
                                                <thead class="bg-primary">
                                                    <tr>
                                                        <th class="text-white">ID</th>
                                                        <th class="text-white">Nombre</th>
                                                        <th class="text-white">Estado</th>
                                                        <th class="text-white">Acción</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${categorias}" var="item">
                                                        <tr>
                                                            <td>${item.idCateg}</td>
                                                            <td>${item.nombreCateg}</td>
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
                                                            <td>
                                                                <a href="categoria?accion=editar&id=${item.idCateg}"
                                                                   class="btn btn-info btn-sm">
                                                                    <i class="fa fa-edit"></i>
                                                                </a>
                                                                <a href="#" 
                                                                   onclick="confirmEliminar(${item.idCateg})"
                                                                   class="btn btn-danger btn-sm">
                                                                    <i class="fa fa-trash"></i>
                                                                </a>
                                                            </td>
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
        </div>

        <jsp:include page="../includes/js.jsp" />

        <script>
            function confirmEliminar(id) {
                Swal.fire({
                    title: '¿Está seguro?',
                    text: '¿Desea eliminar la categoría con id ' + id + '?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#28a745',
                    cancelButtonColor: '#dc3545',
                    confirmButtonText: 'Sí, eliminarla!',
                    cancelButtonText: 'Cancelar',
                    background: '#f8f9fa'
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = 'categoria?accion=eliminar&id=' + id;
                    }
                });
            }
        </script>
    </body>
</html>
