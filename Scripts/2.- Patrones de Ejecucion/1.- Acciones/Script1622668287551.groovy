import internal.GlobalVariable as GlobalVariable

Map Configuracion = [:]
GlobalVariable.Accion = Accion

'Debin'
switch (Accion) {
    case 'debin':
        'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
        CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'POST', '/apiDebinV1/Debin/Debin', 'Debin\\Debin.json', [('StatusCode') : 200
                , ('Mensaje') : '{"respuesta": {"codigo": "00","descripcion": "PERSISTIDO"}}'])

        'Corte, todo lo que cree debe ir por encima de esta sentencia.'
        break
    case 'confirmadebito':
        'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
        CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'POST', '/apiDebinV1/Debin/ConfirmaDebito', 'Debin\\ConfirmaDebito.json', 
            [('StatusCode') : 200, ('Mensaje') : '{"codigo":"00","descripcion":"Ok - GARANTIA CORRECTA"}'])

        'Corte, todo lo que cree debe ir por encima de esta sentencia.'
        break
	case 'confirmadebitocvu':
		'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
		CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'POST', '/apiDebinV1/Debin/ConfirmaDebitoCVU', 'Debin\\ConfirmaDebitoCVU.json',
			[('StatusCode') : 200, ('Mensaje') : '{"codigo":"00","descripcion":"SE ACEPTA EL DEBITO Y SE DA CURSO AL CREDITO"}'])

		'Corte, todo lo que cree debe ir por encima de esta sentencia.'
		break
    case 'credito':
        'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
        CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'POST', '/apiDebinV1/Debin/Credito', 'Debin\\Credito.json', [
                ('StatusCode') : 200, ('Mensaje') : '{"respuesta": {"codigo": "00","descripcion": "GARANTIA CORRECTA"}}'])

        'Corte, todo lo que cree debe ir por encima de esta sentencia.'
        break
    case 'contracargo':
        'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
        CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'POST', '/apiDebinV1/Comprador/SolicitudContraCargo', 'Comprador\\ContraCargo.json', 
            [('StatusCode') : 200, ('Mensaje') : '{"respuesta": {"codigo": "5500","descripcion": "CONTRA-CARGO REALIZADO CORRECTAMENTE"}}'])

        'Corte, todo lo que cree debe ir por encima de esta sentencia.'
        break
}

'QR'
switch (Accion) {
    case 'qrdebin':
        'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
        CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'POST', '/apiDebinV1/QR/QRDebin', 'QR\\QRDebin.json', [('StatusCode') : 200
                , ('Mensaje') : '{"respuesta": {"codigo": "7100","descripcion": "CREACION CORRECTA"}}'])

        'Corte, todo lo que cree debe ir por encima de esta sentencia.'
        break
    case 'confirmadebito':
        'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
        CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'POST', '/apiDebinV1/Debin/ConfirmaDebito', 'Debin\\ConfirmaDebito.json', 
            [('StatusCode') : 200, ('Mensaje') : '{"codigo":"00","descripcion":"Ok - GARANTIA CORRECTA"}'])

        'Corte, todo lo que cree debe ir por encima de esta sentencia.'
        break
    case 'cashout':
        'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
        CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'POST', '/apiDebinV1/QR/CashOut', 'QR\\CashOut.json', [('StatusCode') : 200
                , ('Mensaje') : '{"respuesta": {"codigo": "7541","descripcion": "PERSISTIDO"}}'])

        'Corte, todo lo que cree debe ir por encima de esta sentencia.'
        break
    case 'confirmacashout':
        'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
        CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'POST', '/apiDebinV1/QR/ConfirmaCashOut', 'QR\\ConfirmaCashOut.json', 
            [('StatusCode') : 200, ('Mensaje') : '{"respuesta": {"codigo": "7600","descripcion": "OPERACION CORRECTA"}}'])

        'Corte, todo lo que cree debe ir por encima de esta sentencia.'
        break
    case 'qroperacionok':
        'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
        CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'POST', '/apiDebinV1/QR/QROperacionOk', 'QR\\QROperacionOk.json', 
            [('StatusCode') : 200, ('Mensaje') : '{"respuesta": {"codigo": "","descripcion": ""}}'])

        'Corte, todo lo que cree debe ir por encima de esta sentencia.'
        break
    case 'qrsolicitudcontracargo':
        'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
        CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'POST', '/apiDebinV1/QR/QRSolicitudContraCargo', 'QR\\QRSolicitudContraCargo.json', 
            [('StatusCode') : 200, ('Mensaje') : '{"respuesta": {"codigo": "5600","descripcion": "CONTRA-CARGO REALIZADO CORRECTAMENTE"}}'])

        'Corte, todo lo que cree debe ir por encima de esta sentencia.'
        break
    case 'altabilletera':
        'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
        CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'POST', '/apiDebinV1/QR/Billetera', 'QR\\Billetera\\AltaBilletera.json', 
            [('StatusCode') : 200, ('Mensaje') : '{"respuesta": {"codigo": "4600","descripcion": "BILLETERA ADHERIDA"}}'])

        'Corte, todo lo que cree debe ir por encima de esta sentencia.'
        break
    case 'modificacionbilletera':
        'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
        CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'POST', '/apiDebinV1/QR/Billetera/{id}', 'QR\\Billetera\\ModificacionBilletera.json', 
            [('StatusCode') : 200, ('Mensaje') : '{"respuesta": {"codigo": "4700","descripcion": "BILLETERA MODIFICADA"}}'])

        'Corte, todo lo que cree debe ir por encima de esta sentencia.'
        break
    case 'buscarbilleteraxid':
        'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
        CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'GET', '/apiDebinV1/QR/Billetera/{id}', '', [('StatusCode') : 200
                , ('Mensaje') : '{"respuesta": {"codigo": "4900","descripcion": "BILLETERA ENCONTRADA"}}'])

        'Corte, todo lo que cree debe ir por encima de esta sentencia.'
        break
    case 'billeteralista':
        'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
        CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'POST', '/apiDebinV1/QR/Billetera', 'QR\\Billetera\\ListaBilletera.json', 
            [('StatusCode') : 200, ('Mensaje') : '{"respuesta": {"codigo": "4800","descripcion": "BILLETERA LISTA ENCONTRADA"}}'])

        'Corte, todo lo que cree debe ir por encima de esta sentencia.'
        break
    case 'eliminarbilletera':
        'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
        CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'POST', '/apiDebinV1/QR/Billetera/{id}', 'QR\\Billetera\\EliminarBilletera.json', 
            [('StatusCode') : 200, ('Mensaje') : '{"respuesta": {"codigo": "5800","descripcion": "BILLETERA ELIMINADA CON ÉXITO"}}'])

        'Corte, todo lo que cree debe ir por encima de esta sentencia.'
        break
    case 'altacomercio':
        'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
        CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'POST', '/apiDebinV1/QR/Comercio', 'QR\\Comercio\\AltaComercio.json', 
            [('StatusCode') : 200, ('Mensaje') : '{"respuesta": {"codigo": "3500","descripcion": "COMERCIO DADO DE ALTA CON ÉXITO"}}'])

        'Corte, todo lo que cree debe ir por encima de esta sentencia.'
        break
    case 'modificacioncomercio':
        'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
        CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'POST', '/apiDebinV1/QR/Comercio/{CVU}/{CUIT}', 'QR\\Comercio\\ModificacionComercio.json', 
            [('StatusCode') : 200, ('Mensaje') : '{"respuesta": {"codigo": "3600","descripcion": "COMERCIO MODIFICADO CON ÉXITO"}}'])

        'Corte, todo lo que cree debe ir por encima de esta sentencia.'
        break
}

