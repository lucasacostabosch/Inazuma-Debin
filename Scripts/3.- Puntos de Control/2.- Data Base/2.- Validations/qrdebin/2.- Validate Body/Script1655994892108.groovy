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
		
		String cuit_administrador
		
		cuit_administrador	=	select.get('DAC_ORI_RED')
		
		Map administrador = [
			('cuit'):	cuit_administrador
			]
		
		String cbvu, cuit, banco, terminal
		if(Body.operacion.vendedor.cbu.substring(0, 3) == "000") {
			cuit	= 	select.get('DAC_CREDITO_CVU_CUIT')
			cbvu 	= 	select.get('DAC_CREDITO_CVU')
			banco 	= 	"000"
		}else {
			cuit 	= 	select.get('DAC_CREDITO_CUIT')
			cbvu 	= 	select.get('DAC_CREDITO_CBU')
			banco 	= 	select.get('DAC_CREDITO_BANCOCOD')
		}
		
		sucursal 	= 	select.get('DAC_CREDITO_BANCOSUC')
		terminal 	= 	select.get('DAC_CREDITO_TERMINAL')
		
		Map vendedor = [
			('cuit'):		cuit,
			('cbu'):		cbvu,
			('banco'):		banco,
			('sucursal'):	sucursal,
			('terminal'):	terminal
			]
						
		if(Body.operacion.comprador.cuenta.cbu != "") {
			if(Body.operacion.comprador.cuenta.cbu.substring(0, 3) == "000") {
				cuit 	= 	select.get('DAC_DEBITO_CVU_CUIT')
				cbvu 	= 	select.get('DAC_DEBITO_CVU')
			}else {
				cuit 	= 	select.get('DAC_DEBITO_CUIT')
				cbvu 	= 	select.get('DAC_DEBITO_CBU')
			}
		}else {
			cuit 	= 	select.get('DAC_DEBITO_CUIT')
		}
		
		def alias 	= 	select.get('DAC_DEBITO_ALIAS')

		//Lógica para comparar alias o cbu, según sea el caso.
		def cbuBody 	= 	Body.operacion.comprador.cuenta.cbu
		def aliasBody	= 	Body.operacion.comprador.cuenta.alias
		
		Map datos_cuentas
				
		if(cbuBody != "" && aliasBody != "") {
			datos_cuentas = [
				('cbu'):	cbvu,
				('alias'):	alias
				]
		}else if(cbuBody != "" && aliasBody == ""){
			datos_cuentas = [
				('cbu'):	cbvu,
				('alias'):	""
			]
		}else if(cbuBody == "" && aliasBody != "") {
			datos_cuentas = [
				('cbu'):	"",
				('alias'):	alias
			]
		}	
			
		Map comprador = [
			('cuenta'): datos_cuentas,
			('cuit'):	cuit,
			]
					
		def tiempoExpiracion 	= 	select.get('TIEMPOEXPIRACION')
		def moneda 
		
		if(select.get('DAC_CREDITO_TIPO_MONEDA') == "str") {
			moneda 	= 	"string"
		}else {
			moneda 	= 	select.get('DAC_CREDITO_TIPO_MONEDA').toString()
		}		
				
		String importe 	= 	select.get('DAC_IMPORTE')	
		String[] s 		= 	importe.split("\\.")			  
		String[] dec 	= 	s[1]							
		Integer a 		= 	s[1].length()					
		String decimal 	= 	''							
			
		for(i = 0; i < a; i++) {					
			if(dec[i]!=0)							
				decimal += dec[i] 					
		}
		
		if(decimal=='0000') {
			importe 	= 	s[0]
		}else {
			importe 	= 	s[0]+"."+decimal
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
		operacion.adminitrador		= administrador
		operacion.vendedor			= vendedor
		operacion.comprador			= comprador
		operacion.detalle 			= detalle
		operacion.datos_generador	= datos_generador
		
		Map debin = [:]
		
		debin.operacion	= operacion
		
		//TODO
		// Se remueve hasta encontrar como normalizar body contra bd
		Body.operacion.vendedor.remove('terminal')
		// Se remueve debido a que posible exista un bug con relación a la columnba sucursal en la BD
		Body.operacion.vendedor.remove('sucursal')	
		Body.operacion.detalle.remove('descripcion')
		//Body.operacion.detalle.remove('importe')
	
		String importeBody 	= 	Body.operacion.detalle.importe		//Valor del importe enviado desde el body
		String importeB
	
		if(importeBody.contains(".")) {
			String[] e 	= 	importeBody.split("\\.")					
			String[] r 	= 	e[1]									
			String f 	= 	e[1]										
			Integer y 	= 	e[1].length()							
			Integer dif											
			String t 	= ''										
			String z 	= ''										
			
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
			importeB 	= 	e[0]+'.'+t			
		}else {
			importeB 	= 	importeBody
		}
		
		Body.operacion.detalle.importe 	= 	importeB
		
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
