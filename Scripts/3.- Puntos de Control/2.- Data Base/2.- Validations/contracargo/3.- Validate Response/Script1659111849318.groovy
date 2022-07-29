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
	
	def codigo = response.respuesta.codigo
								
	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*', 'DEBIN_ACTIVAS', 'DAC_ID_HASH =\''+response.id.toUpperCase()+'\'', '')[0]
	
	if (select != null) {
	
		String id, tipo, fechaNegocio, 
				
		id 				= 		select.get('DAC_ID_HASH')
		tipo 			= 		select.get('DAC_TIPO').toLowerCase()
		fechaNegocio 	= 		select.get('DAC_FECHA_NEGOCIO')
		
		Map dato_db = [:]
		
		dato_db.id				= 	id
		dato_db.tipo			= 	tipo
		dato_db.fechaNegocio	=	fechaNegocio	
		
		String tipoB, idB, fechaNegocioB1
		
		tipoB 						= 	response.tipo
		idB 						= 	response.id
		fecha_negocioB1 			= 	response.fecha_negocio
		String[] fechaNegocioB2		= 	fechaNegocioB1.split("T")
		String fechaNegocioB		= 	fechaNegocioB2[0]	
				
		Map response1 = [:]
		
		response1.tipo				=	tipoB
		response1.id				= 	idB
		response1.fecha_negocio 	= 	fechaNegocioB
					
		errores = coelsa.Util.validar(response1, dato_db)
		
		respuesta = [
				db:[
					querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$response.id\'",
					selectresponse: select
					],
					errores: errores
				]
	}else {
		errores = 'Response: Consulta sin resultados. '
		respuesta = [
						db: [
							querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$response.id\'",
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


