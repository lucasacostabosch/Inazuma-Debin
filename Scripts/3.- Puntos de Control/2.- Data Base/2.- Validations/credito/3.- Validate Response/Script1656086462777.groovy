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
							
	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*', 'DEBIN_ACTIVAS', 'DAC_ID_HASH =\''+response.objeto.id.toUpperCase()+'\'', '')[0]
	
	if (select != null) {
	
		String cuitDB, bancoDB, sucursalDB, cbvuDB, monedaDB, importeDB, fechaHoraEjecucionDB, fechaNegocioDB, dest_trxDB, dest_terminalDB, dest_ori_trx_idDB, tipoDB, idDB, codigoDB, descripcionDB, puntajeDB, reglasDB
		
		cuitDB = 					select.get('DAC_CREDITO_CUIT').toString()
		bancoDB = 					select.get('DAC_CREDITO_BANCOCOD').toString()
		sucursalDB = 				select.get('DAC_CREDITO_BANCOSUC').toString()
		cbvuDB = 					select.get('DAC_CREDITO_CBU').toString()
		monedaDB = 					select.get('DAC_CREDITO_TIPO_MONEDA').toString()
		importeDB = 				select.get('DAC_IMPORTE').toString()
		//fechaHoraEjecucion = 		select.get('DAC_IMPORTE')
		fechaNegocioDB = 			select.get('DAC_FECHA_NEGOCIO').toString()
		//dest_trx = 				select.get('DAC_IMPORTE')
		//dest_terminal = 			select.get('DAC_IMPORTE')
		dest_ori_trx_idDB = 		select.get('DAC_ORI_TRX').toString()
		tipoDB = 					select.get('DAC_TIPO').toString()
		idDB = 						select.get('DAC_ID_HASH').toString()
		//codigo = 					select.get('DAC_IMPORTE')
		//descripcion = 			select.get('DAC_IMPORTE')
		
		if(select.get('DAC_SCORING1') == null || select.get('DAC_SCORING1') == 0) {
			puntajeDB = 0
		}else {
			puntajeDB = select.get('DAC_SCORING1').toString()
		}
		
		if(select.get('DAC_REGLAS') == null || select.get('DAC_REGLAS') == "") {
			reglasDB = ""
		}else {
			reglasDB = select.get('DAC_REGLAS').toString()
		}
		
		fechaNegocioDB = fechaNegocioDB.replaceAll(' -', '-')
		
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
				
		String cuitR, bancoR, sucursalR, cbvuR, monedaR, importeR, fechaHoraEjecucionR, fechaNegocioR, dest_trxR, dest_terminalR, dest_adicionalR, dest_ori_trx_idR, tipoR, idR, codigoR, descripcionR, puntajeR, reglasR
							
		cuitR = 					response.credito.cuit.toString()
		bancoR = 					response.credito.banco.toString()
		sucursalR = 				response.credito.sucursal.toString()
		cbvuR = 					response.credito.cuenta.cbu.toString()
		monedaR = 					response.importe.moneda.toString()
		importeR = 					response.importe.importe.toString()
		//fechaHoraEjecucionR = 	select.get('DAC_IMPORTE')
		fechaNegocioR = 			response.fechaNegocio.toString()
		//dest_trxR = 				select.get('DAC_IMPORTE')
		//dest_terminalR = 			select.get('DAC_IMPORTE')
		dest_ori_trx_idR = 			response.dest_ori_trx_id.toString()
		tipoR = 					response.objeto.tipo.toString()
		idR = 						response.objeto.id.toString()
		//codigoR = 				select.get('DAC_IMPORTE')
		//descripcionR = 			select.get('DAC_IMPORTE')
		puntajeR = 					response.evaluacion.puntaje.toString()
		reglasR = 					response.evaluacion.reglas.toString()
		
		fechaNegocioR = fechaNegocioR.replaceAll('T', ' ')
			
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
}
	
return respuesta

