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
	
	def qr_id_r = response.qr_id
	
	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*, DATEDIFF (Minute, DAC_ADD_DT, DAC_FECHA_EXPIRACION) as TIEMPOEXPIRACION', 'DEBIN_ACTIVAS', 'DAC_ID =\''+qr_id_r+'\'', '')[0]
	
	if(select != null) {
		
		String qr_id
		
		def value = select.get('DAC_ADD_DT')
		//def currency
		
		Map amount = [
			('value'):		value,
			//('currency'):	currency
			]
		
		String code, description
		
		code			=	select.get('')
		description		=	select.get('')
		
		Map on_error = [
			('code'):			code,
			('description'):	description
			]
		
		String status
		
		status 	=	select.get('')
				
		Map validation_status = [
			('status'):		status,
			('on_error'):	on_error
			]
			
		String cbu, cvu
		
		cbu =	select.get('')
		cvu	=	select.get('')	
		
		Map account = [
			('cbu'): 	cbu,
			('cvu'):	cvu
			]
			
		String soft_descriptor, cuit, field_of_activity, transaction_reference_id
		
		soft_descriptor				=	select.get('')
		cuit						=	select.get('')
		field_of_activity			=	select.get('')
		transaction_reference_id	=	select.get('')
					
		Map merchant = [
			('soft_descriptor'):			soft_descriptor,
			('cuit'):						cuit,
			('field_of_activity'):			field_of_activity,
			('transaction_reference_id'):	transaction_reference_id,
			('account'):					account
			]
			
		String 	merchant, credit_account, identification_number, transaction_type
		
		merchant				=	select.get('')	
		credit_account			=	select.get('')
		identification_number	=	select.get('')
		transaction_type		=	select.get('')
				
		Map validation_data = [
			('merchant'):				merchant,
			('credit_account'):			credit_account,
			('identification_number'):	identification_number,
			('transaction_type'):		transaction_type
			]
							
		Map dato_db = [:]
		dato_db.qr_id				=	qr_id
		dato_db.amount				=	amount
		dato_db.validation_status	=	validation_status
		dato_db.validation_data		=	validation_data

				
		/*String	fecha1response, addDtB, fecha2response, fechaExpiracionB, puntaje, reglas		
					
		fecha1response 		= 	response.debin.addDt
		addDtB 				= 	fecha1response.replaceAll('T', ' ')
		fecha2response 		= 	response.debin.fechaExpiracion
		fechaExpiracionB 	= 	fecha2response.replaceAll('T', ' ')
		puntaje 			=	response.evaluacion.puntaje
		reglas 				= 	response.evaluacion.reglas*/
			
		/*Map response1 = [:]
			
		response1 = [
						('addDt'):addDtB,
						('fechaExpiracion'):fechaExpiracionB,
						('puntaje'):puntaje,
						('reglas'):reglas
					]*/
							
		//errores = coelsa.Util.validar(response, dato_db)
		errores = ''				
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

