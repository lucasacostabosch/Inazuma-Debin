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

	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*, DATEDIFF (Minute, DAC_ADD_DT, DAC_FECHA_EXPIRACION) as TIEMPOEXPIRACION', 'DEBIN_ACTIVAS', 'DAC_ID_HASH =\''+response.objeto.id+'\'', '')[0]
			
	if(select != null) {
		
		def tipo = select.get('DAC_TIPO').toString()
		
		Map objeto = [
			('tipo'):				tipo.toUpperCase(),
			('ori_trx_id'):			select.get('DAC_ORI_TRX_ID')
			]
		
		String cbvu, cuit, banco, terminal
		if(Body.credito.banco == "000") {
			cuit = 	select.get('DAC_CREDITO_CVU_CUIT').toString()
			cbvu = 	select.get('DAC_CREDITO_CVU').toString()
			banco = "000"
		}else {
			cuit = 	select.get('DAC_CREDITO_CUIT').toString()
			cbvu = 	select.get('DAC_CREDITO_CBU').toString()
			banco = select.get('DAC_CREDITO_BANCOCOD').toString()
		}
		
		Map credito = [
			('cuit'):					cuit,
			('banco'):					banco,
			('sucursal'):				select.get('DAC_CREDITO_BANCOSUC').toString(),
			('cuenta'): [
				('cbu'):				cbvu
				],
			('titular'):				select.get('DAC_CREDITO_TITULAR').toString()
			]
		
		String cuitdebito, cbvudebito
		if(Body.debito.cuenta.cbu.substring(0, 3) == "000") {
			cuitdebito = select.get('DAC_DEBITO_CVU_CUIT').toString()
			cbvudebito = select.get('DAC_DEBITO_CVU').toString()
		}else {
			cuitdebito = select.get('DAC_DEBITO_CUIT').toString()
			cbvudebito = select.get('DAC_DEBITO_CBU').toString()
		}
			
		Map debito = [
			('cuit'):					cuitdebito,
			('banco'):					select.get('DAC_DEBITO_BANCOCOD').toString(),
			('sucursal'):				select.get('DAC_DEBITO_BANCOSUC').toString(),
			('cuenta'): [
				('cbu'):				cbvudebito
				],
			('titular'):				select.get('DAC_DEBITO_TITULAR').toString()	
			]
			
		String concepto, ori_trx, ori_terminal, ori_adicional
		Integer idUsuario, idComprobante, mismoTitular
		
		concepto = 			select.get('DAC_CONCEPTO').toString()
		idUsuario = 		select.get('DAC_USUARIO')
		idComprobante = 	select.get('DAC_COMPROBANTE')
		mismoTitular = 		select.get('DAC_MISMO_TITULAR')
		ori_trx = 			select.get('DAC_ORI_TRX').toString()
		ori_terminal = 		select.get('DAC_ORI_TERMINAL').toString()
		ori_adicional = 	select.get('DAC_ORI_ADICIONAL').toString()
		//descripcion =		select.get('???')	
		
		String importe = select.get('DAC_IMPORTE')	//Importe traido desde la consulta a BD
		String[] s = importe.split("\\.")			//Split para tomar los decimales  
		String[] dec = s[1]							//Valores de los decimales del importe
		Integer a = s[1].length()					//Cantidad de caracteres en los decimales
		String decimal = ''							//Variable creada para inicializar los decimales 
			
		for(i = 0; i < a; i++) {					//Bucle para recorrer los decimales
			if(dec[i]!=0)							//Validacion si el digito decimal es diferente a 0
				decimal += dec[i] 					//Variable que concatena los digitos decamles dentro del bucle
		}
		
		if(decimal=='0000') {
			importe = s[0]
		}else {
			importe = s[0]+"."+decimal
		}
		
		Map importe = [
			//('moneda'):			select.get('DAC_CREDITO_TIPO_MONEDA'),
			('importe'):			importe
			]
			
		Integer lat, lng, precision  
		
		lat = 			select.get('DAC_LATITUD')
		lng = 			select.get('DAC_LONGITUD')	
		precision = 	select.get('DAC_PRECISION')
			
		Map ubicacion = [
			('lat'):			lat,
			('lng'):			lng,
			('precision'):		precision
			]
			
		Map datosGenerador = [
			('ipCliente'):			select.get('DAC_IP').toString(),
			('tipoDispositivo'):	select.get('DAC_DISPOSITIVO').toString(),
			('plataforma'):			select.get('DAC_PLATAFORMA').toString(),
			('imsi'):				select.get('DAC_IMSI').toString(),
			('imei'):				select.get('DAC_IMEI').toString(),
			('ubicacion'):			ubicacion
			]
		
		def tiempoExpiracion = select.get('TIEMPOEXPIRACION').toString()
		def moneda
		
		if(select.get('DAC_CREDITO_TIPO_MONEDA') == "str") {
			moneda = "string"
		}else {
			moneda = select.get('DAC_CREDITO_TIPO_MONEDA').toString()
		}
		
		// Normalizar los datos del generador
		if(datosGenerador.tipoDispositivo	== "  ")	datosGenerador.tipoDispositivo = ""
		if(datosGenerador.plataforma		== "  ")	datosGenerador.plataforma = ""
		if(datosGenerador.imsi == "               ")	datosGenerador.imsi = ""
		if(datosGenerador.imei == "               ")	datosGenerador.imei = ""
						
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
		
		Body.operacion.detalle.importe = Body.operacion.detalle.importe.toString()
		
		errores = coelsa.Util.validar(credin, Body)
		
		respuesta = [
				db: [
					querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$response.objeto.id\'",
					selectbody:	select
					],
				errores:	errores
			]

	}else {
		errores = 'Request: Consulta sin resultados. '
		respuesta = [
						db: [
							querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID_HASH =\'$response.debin.id\'",
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