'Vendedor'
switch (Accion) {
	case 'vendedordevolucion':
		'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
		CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'POST', '/apiDebinV1/Vendedor/Devolucion', 'Vendedor\\VendedorDevolucion.json', [('StatusCode') : 200
				, ('Mensaje') : '{"respuesta": {"codigo": "6500","descripcion": "DEVOLUCIÓN REALIZADA CORRECTAMENTE"}}'])

		'Corte, todo lo que cree debe ir por encima de esta sentencia.'
		break
}

'Responder'
switch (Accion) {
    case 'avisodebinpendiente':
        'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
        CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'GET', '/{id}/debin/AvisoDebinPendiente', '', 
            [('StatusCode') : 200, ('Mensaje') : '{"data":[{"receive":{"debin":{"estado":{"codigo":"INICIADO","descripcion":"PERSISTIDO"},"estadoComprador":{"codigo":"00","descripcion":"ADHERIDO"}}}}]}'])

        'Corte, todo lo que cree debe ir por encima de esta sentencia.'
        break
		
		case 'avisocredito':
			'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
			CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'GET', '/{id}/debin/Credito', '',
				[('StatusCode') : 200, ('Mensaje') : '{}'])
	
			'Corte, todo lo que cree debe ir por encima de esta sentencia.'
			break
}

'ISO'
switch (Accion) {
	case 'creditoiso':
		'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
		CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'POST', '/apiDebinV1/ISO/CreditoISO', 'ISO\\Credito.json', [('StatusCode') : 200
				, ('Mensaje') : '{"respuesta":{"codigo": "00","descripcion":"GARANTIA CORRECTA"},"objeto":{"tipo":"TRXISO","estado":{"codigo":"EN CURSO","descripcion":"00"}}}'])

		'Corte, todo lo que cree debe ir por encima de esta sentencia.'
		break
	case 'reversaiso':
		'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
		CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'POST', '/apiDebinV1/ISO/ReversaISO', 'ISO\\Reversa.json', [('StatusCode') : 200
				, ('Mensaje') : '{}'])

		'Corte, todo lo que cree debe ir por encima de esta sentencia.'
		break
	case 'consultaiso':
		'Configuracion rest:\r\n\t* Metodo: [POST, GET, DELETE, PUT]\r\n\t* Endpoint: url del metodo a ejecutar.\r\n\t* Semilla: Path donde se creo el archivo para el body. (Se debe usar contra barra \\ )\r\n\t* Respuesta Esperada: La respuesta que espero cuando el caso sale ok.\r\n'
		CustomKeywords.'coelsa.Accion.rest'(Configuracion, 'GET', '/apiDebinV1/ISO/ConsultaISO/{id}', '', [('StatusCode') : 200
				, ('Mensaje') : '{}'])

		'Corte, todo lo que cree debe ir por encima de esta sentencia.'
		break
}

'Corte, todo lo que cree debe ir por encima de esta sentencia.'
return Configuracion

