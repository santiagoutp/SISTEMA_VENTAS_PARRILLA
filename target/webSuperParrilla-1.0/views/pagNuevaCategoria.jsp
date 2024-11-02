<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Formulario Categoría</title>
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
                                        <h5 class="card-title">
                                            <i class="flaticon-home"></i> ${categoria.idCateg == 0 ? "Nueva": "Editar"} Categoría
                                        </h5>
                                    </div>
                                    <form method="post" action="categoria?accion=guardar">
                                        <div class="card-body">
                                            <div class="row">
                                                <div class="col-sm-6">
                                                    <div class="mb-3">
                                                        <label>Nombre Categoría: <span style="color: red;">(*)</span></label>
                                                        <input value="${categoria.nombreCateg}" name="nombre" type="text" maxlength="50" 
                                                               class="form-control" required="">
                                                    </div>
                                                </div>
                                                <div class="col-sm-6">
                                                    <div class="mb-3">
                                                        <label>Estado: <span style="color: red;">(*)</span></label>
                                                        <select class="form-control" name="estado" required="">
                                                            <option value="">::: Seleccione :::</option>
                                                            <option value="1" ${categoria.estado == 1 ? "selected":""}>Activo</option>
                                                            <option value="0" ${categoria.estado == 0 ? "selected":""}>Inactivo</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-sm-12">
                                                    <div class="mb-3">
                                                        <input type="hidden" name="id" value="${categoria.idCateg}">
                                                        <button class="btn btn-primary btn-sm">
                                                            <i class="fa fa-save"></i> Guardar
                                                        </button>
                                                        <a href="categoria?accion=listar" 
                                                           class="btn btn-dark btn-sm">
                                                            <i class="fa fa-arrow-left"></i> Volver atrás
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
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
