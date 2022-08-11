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
Map respuesta1

if (response != null) {
	
	def id_hash = response.objeto.id
	
	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*, DATEDIFF (Minute, DAC_ADD_DT, DAC_FECHA_EXPIRACION) as TIEMPOEXPIRACION', 'DEBIN_ACTIVAS', 'DAC_ID_HASH =\''+id_hash+'\'', '')[0]
	
	if(select != null) {
										
		String 	tipo, endpoint_id, ori_trx_id 
			
		tipo		=	select.get('DAC_TIPO')
		ori_trx_id	=	select.get('DAC_ORI_TRX_ID')
		
		Map objeto = [
			('tipo'):			tipo,
			('ori_trx_id'):		ori_trx_id
			]
				
		String cuit_credito, banco_credito, sucursal_credito, cbu_credito, titular_credito
		
		////	Hay que revisar esta decisión	////
		if(Body.credito.cuenta.cbu.substring(0, 3) == "000") {
			cuit_credito 		= 	select.get('DAC_CREDITO_CVU_CUIT')
			cbu_credito 		= 	select.get('DAC_CREDITO_CVU')
			banco_credito 		= 	"000"
			sucursal_credito	=	select.get('DAC_CREDITO_CVU_PSP')
		}else {
			cuit_credito 		= 	select.get('DAC_CREDITO_CUIT')
			cbu_credito 		= 	select.get('DAC_CREDITO_CBU')
			banco_credito 		= 	select.get('DAC_CREDITO_BANCOCOD')
			sucursal_credito	=	select.get('DAC_CREDITO_BANCOSUC')
		}		
		
		Map cuenta_credito = [
			('cbu'):	cbu_credito
			]
		
		titular_credito		=	select.get('DAC_CREDITO_TITULAR')
					
		Map credito = [
			('cuit'):		cuit_credito,
			('banco'):		banco_credito,
			('sucursal'):	sucursal_credito,
			('cuenta'):		cuenta_credito
			]
		
		String cuit_debito, banco_debito, sucursal_debito, cbu_debito, titular_debito
		
		if(Body.debito.cuenta.cbu.substring(0, 3) == "000") {
			cuit_debito 		= 	select.get('DAC_DEBITO_CVU_CUIT')
			cbu_debito 			= 	select.get('DAC_DEBITO_CVU')
			banco_debito		=	"000"
			sucursal_debito		=	select.get('DAC_DEBITO_CVU_PSP')
		}else {
			cuit_debito 		= 	select.get('DAC_DEBITO_CUIT')
			cbu_debito 			= 	select.get('DAC_DEBITO_CBU')
			banco_debito		= 	select.get('DAC_DEBITO_BANCOCOD')
			sucursal_debito		=	select.get('DAC_DEBITO_BANCOSUC')
		}
		
		Map cuenta_debito = [
			('cbu'):	cbu_debito
			]
		
		//banco_debito		=	select.get('DAC_DEBITO_BANCOCOD')
		
		titular_debito		=	select.get('DAC_DEBITO_TITULAR')
		
		Map debito = [
			('cuit'):		cuit_debito,
			('banco'):		banco_debito,
			('sucursal'):	sucursal_debito,
			('cuenta'):		cuenta_debito
			]
			
		String concepto, descripcion, moneda
		Integer idUsuario, idComprobante
		
		String importeI = select.get('DAC_IMPORTE')
		String[] s = importeI.split("\\.")
		String[] dec = s[1]
		Integer a = s[1].length()
		String decimal = ''
			
		for(i = 0; i < a; i++) {
			if(dec[i]!=0)
				decimal += dec[i]
		}
		
		if(decimal=='0000') {
			importeI = s[0]
		}else {
			importeI = s[0]+"."+decimal
		}
				
		concepto			=	select.get('DAC_CONCEPTO')
		moneda				=	select.get('DAC_CREDITO_TIPO_MONEDA')
		//descripcion			=	select.get('')
		idUsuario			=	select.get('DAC_USUARIO')
		idComprobante		=	select.get('DAC_COMPROBANTE')
		
		Map importe = [
			('moneda'):		moneda,
			('importe'):	importeI
			]
			
		Integer lat, lng, precision
		
		lat			=	select.get('DAC_LATITUD')
		lng			=	select.get('DAC_LONGITUD')
		precision	=	select.get('DAC_PRECISION')

		Map ubicacion = [
			('lat'):		lat,
			('lng'):		lng,
			('precision'):	precision
			]
		
		String ori_trx, ipCliente, ori_terminal, ori_adicional, tipoDispositivo, plataforma, imsi, imei	
		Integer mismoTitular, tiempoExpiracion
			
		ori_trx				=	select.get('DAC_ORI_TRX')
		mismoTitular		=	select.get('DAC_MISMO_TITULAR')
		tiempoExpiracion	=	select.get('TIEMPOEXPIRACION')
		
		if(select.get('DAC_ORI_TERMINAL')=="") {
			ori_terminal		=	null
		}else {
			ori_terminal		=	select.get('DAC_ORI_TERMINAL')
		}
		
		
		ori_adicional		=	select.get('DAC_ORI_ADICIONAL')
		
		ipCliente			=	select.get('DAC_IP')
		tipoDispositivo		=	select.get('DAC_DISPOSITIVO')
		plataforma			=	select.get('DAC_PLATAFORMA')
		imsi				=	select.get('DAC_IMSI')
		imei				=	select.get('DAC_IMEI')
				
		Map datosGenerador = [
			('ipCliente'):			ipCliente,
			('tipoDispositivo'):	tipoDispositivo,
			('plataforma'):			plataforma,
			('imsi'):				imsi,
			('imei'):				imei, 
			('ubicacion'):			ubicacion
			]
					
		// Normalizar los datos del generador
		if(datosGenerador.tipoDispositivo	== "  ")	datosGenerador.tipoDispositivo = ""
		if(datosGenerador.plataforma		== "  ")	datosGenerador.plataforma = ""
		if(datosGenerador.imsi == "               ")	datosGenerador.imsi = ""
		if(datosGenerador.imei == "               ")	datosGenerador.imei = ""
							
		Map cashout = [:]
		cashout.objeto				= 	objeto
		cashout.credito				= 	credito
		cashout.debito				= 	debito
		cashout.concepto			= 	concepto
		cashout.idUsuario			=	idUsuario
		cashout.idComprobante		=	idComprobante
		cashout.importe				=	importe
		cashout.mismoTitular		= 	mismoTitular
		cashout.tiempoExpiracion	=	tiempoExpiracion
		cashout.ori_trx				=	ori_trx
		cashout.ori_terminal		=	ori_terminal
		cashout.ori_adicional		= 	ori_adicional
		cashout.datosGenerador		=	datosGenerador
					
		//int ori_trx_id_b = Body.objeto.ori_trx_id.toInteger()
		
		String importeBody = Body.importe.importe		//Valor del importe enviado desde el body
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
		
		def ori_trx_id_body = Body.objeto.ori_trx_id.toString()
		def ori_trx_id_b
		
		if (ori_trx_id_body.substring(0, 2) == "00") {
			ori_trx_id_b = Body.objeto.ori_trx_id.toInteger()
		}else {
			ori_trx_id_body.substring(0, 3)
			ori_trx_id_b = Body.objeto.ori_trx_id.toString()
		}
		
		//TODO
		Body.objeto.ori_trx_id = ori_trx_id_b
		Body.importe.importe = importeB
		Body.remove('descripcion')
		Body.credito.remove('titular')
		Body.debito.remove('titular')
		
		println cashout
		println Body
		
		errores = coelsa.Util.validar(cashout, Body)
		//errores = ''
		respuesta1 = [
				db: [
					querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_QR_ID_TRX =\'$id_hash\'",
					selectbody:	select
					],
				errores:	errores
			]	
		
			
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
