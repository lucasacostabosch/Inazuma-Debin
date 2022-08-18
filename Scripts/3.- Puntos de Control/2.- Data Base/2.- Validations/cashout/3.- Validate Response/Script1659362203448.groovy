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
		
	def id_hash = response.objeto.id
	
	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*, DATEDIFF (Minute, DAC_ADD_DT, DAC_FECHA_EXPIRACION) as TIEMPOEXPIRACION', 'DEBIN_ACTIVAS', 'DAC_ID_HASH =\''+id_hash+'\'', '')[0]
	
	if (select != null) {
	
		//// 	Crédito		////
		String cuit, banco, sucursal, cbu
		
		cuit 		=	select.get('DAC_CREDITO_CUIT')
		banco	 	=	select.get('DAC_CREDITO_BANCOCOD')
		sucursal 	= 	select.get('DAC_CREDITO_BANCOSUC')
		cbu 		=	select.get('DAC_CREDITO_CBU')
		
		Map cuenta = [
			('cbu'):		cbu
			]
		
		Map credito = [
			('cuit'):		cuit,
			('banco'):		banco,
			('sucursal'):	sucursal,
			('cuenta'):		cuenta
			]
		
		////	Importe		////
		String moneda, importe1
		
		moneda 		=	select.get('DAC_CREDITO_TIPO_MONEDA')
		//Hay que revisar importe
		importe1	=	select.get('DAC_IMPORTE')
		
		Map importe = [
			('moneda'):		moneda,
			('importe'):	importe1
			]
						
		String fechaHoraEjecucion, fechaNegocio, dest_trx, dest_terminal, dest_adicional, dest_ori_trx_id, tipo, id, codigo, descripcion
		 		
		//fechaHoraEjecucion 	= 	select.get('') Hay que evaluar
		fechaNegocio		= 	select.get('DAC_FECHA_NEGOCIO') 
		dest_trx			=	select.get('DAC_ORI_TRX')
		
		println select.get('DAC_CREDITO_TERMINAL').toString()
				
		if(select.get('DAC_CREDITO_TERMINAL').toString() == 'null' || select.get('DAC_CREDITO_TERMINAL').toString() == null || select.get('DAC_CREDITO_TERMINAL').toString() == '') {
			dest_terminal		=	null
		}else {
			dest_terminal		=	select.get('DAC_CREDITO_TERMINAL')
		}
		
		dest_adicional		=	select.get('DAC_ORI_ADICIONAL')
		dest_ori_trx_id		= 	select.get('DAC_ORI_TRX_ID')
		
		println dest_ori_trx_id.getProperties()
		
		////	Objeto	////
		codigo 		= 	select.get('DAC_ESTADO_ID')
		//descripcion =	select.get('')
		
		Map estado = [
			('codigo'):			codigo,
			//('descripcion'):	descripcion
			]
			
		tipo 	=	select.get('DAC_TIPO')
		id 		= 	select.get('DAC_ID_HASH')
		
		Map objeto = [
			('tipo'):		tipo,
			('id'):			id,
			//('estado'):		estado
			]
		
		////	Evaluación		////	
		String reglas
		Integer puntaje
		
		if (select.get('DAC_REGLAS') == 'null' || select.get('DAC_REGLAS') == null) {
			reglas 	= 	''
		}else {
			reglas 	= 	select.get('DAC_REGLAS')
		}
		
		if (select.get('DAC_SCORING1') == 'null' || select.get('DAC_SCORING1') == null) {
			puntaje = 	0
		}else {	
			puntaje	=	select.get('DAC_SCORING1')
		}
		
		Map evaluacion = [
			('puntaje'):	puntaje,
			('reglas'):		reglas
			]
						
		Map dato_db = [:]
		
		dato_db.credito				=	credito
		dato_db.importe				= 	importe
		//dato_db.fechaNegocio		=	fechaNegocio
		dato_db.dest_trx			= 	dest_trx
		dato_db.dest_terminal		=	dest_terminal
		dato_db.dest_adicional		=	dest_adicional
		dato_db.dest_ori_trx_id		=	dest_ori_trx_id
		dato_db.objeto				=	objeto
		dato_db.evaluacion			=	evaluacion		
		
		response.remove('respuesta')
		response.remove('fechaHoraEjecucion')
		response.remove('fechaNegocio')
		response.objeto.estado.remove('descripcion')
		response.objeto.remove('estado')
		
		String importeBody = response.importe.importe		
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
				for (i = y; i < 4; i++) {
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
		
		response.importe.importe = importeB
		response.dest_ori_trx_id = response.dest_ori_trx_id.toString()

		errores = coelsa.Util.validar(response, dato_db)
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


