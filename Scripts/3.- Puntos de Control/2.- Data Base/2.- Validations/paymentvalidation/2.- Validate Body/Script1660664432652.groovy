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

if (response != null) {
	
	def qr_id_r = response.qr_id
	
	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*, DATEDIFF (Minute, DAC_ADD_DT, DAC_FECHA_EXPIRACION) as TIEMPOEXPIRACION', 'DEBIN_ACTIVAS', 'DAC_ID =\''+qr_id_r+'\'', '')[0]
	
	if(select != null) {
										
		String 	authorization_code 
		//Integer	qr_id, qr_raw
		
		authorization_code 		= 	select.get('DAC_CODIGO_AUTORIZACION')
		def qr_id 				= 	select.get('DAC_ID')
		def qr_raw 				= 	select.get('DAC_QR')
		
		String value, currency
		
		value 		=	select.get('DAC_IMPORTE')
		//currency 	= 	select.get('')
		
		Map amount = [
				('value'):		value
				//('currency'):	currency
			]
		
		String type, number
		
		//type 	= 	select.get('')
		number 	= 	select.get('DAC_DEBITO_CVU_CUIT')
		
		//Map document = [
			//('type'):	type,
			//('number'): number
			//]
		
		String account_cbu, account_cvu
		
		account_cbu	=	select.get('DAC_DEBITO_CBU')
		account_cvu	=	select.get('DAC_DEBITO_CVU')
		
		Map account = [
			('cbu'):	account_cbu,
			('cvu'):	account_cvu	
			]
		
		String wallet_name, wallet_cuit, wallet_bcra_id
		
		wallet_name		=	select.get('DAC_WALLET_NAME')
		wallet_cuit		=	select.get('DAC_WALLET_CUIT')
		//wallet_bcra_id	=	select.get('')
		
		Map wallet = [
			('name'):		wallet_name,
			('cuit'):		wallet_cuit
			//('bcra_id'): 	wallet_bcra_id
			]
		
		String payer_name, identification_number
		
		payer_name				=	select.get('DAC_DEBITO_CVU_TITULAR')
		identification_number	=	select.get('DAC_DEBITO_CVU_CUIT')
		
		Map payer = [
			('name'):					payer_name,
			('identification_number'):	identification_number,
			//('document'):				document,
			('account'):				account,
			('wallet'):					wallet	
			]
			
		String reverse_domain, acquirer_cuit
		
		reverse_domain	=	select.get('DAC_ACQUIRER_REVERSE_DOMAIN')
		acquirer_cuit	=	select.get('DAC_ACQUIRER_CUIT')
		
		Map acquirer = [
			('reverse_domain'):	reverse_domain,
			('cuit'):			acquirer_cuit	
			]
				
		String merchant_account_cbu, merchant_account_cvu
		
		merchant_account_cbu	=	select.get('DAC_CREDITO_CBU')
		merchant_account_cvu	=	select.get('DAC_CREDITO_CVU')
		
		Map merchantaccount = [
			('cbu'):	merchant_account_cbu,
			('cvu'):	merchant_account_cvu
			]
		
		String merchant_cuit
		
		merchant_cuit			=	select.get('DAC_CREDITO_CVU_CUIT')
		
		Map merchant = [
			('cuit'):		merchant_cuit,
			('account'):	merchantaccount
			]
							
		Map paymentvalidation = [:]
		paymentvalidation.authorization_code	= authorization_code
		paymentvalidation.qr_id					= qr_id
		paymentvalidation.qr_raw				= qr_raw
		paymentvalidation.amount 				= amount
		paymentvalidation.payer					= payer
		paymentvalidation.acquirer				= acquirer
		paymentvalidation.merchant				= merchant		
	
		//TODO		
		//Body.objeto.ori_trx_id = ori_trx_id_b
		//Body.operacion_original.remove('tipo')
		Body.amount.remove('currency')
		Body.payer.remove('document')
		//Body.payer.document.remove('type')
		//Body.payer.document.remove('number')
		Body.payer.wallet.remove('bcra_id')
		
		String valueBody 	= 	Body.amount.value		
		String valueB
		
		if(valueBody.contains(".")) {
			String[] e 	= 	valueBody.split("\\.")
			String[] r 	= 	e[1]
			String f 	= 	e[1]
			Integer y 	= 	e[1].length()
			Integer dif
			String t 	= ''
			String z 	= ''
			
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
			valueB 	= 	e[0]+'.'+t
		}else {
			valueB 	= 	valueB
		}
		
		Body.amount.value 	= 	valueB

		errores = coelsa.Util.validar(paymentvalidation, Body)				
		//errores = ''
		respuesta1 = [
				db: [
					querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID =\'$qr_id_r\'",
					selectbody:	select
					],
				errores:	errores
			]	
			
	}else {

		errores = 'Request: Consulta sin resultados. '
		respuesta1 = [
						db: [
							querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID =\'$qr_id_r\' ",
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
