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
	
	Map select = CustomKeywords.'sql.DML.select'('DEBIN', '*', 'DEBIN_ACTIVAS', 'DAC_ID =\''+Body.qr_id+'\'', '')[0]
	
	println Body
	println select
			
	if(select != null) {
		
		String name, transaction_reference_id, trace_id, type
		
		name					=	select.get('DAC_CREDITO_CVU_TITULAR')
		identification_number	=	select.get('DAC_CREDITO_CVU_CUIT')
		
		/*String type, number
		
		type	=	select.get('')
		number	=	select.get('')
		
		Map document = [
			('type'):	type,
			('number'):	number
			]
		*/
		
		String cbu, cvu
		
		cbu	=	select.get('DAC_CREDITO_CBU')
		cvu	=	select.get('DAC_CREDITO_CVU')
				
		Map account	=	[
			('cbu'):	cbu,
			('cvu'):	cvu
			]
			
		String 	name_wallet, cuit_wallet, bcra_id_wallet
		
		name_wallet		=	select.get('DAC_WALLET_NAME')
		cuit_wallet		=	select.get('DAC_WALLET_CUIT')
		//bcra_id_wallet	=	select.get('')
		
		Map wallet 	=	[
			('name'):		name,
			('cuit'):		cuit_wallet
			//('bcra_id'):	bcra_id_wallet
		]
		
		Map payer	=	[
			('name'):					name,
			('identification_number'):	identification_number,
			('account'):				account,
			('wallet'):					wallet
			]
		
		String 	reverse_domain_acquirer, cuit_acquirer
		
		reverse_domain_acquirer		=	select.get('DAC_ACQUIRER_REVERSE_DOMAIN')
		cuit_acquirer				=	select.get('DAC_ACQUIRER_CUIT')
		
		Map	acquirer = [
			('reverse_domain'):	reverse_domain_acquirer,
			('cuit'):			cuit_acquirer		
		]
		
		String 	soft_descriptor_merchant, cuit_merchant
		
		soft_descriptor_merchant	=	select.get('DAC_DEBITO_CVU_TITULAR')
		cuit_merchant				=	select.get('DAC_DEBITO_CVU_CUIT')
		
		Map merchant	=	[
			('soft_descriptor'):	soft_descriptor_merchant,
			('cuit'):				cuit_merchant
		]
		
		String authorization_code_ot, trace_id_ot, datetime_ot, type_ot
		
		authorization_code_ot	=	select.get('DAC_CODIGO_AUTORIZACION')
		/*trace_id_ot				=	select.get('')
		datetime_ot				=	select.get('')
		type_ot					=	select.get('')*/
		
		String value_amount, currency_amount
		
		value_amount 	=	select.get('DAC_IMPORTE')
		currency_amount =	select.get('DAC_CODIGO_AUTORIZACION')
		
		Map amount	=	[
			('value'):	value_amount,
			('type'):	type
		]
		
		Map original_transaction	=	[
			('authorization_code'):	authorization_code_ot,
			('amount'):				amount
			]
		
		String 	qr_id
		
		qr_id	= 	select.get('DAC_ID')	
			
		Map refundvalidation = [:]
		
		refundvalidation.payer					=	payer
		refundvalidation.acquirer				= 	acquirer
		refundvalidation.merchant				=	merchant
		refundvalidation.original_transaction	=	original_transaction
		refundvalidation.qr_id					=	qr_id
		
		println refundvalidation
		
		println Body
		
		Body.payer.remove('document')
		Body.original_transaction.remove('trace_id')
		Body.original_transaction.remove('datetime')
		Body.original_transaction.remove('type')
		
		errores = coelsa.Util.validar(refundvalidation, Body)
		//errores = ''
		respuesta = [
				db: [
					querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID =\'$Body.qr_id\'",
					selectbody:	select
					],
				errores:	errores
			]
	}else {
		errores = 'Request: Consulta sin resultados. '
		respuesta = [
						db: [
							querybody:	"SELECT * FROM DEBIN_ACTIVAS WHERE DAC_ID =\'$Body.qr_id\'",
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
