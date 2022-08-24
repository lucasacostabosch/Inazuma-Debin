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

def Body = GlobalVariable.Body
def Accion = GlobalVariable.Accion
Map respuesta

if (response != null) {
	
	def qr_id_r = response.qr_id
	
	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*, DATEDIFF (Minute, DAC_ADD_DT, DAC_FECHA_EXPIRACION) as TIEMPOEXPIRACION', 'DEBIN_ACTIVAS', 'DAC_QR_ID_TRX =\''+qr_id_r+'\'', '')[0]
	
	if(select != null) {
		
		String qr_id
		qr_id 	= 	select.get('DAC_QR_ID_TRX')
		def value = select.get('DAC_IMPORTE')
		
		Map amount = [
			('value'):		value
			]
		
		dato_db 		= [:]
		dato_db.qr_id	=	qr_id
		dato_db.amount	=	amount
		
		response.remove('merchant')
		response.remove('payer')
		response.transaction.remove('authorization_code')
		response.transaction.remove('transaction_reference_id')
		response.transaction.remove('on_rejection')
		response.transaction.remove('datetime')
		response.transaction.remove('type')
		response.transaction.remove('amount')
		
		println response
		println dato_db
		
		errores = coelsa.Util.validar(response, dato_db)
		respuesta = [
					db:[
						querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_QR_ID_TRX =\'$qr_id_r\'",
						selectresponse: select
						],
						errores: errores
					]
	}else {
		errores = 'Response: Consulta sin resultados '
		respuesta = [
						db: [
							querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_QR_ID_TRX =\'$qr_id_r\'",
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

