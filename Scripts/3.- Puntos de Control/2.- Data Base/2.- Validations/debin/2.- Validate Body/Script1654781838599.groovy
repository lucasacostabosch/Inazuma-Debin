import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import groovy.json.JsonSlurper
import com.kms.katalon.core.util.KeywordUtil
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Calendar

def jsonSlurper = new JsonSlurper()

Map Body = GlobalVariable.Body
Map respuesta

if (response != null) {
	
	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*, DATEDIFF (Minute, DAC_ADD_DT, DAC_FECHA_EXPIRACION) as TIEMPOEXPIRACION', 'DEBIN_ACTIVAS', 'DAC_ID_HASH =\''+response.debin.id+'\'', '')[0]
			
	if(select != null) {
		
		Map objeto = [
			('ori_trx_id'):			select.get('DAC_ORI_TRX_ID').toString(),
			]
					
		String cbvu, cuit, banco, terminal
		if(Body.operacion.vendedor.cbu.substring(0, 3) == "000") {
			cuit = 	select.get('DAC_CREDITO_CVU_CUIT').toString()
			cbvu = 	select.get('DAC_CREDITO_CVU').toString()
			banco = "000"
		}else {
			cuit = 	select.get('DAC_CREDITO_CUIT').toString()
			cbvu = 	select.get('DAC_CREDITO_CBU').toString()
			banco = select.get('DAC_CREDITO_BANCOCOD').toString()
		 }
				
		terminal = select.get('DAC_CREDITO_TERMINAL').toString()
			
		Map vendedor = [
			('cuit'):				cuit,
			('cbu'):				cbvu,
			('banco'):				banco,
			('sucursal'):			select.get('DAC_CREDITO_BANCOSUC').toString(),
			('terminal'):			terminal,
			('recurrencia'):   		select.get('DAC_RECURRENTE').toString()
			//('prestacion'):			select.get('??')
		]
		
		String cuitcomprador, cbvucomprador
		if(Body.operacion.comprador.cuenta.cbu.substring(0, 3) == "000") {
			cuitcomprador = select.get('DAC_DEBITO_CVU_CUIT').toString()
			cbvucomprador = select.get('DAC_DEBITO_CVU').toString()
		}else {
			cuitcomprador = select.get('DAC_DEBITO_CUIT').toString()
			cbvucomprador = select.get('DAC_DEBITO_CBU').toString()
		}
		
		//Lógica para comparar alias o cbu, según sea el caso.
		def cbuBody 	= Body.operacion.comprador.cuenta.cbu
		def aliasBody	= Body.operacion.comprador.cuenta.alias
		
		Map datos_cuentas
				
		if(cbuBody != "" && aliasBody != "") {
			datos_cuentas = [
				('cbu'):		cbvucomprador,
				('alias'):		select.get('DAC_DEBITO_ALIAS').toString()
				]
		}else if(cbuBody != "" && aliasBody == ""){
			datos_cuentas = [
				('cbu'):		cbvucomprador,
				('alias'):		""
			]
		}else if(cbuBody == "" && aliasBody != "") {
			datos_cuentas = [
				('cbu'):		"",
				('alias'):		select.get('DAC_DEBITO_ALIAS').toString()
			]
		}
			
		Map comprador = [
			('cuit'):				cuitcomprador,
			('cuenta'): 			datos_cuentas
			]
		
		def tiempoExpiracion = select.get('TIEMPOEXPIRACION')
		def moneda
		
		if(select.get('DAC_CREDITO_TIPO_MONEDA') == "str") {
			moneda = "string"
		}else {
			moneda = select.get('DAC_CREDITO_TIPO_MONEDA').toString()
		} 
		
		String importe = select.get('DAC_IMPORTE')	
		String[] s = importe.split("\\.")			 
		String[] dec = s[1]							
		Integer a = s[1].length()					
		String decimal = ''							
			
		for(i = 0; i < a; i++) {					
			if(dec[i]!=0)							
				decimal += dec[i] 					
		}
		
		if(decimal=='0000') {
			importe = s[0]
		}else {
			importe = s[0]+"."+decimal
		}
		
		Map detalle = [
			('concepto'):			select.get('DAC_CONCEPTO').toString(),
			('idUsuario'):			select.get('DAC_USUARIO'),
			('idComprobante'):		select.get('DAC_COMPROBANTE'),
//			('moneda'):				select.get('??'),
			('importe'):			importe,
//			('devolucion'):			select.get('DAC_DEVOLUCION'),
			('tiempoExpiracion'):	tiempoExpiracion,
//			('descripción'):		"",
			('mismoTitular'):		select.get('DAC_MISMO_TITULAR'),
			('idLote'):				select.get('DAC_LOTE_ID')
			]
		
		Map datosGenerador = [
			('ipCliente'):			select.get('DAC_IP').toString(),
			('tipoDispositivo'):	select.get('DAC_DISPOSITIVO').toString(),
			('plataforma'):			select.get('DAC_PLATAFORMA').toString(),
			('imsi'):				select.get('DAC_IMSI').toString(),
			('imei'):				select.get('DAC_IMEI').toString(),
			('ubicacion'): [
					"lat":			select.get('DAC_LATITUD'),
					"lng":			select.get('DAC_LONGITUD'),
					"precision":	select.get('DAC_PRECISION')
				]
			]
			
		// Normalizar los datos del generador
		if(datosGenerador.tipoDispositivo	== "  ")	datosGenerador.tipoDispositivo = ""
		if(datosGenerador.plataforma		== "  ")	datosGenerador.plataforma = ""
		if(datosGenerador.imsi == "               ")	datosGenerador.imsi = ""
		if(datosGenerador.imei == "               ")	datosGenerador.imei = ""
		
		Map operacion = [:]
		operacion.vendedor			= vendedor
		operacion.comprador			= comprador
		operacion.detalle 			= detalle
		operacion.datosGenerador	= datosGenerador
		
		Map debin = [:]
		debin.objeto				= objeto
		debin.operacion				= operacion
		
		//TODO
		// Campos que faltan definir
		Body.operacion.vendedor.remove('recurrencia')
		Body.operacion.vendedor.remove('prestacion')
		Body.operacion.detalle.remove('moneda')
		Body.operacion.detalle.remove('descripción')
		Body.operacion.detalle.remove('devolucion')
		
		String importeBody = Body.operacion.detalle.importe		
		String importeB
	
		if(importeBody.contains(".")) {
			String[] e = importeBody.split("\\.")					
			String[] r = e[1]									
			String f = e[1]										
			Integer y = e[1].length()							
			Integer dif											
			String t = ''										
			String z = ''										
			
			if(y<4) {												
				for (i = y; i < 4; i++) {						
					z += 0										
				}
				t = f+z											
			}else {												
				for(i = 0; i < a; i++) {											
					if(r[i]!=0)
						t += r[i]
				}
			}
			importeB = e[0]+'.'+t			
		}else {
			importeB = importeBody
		}
		
		Body.operacion.detalle.importe = importeB

		errores = coelsa.Util.validar(debin, Body)
		
		respuesta = [
				db: [
					querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$response.debin.id\'",
					selectbody:	select
					],
				errores:	errores
			]
	}else {
		errores = 'Request: Consulta sin resultados. '
		respuesta = [
						db: [
							querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$response.debin.id\'",
							selectbody:	select
							],
						errores: errores
					]
	}
		
}else {
	errores = 'Respuesta vacia. '
	db = ''
	respuesta = [
					db:db,
					errores: errores
				]
}

return respuesta
