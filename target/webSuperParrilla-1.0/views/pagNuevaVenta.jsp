<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Nueva Venta</title>
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
                                        <h5 class="card-title"><i class="fas fa-shopping-cart"></i> Nueva Venta</h5>
                                    </div>
                                    <div class="card-body">
                                        <form method="post" action="venta" onsubmit="return fnValidarProceso()">
                                            <div class="row g-3">
                                                <div class="col-md-6">
                                                    <label  class="form-label">Vendedor:  <span style="color: red;">(*)</span></label>
                                                    <select class="form-control form-select" required="" name="vendedor" id="vendedor" >
                                                        <option value="">:::Seleccione:::</option>
                                                        <c:forEach var="item" items="${vendedores}" >
                                                            <option  value="${item.idUsuario}"
                                                                     ${item.idUsuario == vendedorSelected ?"selected": ""}
                                                                     >${item.apellidos} , ${item.nombres}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <div class="col-md-6">
                                                    <label class="form-label">Cliente: <span style="color: red;">(*)</span></label>
                                                    <div class="input-group mb-6">
                                                        <input type="text" name="lbCliente" id="lbCliente" readonly="" class="form-control" disabled="">
                                                        <a href="#" class="btn btn-default" title="Buscar Cliente" data-toggle="modal" data-target="#modalClientes">
                                                            <i class="fa fa-search"></i>
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>

                                            <br>

                                            <div class="row g-3">
                                                <div class="col-sm-5">
                                                    <label  class="form-label">Producto: </label>
                                                    <div class="input-group mb-6">
                                                        <input type="text" name="lbProducto" id="lbProducto" readonly="" class="form-control" disabled="">
                                                        <a href="#" class="btn btn-default" title="Buscar Productos" data-toggle="modal" data-target="#modalProductos">
                                                            <i class="fa fa-search"></i>
                                                        </a>
                                                    </div>
                                                </div>

                                                <div class="col-sm-2">
                                                    <label class="form-label">Precio (S/):</label>
                                                    <input type="text" class="form-control"  readonly name="lbPrecio" id="lbPrecio" disabled="" >
                                                </div>
                                                <div class="col-sm-2">
                                                    <label class="form-label">Stock:</label>
                                                    <input type="text" class="form-control"  readonly name="lbStock" id="lbStock" disabled="" >
                                                </div>

                                                <div class="col-sm-3">
                                                    <label class="form-label">Cantidad:</label>
                                                    <input type="number" class="form-control"  name="cantidad" id="cantidad" min="1" >
                                                </div>
                                            </div>

                                            <div class="row g-3 mt-3">
                                                <div class="col-sm-12">
                                                    <div class="d-grid gap-2 d-md-flex justify-content-md-end">

                                                        <button class="btn btn-success  btn-sm" type="button" onclick="fnAgregarItem()">
                                                            <i class="fa fa-plus-circle"></i> Agregar
                                                        </button>
                                                        &nbsp;
                                                        <button class="btn btn-default btn-sm" type="button" onclick="fnCancelar()" id="btnCancelar">
                                                            <i class="fa fa-times"></i> Cancelar
                                                        </button>
                                                        &nbsp;
                                                        <button class="btn btn-primary btn-sm" type="submit" id="btnProcesar" >
                                                            <i class="fa fa-save"></i> Realizar Compra
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                            <input type="hidden"  name="lbIdProd" id="lbIdProd" >
                                            <input type="hidden"  name="lbIdCliente" id="lbIdCliente" >
                                            <input type="hidden" name="accion" value="procesar">
                                        </form>

                                        <div class="table-responsive mt-4">
                                            <table class="table table-bordered">
                                                <thead>
                                                    <tr> 
                                                        <th style="background-color: #5272b9;color: white">ITEM</th>
                                                        <th style="background-color: #5272b9;color: white">IMAGEN</th>
                                                        <th style="background-color: #5272b9;color: white">CATEGORIA</th>
                                                        <th style="background-color: #5272b9;color: white">DESCRIPCIÓN</th>
                                                        <th style="background-color: #5272b9;color: white">VALOR UNIT.</th>
                                                        <th style="background-color: #5272b9;color: white">CANTIDAD</th>
                                                        <th style="background-color: #5272b9;color: white">SUB TOTAL</th>
                                                        <th style="background-color: #5272b9;color: white"></th>
                                                    </tr>
                                                </thead>
                                                <tbody id="resultadoDetalle">
                                                    <tr>
                                                        <td colspan='9' class='text-center'>Sin resultados</td>
                                                    </tr>
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

        <div class="modal fade" id="modalProductos" data-backdrop="static" data-keyboard="false" tabindex="-1"  data-keyboard="false">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header bg-primary text-white">
                        <h5 class="modal-title" >::: Productos disponibles :::</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="modal-body">
                        <div class="table-responsive mt-4">
                            <table class="table  table-striped table-bordered text-center data_tabla">
                                <thead>
                                    <tr>
                                        <th  class="text-center" style="width: 5px;">Seleccionar</th>  
                                        <th  class="text-center">Imagen</th>
                                        <th  class="text-center">Categoria</th>
                                        <th  class="text-center">Nombre</th>
                                        <th  class="text-center">Precio</th>
                                        <th  class="text-center">Stock</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${productos}"  >
                                        <tr>
                                            <td style="width: 5px;">
                                                <a href="javascript:fnCargarProducto(${item.idProducto},'${item.nombre}',${item.precioCnIGV},${item.stock})" class="btn btn-primary btn-sm" title="Seleccionar">
                                                    <i class="fa fa-check-circle"></i>
                                                </a>
                                            </td>
                                            <td>
                                                <img src="assets/img/producto/${item.imagen}" 
                                                     onerror="src='assets/img/img_not_found.jpg'"
                                                     style="width: 90px; height: 90px;"/>
                                            </td>
                                            <td>${item.nomCateg}</td>
                                            <td>${item.nombre}</td>
                                            <td><fmt:formatNumber value="${item.precioCnIGV}" type="number" minFractionDigits="2" maxFractionDigits="2"/></td>
                                            <td>${item.stock}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal">
                            <i class="fa fa-times"></i>&nbsp; Cancelar</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="modalClientes" data-backdrop="static" data-keyboard="false" tabindex="-1">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header bg-primary text-white">
                        <h5 class="modal-title">::: Clientes disponibles :::</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="modal-body">
                        <div class="table-responsive mt-4">
                            <table class="table table-striped table-bordered text-center data_tabla">
                                <thead>
                                    <tr>
                                        <th class="text-center">Seleccionar</th>
                                        <th class="text-center">Apellidos</th>
                                        <th class="text-center">Nombre</th>
                                        <th class="text-center">Teléfono</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${clientes}">
                                        <tr>
                                            <td>
                                                <a href="javascript:fnCargarCliente(${item.idCliente},'${item.nombre}','${item.apellidos}')" class="btn btn-primary btn-sm" title="Seleccionar">
                                                    <i class="fa fa-check-circle"></i>
                                                </a>
                                            </td>
                                            <td>${item.apellidos}</td>
                                            <td>${item.nombre}</td>
                                            <td>${item.telefono}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal">
                            <i class="fa fa-times"></i>&nbsp; Cancelar
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="../includes/js.jsp" />


    </body>
    <script>
        function fnCargarProducto(codigo, nombre, precio, stock) {
            $("#lbProducto").val(nombre);
            $("#lbPrecio").val(precio);
            $("#lbStock").val(stock);
            $("#lbIdProd").val(codigo);
            $("#modalProductos").modal("hide");
        }

        function fnCargarCliente(idCliente, nombre, apellidos) {
            $("#lbCliente").val(apellidos + ", " + nombre);
            $("#lbIdCliente").val(idCliente);
            $("#modalClientes").modal("hide");
        }

        function fnValidarProceso() {
            var idCliente = $("#lbIdCliente").val();

            if (!idCliente) {
                fnAlert("info", "Por favor seleccione un cliente.");
                return false;
            }
            return true;
        }

        function fnAgregarItem() {
            var cantidad = $("#cantidad").val();
            var idProd = $("#lbIdProd").val();

            if (!idProd) {
                fnAlert("info", "Debe ingresar un producto.");
                return;
            }

            if (!cantidad) {
                fnAlert("info", "Ingrese una cantidad");
                return;
            }

            $.ajax({
                url: 'venta',
                type: 'POST',
                data: {
                    accion: "agregarItemDetalle",
                    id: idProd,
                    cantidad: cantidad
                },
                success: function (response) {
                    if (response.msg === "OK") {
                        fnCargarDetalle();
                    } else {
                        fnAlert('error', response.msg);
                    }
                },
                error: function () {
                    fnAlert('error', 'Error al seleccionar item catalogo');
                }
            });
        }


        function fnCargarDetalle() {
            $('#btnProcesar').prop('disabled', true);
            $('#btnCancelar').prop('disabled', true);

            $.ajax({
                url: 'venta',
                type: 'GET',
                data: {
                    accion: 'listarDetalle'
                },
                dataType: 'json',
                success: function (response) {
                    $('#resultadoDetalle').empty();
                    var result = "";
                    if (response.data.length > 0) {
                        $.each(response.data, function (index, item) {
                            result += "<tr> ";
                            result += "<td>" + (index + 1) + "</td>";
                            result += "<td><img src='assets/img/producto/" + item.producto.imagen + "' onerror=\"this.src='assets/img/img_not_found.jpg'\" style='width: 90px; height: 90px;'/></td>";
                            result += "<td>" + item.producto.nomCateg + "</td>";
                            result += "<td>" + item.producto.nombre + "</td>";
                            result += "<td>" + (item.producto.precioCnIGV).toFixed(2) + "</td>";
                            result += "<td>" + item.cantidad + "</td>";
                            result += "<td>" + (item.producto.precioCnIGV * item.cantidad).toFixed(2) + "</td>";
                            result += "<td class='text-center'><button type='button'  onclick='fnEliminarItem(" + index + ")' class='btn btn-danger btn-sm' title='Quitar item'><i class='fa fa-trash'></i></button></td>";
                            result += "</tr>";
                        });

                        result += "<tr> ";
                        result += "<td colspan='6' class='text-rigth' style='font-weight: bold;'>Sub Total:</td>";
                        result += "<td colspan='2' class='text-left' style='font-weight: bold;'>" + (response.subTotal).toFixed(2) + "</td>";
                        result += "</tr>";

                        result += "<tr> ";
                        result += "<td colspan='6' class='text-rigth' style='font-weight: bold;'>IGV:</td>";
                        result += "<td colspan='2' class='text-left' style='font-weight: bold;'>" + (response.igv).toFixed(2) + "</td>";
                        result += "</tr>";

                        result += "<tr> ";
                        result += "<td colspan='6' class='text-rigth' style='font-weight: bold;'>Total:</td>";
                        result += "<td colspan='2' class='text-left' style='font-weight: bold;'>" + (response.total).toFixed(2) + "</td>";
                        result += "</tr>";

                        $('#btnProcesar').prop('disabled', false);
                        $('#btnCancelar').prop('disabled', false);
                    } else {
                        result += "<tr> ";
                        result += "<td colspan='9' class='text-center'>Sin resultados</td>";
                        result += "</tr>";
                    }

                    $('#resultadoDetalle').append(result);
                },
                error: function () {
                    fnAlert('error', 'Error al cargar detalle');
                }
            });
        }

        function fnEliminarItem(index) {
            $.ajax({
                url: 'venta',
                type: 'POST',
                data: {
                    accion: "eliminarItemDetalle",
                    index: index
                },
                success: function (response) {
                    if (response.msg === "OK") {
                        $("#modalProductos").modal("hide");
                        fnCargarDetalle();
                    } else {
                        fnAlert('error', response.msg);
                    }
                },
                error: function () {
                    fnAlert('error', 'Error al quitar item producto');
                }
            });
        }

        function fnCancelar() {
            $.ajax({
                url: 'venta',
                type: 'POST',
                data: {
                    accion: "eliminarDetalle"
                },
                success: function (response) {
                    if (response.msg === "OK") {
                        $("#modalProductos").modal("hide");
                        fnCargarDetalle();
                    } else {
                        fnAlert('error', response.msg);
                    }
                },
                error: function () {
                    fnAlert('error', 'Error al quitar producto');
                }
            });
        }

        fnCargarDetalle();
    </script>
</html>
