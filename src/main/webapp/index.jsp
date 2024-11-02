
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Login</title>
        <meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
        <link rel="icon" href="../assets/img/icon.ico" type="image/x-icon"/>
        <jsp:include page="includes/css.jsp" />
    </head>
    <style>
        .fondo{
            background-image:url(assets/img/fondo.jpg);
            position: relative;
            background-position: center;
            background-repeat: no-repeat;
            background-size: cover;
            overflow: hidden;
            height:100vh;
        }
    </style>
    <body class="login fondo">
         <jsp:include page="includes/mensaje.jsp" />
        <div class="wrapper wrapper-login">
            <div class="container container-login animated fadeIn">
                <div class="text-center">
                    <img src="assets/img/logo.png" style="width: 180px; height: 170px;"/>
                </div>
                <form action="auth" method="post">
                    <div class="login-form">
                        <div class="form-group">
                            <label for="username" class="placeholder"><b>Usuario:</b></label>
                            <input value="${username}" id="username" name="username" type="text" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="password" class="placeholder"><b>Contraseña:</b></label>
                            <div class="position-relative">
                                <input value="" id="password" name="password" type="password" class="form-control" required>
                                <div class="show-password">
                                    <i class="flaticon-interface"></i>
                                </div>
                            </div>
                        </div>
                        <div class="form-group form-action-d-flex mb-3">
                            <div class="custom-control custom-checkbox">
                            </div>
                            <input type="hidden" name="accion" value="autentificarse">
                            <button class="btn btn-primary col-md-5 float-right mt-3 mt-sm-0 fw-bold">Iniciar Sesión</button>
                        </div>
                    </div>
                </form>
            </div>

        </div>
        <jsp:include page="includes/js.jsp" />
    </body>
</html>