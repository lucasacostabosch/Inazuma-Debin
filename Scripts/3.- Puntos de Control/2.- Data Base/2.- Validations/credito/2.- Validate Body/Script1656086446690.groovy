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
	
	//println response.objeto.id
	
	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*, DATEDIFF (Minute, DAC_ADD_DT, DAC_FECHA_EXPIRACION) as TIEMPOEXPIRACION', 'DEBIN_ACTIVAS', 'DAC_ID_HASH =\''+response.objeto.id+'\'', '')[0]
			
	if(select != null) {
		
		Map objeto = [
			('tipo'):				select.get('DAC_TIPO'),
			('ori_trx_id'):			select.get('DAC_ORI_TRX_ID')
			]
		
		String cbvu, cuit, banco, terminal
		if(Body.credito.banco == "000") {
			cuit = 	select.get('DAC_CREDITO_CVU_CUIT')
			cbvu = 	select.get('DAC_CREDITO_CVU')
			banco = "000"
		}else {
			cuit = 	select.get('DAC_CREDITO_CUIT')
			cbvu = 	select.get('DAC_CREDITO_CBU')
			banco = select.get('DAC_CREDITO_BANCOCOD')
		}
		
		Map credito = [
			('cuit'):					cuit,
			('banco'):					banco,
			('sucursal'):				select.get('DAC_CREDITO_BANCOSUC'),
			('cuenta'): [
				('cbu'):				cbvu
				],
			('titular'):				select.get('DAC_CREDITO_CVU_TITULAR')
			]
		
		String cuitdebito, cbvudebito
		if(Body.debito.cuenta.cbu.substring(0, 3) == "000") {
			cuitdebito = select.get('DAC_DEBITO_CVU_CUIT')
			cbvudebito = select.get('DAC_DEBITO_CVU')
		}else {
			cuitdebito = select.get('DAC_DEBITO_CUIT')
			cbvudebito = select.get('DAC_DEBITO_CBU')
		}
			
		Map debito = [
			('cuit'):					cuitdebito,
			('banco'):					select.get('DAC_DEBITO_BANCOCOD'),
			('sucursal'):				select.get('DAC_DEBITO_BANCOSUC'),
			('cuenta'): [
				('cbu'):				cbvudebito
				],
			('titular'):				select.get('DAC_DEBITO_TITULAR')	
			]
			
		String concepto, idUsuario, idComprobante, mismoTitular, ori_trx, ori_terminal, ori_adicional
		
		concepto = 			select.get('DAC_CONCEPTO')
		idUsuario = 		select.get('DAC_USUARIO')
		idComprobante = 	select.get('DAC_COMPROBANTE')
		mismoTitular = 		select.get('DAC_MISMO_TITULAR')
		ori_trx = 			select.get('DAC_ORI_TRX')
		ori_terminal = 		select.get('DAC_ORI_TERMINAL')
		ori_adicional = 	select.get('DAC_ORI_ADICIONAL')
		//descripcion =		select.get('???')	
		
		Map importe = [
			//('moneda'):				select.get('DAC_CREDITO_TIPO_MONEDA'),
			('importe'):			select.get('DAC_IMPORTE')
			]
			
		Map ubicacion = [
			('lat'):			select.get('DAC_LATITUD'),
			('lng'):			select.get('DAC_LONGITUD'),
			('precision'):		select.get('DAC_PRECISION')
			]
			
		Map datosGenerador = [
			('ipCliente'):			select.get('DAC_IP'),
			('tipoDispositivo'):	select.get('DAC_DISPOSITIVO'),
			('plataforma'):			select.get('DAC_PLATAFORMA'),
			('imsi'):				select.get('DAC_IMSI'),
			('imei'):				select.get('DAC_IMEI'),
			('ubicacion'):			ubicacion
			]
		
		def tiempoExpiracion = select.get('TIEMPOEXPIRACION')
		def moneda
		
		if(select.get('DAC_CREDITO_TIPO_MONEDA') == "str") {
			moneda = "string"
		}else {
			moneda = select.get('DAC_CREDITO_TIPO_MONEDA')
		}
		
		// Normalizar los datos del generador
		if(datosGenerador.tipoDispositivo	== "  ")	datosGenerador.tipoDispositivo = ""
		if(datosGenerador.plataforma		== "  ")	datosGenerador.plataforma = ""
		if(datosGenerador.imsi == "               ")	datosGenerador.imsi = ""
		if(datosGenerador.imei == "               ")	datosGenerador.imei = ""
				
		/*Map operacion = [:]
		objeto			= objeto
		credito			= credito
		debito			= debito
		detalle 			= detalle*/	
		
		Map credin = [:]
		credin.objeto				= objeto
		credin.debito				= debito
		credin.credito				= credito		
		credin.concepto				= concepto
		credin.idUsuario			= idUsuario
		credin.idComprobante 		= idComprobante
		credin.importe				= importe
		credin.mismoTitular			= mismoTitular
		credin.ori_trx				= ori_trx
		credin.ori_terminal			= ori_terminal
		credin.ori_adicional		= ori_adicional
		credin.datosGenerador		= datosGenerador	
		
		//TODO
		// Campos que faltan definir
		Body.importe.remove('moneda')
		Body.remove('descripción')

		println credin
		
		errores = coelsa.Util.validar(credin, Body)
		
		respuesta = [
				db: [
					querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$response.objeto.id\'",
					selectbody:	select
					],
				errores:	errores
			]

	}
		
}

return respuesta
