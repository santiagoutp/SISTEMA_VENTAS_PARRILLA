<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Formulario Usuario</title>
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
                                        <h5 class="card-title"><i class="flaticon-home"></i> ${usuario.idUsuario == 0 ? "Nuevo": "Editar"} Usuario</h5>
                                    </div>
                                    <form>
                                        <div class="card-body">
                                            <div class="row">
                                                <div class="col-sm-6">
                                                    <div class="mb-3">
                                                        <label>Nombres: <span style="color: red;">(*)</span></label>
                                                        <input value="${usuario.nombres}" name="nombres" type="text" maxlength="50" 
                                                               class="form-control" required="">
                                                    </div>
                                                </div>
                                                <div class="col-sm-6">
                                                    <div class="mb-3">
                                                        <label>Apellidos: <span style="color: red;">(*)</span></label>
                                                        <input value="${usuario.apellidos}" name="apellidos" type="text" maxlength="50" 
                                                               class="form-control" required="">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-sm-6">
                                                    <div class="mb-3">
                                                        <label>Telefono: <span style="color: red;">(*)</span></label>
                                                        <input value="${usuario.telefono}" name="telefono" type="text" maxlength="15" 
                                                               class="form-control" required="">
                                                    </div>
                                                </div>
                                                <div class="col-sm-3">
                                                    <div class="mb-3">
                                                        <label>Rol: <span style="color: red;">(*)</span></label>
                                                        <select class="form-control" name="rol">
                                                            <option value="">::: Seleccione :::</option>
                                                            <c:forEach var="item" items="${roles}" >
                                                                <option value="${item.idRol}"
                                                                        ${item.idRol == usuario.idRol ? 'selected' : ''}>${item.nombreRol}</option> 
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="col-sm-3">
                                                    <div class="mb-3">
                                                        <label>Estado: <span style="color: red;">(*)</span></label>
                                                        <select class="form-control" name="estado">
                                                            <option value="">::: Seleccione :::</option>
                                                            <option value="1" ${usuario.estado == 1 ? "selected":""}>Activo</option>
                                                            <option value="0" ${usuario.estado == 0 ? "selected":""}>Inactivo</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-sm-6">
                                                    <div class="mb-3">
                                                        <label>Username: <span style="color: red;">(*)</span></label>
                                                        <input value="${usuario.username}" name="username" type="text" maxlength="50" 
                                                               class="form-control" required="">
                                                    </div>
                                                </div>
                                                <div class="col-sm-6">
                                                    <div class="mb-3">
                                                        <label>Password <span style="color: red;">(*)</span></label>
                                                        <input value="${usuario.password}" name="password" type="password" maxlength="50" 
                                                               class="form-control" required="">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-sm-12">
                                                    <div class="mb-3">
                                                        <input type="hidden" name="id" value="${usuario.idUsuario}">
                                                        <input type="hidden" name="accion" value="guardar">
                                                        <button class="btn btn-primary btn-sm">
                                                            <i class="fa fa-save"></i> Guardar
                                                        </button>
                                                        <a href="usuario?accion=listar" 
                                                           class="btn btn-dark btn-sm">
                                                            <i class="fa fa-arrow-left"></i> Volver atras
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