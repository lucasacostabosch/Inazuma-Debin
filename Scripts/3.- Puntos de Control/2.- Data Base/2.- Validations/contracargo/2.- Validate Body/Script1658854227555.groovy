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
										
		String moneda, tipo
		
		tipo = select.get('DAC_TIPO').toLowerCase()
		
		if(select.get('DAC_CREDITO_TIPO_MONEDA') == "str") {
			moneda = "string"
		}else {
			moneda = select.get('DAC_CREDITO_TIPO_MONEDA')
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
		
		def motivo = select.get('DAC_DEVOLUCION_DETALLE').toString()
		
		Map detalle = [
				('importe'):		importe,
				('moneda'):			moneda,
				('motivo'):			motivo
			]
						
		Map operacionOriginal = [
				('id'):			select.get('DAC_ID_HASH').toString(),
				('tipo'):		tipo,
				('detalle'):	detalle	
			]
		
		String cuitcomprador, cbvucomprador		
		if(Body.comprador.cuenta.cbu.substring(0, 3) == "000") {
			cuitcomprador = select.get('DAC_DEBITO_CVU_CUIT')
			cbvucomprador = select.get('DAC_DEBITO_CVU')
		}else {
			cuitcomprador = select.get('DAC_DEBITO_CUIT')
			cbvucomprador = select.get('DAC_DEBITO_CBU')
		}
			
		Map cuenta = [
				('cbu'):		cbvucomprador
			]
			
		Map comprador = [
				('cuit'):				cuitcomprador,
				('cuenta'): 			cuenta
			]
		
		String cuitvendedor, cbvucvendedor	
		if(Body.vendedor.cbu.substring(0, 3) == "000") {
			cuitvendedor = 		select.get('DAC_CREDITO_CVU_CUIT')
			cbvucvendedor = 	select.get('DAC_CREDITO_CVU')
		}else {
			cuitvendedor = 		select.get('DAC_CREDITO_CUIT')
			cbvucvendedor = 	select.get('DAC_CREDITO_CBU')
		 }
			
		Map cuenta1 = [
				('cbu'):		cbvucvendedor
				]
			
		Map vendedor = [
				('cuit'):			cuitvendedor,
				('cuenta'):			cuenta1
			]		
							
		Map contracargo = [:]
		contracargo.operacionOriginal			= operacionOriginal
		contracargo.comprador					= comprador
		contracargo.vendedor					= vendedor
				
		//TODO
		// Campos que faltan definir
		
		String importeBody = Body.operacionOriginal.detalle.importe		
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
		
		Body.operacionOriginal.detalle.importe = importeB
		
		errores = coelsa.Util.validar(contracargo, Body)				
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
