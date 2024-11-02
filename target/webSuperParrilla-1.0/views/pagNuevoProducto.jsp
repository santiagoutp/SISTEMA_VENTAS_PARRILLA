<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Formulario Producto</title>
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
                                        <h5 class="card-title"><i class="flaticon-home"></i> ${producto.idProducto == 0 ? "Nuevo": "Editar"} Producto</h5>
                                    </div>
                                    <form action="producto" enctype="multipart/form-data" method="post">
                                        <div class="card-body">
                                            <div class="row">
                                                <div class="col-sm-6">
                                                    <div class="mb-3">
                                                        <label>Nombre: <span style="color: red;">(*)</span></label>
                                                        <input value="${producto.nombre}" name="nombre" type="text" maxlength="50" 
                                                               class="form-control" required="">
                                                    </div>
                                                </div>
                                                <div class="col-sm-6">
                                                    <div class="mb-3">
                                                        <label>Categoría: <span style="color: red;">(*)</span></label>
                                                        <select class="form-control" name="idCateg" required>
                                                            <option value="">::: Seleccione :::</option>
                                                            <c:forEach var="item" items="${categorias}">
                                                                <option value="${item.idCateg}" 
                                                                        ${item.idCateg == producto.idCateg ? 'selected' : ''}>
                                                                    ${item.nombreCateg}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-sm-6">
                                                    <div class="mb-3">
                                                        <label>Stock: <span style="color: red;">(*)</span></label>
                                                        <input value="${producto.stock}" name="stock" type="number" min="0" 
                                                               class="form-control" required="">
                                                    </div>
                                                </div>
                                                <div class="col-sm-6">
                                                    <div class="mb-3">
                                                        <label>Precio: <span style="color: red;">(*)</span></label>
                                                        <input value="${producto.precio}" name="precio" type="number" step="0.01" min="0" 
                                                               class="form-control" required="">
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-sm-12">
                                                    <div class="mb-3">
                                                        <label>Descripción:</label>
                                                        <textarea name="descripcion" class="form-control" rows="3">${producto.descripcion}</textarea>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-sm-6">
                                                    <div class="mb-3">
                                                        <label>Porcentaje IGV: <span style="color: red;">(*)</span></label>
                                                        <input value="${producto.porcentajeIgv}" name="porcentajeIgv" type="number" step="0.01" min="0" max="100" 
                                                               class="form-control" required="">
                                                    </div>
                                                </div>
                                                <div class="col-sm-6">
                                                    <div class="mb-3">
                                                        <label>Estado: <span style="color: red;">(*)</span></label>
                                                        <select class="form-control" name="estado" required>
                                                            <option value="">::: Seleccione :::</option>
                                                            <option value="1" ${producto.estado == 1 ? "selected":""}>Activo</option>
                                                            <option value="0" ${producto.estado == 0 ? "selected":""}>Inactivo</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-sm-12">
                                                    <div class="mb-3">
                                                        <label>Imagen:</label>
                                                        <input type="file" name="imagen" class="form-control" accept="image/*" id="inputImagen">
                                                        <div style="margin-top: 10px; max-width: 100%; overflow: hidden;"> <!-- Contenedor para la imagen -->
                                                            <img id="vistaPrevia" src="assets/img/producto/${producto.imagen}" 
                                                                 onerror="src='assets/img/img_not_found.jpg'"
                                                                 alt="Vista Previa" style="display: ${producto.imagen != null ? 'block' : 'none'}; width: 100%; height: auto;">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-sm-12">
                                                    <div class="mb-3">
                                                        <input type="hidden" name="id" value="${producto.idProducto}">
                                                        <input type="hidden" name="accion" value="guardar">
                                                        <button class="btn btn-primary btn-sm">
                                                            <i class="fa fa-save"></i> Guardar
                                                        </button>
                                                        <a href="producto?accion=listar" 
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
    <script>
        document.getElementById('inputImagen').addEventListener('change', function (event) {
            const file = event.target.files[0]; 
            const vistaPrevia = document.getElementById('vistaPrevia'); 

            if (file) {
                const reader = new FileReader(); 

                reader.onload = function (e) {
                    vistaPrevia.src = e.target.result; 
                    vistaPrevia.style.display = 'block'; 
                }

                reader.readAsDataURL(file); 
            } else {
                vistaPrevia.style.display = 'none';
            }
        });
    </script>
</html>
