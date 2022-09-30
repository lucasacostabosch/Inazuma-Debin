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

def id_hash = GlobalVariable.Debin.Parametros.id
def Accion = GlobalVariable.Accion
Map respuesta

if (response != null) {
		
	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*', 'DEBIN_ACTIVAS', 'DAC_ID_HASH =\''+id_hash+'\'', '')[0]	

	if (select != null) {
		
		String id, garantiaOk, tipo, fechaNegocio
		Integer	loteId
		
		id 		= 	select.get('DAC_ID_HASH')
		tipo	=	select.get('DAC_TIPO')
		
		if(select.get('DAC_LOTE_ID')!=null) {
			loteId	=	select.get('DAC_LOTE_ID')
		}else {
			loteId	=	0
		}	
		
		fechaNegocio	=	select.get('DAC_FECHA_NEGOCIO')
		
		String codigo_co, titular_co, cuit_co

		titular_co 			=	select.get('DAC_DEBITO_TITULAR')
		cuit_co 			= 	select.get('DAC_DEBITO_CUIT')
		
		String banco_cuenta, sucursal_cuenta, terminal_cuenta, alias_cuenta, cbu_cuenta, esTitular_cuenta, moneda_cuenta, tipo_cuenta, endpointId_cuenta
		
		banco_cuenta		=	select.get('DAC_DEBITO_BANCOCOD')
		sucursal_cuenta		=	select.get('DAC_DEBITO_BANCOSUC')
		
		if(select.get('DAC_ORI_TERMINAL')!=	null) {
			terminal_cuenta	=	select.get('DAC_ORI_TERMINAL')
		}else {
			terminal_cuenta	=	''
		}
		
		if(select.get('DAC_DEBITO_ALIAS')!= null) {
			alias_cuenta	=	select.get('DAC_DEBITO_ALIAS')
		}else {
			alias_cuenta	=	''
		}				
		
		cbu_cuenta			=	select.get('DAC_DEBITO_CBU')
		moneda_cuenta		=	select.get('DAC_DEBITO_TIPO_MONEDA')
		tipo_cuenta			=	select.get('DAC_DEBITO_TIPO_CUENTA')

		Map titulares_co = [:]
		
		Map cuenta_co = [:]

		cuenta_co = [
			('banco'):		banco_cuenta,
			('sucursal'):	sucursal_cuenta,
			('terminal'):	terminal_cuenta,
			('alias'):		alias_cuenta,
			('cbu'):		cbu_cuenta,
			('moneda'):		moneda_cuenta,
			('tipo'):		tipo_cuenta
			]
		
		String codigo_ec, descripcion_ec	
				
		Map estadoComprador = [:]	
		Map comprador = [:]	
		
		comprador = [
			('titular'):	titular_co,
			('cuit'):		cuit_co,
			('cuenta'):		cuenta_co,
			]
			
		String concepto_det, moneda_det, descripcion_det, idOperacionOriginal_det, paymentReference_det, codigoPostal_det, mcc_det, fecha1_det, fecha_det, fechaExpiracion1_det, fechaExpiracion_det1, fechaExpiracion_det   
		
		def idUsuario_det
		def idComprobante_det
		def importe_det
		def devolucion_det
		def importeComision_det
		def comision_det
		def devolucionParcial_det
		
		concepto_det			=	select.get('DAC_CONCEPTO')
		
		if (select.get('DAC_USUARIO') != null) {
			idUsuario_det	=	select.get('DAC_USUARIO')
		}else {
			idUsuario_det	=	0
		}
		
		println select.get('DAC_COMPROBANTE')
		if (select.get('DAC_COMPROBANTE')!= null) {
			idComprobante_det	=	select.get('DAC_COMPROBANTE')
		}else {
			idComprobante_det	=	0
		}
		
		importe_det				=	select.get('DAC_IMPORTE')
		devolucion_det			=	select.get('DAC_DEVOLUCION')
		if(select.get('DAC_IMPORTE_COMISION').toString() != "null") {
			importeComision_det	=	select.get('DAC_IMPORTE_COMISION')
		}else {
			importeComision_det	=	0.0
		}
		if(select.get('DAC_COMISION').toString() != "null") {
			comision_det		=	select.get('DAC_COMISION')
		}else {
			comision_det		=	0.0
		}
		idOperacionOriginal_det	=	select.get('DAC_DEVOLUCION_ID')
		if(select.get('DAC_INTERCHANGE_CP').toString() != "null") {
			codigoPostal_det	=	select.get('DAC_INTERCHANGE_CP')
		}else {
			codigoPostal_det	=	''
		}
		if(select.get('DAC_INTERCHANGE_MCC').toString() != "null") {
			mcc_det				=	select.get('DAC_INTERCHANGE_MCC')
		}else {
			mcc_det				=	''
		}
		if(select.get('DAC_DEVOLUCION_PARCIAL').toString() != "null") {
			devolucionParcial_det	=	select.get('DAC_DEVOLUCION_PARCIAL')		
		}else {
			devolucionParcial_det	=	false
		}
		fecha1_det						=	select.get('DAC_ADD_DT')
		fecha_det 						=	fecha1_det.replaceAll(' -', '-')
		fechaExpiracion1_det			=	select.get('DAC_FECHA_EXPIRACION')
		fechaExpiracion_det1			=	fechaExpiracion1_det.replaceAll(' -', '-')
		String[] fechaExpiracion_det2	=	fechaExpiracion_det1.split(" ")
		fechaExpiracion_det				=	fechaExpiracion_det2[0]
		
		println fechaExpiracion_det
				
		Map detalle = [:]
		
		detalle = [
			('concepto'):				concepto_det,
			('idUsuario'):				idUsuario_det,
			('idComprobante'):			idComprobante_det,
			('importe'):				importe_det,
			('devolucion'):				devolucion_det,
			('importeComision'): 		importeComision_det,		
			('comision'):				comision_det,
			('idOperacionOriginal'):	idOperacionOriginal_det,
			('codigoPostal'):			codigoPostal_det,
			('mcc'):					mcc_det,
			('devolucionParcial'):		devolucionParcial_det,
			('fecha'):					fecha_det,
			('fechaExpiracion'):		fechaExpiracion_det
			]
			
		String codigo_ve, titular_ve, cuit_ve
				
		titular_ve	=	select.get('DAC_CREDITO_TITULAR')
		cuit_ve		=	select.get('DAC_CREDITO_CUIT')
		
		String banco_ve, sucursal_ve, terminal_ve, alias_ve, cbu_ve, esTitular_ve, moneda_ve, tipo_ve, endpointId_ve
		
		banco_ve		=	select.get('DAC_CREDITO_BANCOCOD')
		sucursal_ve		=	select.get('DAC_CREDITO_BANCOSUC')
		
		if(select.get('DAC_CREDITO_TERMINAL')!= null) {
			terminal_ve	=	select.get('DAC_CREDITO_TERMINAL')
		}else {
			terminal_ve	=	''
		}
		
		if(select.get('DAC_CREDITO_ALIAS') !=null) {
			alias_ve	=	select.get('DAC_CREDITO_ALIAS')
		}else {
			alias_ve	=	''
		}
		
		cbu_ve			=	select.get('DAC_CREDITO_CBU')
		moneda_ve		=	select.get('DAC_CREDITO_TIPO_MONEDA')
		tipo_ve			=	select.get('DAC_CREDITO_TIPO_CUENTA')
		
		Map titulares_ve = [:]
		
		Map cuenta_ve = [:]
		
		cuenta_ve = [
			('banco'):		banco_ve,
			('sucursal'):	sucursal_ve,
			('terminal'):	terminal_ve,
			('alias'):		alias_ve,
			('cbu'):		cbu_ve,
			('moneda'):		moneda_ve,
			('tipo'):		tipo_ve
			]
		
		Map vendedor = [:]
		
		vendedor = [
			('titular'):	titular_ve,
			('cuit'):		cuit_ve,
			('cuenta'):		cuenta_ve
			]
			
		String codigo_es, descripcion_es

		Map estado = [:]
		
		Map operacion = [:]
		
		operacion = [
			('id'):				id,
			('comprador'):		comprador,
			('detalle'):		detalle,
			('vendedor'):		vendedor,
			('tipo'):			tipo,
			('loteId'):			loteId,
			('fechaNegocio'):	fechaNegocio	
			]
		
		String reglas
		Integer puntaje
		
		if (select.get('DAC_SCORING1')!=null) {
			puntaje	=	select.get('DAC_SCORING1')
		}else {
			puntaje	=	0
		}
		
		if (select.get('DAC_REGLAS')!=null) {
			reglas	=	select.get('DAC_REGLAS')
		}else {
			reglas	=	''
		}
		
		Map evaluacion = [:]
		
		evaluacion = [
			('puntaje'):	puntaje,
			('reglas'):		reglas
			]	
			
		def preautorizado
		
		String codigo_re, descripcion_re
		
		Map dato_db = [:]
		
		dato_db.operacion		= 	operacion
		dato_db.evaluacion		= 	evaluacion
		dato_db.preautorizado	=	preautorizado	
		
		response.operacion.comprador.remove('codigo')
		response.operacion.comprador.remove('esTitular')
		response.operacion.comprador.remove('titulares')
		response.operacion.comprador.cuenta.remove('endpointId')
		response.operacion.comprador.remove('estadoComprador')
		response.operacion.detalle.remove('moneda')
		response.operacion.detalle.remove('descripcion')
		response.operacion.detalle.remove('paymentReference')
		response.operacion.vendedor.cuenta.remove('esTitular')
		response.operacion.vendedor.cuenta.remove('titulares')
		response.operacion.vendedor.cuenta.remove('endpointId')
		response.operacion.remove('estado')
		response.operacion.remove('garantiaOk')
		response.remove('preautorizado')
		response.remove('respuesta')
		
		String	fecha1response, fecha_response, fechaExpiracion1response, fechaExpiracionExpiracion1, fechaExpiracionExpiracion, fechaNegocioresponse
		
		fecha1response 					= 	response.operacion.detalle.fecha
		fecha_response 					= 	fecha1response.replaceAll('T', ' ')
		fechaExpiracion1response		= 	response.operacion.detalle.fechaExpiracion
		fechaExpiracionExpiracion1 		= 	fechaExpiracion1response.replaceAll('T', ' ')
		String[] fechaExpiracion2		=	fechaExpiracionExpiracion1.split(" ")
		fechaExpiracionExpiracion		=	fechaExpiracion2[0]
		String[] fechaNegocio1response 	= 	response.operacion.fechaNegocio.split("T")
		fechaNegocioresponse			= 	fechaNegocio1response[0]
				
		response.operacion.detalle.fecha				=	fecha_response
		response.operacion.detalle.fechaExpiracion		=	fechaExpiracionExpiracion
		response.operacion.fechaNegocio					=	fechaNegocioresponse
	
		errores = coelsa.Util.validar(response, dato_db)
		respuesta = [
				db:[
					querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$id_hash\'",
					selectresponse: select
					],
					errores: errores
				]
	}else {
		errores = 'Response: Consulta sin resultados. '
		respuesta = [
						db: [
							querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$id_hash\'",
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


