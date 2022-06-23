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
	
	def codigo = response.respuesta.codigo
								
	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*', 'DEBIN_ACTIVAS', 'DAC_ID_HASH =\''+response.debin.id.toUpperCase()+'\'', '')[0]
	
	if (select != null) {
	
		def fecha1bd = select.get('DAC_ADD_DT').toString()
		def dac_add_dt = fecha1bd.replaceAll(' -', '-')
		
		def fecha2bd = select.get('DAC_FECHA_EXPIRACION').toString()
		def dac_fecha_expiracion = fecha2bd.replaceAll(' -', '-')
		
		Map dato_db = [:]
		
		dato_db = [
					('addDt'):dac_add_dt,
					('fechaExpiracion'):dac_fecha_expiracion,
					('puntaje'):select.get('DAC_SCORING1'),
					('reglas'):select.get('DAC_REGLAS')
				]
				
		def fecha1response = response.debin.addDt.toString()
		def addDTB = fecha1response.replaceAll('T', ' ')
		
		def fecha2response = response.debin.fechaExpiracion.toString()
		def fechaExpiracionB = fecha2response.replaceAll('T', ' ')
		
		Map response1 = [:]
		
		response1 = [
						('addDt'):addDTB,
						('fechaExpiracion'):fechaExpiracionB,
						('puntaje'):response.evaluacion.puntaje,
						('reglas'):response.evaluacion.reglas
					]
						
		errores = coelsa.Util.validar(response1, dato_db)
		
		respuesta = [
				db:[
					querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$response.debin.id\'",
					selectresponse: select
					],
					errores: errores
				]
	}
	
	return respuesta

}


