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
def DebinId = GlobalVariable.Debin.id

Map Body = GlobalVariable.Body
Map respuesta1

if (response != null) {
	
	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*, DATEDIFF (Minute, DAC_ADD_DT, DAC_FECHA_EXPIRACION) as TIEMPOEXPIRACION', 'DEBIN_ACTIVAS', 'DAC_ID_HASH =\''+DebinId+'\'', '')[0]
				
	if(select != null) {
											
		String cuitcomprador, cbvucomprador, moneda, ori_trx
		if(Body.operacion.comprador.cuenta.cbu.substring(0, 3) != "000") {
			cuitcomprador 	= 	select.get('DAC_DEBITO_CUIT')
			cbvucomprador 	= 	select.get('DAC_DEBITO_CBU')
		}else {
			cuitcomprador 	= 	Body.operacion.comprador.cuit
			cbvucomprador 	= 	Body.operacion.comprador.cuenta.cbu
		}
				
		Map datos_cuentas = [
			('cbu'):				cbvucomprador
			]
			
		Map comprador = [
			('cuit'):				cuitcomprador,
			('cuenta'): 			datos_cuentas
			]
						
		if(select.get('DAC_CREDITO_TIPO_MONEDA') == "str") {
			moneda			= 	"string"
		}else {
			moneda 			= 	select.get('DAC_CREDITO_TIPO_MONEDA')
		} 
		
		String importe 		= 	select.get('DAC_IMPORTE')	
		String[] s 			= 	importe.split("\\.")			
		String[] dec 		= 	s[1]							
		Integer a 			= 	s[1].length()					
		String decimal 		= 	''							
			
		for(i = 0; i < a; i++) {					
			if(dec[i]!=0)							
				decimal 	+= 	dec[i] 					
		}
		
		if(decimal=='0000') {
			importe 		= 	s[0]
		}else {
			importe 		= 	s[0]+"."+decimal
		}

		if(response.descripcion == 'EL DEBIN SE ENCUENTRA EN UN ESTADO QUE NO SE PUEDE MODIFICAR') {
			ori_trx = Body.operacion.detalle.ori_trx
		}else {
			ori_trx = select.get('DAC_ORI_TRX')
		}
		
		Map detalle = [
			('ori_trx'):			ori_trx,		
			('ori_terminal'):		select.get('DAC_ORI_TERMINAL').toString(),
			('ori_adicional'):		select.get('DAC_ORI_ADICIONAL').toString(),
			('moneda'):				moneda,
			('importe'):			importe
			]
			
		def id = select.get('DAC_ID_HASH').toString()
		def codigo = '00'
					
		Map respuesta = [
			('codigo'):				codigo
			]
						
		Map datosGenerador = [
			('ipCliente'):			select.get('DAC_IP'),
			('tipoDispositivo'):	select.get('DAC_DISPOSITIVO'),
			('plataforma'):			select.get('DAC_PLATAFORMA'),
			('imsi'):				select.get('DAC_IMSI'),
			('imei'):				select.get('DAC_IMEI'),
			('ubicacion'): [
					"lat":			select.get('DAC_LATITUD'),
					"lng":			select.get('DAC_LONGITUD'),
					"precision":	select.get('DAC_PRECISION')
				]
			]
			
		// Normalizar los datos del generador
		if(datosGenerador.ipCliente	== "")				datosGenerador.ipCliente = "string"
		if(datosGenerador.tipoDispositivo	== "  ")	datosGenerador.tipoDispositivo = "string"
		if(datosGenerador.plataforma		== "  ")	datosGenerador.plataforma = "string"
		if(datosGenerador.imsi == "               ")	datosGenerador.imsi = "string"
		if(datosGenerador.imei == "               ")	datosGenerador.imei = "string"
		
		Map operacion = [:]
		operacion.comprador			= comprador
		operacion.detalle 			= detalle
						
		Map confirmadebito = [:]
		confirmadebito.operacion				= operacion
		confirmadebito.id						= id
		confirmadebito.datosGenerador			= datosGenerador	
				
		//TODO
		// Campos que faltan definir
		
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
				dif = 4-y	
				for (i = dif; i < 4; i++) {					
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
		Body.remove('respuesta')

		errores = coelsa.Util.validar(confirmadebito, Body)				
		respuesta1 = [
				db: [
					querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$DebinId\'",
					selectbody:	select
					],
				errores:	errores
			]	
	}else {

		errores = 'Request: Consulta sin resultados. '
		respuesta1 = [
						db: [
							querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$DebinId\'",
							selectbody:	select
							],
						errores: errores
					]
	}
		
}else {
	
	errores = 'Respuesta vacia. '
	db = ''
	respuesta1 = [
					db:db,
					errores: errores
				]
}

return respuesta1
