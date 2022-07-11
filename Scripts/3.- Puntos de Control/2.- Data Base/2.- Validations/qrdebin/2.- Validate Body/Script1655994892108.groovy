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
				
		String cbvu, cuit, banco, terminal
		if(Body.operacion.vendedor.cbu.substring(0, 3) == "000") {
			cuit = select.get('DAC_CREDITO_CVU_CUIT').toString()
			cbvu = select.get('DAC_CREDITO_CVU').toString()
			banco = "000"
		}else {
			cuit = select.get('DAC_CREDITO_CUIT').toString()
			cbvu = select.get('DAC_CREDITO_CBU').toString()
			banco = select.get('DAC_CREDITO_BANCOCOD').toString()
		}
		
		sucursal = select.get('DAC_CREDITO_BANCOSUC').toString()
		terminal = select.get('DAC_CREDITO_TERMINAL')
		
		Map vendedor = [
			('cuit'):				cuit,
			('cbu'):				cbvu,
			('banco'):				banco,
			('sucursal'):			sucursal,
			('terminal'):			terminal
			]
						
		if(Body.operacion.comprador.cuenta.cbu != "") {
			if(Body.operacion.comprador.cuenta.cbu.substring(0, 3) == "000") {
				cuit = select.get('DAC_DEBITO_CVU_CUIT').toString()
				cbvu = select.get('DAC_DEBITO_CVU').toString()
			}else {
				cuit = select.get('DAC_DEBITO_CUIT').toString()
				cbvu = select.get('DAC_DEBITO_CBU').toString()
			}
		}else {
			cuit = select.get('DAC_DEBITO_CUIT').toString()
		}
		
		def alias = select.get('DAC_DEBITO_ALIAS').toString()

		//Lógica para comparar alias o cbu, según sea el caso.
		def cbuBody 	= Body.operacion.comprador.cuenta.cbu
		def aliasBody	= Body.operacion.comprador.cuenta.alias
		
		Map datos_cuentas
				
		if(cbuBody != "" && aliasBody != "") {
			datos_cuentas = [
				('cbu'):			cbvu,
				('alias'):			alias
				]
		}else if(cbuBody != "" && aliasBody == ""){
			datos_cuentas = [
				('cbu'):		cbvu,
				('alias'):		""
			]
		}else if(cbuBody == "" && aliasBody != "") {
			datos_cuentas = [
				('cbu'):		"",
				('alias'):		alias
			]
		}	
			
		Map comprador = [
			('cuenta'): 			datos_cuentas,
			('cuit'):				cuit,
			]
					
		def tiempoExpiracion = select.get('TIEMPOEXPIRACION')
		def moneda 
		
		if(select.get('DAC_CREDITO_TIPO_MONEDA') == "str") {
			moneda = "string"
		}else {
			moneda = select.get('DAC_CREDITO_TIPO_MONEDA').toString()
		}		
				
		String importe1 = select.get('DAC_IMPORTE')
		String[] s = importe1.split("\\.")
		String[] dec = s[1]		
		Integer i = 0
		Integer a = s[1].length()
		String decimal = ''
			
		for(i = 0; i < a; i++) {
			if(dec[i]!=0)
				decimal += dec[i] 
		}
		
		String importe
		if(decimal=='0000') {
			importe = s[0]
		}else {
			importe = s[0]+"."+decimal
		}
							
		Map detalle = [
			('concepto'):			select.get('DAC_CONCEPTO').toString(),
			('id_usuario'):			select.get('DAC_USUARIO'),
			('id_comprobante'):		select.get('DAC_COMPROBANTE'),
			('moneda'):				moneda,
			('importe'):			importe,
			//('devolucion'):		select.get('??'),
			('tiempo_expiracion'):	tiempoExpiracion,
			//('descripcion'):		select.get(''),
			('qr'):					select.get('DAC_QR').toString(),
			('qr_hash'):			select.get('DAC_QR_HASH').toString(),
			('qr_id_trx'):			select.get('DAC_QR_ID_TRX').toString(),
			('id_billetera'):		select.get('DAC_QR_ID_BILLETERA')
			]
		
		Map datos_generador = [
			('ubicacion'): [
				"lat":			select.get('DAC_LATITUD'),
				"lng":			select.get('DAC_LONGITUD'),
				"precision":	select.get('DAC_PRECISION')
			],
			('ip_cliente'):			select.get('DAC_IP').toString(),
			('tipo_dispositivo'):	select.get('DAC_DISPOSITIVO').toString(),
			('plataforma'):			select.get('DAC_PLATAFORMA').toString(),
			('imsi'):				select.get('DAC_IMSI').toString(),
			('imei'):				select.get('DAC_IMEI').toString()
			]
			
		// Normalizar los datos del generador
		if(datos_generador.tipo_dispositivo	== "  ")	datos_generador.tipo_dispositivo = ""
		if(datos_generador.plataforma		== "  ")	datos_generador.plataforma = ""
		if(datos_generador.imsi == "               ")	datos_generador.imsi = ""
		if(datos_generador.imei == "               ")	datos_generador.imei = ""
		
		Map operacion = [:]
		operacion.vendedor			= vendedor
		operacion.comprador			= comprador
		operacion.detalle 			= detalle
		operacion.datos_generador	= datos_generador
		
		Map debin = [:]
		
		debin.operacion				= operacion
		
		//TODO
		// Se remueve hasta encontrar como normalizar body contra bd
		Body.operacion.vendedor.remove('terminal')
		// Se remueve debido a que posible exista un bug con relación a la columnba sucursal en la BD
		Body.operacion.vendedor.remove('sucursal')	
		Body.operacion.detalle.remove('descripcion')
		//Body.operacion.detalle.remove('importe')
		
		String importeBody = Body.operacion.detalle.importe
		String importeB
	
		if(importeBody.contains(".")) {
			String[] e = importeBody.split("\\.")
			String r = e[1]
			String t
			
			if(r.length()<=2) {
				t = r+'00'
			}else {
				t = r
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
