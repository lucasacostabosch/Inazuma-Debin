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

/*if (response != null) {
	
	def codigo = response.respuesta.codigo
							
	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*', 'DEBIN_ACTIVAS', 'DAC_ID_HASH =\''+response.objeto.id.toUpperCase()+'\'', '')[0]
	
	if (select != null) {
	
		String cuitDB, bancoDB, sucursalDB, cbvuDB, monedaDB, fechaHoraEjecucionDB, fechaNegocioDB, dest_trxDB, dest_terminalDB, tipoDB, idDB, codigoDB, descripcionDB, reglasDB
		Integer importeDB, dest_ori_trx_idDB, puntajeDB
		
		cuitDB = 					select.get('DAC_CREDITO_CUIT')
		bancoDB = 					select.get('DAC_CREDITO_BANCOCOD')
		sucursalDB = 				select.get('DAC_CREDITO_BANCOSUC')
		cbvuDB = 					select.get('DAC_CREDITO_CBU')
		monedaDB = 					select.get('DAC_CREDITO_TIPO_MONEDA')
		// Esto es una prueba
		importeDB = 				select.get('DAC_IMPORTE')
		/////////
			//fechaHoraEjecucion = 		select.get('DAC_IMPORTE')
		fechaNegocioDB = 			select.get('DAC_FECHA_NEGOCIO')
			//dest_trx = 				select.get('DAC_IMPORTE')
			//dest_terminal = 			select.get('DAC_IMPORTE')
		dest_ori_trx_idDB = 		select.get('DAC_ORI_TRX_ID')
		tipoDB = 					select.get('DAC_TIPO')
		idDB = 						select.get('DAC_ID_HASH')
			//codigo = 					select.get('DAC_IMPORTE')
			//descripcion = 			select.get('DAC_IMPORTE')
				
		if(select.get('DAC_SCORING1') == null || select.get('DAC_SCORING1') == 0) {
			puntajeDB = 0
		}else {
			puntajeDB = select.get('DAC_SCORING1')
		}
		
		if(select.get('DAC_REGLAS') == null || select.get('DAC_REGLAS') == "") {
			reglasDB = ""
		}else {
			reglasDB = select.get('DAC_REGLAS').toString()
		}
				
		Map dato_db = [:]
	
		dato_db = [
					('cuit'):cuitDB,
					('banco'):bancoDB,
					('sucursal'):sucursalDB,
					('cbvu'):cbvuDB,
					('moneda'):monedaDB,
					('importe'):importeDB,
					('fechaNegocio'):fechaNegocioDB,
					('dest_ori_trx_id'):dest_ori_trx_idDB,
					('tipo'):tipoDB,
					('id'):idDB,
					('puntaje'):puntajeDB,
					('reglas'):reglasDB
				]	
				
		String cuitR, bancoR, sucursalR, cbvuR, monedaR, fechaHoraEjecucionR, dest_trxR, dest_terminalR, dest_adicionalR, tipoR, idR, codigoR, descripcionR, reglasR
		Integer importeR, dest_ori_trx_idR, puntajeR 
		
		def tipo = response.objeto.tipo.toString()
									
		cuitR = 					response.credito.cuit
		bancoR = 					response.credito.banco
		sucursalR = 				response.credito.sucursal
		cbvuR = 					response.credito.cuenta.cbu
		monedaR = 					response.importe.moneda
		importeR = 					response.importe.importe
			//fechaHoraEjecucionR = 	response.importe.importe
			//fechaNegocioR1 = 			response.fechaNegocio
			//dest_trxR = 				response.importe.importe
			//dest_terminalR = 			response.importe.importe
		dest_ori_trx_idR = 			response.dest_ori_trx_id
		tipoR = 					tipo.toUpperCase()
		idR = 						response.objeto.id
			//codigoR = 				response.importe.importe
			//descripcionR = 			response.importe.importe
		puntajeR = 					response.evaluacion.puntaje
		reglasR = 					response.evaluacion.reglas
		
		String fechaNegocioR
		
		if(response.fechaNegocio!='0001-01-01T00:00:00') {
			String[] fechaNegocioR1 = response.fechaNegocio.split("T")
			fechaNegocioR = fechaNegocioR1[0]
		}else {
			fechaNegocioR = null
		}	
					
		Map response1 = [:]
		
		response1 = [
						('cuit'):cuitR,
						('banco'):bancoR,
						('sucursal'):sucursalR,
						('cbvu'):cbvuR,
						('moneda'):monedaR,
						('importe'):importeR,
						('fechaNegocio'):fechaNegocioR,
						('dest_ori_trx_id'):dest_ori_trx_idR,
						('tipo'):tipoR,
						('id'):idR,
						('puntaje'):puntajeR,
						('reglas'):reglasR					
					]
						
		errores = coelsa.Util.validar(response1, dato_db)
					
		respuesta = [
				db:[
					querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$response.objeto.id\'",
					selectresponse: select
					],
					errores: errores
				]
	
	}else {
		
		errores = 'Response: Consulta sin resultados. '
		respuesta = [
						db: [
							querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$response.debin.id\'",
							selectbody:	select
							],
						errores: errores
					]
	}

}else {
	errores = 'No hubo respuesta. '
	db = ''
	respuesta = [
					db:db,
					errores: errores
				]
}*/

errores = ''
db = ''
respuesta = [
				db:db,
				errores: errores
			]
	
return respuesta

