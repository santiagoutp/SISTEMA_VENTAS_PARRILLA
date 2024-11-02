function fnAlert(tipo, mensaje) {
    if (tipo === "success") {
        Swal.fire(
                '¡Exito!',
                mensaje,
                'success'
                );
    }
    if (tipo === "info") {
        Swal.fire(
                '¡Información!',
                mensaje,
                'info'
                );
    }
    if (tipo === "error") {
        Swal.fire(
                '¡Advertencia!',
                mensaje,
                'error'
                );
    }
}