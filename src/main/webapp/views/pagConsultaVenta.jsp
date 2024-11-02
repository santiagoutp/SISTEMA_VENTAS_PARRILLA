<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Consulta Venta</title>
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
                                        <h5 class="card-title"><i class="fas fa-shopping-cart"></i> Consulta Ventas</h5>
                                    </div>
                                    <div class="card-body">
                                        <form method="post" action="venta" class="mt-3">
                                            <div class="row">
                                                <div class="form-group col-md-2">
                                                    <label>Fecha Inicio:</label>
                                                    <input value="${inicio}" name="inicio" type="date" class="form-control" required="" >
                                                </div>

                                                <div class="form-group col-md-2">
                                                    <label>Fecha Fin:</label>
                                                    <input value="${fin}" name="fin"  type="date" class="form-control" required="" >
                                                </div>

                                                <div class="form-group col-md-8 mt-4" style="text-align: right;">
                                                    <input type="hidden" name="accion" value="consulta">
                                                    <button  type="submit" class="btn btn-primary btn-sm">
                                                        <i class="fa fa-search"></i> Consultar
                                                    </button>
                                                </div>

                                            </div>
                                        </form>

                                        <hr />

                                        <div class="table-responsive mt-2">
                                            <table class="tabla table table-bordered table-hover table-striped mt-3 data_tabla" >
                                                <thead class="bg-primary text-white">
                                                    <tr>
                                                        <th style="color: white;"># Venta</th>
                                                        <th style="color: white;">Cliente</th>
                                                        <th style="color: white;">Vendedor</th>
                                                        <th style="color: white;">Fecha</th>
                                                        <th style="color: white;">Total</th>
                                                        <th style="color: white;">Estado</th>
                                                        <th style="color: white;">Ver detalle</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${ventas}" var="item" varStatus="loop">
                                                        <tr>
                                                            <td>${item.idVenta}</td>
                                                            <td>${item.nombreCliente}</td>
                                                            <td>${item.nombreVendedor}</td>
                                                            <td>${item.fechaVenta}</td>
                                                            <td>${item.valorPagar}</td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${item.estado == 1}">
                                                                        <span class="badge badge-primary">Vendido</span>
                                                                    </c:when>
                                                                    <c:when test="${item.estado == 0}">
                                                                        <span class="badge badge-danger">Anulado</span>
                                                                    </c:when>
                                                                </c:choose>
                                                            </td>
                                                            <td>
                                                                <button     onclick="fnCargarDetalle(${item.idVenta})" type="button"  class="btn btn-info btn-sm" title="Ver detalle">
                                                                    <i class="fa fa-info-circle" ></i>
                                                                </button>
                                                                <button ${item.estado == 0 ? "disabled": ""}
                                                                    onclick="fnAnularVenta(${item.idVenta},'${inicio}','${fin}')" type="button" class="btn btn-danger btn-sm" title="Anular venta">
                                                                    <i class="fa fa-times-circle"></i>
                                                                </button>
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

        <div class="modal fade" id="modalDetalle" data-backdrop="static" data-keyboard="false" tabindex="-1">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header bg-primary text-white">
                        <h5 class="modal-title">::: Detalle Venta :::</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="modal-body">
                        <div class="mb-3">
                            <p><strong>Número de Venta:</strong> <span id="nroVenta"></span></p>
                            <p><strong>Cliente:</strong> <span id="nombreCliente"></span></p>
                            <p><strong>Vendedor:</strong> <span id="nombreVendedor"></span></p>
                            <p><strong>Estado:</strong> <span id="estadoVenta"></span></p>
                            <p><strong>Fecha:</strong> <span id="fechaVenta"></span></p>
                        </div>

                        <div class="table-responsive mt-4">
                            <table class="table table-bordered">
                                <thead class="bg-primary ">
                                    <tr> 
                                        <th class="text-white">ITEM</th>
                                        <th class="text-white">IMAGEN</th>
                                        <th class="text-white">CATEGORIA</th>
                                        <th class="text-white">DESCRIPCIÓN</th>
                                        <th class="text-white">VALOR UNIT.</th>
                                        <th class="text-white">CANTIDAD</th>
                                        <th class="text-white">SUB TOTAL</th>
                                    </tr>
                                </thead>
                                <tbody id="resultadoDetalle">
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal">
                            <i class="fa fa-times"></i>&nbsp; Cerrar
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="../includes/js.jsp" />

    </body>
    <script>
        function fnCargarDetalle(id) {
            $.ajax({
                url: 'venta',
                type: 'POST',
                data: {
                    accion: "consulta_comprobante",
                    id: id
                },
                success: function (response) {
                    $('#nroVenta').text(response.idVenta);
                    $('#nombreCliente').text(response.nombreCliente);
                    $('#nombreVendedor').text(response.nombreVendedor);
                    $('#estadoVenta').text(response.estado === 1 ? "Vendido" : "Anulado");
                    $('#fechaVenta').text(response.fechaVenta);

                    $('#modalDetalle').modal("show");
                    var result = "";
                    $.each(response.detalles, function (index, item) {
                        result += "<tr> ";
                        result += "<td>" + (index + 1) + "</td>";
                        result += "<td><img src='assets/img/producto/" + item.producto.imagen + "' onerror=\"this.src='assets/img/img_not_found.jpg'\" style='width: 90px; height: 90px;'/></td>";
                        result += "<td>" + item.producto.nomCateg + "</td>";
                        result += "<td>" + item.producto.nombre + "</td>";
                        result += "<td>" + (item.producto.precioCnIGV).toFixed(2) + "</td>";
                        result += "<td>" + item.cantidad + "</td>";
                        result += "<td>" + (item.producto.precioCnIGV * item.cantidad).toFixed(2) + "</td>";
                        result += "</tr>";
                    });


                    $('#resultadoDetalle').empty();
                    $('#resultadoDetalle').append(result);
                },
                error: function () {
                    fnAlert('error', 'Error al consultar detalle');
                }
            });
        }

        function fnAnularVenta(id,inicio,fin) {
            Swal.fire({
                title: 'Anulación de venta',
                text: '¿Está seguro que desea anular la venta con el id ' + id + '?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#28a745',
                cancelButtonColor: '#dc3545',
                confirmButtonText: 'Sí, eliminarlo!',
                cancelButtonText: 'Cancelar',
                background: '#f8f9fa'
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = 'venta?accion=anular&id=' + id+"&inicio="+inicio+"&fin="+fin;
                }
            });
        }
    </script>
</html>
