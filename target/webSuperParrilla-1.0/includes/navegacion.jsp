<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="main-header" data-background-color="blue">
    <div class="logo-header">
        <a href="#" class="logo" style="font-weight: bold; color: white;">
            La Gran Parrilla
        </a>
        <button class="navbar-toggler sidenav-toggler ml-auto" type="button" data-toggle="collapse" data-target="collapse" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon">
                <i class="fa fa-bars"></i>
            </span>
        </button>
        <button class="topbar-toggler more"><i class="fa fa-ellipsis-v"></i></button>
        <div class="navbar-minimize">
            <button class="btn btn-minimize btn-rounded">
                <i class="fa fa-bars"></i>
            </button>
        </div>
    </div>

    <nav class="navbar navbar-header navbar-expand-lg">

        <div class="container-fluid">

            <ul class="navbar-nav topbar-nav ml-md-auto align-items-center">
                <li class="nav-item toggle-nav-search hidden-caret">
                    <a class="nav-link" data-toggle="collapse" href="#search-nav" role="button" aria-expanded="false" aria-controls="search-nav">
                        <i class="fa fa-search"></i>
                    </a>
                </li>

                <li class="nav-item dropdown hidden-caret">
                    <a class="dropdown-toggle profile-pic" data-toggle="dropdown" href="#" aria-expanded="false">
                        <div class="avatar-sm">
                            <img src="assets/img/config.jpg" alt="..." class="avatar-img rounded-circle">
                        </div>
                    </a>
                    <ul class="dropdown-menu dropdown-user animated fadeIn">
                        <li>
                            <div class="user-box">
                                <div class="avatar-lg"><img src="assets/img/logo.png" alt="image profile" class="avatar-img rounded"></div>
                                <div class="u-text">
                                    <h4>${sessionScope.usuario.username}</h4>
                                    <p class="text-muted">${sessionScope.usuario.nombres} ${sessionScope.usuario.apellidos}</p>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="auth?accion=logout">Cerrar Sesión</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
</div>

<div class="sidebar">
    <div class="sidebar-wrapper scrollbar-inner">
        <div class="sidebar-content">
            <div class="user">
                <div class="avatar-sm float-left mr-2">
                    <img src="assets/img/logo.png" alt="..." class="avatar-img rounded-circle">
                </div>
                <div class="info">
                    <a data-toggle="collapse" href="#collapseExample" aria-expanded="true">
                        <span>
                            ${sessionScope.usuario.username}
                            <span class="user-level">${sessionScope.usuario.nombreRol}</span>
                        </span>
                    </a>
                    <div class="clearfix"></div>

                </div>
            </div>
            <ul class="nav">
                <li class="nav-item">
                    <a href="auth?accion=main">
                        <i class="fas fa-home"></i>
                        <p>Inicio</p>
                    </a>
                </li>
                <li class="nav-section">
                    <span class="sidebar-mini-icon">
                        <i class="fa fa-ellipsis-h"></i>
                    </span>
                    <h4 class="text-section">Módulos</h4>
                </li>


                <li class="nav-item">
                    <a data-toggle="collapse" href="#forms">
                        <i class="fas fa-tasks"></i>
                        <p>Gestión</p>
                        <span class="caret"></span>
                    </a>
                    <div class="collapse" id="forms">
                        <ul class="nav nav-collapse">
                            <c:if test="${sessionScope.esAdmin == true}">
                                <li>
                                    <a href="categoria?accion=listar">
                                        <span class="sub-item">Categoria</span>
                                    </a>
                                </li>
                                <li>
                                    <a href="producto?accion=listar">
                                        <span class="sub-item">Producto</span>
                                    </a>
                                </li>
                                <li>
                                    <a href="usuario?accion=listar">
                                        <span class="sub-item">Usuario</span>
                                    </a>
                                </li>
                            </c:if>
                            <li>
                                <a href="cliente?accion=listar">
                                    <span class="sub-item">Cliente</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </li>

                <li class="nav-item submenu">
                    <a data-toggle="collapse" href="#base">
                        <i class="fas fa-shopping-cart"></i>
                        <p>Procesos</p>
                        <span class="caret"></span>
                    </a>
                    <div class="collapse" id="base">
                        <ul class="nav nav-collapse">
                            <li>
                                <a href="venta?accion=nueva">
                                    <span class="sub-item">Genera Venta</span>
                                </a>
                            </li>

                            <li>
                                <a href="venta?accion=consulta">
                                    <span class="sub-item">Consulta Venta</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>