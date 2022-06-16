import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import groovy.json.JsonSlurper
import com.kms.katalon.core.util.KeywordUtil 

def jsonSlurper = new JsonSlurper()

Map Body = GlobalVariable.Body
String Accion = GlobalVariable.Accion
Map respuesta = [
	db:[:],
	errores:[]
	]
Map select = [:]

if (response != null) {
	
	def codigo = response.respuesta.codigo
	
	switch (Accion) {
		case 'debin':
			switch (codigo) {
				case '00':
								
					select = CustomKeywords.'sql.DML.select'('DEBIN', '*', 'DEBIN_ACTIVAS', 'DAC_ID_HASH =\''+response.debin.id.toUpperCase()+'\'', '')[0]
					
					def fecha1bd = select.get('DAC_ADD_DT').toString()
					def dac_add_dt = fecha1bd.replaceAll(' -', '-')			
					
					def fecha2bd = select.get('DAC_ADD_DT').toString()
					def dac_fecha_expiracion = fecha2bd.replaceAll(' -', '-')
					
					Map dato_db = [:]
					
					dato_db.debin = [
										('id'):select.get('DAC_ID_HASH'),
										('addDt'):dac_add_dt,
										('fechaExpiracion'):dac_fecha_expiracion,
										('puntaje'):select.get('DAC_SCORING1'),
										('reglas'):select.get('DAC_REGLAS')
									]
					
					def fecha1response = response.debin.addDt.toString()
					def addDTB = fecha1response.replaceAll('T', '')
					
					def fecha2response = response.debin.fechaExpiracion.toString()
					def fechaExpiracionB = fecha2response.replaceAll('T', '')
					
					Map response1 = [:]
					
					response1 = [
									('codigo'):response.debin.codigo,
									('descripcion'):response.debin.estado.descripcion,
									('addDT'):addDTB,
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
							
					println respuesta
																	
				break
				
				case '44':	
				/*
				
					def mensaje = response.respuesta.descripcion
					
					if (mensaje=='CBU VENDEDOR NO EXISTE') { 
						
						def cbu_body = Body.operacion.vendedor.cbu
						
						select = sql.DML.select('DEBIN', '*', 'DEBIN_VENDEDOR', 'DVE_CBU =\''+cbu_body.toUpperCase()+'\'', '')[0] 	
						
						def bco_cod_bd = select.get('DVE_BCO_COD')
	
						select = sql.DML.select('ccbu', '*', 'CBU_'+bco_cod_bd+'', 'CBU_CBU=\''+cbu_body.toUpperCase()+'\'', '')[0]
						
					}else {
						
						def cbu_body = Body.operacion.comprador.cuenta.cbu
						
						select = sql.DML.select('DEBIN', '*', 'DEBIN_COMPRADORES', 'DCL_CBU =\''+cbu_body.toUpperCase()+'\'', '')[0]
						
						def bco_cod_bd = select.get('DCL_BCO_COD')
	
						select = sql.DML.select('ccbu', '*', 'CBU_'+bco_cod_bd+'', 'CBU_CBU=\''+cbu_body.toUpperCase()+'\'', '')[0]
						
					}
					*/
				break
				
				case '99':
				
					def ori_trx_id_body = Body.objeto.ori_trx_id	 
					
					select = sql.DML.select('DEBIN', 'TOP 1 *', 'DEBIN_ACTIVAS', 'DAC_ORI_TRX_ID ='+ori_trx_id_body+'', '')[0]
					
					def dac_tipo_bd = select.get('DAC_TIPO')
					def dac_id_hash = select.get('DAC_ID_HASH')
					
				break
			}
						
		break
		
		/*case 'qrdebin':
					
		break
		
		case 'Responder':
		break
		
		case 'ISO':
		break*/
		
	}
	
	return respuesta

}


