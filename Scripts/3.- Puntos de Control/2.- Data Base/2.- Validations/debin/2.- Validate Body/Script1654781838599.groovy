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

Map Body = GlobalVariable.Body
Map respuesta

if (response != null) {
	
	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*, DATEDIFF (Minute, DAC_ADD_DT, DAC_FECHA_EXPIRACION) as TIEMPOEXPIRACION', 'DEBIN_ACTIVAS', 'DAC_ID_HASH =\''+response.debin.id+'\'', '')[0]
			
	if(select != null) {
		
		Map objeto = [
			('ori_trx_id'):			select.get('DAC_ORI_TRX_ID'),
			]
			
			String cbvu, cuit, banco, terminal
			if(Body.operacion.vendedor.banco == "000") {
				cuit = "DAC_CREDITO_CVU_CUIT"
				cbvu = "DAC_CREDITO_CVU"
				banco = "000"
			}else {
				cuit = "DAC_CREDITO_CUIT"
				cbvu = "DAC_CREDITO_CBU"
				banco = select.get('DAC_CREDITO_BANCOCOD')
			}
			
			terminal = select.get('DAC_CREDITO_TERMINAL').toString()
			
			Map vendedor = [
				('cuit'):				select.get(cuit),
				('cbu'):				select.get(cbvu),
				('banco'):				banco,
				('sucursal'):			select.get('DAC_CREDITO_BANCOSUC'),
				('terminal'):			terminal
//			('prestacion'):			select.get('??')
			]
			
		if(Body.operacion.comprador.cuenta.cbu.substring(0, 3) == "000") {
			cuit = "DAC_DEBITO_CVU_CUIT"
			cbvu = "DAC_DEBITO_CVU"
		}else {
			cuit = "DAC_DEBITO_CUIT"
			cbvu = "DAC_DEBITO_CBU"
		}
		
		//Lógica para comparar alias o cbu, según sea el caso.
		
		def cbuBody 	= Body.operacion.comprador.cuenta.cbu
		def aliasBody	= Body.operacion.comprador.cuenta.alias
		
		Map datos_cuentas
				
		if(cbuBody != "" && aliasBody != "") {
			datos_cuentas = [
				('cbu'):			select.get(cbvu),
				('alias'):			select.get('DAC_DEBITO_ALIAS')
				]
		}else if(cbuBody != "" && aliasBody == ""){
			datos_cuentas = [
				('cbu'):		select.get(cbvu),
				('alias'):		""
			]
		}else if(cbuBody == "" && aliasBody != "") {
			datos_cuentas = [
				('cbu'):		"",
				('alias'):		select.get('DAC_DEBITO_ALIAS')
			]
		}
			
		Map comprador = [
			('cuit'):				select.get(cuit),
			('cuenta'): 			datos_cuentas
			]
		
		def tiempoExpiracion = select.get('TIEMPOEXPIRACION')

		Map detalle = [
			('concepto'):			select.get('DAC_CONCEPTO'),
			('idUsuario'):			select.get('DAC_USUARIO'),
			('idComprobante'):		select.get('DAC_COMPROBANTE'),
//			('moneda'):				select.get('??'),
			('importe'):			select.get('DAC_IMPORTE'),
//			('devolucion'):			select.get('DAC_DEVOLUCION'),
			('tiempoExpiracion'):	tiempoExpiracion,
//			('descripción'):		"",
			('mismoTitular'):		select.get('DAC_MISMO_TITULAR'),
			('idLote'):				select.get('DAC_LOTE_ID')
			]
		
		Map datosGenerador = [
			('ipCliente'):			select.get('DAC_IP'),
			('tipoDispositivo'):	select.get('DAC_DISPOSITIVO'),
			('plataforma'):			select.get('DAC_PLATAFORMA'),
			('imsi'):				select.get('DAC_IMSI'),
			('imei'):				select.get('DAC_IMEI'),
			('ubicacion'): [
					"lat":			select.get('DAC_LATITUD'),
					"lng":			select.get('DAC_LONGITUD'),
					"precision":	select.get('DAC_PRECISION')
				]
			]
			
		// Normalizar los datos del generador
		if(datosGenerador.tipoDispositivo	== "  ")	datosGenerador.tipoDispositivo = ""
		if(datosGenerador.plataforma		== "  ")	datosGenerador.plataforma = ""
		if(datosGenerador.imsi == "               ")	datosGenerador.imsi = ""
		if(datosGenerador.imei == "               ")	datosGenerador.imei = ""
		
		Map operacion = [:]
		operacion.vendedor			= vendedor
		operacion.comprador			= comprador
		operacion.detalle 			= detalle
		operacion.datosGenerador	= datosGenerador
		
		Map debin = [:]
		debin.objeto				= objeto
		debin.operacion				= operacion
		
		//TODO
		// Campos que faltan definir
		Body.operacion.vendedor.remove('prestacion')
		Body.operacion.detalle.remove('moneda')
		Body.operacion.detalle.remove('descripción')
		Body.operacion.detalle.remove('devolucion')

		errores = coelsa.Util.validar(debin, Body)
		
		respuesta = [
				db: [
					querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$response.debin.id\'",
					selectbody:	select
					],
				errores:	errores
			]
	}
		
}

return respuesta
