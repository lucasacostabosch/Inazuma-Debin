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
def DebinId 	= GlobalVariable.Debin.id

Map Body = GlobalVariable.Body
Map respuesta1

println Body

if (response != null) {
	
	def qr_id_trx_body1 = Body.operacion_original.qr_id_trx
	//def ori_trx_id_body1 = response.id
	
	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*, DATEDIFF (Minute, DAC_ADD_DT, DAC_FECHA_EXPIRACION) as TIEMPOEXPIRACION', 'DEBIN_ACTIVAS', 'DAC_QR_ID_TRX =\''+qr_id_trx_body1+'\'', '')[0]
	
	if(select != null) {
										
		String moneda, tipo, qr_id_trx, id
		
		if(select.get('DAC_CREDITO_TIPO_MONEDA') == "str") {
			moneda = "string"
		}else {
			moneda = select.get('DAC_CREDITO_TIPO_MONEDA')
		}
		
		String motivo
		if (Body.operacion_original.detalle.motivo == 'string') {
			motivo = 'string'
		}else {
			motivo = select.get('DAC_DEVOLUCION_DETALLE')
		}
		
		Map detalle = [
				('moneda'):		moneda,
				('motivo'):		motivo
			]
			
		String cuitvendedor, cbvucvendedor
		if(Body.operacion_original.vendedor.cbu.substring(0, 3) == "000") {
			cuitvendedor = 		select.get('DAC_DEBITO_CVU_CUIT')
			cbvucvendedor = 	select.get('DAC_DEBITO_CVU')
		}else {
			cuitvendedor = 		select.get('DAC_DEBITO_CUIT')
			cbvucvendedor = 	select.get('DAC_DEBITO_CBU')
		 }
			
		Map vendedor = [
				('cuit'):		cuitvendedor,
				('cbu'):		cbvucvendedor
			]
		
		tipo 		= 	select.get('DAC_TIPO').toLowerCase()
		qr_id_trx 	= 	select.get('DAC_QR_ID_TRX')	
		ori_trx_id  =   select.get('DAC_ORI_TRX_ID')
						
		Map objeto = [
				('ori_trx_id'):			ori_trx_id
			]	
			
		Map operacion_original = [:]
		operacion_original.detalle 			= detalle
		operacion_original.vendedor			= vendedor
		operacion_original.qr_id_trx		= qr_id_trx
							
		Map contracargoqr = [:]
		contracargoqr.operacion_original	= operacion_original
		contracargoqr.objeto				= objeto
		
		def ori_trx_id_body = Body.objeto.ori_trx_id.toString()
		def ori_trx_id_b
		
		if (ori_trx_id_body.substring(0, 2) == "00") {
			ori_trx_id_b = Body.objeto.ori_trx_id.toInteger()
		}else {
			ori_trx_id_body.substring(0, 3)
			ori_trx_id_b = Body.objeto.ori_trx_id
		}
		
		//TODO		
		Body.objeto.ori_trx_id = ori_trx_id_b
		
		Body.operacion_original.remove('tipo')
		Body.operacion_original.detalle.remove('importe')
		
		errores = coelsa.Util.validar(contracargoqr, Body)				
		respuesta1 = [
				db: [
					querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_QR_ID_TRX =\'$qr_id_trx_body1\'",
					selectbody:	select
					],
				errores:	errores
			]	
			
	}else {

		errores = 'Request: Consulta sin resultados. '
		respuesta1 = [
						db: [
							querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_QR_ID_TRX =\'$qr_id_trx_body1\' ",
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
