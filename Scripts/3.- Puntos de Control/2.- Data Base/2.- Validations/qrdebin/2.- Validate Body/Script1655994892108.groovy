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
		if(Body.operacion.vendedor.banco == "000") {
			cuit = "DAC_CREDITO_CVU_CUIT"
			cbvu = "DAC_CREDITO_CVU"
			banco = "000"
		}else {
			cuit = "DAC_CREDITO_CUIT"
			cbvu = "DAC_CREDITO_CBU"
			banco = select.get('DAC_CREDITO_BANCOCOD')
		}
		
		terminal = select.get('DAC_CREDITO_TERMINAL').toString()
		
		Map vendedor = [
			('cuit'):				select.get(cuit),
			('cbu'):				select.get(cbvu),
			('banco'):				banco,
			('sucursal'):			select.get('DAC_CREDITO_BANCOSUC'),
			('terminal'):			terminal
			]
						
		if(Body.operacion.comprador.cuenta.cbu != "") {
			if(Body.operacion.comprador.cuenta.cbu.substring(0, 3) == "000") {
				cuit = "DAC_DEBITO_CVU_CUIT"
				cbvu = "DAC_DEBITO_CVU"
			}else {
				cuit = "DAC_DEBITO_CUIT"
				cbvu = "DAC_DEBITO_CBU"
			}
		}else {
			cuit = "DAC_DEBITO_CUIT"
		}
		
		String cuitcomprador, cbvucomprador
		if(Body.operacion.comprador.cuenta.cbu.substring(0, 3) == "000") {
			cuitcomprador = select.get('DAC_DEBITO_CVU_CUIT')
			cbvucomprador = select.get('DAC_DEBITO_CVU')
		}else {
			cuitcomprador = select.get('DAC_DEBITO_CUIT')
			cbvucomprador = select.get('DAC_DEBITO_CBU')
		}
		
		//Lógica para comparar alias o cbu, según sea el caso.
		def cbuBody 	= Body.operacion.comprador.cuenta.cbu
		def aliasBody	= Body.operacion.comprador.cuenta.alias
		
		Map datos_cuentas
				
		if(cbuBody != "" && aliasBody != "") {
			datos_cuentas = [
				('cbu'):			cbvucomprador,
				('alias'):			select.get('DAC_DEBITO_ALIAS')
				]
		}else if(cbuBody != "" && aliasBody == ""){
			datos_cuentas = [
				('cbu'):		cbvucomprador,
				('alias'):		""
			]
		}else if(cbuBody == "" && aliasBody != "") {
			datos_cuentas = [
				('cbu'):		"",
				('alias'):		select.get('DAC_DEBITO_ALIAS')
			]
		}	
			
		Map comprador = [
			('cuenta'): 			datos_cuentas,
			('cuit'):				cuitcomprador,
			]
		
		def tiempoExpiracion = select.get('TIEMPOEXPIRACION')
		def moneda 
		
		if(select.get('DAC_CREDITO_TIPO_MONEDA') == "str") {
			moneda = "string"
		}else {
			moneda = select.get('DAC_CREDITO_TIPO_MONEDA')
		}

		Map detalle = [
			('concepto'):			select.get('DAC_CONCEPTO'),
			('id_usuario'):			select.get('DAC_USUARIO'),
			('id_comprobante'):		select.get('DAC_COMPROBANTE'),
			('moneda'):				moneda,
			('importe'):			select.get('DAC_IMPORTE'),
			//('devolucion'):		select.get('??'),
			('tiempo_expiracion'):	tiempoExpiracion,
			//('descripcion'):		select.get(''),
			('qr'):					select.get('DAC_QR'),
			('qr_hash'):			select.get('DAC_QR_HASH'),
			('qr_id_trx'):			select.get('DAC_QR_ID_TRX'),
			('id_billetera'):		select.get('DAC_QR_ID_BILLETERA')
			]
		
		Map datos_generador = [
			('ubicacion'): [
				"lat":			select.get('DAC_LATITUD'),
				"lng":			select.get('DAC_LONGITUD'),
				"precision":	select.get('DAC_PRECISION')
			],
			('ip_cliente'):			select.get('DAC_IP'),
			('tipo_dispositivo'):	select.get('DAC_DISPOSITIVO'),
			('plataforma'):			select.get('DAC_PLATAFORMA'),
			('imsi'):				select.get('DAC_IMSI'),
			('imei'):				select.get('DAC_IMEI')
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
		
		errores = coelsa.Util.validar(debin, Body)
		
		respuesta = [
				db: [
					querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$response.debin.id\'",
					selectbody:	select
					],
				errores:	errores
			]
	}
		
}

return respuesta
