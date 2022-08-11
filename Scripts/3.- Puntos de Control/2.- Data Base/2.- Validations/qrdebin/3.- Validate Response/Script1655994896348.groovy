import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import groovy.json.JsonSlurper
import com.kms.katalon.core.util.KeywordUtil
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Calendar
//import java.util.GregorianCalendar

def jsonSlurper = new JsonSlurper()

def Body = GlobalVariable.Body
def Accion = GlobalVariable.Accion
Map respuesta

if (response != null) {
	
	def codigo 	= 	response.respuesta.codigo
							
	Map select 	= 	CustomKeywords.'sql.DML.select'('DEBIN', '*', 'DEBIN_ACTIVAS', 'DAC_ID_HASH =\''+response.debin.id.toUpperCase()+'\'', '')[0]
			
	if (select != null) {
		
		String fecha1bd, dac_add_dt, fecha2bd, dac_fecha_expiracion
		
		fecha1bd 				= 	select.get('DAC_ADD_DT')
		dac_add_dt 				= 	fecha1bd.replaceAll(' -', '-')
		fecha2bd 				= 	select.get('DAC_FECHA_EXPIRACION')
		dac_fecha_expiracion 	= 	fecha2bd.replaceAll(' -', '-')
		
		String dac_scoring1, dac_reglas 
		if(select.get('DAC_SCORING1') == null || select.get('DAC_SCORING1') == 0) {
			dac_scoring1 	=	 0
		}else {
			dac_scoring1 	= 	select.get('DAC_SCORING1')
		}
			
		if(select.get('DAC_REGLAS') == null || select.get('DAC_REGLAS') == "") {
			dac_reglas 	= 	""
		}else {
			dac_reglas 	= 	select.get('DAC_REGLAS')
		}
					
		Map dato_db = [:]
	
		dato_db = [
					('addDt'):dac_add_dt,
					('fechaExpiracion'):dac_fecha_expiracion,
					('puntaje'):dac_scoring1,
					('reglas'):dac_reglas
				]
				
		String	fecha1response, addDtB, fecha2response, fechaExpiracionB, puntaje, reglas		
					
		fecha1response 		= 	response.debin.addDt
		addDtB 				= 	fecha1response.replaceAll('T', ' ')
		fecha2response 		= 	response.debin.fechaExpiracion
		fechaExpiracionB 	= 	fecha2response.replaceAll('T', ' ')
		puntaje 			=	response.evaluacion.puntaje
		reglas 				= 	response.evaluacion.reglas
			
		Map response1 = [:]
			
		response1 = [
						('addDt'):addDtB,
						('fechaExpiracion'):fechaExpiracionB,
						('puntaje'):puntaje,
						('reglas'):reglas
					]
							
		errores = coelsa.Util.validar(response1, dato_db)
						
		respuesta = [
					db:[
						querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$response.debin.id\'",
						selectresponse: select
						],
						errores: errores
					]
	}else {
		errores = 'Response: Consulta sin resultados '
		respuesta = [
						db: [
							querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$response.debin.id\'",
							selectbody:	select
							],
						errores: errores
					]
	}
		
}else {
	errores = 'Respuesta vacia '
	db = ''
	respuesta = [
					db:db,
					errores: errores
				]
}

return respuesta

