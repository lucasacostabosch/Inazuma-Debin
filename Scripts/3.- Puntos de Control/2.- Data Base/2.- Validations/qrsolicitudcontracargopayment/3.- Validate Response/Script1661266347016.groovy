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
	
	def qr_id_trx_body1 = Body.operacion_original.qr_id_trx
	//def ori_trx_id_body = response.id
	
	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*, DATEDIFF (Minute, DAC_ADD_DT, DAC_FECHA_EXPIRACION) as TIEMPOEXPIRACION', 'DEBIN_ACTIVAS', 'DAC_QR_ID_TRX =\''+qr_id_trx_body1+'\'', '')[0]
	
	if (select != null) {
	
		String tipo, id, fecha_negocio
				
		if(select.get('DAC_TIPO') == 'CONTRACARGOQR') {
			tipo	= 	'contraCargo'
		}else {
			tipo 	= 	select.get('DAC_TIPO').toLowerCase()
		}
		
		id	= 	select.get('DAC_ID_HASH')
		
		if (response.fecha_negocio == '0001-01-01T00:00:00') {
			fecha_negocio 	=	'0001-01-01' 		
		}else {
			fecha_negocio 	=	select.get('DAC_FECHA_NEGOCIO') 	
		}

		Map dato_db = [:]
		
		dato_db.tipo			= 	tipo
		dato_db.id				= 	id
		dato_db.fecha_negocio	=	fecha_negocio	
		
		String tipoB, idB, fecha_negocioB1
		
		tipoB 						= 	response.tipo
		idB 						= 	response.id
		fecha_negocioB1 			= 	response.fecha_negocio
		String[] fecha_negocioB2	= 	fecha_negocioB1.split("T")
		String fecha_negocioB		= 	fecha_negocioB2[0]	
				
		Map response1 = [:]
		
		response1.tipo				=	tipoB
		response1.id				= 	idB
		response1.fecha_negocio 	= 	fecha_negocioB
		
		errores = coelsa.Util.validar(response1, dato_db)
		
		respuesta = [
				db:[
					querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_QR_ID_TRX =\'$qr_id_trx_body1\'",
					selectresponse: select
					],
					errores: errores
				]
	}else {
		errores = 'Response: Consulta sin resultados. '
		respuesta = [
						db: [
							querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_QR_ID_TRX =\'$qr_id_trx_body1\'",
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


