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

println response

if (response != null) {
	
	println "si"
	
	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*', 'DEBIN_ACTIVAS', 'DAC_ID_HASH =\''+response.id+'\'', '')[0]
	
	println select
	
	if(select != null) {
										
		/*String tipo, endpoint_id, ori_trx_id
			
		tipo		=	select.get('')
		endpoint_id	=	select.get('')
		ori_trx_id	=	select.get('')
		
		Map objeto = [
			('tipo'):			tipo,
			('endpoint_id'):	endpoint_id,
			('ori_trx_id'):		ori_trx_id
			]
				
		String cuit_credito, banco_credito, sucursal_credito, cbu_credito, titular_credito
		
		cbu_credito	=	select.get('')	
		
		Map cuenta_credito = [
			('cbu'):	select.get('')
			]
		
		cuit_credito		=	select.get('')
		banco_credito		=	select.get('')
		sucursal_credito	=	select.get('')
		titular_credito		=	select.get('')
					
		Map credito = [
			('cuit'):		cuit_credito,
			('banco'):		banco_credito,
			('sucursal'):	sucursal_credito
			]
		
		String cuit_debito, banco_debito, sucursal_debito, cbu_debito, titular_debito
		
		cbu_debito	=	select.get('')	
		
		Map cuenta_debito = [
			('cbu'):	select.get('')
			]
		
		cuit_debito			=	select.get('')
		banco_debito		=	select.get('')
		sucursal_debito		=	select.get('')
		titular_debito		=	select.get('')
		
		Map debito = [
			('cuit'):		cuit_debito,
			('banco'):		banco_debito,
			('sucursal'):	sucursal_debito
			]
			
		String concepto, descripcion, moneda
		Integer idUsuario, idComprobante, importeI
		
		concepto			=	select.get('')
		descripcion			=	select.get('')
		idUsuario			=	select.get('')
		idComprobante		=	select.get('')
		
		Map importe = [
			('moneda'):		moneda,
			('importe'):	importeI
			]
		
		String ori_trx, ipCliente, tipoDispositivo, plataforma, imsi, imei	
		Integer mismoTitular, tiempoExpiracion, ori_terminal, ori_adicional
			
		ori_trx				=	select.get('')
		mismoTitular		=	select.get('')
		tiempoExpiracion	=	select.get('')
		ori_terminal		=	select.get('')
		ori_adicional		=	select.get('')
		
		ipCliente			=	select.get('')
		tipoDispositivo		=	select.get('')
		plataforma			=	select.get('')
		imsi				=	select.get('')
		imei				=	select.get('')
				
		Map datosGenerador = [
			('ipCliente'):			ipCliente,
			('tipoDispositivo'):	tipoDispositivo,
			('plataforma'):			plataforma,
			('imsi'):				imsi,
			('imei'):				imei
			]
			
		Integer lat, lng, precision
		
		lat			=	select.get('')	
		lng			=	select.get('')	
		precision	=	select.get('')

		Map ubicacion = [
			('lat'):		lat,
			('lng'):		lng,
			('precision'):	precision
			]
		
		
		
		
		
		
		
		
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
				
		int ori_trx_id_b = Body.objeto.ori_trx_id.toInteger()
		
		//TODO		
		Body.objeto.ori_trx_id = ori_trx_id_b
		
		Body.operacion_original.remove('tipo')
		Body.operacion_original.detalle.remove('importe')
		
		errores = coelsa.Util.validar(contracargoqr, Body)				
		respuesta1 = [
				db: [
					querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_QR_ID_TRX =\'$id_hash\'",
					selectbody:	select
					],
				errores:	errores
			]	
		*/
			
	}else {

		errores = 'Request: Consulta sin resultados. '
		respuesta1 = [
						db: [
							querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_QR_ID_TRX =\'$id_hash\' ",
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
